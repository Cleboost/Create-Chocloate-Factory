package fr.cleboost.createchocolatefactory.blockentity;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class DryingKitBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tickCount = 0;
    private boolean tickerEnable = false;
    private int tickToDry = 7000;

    public DryingKitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntity.DRYING_KIT_ENTITY.get(), pPos, pBlockState);
    }

    public void setTickToDry() {
        assert this.level != null;
        float multyplier = 1F;
        if (this.level.getBiome(this.worldPosition).is(Tags.Biomes.IS_CAVE)) multyplier = 0F;
        if (this.level.getBiome(this.worldPosition).is(Tags.Biomes.IS_COLD)) multyplier = 0F;
        if (this.level.getBiome(this.worldPosition).is(Tags.Biomes.IS_DRY)) multyplier *= 1.2F;
        if (this.level.getBiome(this.worldPosition).is(Tags.Biomes.IS_HOT)) multyplier *= 1.5F;
        if (this.level.getBiome(this.worldPosition).is(Tags.Biomes.IS_WET)) multyplier *= 0.5F;
        this.tickToDry=Math.round(this.tickToDry/multyplier);
    }

    public void setTickerEnable() {
        this.tickerEnable = true;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag data = nbt.getCompound(CreateChocolateFactory.MOD_ID);
        this.tickCount = data.getInt("counter");
        this.tickerEnable = this.tickCount != 0;
        this.tickToDry = data.getInt("time");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        var data = new CompoundTag();
        data.putInt("counter", this.tickCount);
        data.putInt("time", this.tickToDry);
        nbt.put(CreateChocolateFactory.MOD_ID, data);
    }

    public boolean isTickerEnable() {
        return this.tickerEnable;
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide() || !this.tickerEnable) return;
        if (!this.level.canSeeSky(worldPosition) || !this.level.isDay() || this.level.isRaining() || this.level.isThundering())
            return;
        this.tickCount++;
        if (tickCount >= tickToDry) {
            this.tickCount = 0;
            this.tickerEnable = false;
            BlockState blockState = this.level.getBlockState(this.worldPosition);
            this.level.setBlockAndUpdate(this.worldPosition, blockState.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DRY));
        }
    }
}
