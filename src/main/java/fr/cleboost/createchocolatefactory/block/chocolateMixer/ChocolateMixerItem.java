package fr.cleboost.createchocolatefactory.block.chocolateMixer;

import com.simibubi.create.content.processing.basin.BasinBlock;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ChocolateMixerItem extends BlockItem {
    public ChocolateMixerItem(Block block, Properties properties) {
        super(block, properties);
    }
    
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        
        if (BasinBlock.isBasin(level, clickedPos)) {
            BlockPos targetPos = clickedPos.above(2);
            BlockState targetState = level.getBlockState(targetPos);
            BlockState oneAboveBasin = level.getBlockState(clickedPos.above());
            
            if (targetState.isAir() && oneAboveBasin.isAir()) {
                BlockHitResult newHitResult = new BlockHitResult(
                    new Vec3(clickedPos.getX() + 0.5, clickedPos.getY() + 1.0, clickedPos.getZ() + 0.5),
                    Direction.UP,
                    clickedPos,
                    false
                );
                
                UseOnContext newContext = new UseOnContext(level, context.getPlayer(), context.getHand(), 
                    context.getItemInHand(), newHitResult);
                BlockPlaceContext bContext = new BlockPlaceContext(newContext);
                BlockState blockToPlace = this.getBlock().getStateForPlacement(bContext);
                if (blockToPlace != null && level.setBlock(targetPos, blockToPlace, 3)) {
                    if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                        context.getItemInHand().shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }
}