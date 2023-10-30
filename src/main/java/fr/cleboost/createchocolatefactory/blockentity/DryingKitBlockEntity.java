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
import org.jetbrains.annotations.NotNull;

public class DryingKitBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tickCount = 0;
    private boolean tickerEnable = false;
    private int tickToDry = 7000;

    public DryingKitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntity.DRYING_KIT_ENTITY.get(), pPos, pBlockState);
    }

    //Define time bonus/malus for the drying kit
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

    //Enable the ticker
    public void setTickerEnable() {
        this.tickerEnable = true;
    }

    //Load data from block entity when
    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        CompoundTag data = nbt.getCompound(CreateChocolateFactory.MOD_ID);
        this.tickCount = data.getInt("counter");
        this.tickerEnable = this.tickCount != 0;
        this.tickToDry = data.getInt("time");
    }

    //Save data to block when map is saved
    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        var data = new CompoundTag();
        data.putInt("counter", this.tickCount);
        data.putInt("time", this.tickToDry);
        nbt.put(CreateChocolateFactory.MOD_ID, data);
    }

    //Add tick to block for change state
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
