package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.ponder.CreateChocolateFactoryPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = CreateChocolateFactory.MODID, value = Dist.CLIENT)
public class CCFClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CCFPartialModel.init();
        PonderIndex.addPlugin(new CreateChocolateFactoryPonderPlugin());
        CCFItemProperties.addCustomItemProperties();
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (ItemStack stack, int tintIndex) -> {
                    if (tintIndex == 0 && stack.has(CCFDataComponents.CHOCOLATE)) {
                        return stack.get(CCFDataComponents.CHOCOLATE).getColor();
                    }
                    return 0xFFFFFFFF;
                },
                CCFItems.CHOCOLATE_EGG.get(),
                CCFItems.CHOCOLATE_BAR.get()//,
                //CCFFluids.CHOCOLATE.get().getBucket()
        );
    }
}