package fr.cleboost.createchocolatefactory.block;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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

    @Override
    protected @Nonnull ItemInteractionResult useItemOn(@Nonnull ItemStack item, @Nonnull BlockState state, @Nonnull Level level,
                                                       @Nonnull BlockPos pos,
                                                       @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.COCOA_BEANS)) {
            Direction face = hitResult.getDirection();
            BlockPos targetPos = pos.relative(face);
            if (level.getBlockState(targetPos).isAir() && face.getAxis().isHorizontal()) {
                BlockState cocoaState = Blocks.COCOA.defaultBlockState()
                        .setValue(CocoaBlock.AGE, 0)
                        .setValue(CocoaBlock.FACING, face.getOpposite());
                level.setBlock(targetPos, cocoaState, 3);
                if (!player.isCreative()) {
                    player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                }
                return ItemInteractionResult.SUCCESS;
            } 
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
