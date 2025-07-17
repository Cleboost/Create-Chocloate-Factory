package fr.cleboost.createchocolatefactory.network;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.blockentity.DryingKitBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record RequestSyncPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<RequestSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID, "request_sync"));
    
    public static final StreamCodec<FriendlyByteBuf, RequestSyncPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, RequestSyncPacket::pos,
        RequestSyncPacket::new
    );

    @Override
    public @Nonnull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RequestSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().level().getBlockEntity(packet.pos) instanceof DryingKitBlockEntity dryingKit) {
                dryingKit.requestSync();
            }
        });
    }
} 