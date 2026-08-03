package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.IceCreamMixingMarker;
import fr.cleboost.createchocolatefactory.utils.Taste;
import fr.cleboost.createchocolatefactory.utils.TasteMixingMarker;
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

@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinOperatingBlockEntityMixin extends KineticBlockEntity {

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
    private void ccf$customMix(CallbackInfoReturnable<Boolean> cir) {
        if (!isSpeedRequirementFulfilled() || getSpeed() == 0 || isRunning())
            return;
        if (level == null || level.isClientSide)
            return;

        Optional<BasinBlockEntity> basinOpt = getBasin();
        if (basinOpt.isEmpty() || !basinOpt.get().canContinueProcessing())
            return;
        BasinBlockEntity basin = basinOpt.get();

        Recipe<?> marker = null;
        if (ccf$canTasteMix(basin))
            marker = TasteMixingMarker.INSTANCE;
        else if (ccf$canIceCreamMix(basin))
            marker = IceCreamMixingMarker.INSTANCE;

        if (marker == null)
            return;

        this.currentRecipe = marker;
        startProcessingBasin();
        sendData();
        cir.setReturnValue(true);
    }

    private FluidStack ccf$findChocolate(BasinBlockEntity basin) {
        IFluidHandler availableFluids = level.getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
        if (availableFluids == null)
            return FluidStack.EMPTY;
        for (int tank = 0; tank < availableFluids.getTanks(); tank++) {
            FluidStack fluid = availableFluids.getFluidInTank(tank);
            if (fluid.is(CCFFluids.CHOCOLATE.get()))
                return fluid;
        }
        return FluidStack.EMPTY;
    }

    private boolean ccf$noHeat(BasinBlockEntity basin) {
        return BasinBlockEntity.getHeatLevelOf(level.getBlockState(basin.getBlockPos().below(1)))
                == BlazeBurnerBlock.HeatLevel.NONE;
    }

    private boolean ccf$canTasteMix(BasinBlockEntity basin) {
        IItemHandler availableItems = level.getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null || !ccf$noHeat(basin))
            return false;

        FluidStack chocolateFluid = ccf$findChocolate(basin);
        Chocolate chocolate = chocolateFluid.isEmpty() ? null : chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null || chocolate.hasTaste())
            return false;

        for (int itemSlot = 0; itemSlot < availableItems.getSlots(); itemSlot++) {
            var stack = availableItems.getStackInSlot(itemSlot);
            if (!stack.isEmpty() && Taste.get(level, stack.getItem()).isPresent())
                return true;
        }
        return false;
    }

    private boolean ccf$canIceCreamMix(BasinBlockEntity basin) {
        IItemHandler availableItems = level.getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null || !ccf$noHeat(basin))
            return false;

        FluidStack chocolateFluid = ccf$findChocolate(basin);
        if (chocolateFluid.isEmpty()
                || chocolateFluid.get(CCFDataComponents.CHOCOLATE) == null
                || chocolateFluid.getAmount() < 50)
            return false;

        for (int itemSlot = 0; itemSlot < availableItems.getSlots(); itemSlot++) {
            if (availableItems.getStackInSlot(itemSlot).is(CCFItems.CRUSHED_ICE.get()))
                return true;
        }
        return false;
    }
}
