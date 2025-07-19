package fr.cleboost.createchocolatefactory.utils;

import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.ponder.CreateChocolateFactoryPonderPlugin;

@EventBusSubscriber(modid = CreateChocolateFactory.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModPartialModel.init();
        PonderIndex.addPlugin(new CreateChocolateFactoryPonderPlugin());
    }

    // @SubscribeEvent
    // public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
    //     event.register(
    //         (ItemStack stack, int tintIndex) -> {
    //             if (tintIndex == 0 && stack.getItem() instanceof ChocolateBaseItem item) {
    //                 return (new Chocolate(stack)).getColor();
    //             }
    //             return 0xFFFFFFFF;
    //         },
    //         CCFItems.CHOCOLATE_BAR.get()
    //     );
    // }
}