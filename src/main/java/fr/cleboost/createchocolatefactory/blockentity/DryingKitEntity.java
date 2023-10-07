package fr.cleboost.createchocolatefactory.blockentity;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.custom.DryingKitBlock;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DryingKitEntity extends BlockEntity implements TickableBlockEntity {

    private int tickCount = 0;
    private boolean tickerEnable = false;
    public DryingKitEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntity.DRYING_KIT_ENTITY.get(), pPos, pBlockState);
    }

    public void setTickerEnable() {
        tickerEnable = true;
        CreateChocolateFactory.LOGGER.info("Ticker enable");
    }

    public boolean isTickerEnable() {
        return tickerEnable;
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide()) return;
        if (!tickerEnable) return;
        tickCount++;
        CreateChocolateFactory.LOGGER.info("Tick count: " + tickCount);
        if (tickCount >= 100) {
            tickCount = 0;
            tickerEnable = false;

            BlockState blockState = level.getBlockState(worldPosition);
            level.setBlockAndUpdate(worldPosition, blockState.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DIRTY));
        }
    }
}
