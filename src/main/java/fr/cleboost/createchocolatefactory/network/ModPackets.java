package fr.cleboost.createchocolatefactory.network;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CreateChocolateFactory.MODID)
public class ModPackets {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        
        registrar.playToServer(
            RequestSyncPacket.TYPE,
            RequestSyncPacket.STREAM_CODEC,
            RequestSyncPacket::handle
        );
    }
} 