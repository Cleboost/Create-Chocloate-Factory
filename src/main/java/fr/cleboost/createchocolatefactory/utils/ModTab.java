package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateChocolateFactory.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_CHOCOLATE_FACTORY_TAB = CREATIVE_MODE_TABS.register("createchocolatefactory_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.LOGO.get()))
            .title(Component.translatable("itemGroup.createchocolatefactory.createchocolatefactory_tab"))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.LOGO.get());
                for (DeferredHolder<Item, ? extends Item> item : ModItems.ITEMS.getEntries()) {
                    pOutput.accept(item.get());
                }
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}