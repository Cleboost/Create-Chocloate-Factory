package fr.cleboost.createchocolatefactory.core;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class CCFCommonEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CCFCommands.register(event.getDispatcher(), event.getBuildContext());
    }
}
