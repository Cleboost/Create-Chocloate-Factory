package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, CreateChocolateFactory.MODID);

    public static final DeferredHolder<Item, Item> LOGO = ITEMS.register("logo",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> COCOA_BEANS_WET = ITEMS.register("cocoa_beans_wet",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> COCOA_BEANS_DIRTY = ITEMS.register("cocoa_beans_dirty",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> COCOA_BEANS_ROASTED = ITEMS.register("cocoa_beans_roasted",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> MACHETE = ITEMS.register("machete",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> MINT_LEAF = ITEMS.register("mint_leaf",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> MINT_SEEDS = ITEMS.register("mint_seeds",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> ORANGE = ITEMS.register("orange",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> ORANGE_JUICE = ITEMS.register("orange_juice",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> ORANGE_WEDGE = ITEMS.register("orange_wedge",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
