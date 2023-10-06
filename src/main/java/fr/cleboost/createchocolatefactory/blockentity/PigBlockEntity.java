package fr.cleboost.createchocolatefactory.blockentity;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PigBlockEntity extends BlockEntity implements TickableBlockEntity {

    private int tickCount = 0;
    public PigBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super (ModBlocksEntity.PIG_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        tickCount++;
        if (tickCount >= 100) {
            tickCount = 0;
            Pig pig = EntityType.PIG.create(level);
            assert pig != null;
            pig.setPos(worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5);
            level.addFreshEntity(pig);
        }
    }
}
