package fr.cleboost.createchocolatefactory.blockentity;

import java.util.List;

import javax.annotation.Nonnull;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.network.RequestSyncPacket;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

public class DryingKitBlockEntity extends BlockEntity implements TickableBlockEntity, IHaveGoggleInformation {
    private int tickCount = 0;
    private final int tickToDry = 3000;
    private long lastSyncRequest = 0;

    public DryingKitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntity.DRYING_KIT_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (this.getLevel() == null) return;
        if (this.getLevel().isClientSide()) return;
        
        if (!this.getLevel().canSeeSky(worldPosition) || !this.getLevel().isDay() || this.getLevel().isRaining() || this.getLevel().isThundering())
            return;

        this.tickCount++;
        
        if (tickCount >= tickToDry) {
            this.tickCount = 0;
            BlockState blockState = this.level.getBlockState(this.worldPosition);
            this.level.setBlockAndUpdate(this.worldPosition, blockState.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DRY));
            syncToClient();
        }
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("tickCount", this.tickCount);
    }

    @Override
    protected void loadAdditional(@Nonnull CompoundTag pTag, @Nonnull HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.tickCount = pTag.getInt("tickCount");
    }

    @Override
    public CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider pRegistries) {
        CompoundTag tag = super.getUpdateTag(pRegistries);
        saveAdditional(tag, pRegistries);
        return tag;
    }

    @Override
    public void handleUpdateTag(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider pRegistries) {
        loadAdditional(tag, pRegistries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void syncToClient() {
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            setChanged();
        }
    }

    public void requestSync() {
        if (this.level != null && !this.level.isClientSide) {
            syncToClient();
        }
    }

    public int getTickCount() {
        return this.tickCount;
    }

    public int getTickToDry() {
        return this.tickToDry;
    }

    @Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (this.level != null && this.level.isClientSide && this.level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE).equals(DryingKitBlock.State.DRYING)) {
            long currentTime = this.level.getGameTime();
            if (currentTime - lastSyncRequest >= 20) {
                PacketDistributor.sendToServer(new RequestSyncPacket(this.worldPosition));
                lastSyncRequest = currentTime;
            }
        }

		String spacing = "    ";
		tooltip.add(Component.literal(spacing)
				.append(Component.translatable(CreateChocolateFactory.MODID + ".tooltip.dryingkit.info").withStyle(ChatFormatting.WHITE)));
        tooltip.add(Component.literal(spacing)
                .append(Component.translatable(
                    CreateChocolateFactory.MODID + ".tooltip.dryingkit.status",
                    this.level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE) == DryingKitBlock.State.DRYING
                        ? Component.translatable(CreateChocolateFactory.MODID + ".tooltip.dryingkit.drying").withStyle(ChatFormatting.AQUA)
                        : (this.level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE) == DryingKitBlock.State.DRY
                            ? Component.translatable(CreateChocolateFactory.MODID + ".tooltip.dryingkit.dry").withStyle(ChatFormatting.GREEN)
                            : Component.translatable(CreateChocolateFactory.MODID + ".tooltip.dryingkit.empty").withStyle(ChatFormatting.AQUA)
                        )
                ).withStyle(ChatFormatting.GRAY)));
                if (this.level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE) == DryingKitBlock.State.DRYING) {
                    tooltip.add(Component.literal(spacing)
                            .append(Component.translatable(
                                CreateChocolateFactory.MODID + ".tooltip.dryingkit.progress",
                                Component.literal(String.valueOf(this.tickCount * 100 / this.tickToDry)+"%").withStyle(ChatFormatting.AQUA)
                            ).withStyle(ChatFormatting.GRAY)));
                    int remainingTicks = this.tickToDry - this.tickCount;
                    int remainingSeconds = remainingTicks / 20;
                    int minutes = remainingSeconds / 60;
                    int seconds = remainingSeconds % 60;
                    
                    String timeFormatted = String.format("%d:%02d", minutes, seconds);
                    
                    tooltip.add(Component.literal(spacing)
                            .append(Component.translatable(
                                CreateChocolateFactory.MODID + ".tooltip.dryingkit.time_remaining",
                                Component.literal(timeFormatted).withStyle(ChatFormatting.AQUA)
                            ).withStyle(ChatFormatting.GRAY)));
                }

		return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
	}
}
