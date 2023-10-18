package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.blockentity.DryingKitBlockEntity;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingKitBlock extends Block implements EntityBlock {

    public static final EnumProperty<DryingKitBlock.State> STATE = EnumProperty.create("state", DryingKitBlock.State.class);

    public DryingKitBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(STATE, State.EMPTY));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_154346_, @NotNull BlockGetter p_154347_, @NotNull BlockPos p_154348_, @NotNull CollisionContext p_154349_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    }

    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, CollisionContext pContext) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        return ModBlocksEntity.DRYING_KIT_ENTITY.get().create(pPos, pBlockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return TickableBlockEntity.getTickerHelper(pLevel);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STATE);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevelR, BlockPos pPos) {
        return pLevelR.getBlockState(pPos.below()).isSolidRender(pLevelR, pPos.below());
    }

    @Override
    public BlockState updateShape(BlockState p_152926_, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
        return !p_152926_.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152926_, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    public enum State implements StringRepresentable {
        EMPTY("empty"),
        DRYING("drying"),
        DRY("dry");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide() || pHand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY)) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.is(ModItems.COCOA_BEANS_WET.get())) {
                if (itemStack.getCount() >= 9 || pPlayer.isCreative()) {
                    if (!pPlayer.isCreative()) itemStack.shrink(9);
                    pLevel.setBlockAndUpdate(pPos, ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.DRYING));
                    DryingKitBlockEntity blockEntity = (DryingKitBlockEntity) pLevel.getBlockEntity(pPos);
                    assert blockEntity != null;
                    blockEntity.setTickToDry();
                    blockEntity.setTickerEnable();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
        if (pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.DRY)) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.isEmpty() || itemStack.is(ModItems.COCOA_BEANS_DIRTY.get())) {
                if (itemStack.isEmpty()) {
                    ItemStack cocoa_dirty = new ItemStack(ModItems.COCOA_BEANS_DIRTY.get(), 9);
                    pPlayer.setItemInHand(pHand, cocoa_dirty);
                } else {
                    if (itemStack.getCount() + 9 > itemStack.getMaxStackSize()) return InteractionResult.PASS;
                    ItemStack cocoa_dirty = itemStack.copy();
                    cocoa_dirty.grow(9);
                    pPlayer.setItemInHand(pHand, cocoa_dirty);
                }
                pLevel.setBlockAndUpdate(pPos, ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY));
                return InteractionResult.SUCCESS;

            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if (pLevel.isClientSide() || pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY) || pNewState.is(ModBlocks.DRYING_KIT.get()))
            return;
        if (pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.DRYING)) {
            ItemStack cocoas = new ItemStack(ModItems.COCOA_BEANS_WET.get(), 9);
            var itemsE = new ItemEntity(pLevel, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, cocoas);
            pLevel.addFreshEntity(itemsE);
        }
        if (pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.DRY)) {
            ItemStack cocoas = new ItemStack(ModItems.COCOA_BEANS_DIRTY.get(), 9);
            var itemsE = new ItemEntity(pLevel, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, cocoas);
            pLevel.addFreshEntity(itemsE);
        }
    }
}
