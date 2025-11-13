package fr.cleboost.createchocolatefactory.network;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.chocolatepreparer.ChocolatePreparerBlockEntity;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record UpdatePreparerValuesPacket(BlockPos pos, Chocolate chocolate) implements CustomPacketPayload {
    public static final Type<UpdatePreparerValuesPacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID, "update_preparer_values")
    );
    
    public static final StreamCodec<FriendlyByteBuf, UpdatePreparerValuesPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, UpdatePreparerValuesPacket::pos,
        Chocolate.STREAM_CODEC, UpdatePreparerValuesPacket::chocolate,
        UpdatePreparerValuesPacket::new
    );

    @Override
    public @Nonnull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdatePreparerValuesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().level().getBlockEntity(packet.pos) instanceof ChocolatePreparerBlockEntity preparer) {
                CreateChocolateFactory.LOGGER.info(packet.chocolate.toString());
                preparer.setValue(packet.chocolate);
            }
        });
    }
}

