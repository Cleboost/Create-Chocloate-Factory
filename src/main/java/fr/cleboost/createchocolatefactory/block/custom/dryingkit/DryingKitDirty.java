package fr.cleboost.createchocolatefactory.block.custom.dryingkit;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingKitDirty extends DirectionalBlock {

    public static final boolean CustomModel = true;
    public DryingKitDirty(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_154346_, @NotNull BlockGetter p_154347_, @NotNull BlockPos p_154348_, @NotNull CollisionContext p_154349_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.7D, 4.0D, 16.0D);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.createchocolatefactory.cocoa_block_closed"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        level.setBlockAndUpdate(pos, ModBlocks.DRYING_KIT_EMPTY.get().defaultBlockState());
        Block.popResource(level, pos, new ItemStack(ModItems.COCOA_BEANS_DIRTY.get(), 9));

        return InteractionResult.SUCCESS;
    }
}
