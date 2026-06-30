package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Taste mixing is purely custom logic and has no backing recipe in Create's
 * recipe trie, so the basin's own recipe lookup would never find a candidate
 * to drive the timed processing animation. This detects when taste mixing
 * should start and kicks off the basin's normal timed processing (using a
 * never-registered, in-memory marker recipe), letting the existing
 * {@link BasinRecipeMixin} perform the actual taste application once the
 * timer elapses, exactly like a real recipe would.
 */
@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinOperatingBlockEntityMixin extends KineticBlockEntity {

    private static final Recipe<?> CCF_TASTE_MIXING_MARKER = new StandardProcessingRecipe.Builder<>(
            MixingRecipe::new, CreateChocolateFactory.asResource("taste_mixing"))
            .duration(100)
            .requiresHeat(HeatCondition.NONE)
            .build();

    public BasinOperatingBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow
    protected Recipe<?> currentRecipe;

    @Shadow
    protected abstract Optional<BasinBlockEntity> getBasin();

    @Shadow
    protected abstract boolean isRunning();

    @Shadow
    public abstract void startProcessingBasin();

    @Inject(method = "updateBasin", at = @At("HEAD"), cancellable = true)
    private void ccf$tasteMix(CallbackInfoReturnable<Boolean> cir) {
        if (!isSpeedRequirementFulfilled() || getSpeed() == 0 || isRunning())
            return;
        if (level == null || level.isClientSide)
            return;

        Optional<BasinBlockEntity> basinOpt = getBasin();
        if (basinOpt.isEmpty() || !basinOpt.get().canContinueProcessing())
            return;
        BasinBlockEntity basin = basinOpt.get();

        if (!ccf$canTasteMix(basin))
            return;

        this.currentRecipe = CCF_TASTE_MIXING_MARKER;
        startProcessingBasin();
        sendData();
        cir.setReturnValue(true);
    }

    private boolean ccf$canTasteMix(BasinBlockEntity basin) {
        IFluidHandler availableFluids = level.getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
        IItemHandler availableItems = level.getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableFluids == null || availableItems == null)
            return false;

        int fluidSlot = 0;
        while (fluidSlot < availableFluids.getTanks() && !availableFluids.getFluidInTank(fluidSlot).is(CCFFluids.CHOCOLATE.get()))
            fluidSlot++;
        if (fluidSlot == availableFluids.getTanks())
            return false;

        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null || chocolate.hasTaste())
            return false;

        BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(level.getBlockState(basin.getBlockPos().below(1)));
        if (heat != BlazeBurnerBlock.HeatLevel.NONE)
            return false;

        int itemSlot = 0;
        while (itemSlot < availableItems.getSlots() && availableItems.getStackInSlot(itemSlot).isEmpty())
            itemSlot++;
        return itemSlot != availableItems.getSlots();
    }
}
