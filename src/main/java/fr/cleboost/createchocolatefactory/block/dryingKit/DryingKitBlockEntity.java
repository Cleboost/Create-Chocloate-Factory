package fr.cleboost.createchocolatefactory.block.dryingKit;

import java.util.List;

import javax.annotation.Nonnull;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.blockentity.utils.TickableBlockEntity;
import fr.cleboost.createchocolatefactory.network.RequestSyncPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

public class DryingKitBlockEntity extends BlockEntity implements TickableBlockEntity, IHaveGoggleInformation {
    private int tickCount = 0;
    private final int tickToDry = 3000;
    private long lastSyncRequest = 0;

    public DryingKitBlockEntity(BlockEntityType<? extends DryingKitBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        Level level = this.getLevel();
        if (level == null)
            return;
        if (level.isClientSide())
            return;

        if (level.isRaining() || level.isThundering()) {
            this.tickCount = 0;
            return;
        }
        if (!level.canSeeSky(worldPosition) && !level.dimension().equals(Level.NETHER)) return;
        if (!level.isDay() && !level.dimension().equals(Level.NETHER)) return;


        this.tickCount++;
        if (level.dimension().equals(Level.NETHER)) this.tickCount++;

        if (tickCount >= tickToDry) {
            this.tickCount = 0;
            
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                for (int i = 0; i < 25; i++) {
                    double x = this.worldPosition.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 0.8;
                    double y = this.worldPosition.getY() + 1.0 + level.random.nextDouble() * 0.5;
                    double z = this.worldPosition.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 0.8;
                    double motionY = 0.02 + level.random.nextDouble() * 0.08;
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 1, 0.0, motionY, 0.0, 0.01);
                }
                
                for (int i = 0; i < 15; i++) {
                    double x = this.worldPosition.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 0.6;
                    double y = this.worldPosition.getY() + 1.0 + level.random.nextDouble() * 0.3;
                    double z = this.worldPosition.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 0.6;
                    serverLevel.sendParticles(ParticleTypes.WHITE_ASH, x, y, z, 2, 0.1, 0.1, 0.1, 0.02);
                }
            }
  
            level.playSound(
                null, 
                this.worldPosition,
                net.minecraft.sounds.SoundEvents.WET_SPONGE_DRIES,
                net.minecraft.sounds.SoundSource.BLOCKS,
                1.0F, // volume
                1.0F  // pitch
            );

            BlockState blockState = level.getBlockState(this.worldPosition);
            level.setBlockAndUpdate(this.worldPosition,
                    blockState.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DRY));
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
    public @Nonnull CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider pRegistries) {
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

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Level level = this.getLevel();
        if (level == null)
            return false;
        if (level.isClientSide() && level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE)
                .equals(DryingKitBlock.State.DRYING)) {
            long currentTime = level.getGameTime();
            if (currentTime - lastSyncRequest >= 20) {
                PacketDistributor.sendToServer(new RequestSyncPacket(this.worldPosition));
                lastSyncRequest = currentTime;
            }
        }

        String spacing = "    ";
        tooltip.add(Component.literal(spacing)
            .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.info")
                    .withStyle(ChatFormatting.WHITE)));

        if (level.dimension().equals(Level.NETHER)) {
            tooltip.add(Component.literal(spacing + "  ")
                .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.bonus")
                    .withStyle(ChatFormatting.GOLD)));
            tooltip.add(Component.literal(spacing + "    ")
                .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.bonus_text")
                    .withStyle(ChatFormatting.GREEN)));
        }

        DryingKitBlock.State state = level.getBlockState(this.worldPosition).getValue(DryingKitBlock.STATE);
        Component stateComponent;
        switch (state) {
            case DRYING:
                stateComponent = Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.drying")
                    .withStyle(ChatFormatting.AQUA);
                break;
            case DRY:
                stateComponent = Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.dry")
                    .withStyle(ChatFormatting.GREEN);
                break;
            default:
                stateComponent = Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.empty")
                    .withStyle(ChatFormatting.AQUA);
                break;
        }
        tooltip.add(Component.literal(spacing)
                .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.status",stateComponent)
                .withStyle(ChatFormatting.GRAY)));

        if (state == DryingKitBlock.State.DRYING) {
            tooltip.add(Component.literal(spacing)
                    .append(Component.translatable(
                            "goggle."+CreateChocolateFactory.MODID + ".dryingkit.progress",
                            Component.literal(this.tickCount * 100 / this.tickToDry + "%")
                                    .withStyle(ChatFormatting.AQUA))
                            .withStyle(ChatFormatting.GRAY)));
            int remainingTicks = this.tickToDry - this.tickCount;
            int remainingSeconds = remainingTicks / 20;
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            String timeFormatted = String.format("%d:%02d", minutes, seconds);
            tooltip.add(Component.literal(spacing)
                    .append(Component.translatable(
                            "goggle."+CreateChocolateFactory.MODID + ".dryingkit.time_remaining",
                            Component.literal(timeFormatted).withStyle(ChatFormatting.AQUA))
                            .withStyle(ChatFormatting.GRAY)));

            if (!level.canSeeSky(worldPosition) && !level.dimension().equals(Level.NETHER)) {
                tooltip.add(Component.literal(spacing)
                        .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.blocked_sky")
                                .withStyle(ChatFormatting.RED)));
            } else if (!level.isDay() && !level.dimension().equals(Level.NETHER)) {
                tooltip.add(Component.literal(spacing)
                        .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.night")
                                .withStyle(ChatFormatting.DARK_GRAY)));
            } else if (level.isRaining() || level.isThundering()) {
                tooltip.add(Component.literal(spacing)
                        .append(Component.translatable("goggle."+CreateChocolateFactory.MODID + ".dryingkit.raining")
                                .withStyle(ChatFormatting.DARK_GRAY)));
            }
        }

        return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }
}
