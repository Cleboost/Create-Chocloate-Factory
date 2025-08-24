package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BasinRecipe.class)
public abstract class BasinRecipeMixin {

    @Inject(method = "apply*", at = @At("HEAD"), cancellable = true)
    private static void apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test, CallbackInfoReturnable<Boolean> cir) {
        IFluidHandler availableFluids = basin.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null || availableFluids == null) {
            cir.setReturnValue(false);
            return;
        }
        int fluidSlot = 0;
        while (fluidSlot < availableFluids.getTanks()) {
            if (availableFluids.getFluidInTank(fluidSlot).is(CCFFluids.CHOCOLATE.get())) break;
            fluidSlot++;
        }
        if (fluidSlot == availableFluids.getTanks() || availableFluids.getFluidInTank(fluidSlot) == null) return;

        BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel()
                .getBlockState(basin.getBlockPos()
                        .below(1)));
        if (heat != BlazeBurnerBlock.HeatLevel.NONE) return;

        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);

        if (chocolateFluid.get(CCFDataComponents.CHOCOLATE) == null) return;
        if (chocolate.hasTaste()) {
            return;
        }
        int itemSlot = 0;
        while (itemSlot < availableItems.getSlots()) {
            if (!availableItems.getStackInSlot(itemSlot).isEmpty()) break;
            itemSlot++;
        }
        if (itemSlot == availableItems.getSlots()) return;
        if (test) {
            cir.setReturnValue(true);
            return;
        }

        int amount = Math.max(1000, chocolateFluid.getAmount());
        chocolateFluid.shrink(amount);
        FluidStack fluidOutput = new FluidStack(CCFFluids.CHOCOLATE, amount);
        fluidOutput.set(CCFDataComponents.CHOCOLATE, chocolate.addTaste(availableItems.getStackInSlot(itemSlot).getItem()));
        basin.getBehaviour(SmartFluidTankBehaviour.INPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);
        basin.getBehaviour(SmartFluidTankBehaviour.OUTPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);

        availableItems.extractItem(itemSlot, 1, false);


        cir.setReturnValue(basin.acceptOutputs(List.of(), List.of(fluidOutput), test));
    }

    @Inject(method = "match", at = @At("HEAD"), cancellable = true)
    private static void match(BasinBlockEntity basin, Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        IFluidHandler availableFluids = basin.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null || availableFluids == null)
            return;
        int fluidSlot = 0;
        while (fluidSlot < availableFluids.getTanks()) {
            if (availableFluids.getFluidInTank(fluidSlot).is(CCFFluids.CHOCOLATE.get())) break;
            fluidSlot++;
        }
        if (fluidSlot == availableFluids.getTanks() || availableFluids.getFluidInTank(fluidSlot) == null) return;
        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);

        BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel()
                .getBlockState(basin.getBlockPos()
                        .below(1)));
        if (heat != BlazeBurnerBlock.HeatLevel.NONE) {
            return;
        }
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolateFluid.get(CCFDataComponents.CHOCOLATE) == null) {
            return;

        }
        if (chocolate.hasTaste()) {
            cir.setReturnValue(false);
            return;
        }
        int slotID = 0;
        while (slotID < availableItems.getSlots()) {
            if (!availableItems.getStackInSlot(slotID).isEmpty()) break;
            slotID++;
        }
        if (slotID == availableItems.getSlots()) {
            cir.setReturnValue(false);
            return;
        }
        cir.setReturnValue(true);
    }

}
