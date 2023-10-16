package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateChocolateFactory.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATE_CHOCOLATE_FACTORY_TAB = CREATIVE_MODE_TABS.register("create_chocolate_factory_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.LOGO.get()))
            .title(Component.translatable("item_group.createchocolatefactory.tab"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(Items.COCOA_BEANS);
                for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                    pOutput.accept(item.get());
                }
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
