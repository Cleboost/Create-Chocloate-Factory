package fr.cleboost.createchocolatefactory.network;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.chocolatePreparer.ChocolatePreparerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record UpdatePreparerValuesPacket(BlockPos pos, int strength, int sugar, int cocoaButter, int milk) implements CustomPacketPayload {
    public static final Type<UpdatePreparerValuesPacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID, "update_preparer_values")
    );
    
    public static final StreamCodec<FriendlyByteBuf, UpdatePreparerValuesPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, UpdatePreparerValuesPacket::pos,
        ByteBufCodecs.VAR_INT, UpdatePreparerValuesPacket::strength,
        ByteBufCodecs.VAR_INT, UpdatePreparerValuesPacket::sugar,
        ByteBufCodecs.VAR_INT, UpdatePreparerValuesPacket::cocoaButter,
        ByteBufCodecs.VAR_INT, UpdatePreparerValuesPacket::milk,
        UpdatePreparerValuesPacket::new
    );

    @Override
    public @Nonnull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdatePreparerValuesPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().level().getBlockEntity(packet.pos) instanceof ChocolatePreparerBlockEntity preparer) {
                preparer.setValues(packet.strength, packet.sugar, packet.cocoaButter, packet.milk);
            }
        });
    }
}

