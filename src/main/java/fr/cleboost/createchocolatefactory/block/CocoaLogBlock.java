package fr.cleboost.createchocolatefactory.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CocoaLogBlock extends FlammableRotatedPillarBlock {
    public CocoaLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!level.getBlockState(pos.relative(direction)).isAir()) continue;
            BlockState cocoaState = Blocks.COCOA.defaultBlockState()
                    .setValue(CocoaBlock.AGE, 0)
                    .setValue(CocoaBlock.FACING, direction.getOpposite());

            level.setBlock(pos.relative(direction), cocoaState, 3);
            return;
        }
    }
}
