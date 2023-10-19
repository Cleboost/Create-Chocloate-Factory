package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;
import fr.cleboost.createchocolatefactory.item.BarItem;
import fr.cleboost.createchocolatefactory.utils.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(CreateChocolateFactory.MOD_ID)
public class CreateChocolateFactory {
    public static final String MOD_ID = "createchocolatefactory";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateChocolateFactory() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlocksEntity.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
        }
        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.@NotNull Item event) {
            CreateChocolateFactory.LOGGER.info("###########################");
            event.register(((pStack, pTintIndex) -> {
                if (pTintIndex > 0) return -1;
                return new Chocolate(pStack.getTag()).getColor();
            }), ModItems.BARS.get());
        }
    }

    /*@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = LogicalSide.CLIENT)
    public static class LogicalClientModEvents {


    }*/
}