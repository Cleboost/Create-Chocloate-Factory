package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MacheteItem extends AxeItem {
    public MacheteItem() {
        super(new ForgeTier(1, 200, 0.9F, 0F, 14,
                        ModTags.Blocks.MACHETE_BREAKABLE, () -> Ingredient.of(Tags.Items.INGOTS_IRON)),
                6F, -2.8F, new Item.Properties());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.createchocolatefactory.machete"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) return super.useOn(pContext);
        BlockPos blockpos = pContext.getClickedPos();
        BlockState bs = level.getBlockState(blockpos);
        if (bs.is(ModTags.Blocks.MACHETE_CLEANABLE)) {
            Player player = pContext.getPlayer();
            assert player != null;
            level.destroyBlock(blockpos, true);
            bs = level.getBlockState(blockpos.below());
            pContext.getItemInHand().hurtAndBreak(1, player, playerEvent -> player.broadcastBreakEvent(player.getUsedItemHand()));
            if (bs.is(ModTags.Blocks.MACHETE_CLEANABLE)) {
                level.destroyBlock(blockpos.below(), true);
                pContext.getItemInHand().hurtAndBreak(1, player, playerEvent -> player.broadcastBreakEvent(player.getUsedItemHand()));
            }
            super.useOn(pContext);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(pContext);
    }
}
