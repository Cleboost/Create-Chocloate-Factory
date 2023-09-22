package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
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
                for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                    pOutput.accept(item.get());
                }
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
