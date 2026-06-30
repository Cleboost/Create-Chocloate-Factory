package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

/**
 * Taste mixing is purely custom logic and has no backing recipe in Create's
 * recipe trie, so the basin's own recipe lookup would never find a candidate
 * to call into {@link BasinRecipeMixin}. This intercepts the basin's update
 * loop directly so taste mixing works regardless of registered recipes.
 */
@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinOperatingBlockEntityMixin extends KineticBlockEntity {

    public BasinOperatingBlockEntityMixin(net.minecraft.world.level.block.entity.BlockEntityType<?> type, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        super(type, pos, state);
    }

    @Shadow
    protected abstract Optional<BasinBlockEntity> getBasin();

    @Shadow
    protected abstract boolean isRunning();

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

        IFluidHandler availableFluids = level.getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
        IItemHandler availableItems = level.getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableFluids == null || availableItems == null)
            return;

        int fluidSlot = 0;
        while (fluidSlot < availableFluids.getTanks() && !availableFluids.getFluidInTank(fluidSlot).is(CCFFluids.CHOCOLATE.get()))
            fluidSlot++;
        if (fluidSlot == availableFluids.getTanks())
            return;

        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null || chocolate.hasTaste())
            return;

        BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(level.getBlockState(basin.getBlockPos().below(1)));
        if (heat != BlazeBurnerBlock.HeatLevel.NONE)
            return;

        int itemSlot = 0;
        while (itemSlot < availableItems.getSlots() && availableItems.getStackInSlot(itemSlot).isEmpty())
            itemSlot++;
        if (itemSlot == availableItems.getSlots())
            return;

        ItemStack tasteStack = availableItems.extractItem(itemSlot, 1, false);

        int amount = Math.max(1000, chocolateFluid.getAmount());
        chocolateFluid.shrink(amount);
        FluidStack fluidOutput = new FluidStack(CCFFluids.CHOCOLATE, amount);
        fluidOutput.set(CCFDataComponents.CHOCOLATE, chocolate.addTaste(tasteStack.getItem()));

        basin.getBehaviour(SmartFluidTankBehaviour.INPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);
        basin.getBehaviour(SmartFluidTankBehaviour.OUTPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);

        basin.acceptOutputs(List.of(), List.of(fluidOutput), false);
        basin.notifyChangeOfContents();
        sendData();

        cir.setReturnValue(true);
    }
}
