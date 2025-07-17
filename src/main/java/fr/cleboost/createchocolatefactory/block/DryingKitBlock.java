package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.blockentity.DryingKitBlockEntity;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.ItemInteractionResult;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

public class DryingKitBlock extends Block implements EntityBlock {

    public static final EnumProperty<DryingKitBlock.State> STATE = EnumProperty.create("state",
            DryingKitBlock.State.class);

    public DryingKitBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(STATE, State.EMPTY));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel,
            @NotNull BlockPos p_154348_, @NotNull CollisionContext pContext) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel,
            @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pBlockState) {
        return ModBlocksEntity.DRYING_KIT_ENTITY.get().create(pPos, pBlockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level pLevel, @Nonnull BlockState pState,
            @Nonnull BlockEntityType<T> pBlockEntityType) {
        if (!pState.getValue(STATE).equals(State.DRYING)) return null;
        return TickableBlockEntity.getTickerHelper(pLevel);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STATE);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevelR, BlockPos pPos) {
        return pLevelR.getBlockState(pPos.below()).isSolidRender(pLevelR, pPos.below());
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection,
            @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos,
            @NotNull BlockPos pNeighborPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
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
    protected ItemInteractionResult useItemOn(ItemStack item, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide() || hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (state.getValue(STATE) == State.EMPTY) {
            if (item.is(ModItems.COCOA_BEANS_WET)) {
                if (item.getCount() >= 9 || player.isCreative()) {
                    if (!player.isCreative()) {
                        item.shrink(9);
                    }
                    level.setBlockAndUpdate(pos,
                            ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.DRYING));
                    return ItemInteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(
                        Component.translatable("message.createchocolatefactory.dryingkit.need_more_cocoa_beans", item.getCount())
                            .withStyle(ChatFormatting.RED), true
                    );
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
                
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (state.getValue(STATE) == State.DRY) {
            if (item.isEmpty() || item.is(ModItems.COCOA_BEANS_DIRTY.get())) {
                if (item.isEmpty()) {
                    ItemStack cocoas = new ItemStack(ModItems.COCOA_BEANS_DIRTY.get(), 9);
                    var itemsE = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, cocoas);
                    level.addFreshEntity(itemsE);
                } else {
                    if (item.getCount() + 9 > item.getMaxStackSize()) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }
                    item.grow(9);
                }
                level.setBlockAndUpdate(pos,
                        ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY));
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos,
            @NotNull BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if (pLevel.isClientSide()
                || pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY)
                || pNewState.is(ModBlocks.DRYING_KIT.get()))
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
