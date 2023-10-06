package fr.cleboost.createchocolatefactory.block.custom;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import fr.cleboost.createchocolatefactory.blockentity.DryingKitEntity;
import fr.cleboost.createchocolatefactory.blockentity.ModBlocksEntity;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

public class DryingKitBlock extends Block implements EntityBlock {

    public static final EnumProperty<DryingKitBlock.State> STATE = EnumProperty.create("state", DryingKitBlock.State.class);

    public DryingKitBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.defaultBlockState().setValue(STATE, State.EMPTY));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_154346_, @NotNull BlockGetter p_154347_, @NotNull BlockPos p_154348_, @NotNull CollisionContext p_154349_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.7D, 4.0D, 16.0D);
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

    public enum State implements StringRepresentable {
        EMPTY("empty"),
        WET("wet"),
        DIRTY("dirty");

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
        if (pLevel.isClientSide()) return InteractionResult.PASS;
        if (pState == ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.EMPTY)) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.getItem() == ModItems.COCOA_BEANS_WET.get()) {
                if (itemStack.getCount() >= 9) {
                    itemStack.shrink(9);
                    pLevel.setBlockAndUpdate(pPos, ModBlocks.DRYING_KIT.get().defaultBlockState().setValue(STATE, State.WET));
                    DryingKitEntity blockEntity = (DryingKitEntity) pLevel.getBlockEntity(pPos);
                    assert blockEntity != null;
                    blockEntity.setTickerEnable();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
