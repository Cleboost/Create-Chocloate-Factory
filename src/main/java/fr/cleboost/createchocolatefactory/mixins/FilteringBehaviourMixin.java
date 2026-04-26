package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import fr.cleboost.createchocolatefactory.item.ChocolateFilterItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilteringBehaviour.class, remap = false)
public abstract class FilteringBehaviourMixin {

    @Shadow
    public abstract boolean setFilter(Direction side, ItemStack stack);

    @Shadow
    public abstract ItemStack getFilter(Direction side);

    @Inject(method = "canShortInteract", at = @At("HEAD"), cancellable = true)
    private void CCF$allowChocolateFilterInteraction(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof ChocolateFilterItem) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onShortInteract", at = @At("HEAD"), cancellable = true)
    private void CCF$handleChocolateFilterInteraction(Player player, InteractionHand hand, Direction side, BlockHitResult hit, CallbackInfo ci) {
        ItemStack stackInHand = player.getItemInHand(hand);
        ItemStack currentFilter = getFilter(side);

        if (!(stackInHand.getItem() instanceof ChocolateFilterItem) && currentFilter.getItem() instanceof ChocolateFilterItem) {
            if (player.level().isClientSide) {
                ci.cancel();
                return;
            }

            player.getInventory().placeItemBackInInventory(currentFilter.copy());
            setFilter(side, ItemStack.EMPTY);
            ci.cancel();
            return;
        }

        if (stackInHand.getItem() instanceof ChocolateFilterItem) {
            if (player.level().isClientSide) {
                ci.cancel();
                return;
            }

            if (currentFilter.getItem() instanceof ChocolateFilterItem) {
                player.getInventory().placeItemBackInInventory(currentFilter.copy());
            }

            ItemStack filterCopy = stackInHand.copy();
            filterCopy.setCount(1);
            if (setFilter(side, filterCopy)) {
                if (!player.isCreative()) {
                    stackInHand.shrink(1);
                }
                ci.cancel();
            }
        }
    }
}
