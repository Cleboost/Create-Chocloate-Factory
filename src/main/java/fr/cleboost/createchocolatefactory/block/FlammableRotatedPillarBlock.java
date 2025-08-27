package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class FlammableRotatedPillarBlock extends RotatedPillarBlock {
    public FlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState pState, UseOnContext pContext, ItemAbility itemAbility, boolean simulate) {
        if (pContext.getItemInHand().getItem() instanceof AxeItem) {
            if (pState.is(CCFBlocks.COCOA_LOG.get()))
                return CCFBlocks.COCOA_STRIPPED_LOG.get().defaultBlockState().setValue(AXIS, pState.getValue(AXIS));
            if (pState.is(CCFBlocks.COCOA_WOOD.get()))
                return CCFBlocks.COCOA_STRIPPED_WOOD.get().defaultBlockState().setValue(AXIS, pState.getValue(AXIS));
        }
        return super.getToolModifiedState(pState, pContext, itemAbility, simulate);
    }
}
