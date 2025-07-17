package fr.cleboost.createchocolatefactory.blockentity;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DryingKitBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tickCount = 0;
    private boolean tickerEnable = false;
    private int tickToDry = 3000;

    public DryingKitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntity.DRYING_KIT_ENTITY.get(), pPos, pBlockState);
    }

    //Define time bonus/malus for the drying kit
    public void setTickToDry() {
        assert this.level != null;
        float multyplier = 1F;

        var biomeHolder = this.level.getBiome(this.worldPosition);
        if (biomeHolder.is(Biomes.BEACH) || biomeHolder.is(Biomes.SNOWY_BEACH)) {
            multyplier = 0.5F;
        } else if (biomeHolder.is(Biomes.DESERT) || biomeHolder.is(Biomes.BADLANDS)) {
            multyplier = 1.5F;
        }

        this.tickToDry = Math.round(this.tickToDry / multyplier);
    }

    //Enable the ticker
    public void setTickerEnable() {
        this.tickerEnable = true;
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

    @Override
    protected void saveAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("tickCount", this.tickCount);
        pTag.putBoolean("tickerEnable", this.tickerEnable);
        pTag.putInt("tickToDry", this.tickToDry);
    }

    @Override
    protected void loadAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.tickCount = pTag.getInt("tickCount");
        this.tickerEnable = pTag.getBoolean("tickerEnable");
        this.tickToDry = pTag.getInt("tickToDry");
    }
}
