package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.core.CCFLangs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MacheteItem extends AxeItem {
    public MacheteItem(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(CCFLangs.MACHETE.getComponent());
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public @Nonnull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide()) return super.useOn(pContext);

        BlockPos blockpos = pContext.getClickedPos();
        BlockState bs = level.getBlockState(blockpos);

        if (bs.getBlock() instanceof LeavesBlock) {
            Player player = pContext.getPlayer();
            if (player != null) {
                player.startUsingItem(pContext.getHand());
                level.destroyBlock(blockpos, true);

                BlockState bsBelow = level.getBlockState(blockpos.below());
                if (!bsBelow.isAir() && bsBelow.getDestroySpeed(level, blockpos.below()) >= 0) {
                    level.destroyBlock(blockpos.below(), true);
                    pContext.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }

                pContext.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(pContext);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level instanceof ClientLevel cl) {
            ItemStack stack = player.getItemInHand(usedHand);
            var model = Minecraft.getInstance().getItemRenderer().getModel(stack, level, null, 0);
            var tx = model.getOverrides().resolve(model, stack, cl, null, 0).getParticleIcon(net.neoforged.neoforge.client.model.data.ModelData.EMPTY);
            tx.contents().getOriginalImage().
        }
        return super.use(level, player, usedHand);
    }*/

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10;
    }

}
