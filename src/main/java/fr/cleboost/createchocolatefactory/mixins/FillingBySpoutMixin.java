package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.content.fluids.spout.FillingBySpout;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateMouldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FillingBySpout.class)
public class FillingBySpoutMixin {
    @Inject(method = "canItemBeFilled", at = @At("HEAD"), cancellable = true)
    private static void canItemBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof ChocolateMouldItem) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getRequiredAmountForItem", at = @At("HEAD"), cancellable = true)
    private static void getRequiredAmountForItem(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (availableFluid.is(CCFFluids.CHOCOLATE.get()) && stack.getItem() instanceof ChocolateMouldItem mould) {
            cir.setReturnValue(mould.getResultItem().getAmount());
        }
    }

    @Inject(method = "fillItem", at = @At("HEAD"), cancellable = true)
    private static void fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (availableFluid.is(CCFFluids.CHOCOLATE.get()) && stack.getItem() instanceof ChocolateMouldItem mould) {
            if (availableFluid.getAmount() < requiredAmount) cir.setReturnValue(stack);
            availableFluid.shrink(requiredAmount);
            cir.setReturnValue(mould.getResult(availableFluid.get(CCFDataComponents.CHOCOLATE.get())));
        }
    }
}
