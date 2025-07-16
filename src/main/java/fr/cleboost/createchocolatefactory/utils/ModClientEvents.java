package fr.cleboost.createchocolatefactory.utils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.items.utils.ChocolateBaseItem;
import net.minecraft.world.item.ItemStack;

@EventBusSubscriber(modid = CreateChocolateFactory.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModItems.registerItemProperties();
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
            (ItemStack stack, int tintIndex) -> {
                if (tintIndex == 0 && stack.getItem() instanceof ChocolateBaseItem item) {
                    return item.getChocolate().getColor();
                    // return 0xFFFF00FF;
                }
                return 0xFFFFFFFF;
            },
            ModItems.CHOCOLATE_BAR.get()
        );
    }
}