package fr.cleboost.createchocolatefactory.core;

import com.tterrag.registrate.util.entry.ItemEntry;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import java.util.stream.Stream;
import fr.cleboost.createchocolatefactory.ponder.CreateChocolateFactoryPonderPlugin;
import fr.cleboost.createchocolatefactory.utils.ChocolateFoodPack;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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
                Stream.concat(
                    Stream.of(
                        CCFItems.CHOCOLATE_BAR.get(),
                        CCFItems.CHOCOLATE_FILTER.get(),
                        CCFItems.COOKIE.get()
                    ),
                    CCFItems.getAllChocolatePack().stream().map(ChocolateFoodPack::getChocolateItems).map(ItemEntry::get)
                ).toArray(ItemLike[]::new)
        );
    }
}