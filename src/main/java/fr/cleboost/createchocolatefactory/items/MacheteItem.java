package fr.cleboost.createchocolatefactory.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MacheteItem extends Item {
    public MacheteItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.createchocolatefactory.machete"));
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
}
