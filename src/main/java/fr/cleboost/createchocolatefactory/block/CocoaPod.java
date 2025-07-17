package fr.cleboost.createchocolatefactory.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModItems;

public class CocoaPod extends Block {
    public static final BooleanProperty OPENED = BooleanProperty.create("opened");
    
    public CocoaPod(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPENED, false));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull Builder<Block, BlockState> builder) {
        builder.add(OPENED);
    }

    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return Block.box(3.0D, 0.0D, 6.0D, 14.7D, 4.0D, 10.0D);
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState pState, @Nonnull LevelReader pLevel, @Nonnull BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolidRender(pLevel, pPos.below());
    }

    @Override
    public @Nonnull BlockState updateShape(@Nonnull BlockState pState, @Nonnull Direction pDirection, @Nonnull BlockState pNeighborState, @Nonnull LevelAccessor pLevel, @Nonnull BlockPos pCurrentPos, @Nonnull BlockPos pNeighborPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    protected @Nonnull ItemInteractionResult useItemOn(@Nonnull ItemStack stack, @Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos,
            @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        if (!level.isClientSide() && player.getItemInHand(hand).is(ModItems.MACHETE.get())) {
            Block.popResource(level, pos, new ItemStack(ModItems.COCOA_BARK.get(), level.random.nextInt(1, 4)));
            if (state.getValue(OPENED)) {
                Block.popResource(level, pos, new ItemStack(ModItems.COCOA_BEANS_WET.get(), 1));
                level.destroyBlock(pos, false);
            } else {
                level.setBlockAndUpdate(pos, state.setValue(OPENED, true));
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            if (state.getValue(OPENED)) {
                Block.popResource(level, pos, new ItemStack(ModItems.COCOA_BARK.get(), level.random.nextInt(1, 4)));
            } else {
                Block.popResource(level, pos, new ItemStack(ModBlocks.COCOA_POD.get(), 1));
            }
        }
    }
}
