package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.IceCreamMixingMarker;
import fr.cleboost.createchocolatefactory.utils.Taste;
import fr.cleboost.createchocolatefactory.utils.TasteMixingMarker;
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

    private static final int ICE_CREAM_CHOCOLATE_COST = 50;

    @Inject(method = "apply*", at = @At("HEAD"), cancellable = true)
    private static void apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test, CallbackInfoReturnable<Boolean> cir) {
        if (recipe == TasteMixingMarker.INSTANCE) {
            ccf$applyTaste(basin, test, cir);
        } else if (recipe == IceCreamMixingMarker.INSTANCE || (recipe != null && recipe.getResultItem(basin.getLevel().registryAccess()).is(CCFItems.ICE_CREAM_BALL.get()))) {
            ccf$applyIceCream(basin, test, cir);
        }
    }

    @Inject(method = "match", at = @At("HEAD"), cancellable = true)
    private static void match(BasinBlockEntity basin, Recipe<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        if (recipe == TasteMixingMarker.INSTANCE || recipe == IceCreamMixingMarker.INSTANCE || (recipe != null && recipe.getResultItem(basin.getLevel().registryAccess()).is(CCFItems.ICE_CREAM_BALL.get()))) {
            // Reuse apply(test=true) for the match check so the two stay in sync.
            apply(basin, recipe, true, cir);
        }
    }

    // ------------------------------------------------------------------ helpers

    private static IFluidHandler ccf$fluids(BasinBlockEntity basin) {
        return basin.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);
    }

    private static IItemHandler ccf$items(BasinBlockEntity basin) {
        return basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
    }

    private static int ccf$chocolateTank(IFluidHandler fluids) {
        for (int tank = 0; tank < fluids.getTanks(); tank++)
            if (fluids.getFluidInTank(tank).is(CCFFluids.CHOCOLATE.get()))
                return tank;
        return -1;
    }

    private static boolean ccf$noHeat(BasinBlockEntity basin) {
        return BasinBlockEntity.getHeatLevelOf(basin.getLevel()
                .getBlockState(basin.getBlockPos().below(1))) == BlazeBurnerBlock.HeatLevel.NONE;
    }

    // ---------------------------------------------------------------- taste mix

    private static void ccf$applyTaste(BasinBlockEntity basin, boolean test, CallbackInfoReturnable<Boolean> cir) {
        IFluidHandler availableFluids = ccf$fluids(basin);
        IItemHandler availableItems = ccf$items(basin);
        if (availableItems == null || availableFluids == null || !ccf$noHeat(basin)) {
            cir.setReturnValue(false);
            return;
        }

        int fluidSlot = ccf$chocolateTank(availableFluids);
        if (fluidSlot == -1) {
            cir.setReturnValue(false);
            return;
        }

        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null || chocolate.hasTaste()) {
            cir.setReturnValue(false);
            return;
        }

        int itemSlot = -1;
        for (int slot = 0; slot < availableItems.getSlots(); slot++) {
            ItemStack stack = availableItems.getStackInSlot(slot);
            if (!stack.isEmpty() && Taste.get(basin.getLevel(), stack.getItem()).isPresent()) {
                itemSlot = slot;
                break;
            }
        }
        if (itemSlot == -1) {
            cir.setReturnValue(false);
            return;
        }
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

    // ------------------------------------------------------------ ice cream mix

    private static void ccf$applyIceCream(BasinBlockEntity basin, boolean test, CallbackInfoReturnable<Boolean> cir) {
        IFluidHandler availableFluids = ccf$fluids(basin);
        IItemHandler availableItems = ccf$items(basin);
        if (availableItems == null || availableFluids == null || !ccf$noHeat(basin)) {
            cir.setReturnValue(false);
            return;
        }

        int fluidSlot = ccf$chocolateTank(availableFluids);
        if (fluidSlot == -1) {
            cir.setReturnValue(false);
            return;
        }

        FluidStack chocolateFluid = availableFluids.getFluidInTank(fluidSlot);
        Chocolate chocolate = chocolateFluid.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null || chocolateFluid.getAmount() < ICE_CREAM_CHOCOLATE_COST) {
            cir.setReturnValue(false);
            return;
        }

        int itemSlot = -1;
        for (int slot = 0; slot < availableItems.getSlots(); slot++) {
            if (availableItems.getStackInSlot(slot).is(CCFItems.CRUSHED_ICE.get())) {
                itemSlot = slot;
                break;
            }
        }
        if (itemSlot == -1) {
            cir.setReturnValue(false);
            return;
        }
        if (test) {
            cir.setReturnValue(true);
            return;
        }

        // The ice cream ball inherits the exact chocolate that was mixed in.
        ItemStack ball = new ItemStack(CCFItems.ICE_CREAM_BALL.get());
        ball.set(CCFDataComponents.CHOCOLATE, chocolate);

        chocolateFluid.shrink(ICE_CREAM_CHOCOLATE_COST);
        basin.getBehaviour(SmartFluidTankBehaviour.INPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);
        basin.getBehaviour(SmartFluidTankBehaviour.OUTPUT)
                .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);

        availableItems.extractItem(itemSlot, 1, false);

        cir.setReturnValue(basin.acceptOutputs(List.of(ball), List.of(), test));
    }
}
