package fr.cleboost.createchocolatefactory.block.cocoablock;

import fr.cleboost.createchocolatefactory.utils.ModItems;
import fr.cleboost.createchocolatefactory.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CocoaOpenedPod extends DirectionalBlock {
    public CocoaOpenedPod(Properties pProperties) {
        super(pProperties);
    }

    //Define the hitbox of the block
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(3.0D, 0.0D, 6.0D, 14.7D, 4.0D, 10.0D);
    }

    //Add description to the block
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.createchocolatefactory.cocoa_pod_opened"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    //The block can only be placed on a solid block (2 functions)
    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevelR, BlockPos pPos) {
        return pLevelR.getBlockState(pPos.below()).isSolidRender(pLevelR, pPos.below());
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    //When the player right-click on the block with a machete, the block drop cocoa beans and cocoa bark
    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide() && pPlayer.getItemInHand(pHand).is(ModTags.Items.MACHETE_LIKE)) {
            Random random = new Random();
            pLevel.destroyBlock(pPos, false);
            Block.popResource(pLevel, pPos, new ItemStack(ModItems.COCOA_BEANS_WET.get(), random.nextInt(4, 7)));//4-6
            Block.popResource(pLevel, pPos, new ItemStack(ModItems.COCOA_BARK.get(), random.nextInt(1, 4)));//1-3
            pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer, playerEvent -> pPlayer.broadcastBreakEvent(pPlayer.getUsedItemHand()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
