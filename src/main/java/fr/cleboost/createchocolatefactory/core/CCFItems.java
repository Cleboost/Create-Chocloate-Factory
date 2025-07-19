package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import net.minecraft.world.item.Item;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.ItemDisplayContext;

public class CCFItems {
        // //Fruits & Foods
        // public static final DeferredItem<Item> MINT_LEAF =
        // ITEMS.register("mint_leaf", () -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> MINT_SEEDS =
        // ITEMS.register("mint_seeds", () -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> ORANGE = ITEMS.register("orange", ()
        // -> new Item(new Item.Properties()));

        private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

        // Logo
        public static final ItemEntry<Item> LOGO = REGISTRATE.item("logo", Item::new).lang("Logo")
                .model((ctx, prov) -> {
                        prov.generated(ctx, prov.modLoc("item/" + ctx.getName()))
                                        .transforms()
                                        .transform(ItemDisplayContext.FIXED)
                                        .rotation(0, 180, 0)
                                        .translation(0, 0, 0.75f)
                                        .scale(3, 3, 3)
                                        .end();
                })
                .register();

        // Tools
        public static final ItemEntry<MacheteItem> MACHETE = REGISTRATE.item("machete", MacheteItem::new).model((ctx, prov) -> prov.handheld(ctx)).register();
        // Cocoa Stuuf
        public static final ItemEntry<Item> COCOA_BEANS_WET = REGISTRATE.item("cocoa_beans_wet", Item::new).lang("Damp Cocoa Beans").register();
        public static final ItemEntry<Item> COCOA_BEANS_DIRTY = REGISTRATE.item("cocoa_beans_dirty", Item::new).lang("Dirty Cocoa Beans").register();
        public static final ItemEntry<Item> COCOA_BEANS_ROASTED = REGISTRATE.item("cocoa_beans_roasted", Item::new).lang("Roasted Cocoa Beans").register();
        public static final ItemEntry<Item> COCOA_BARK = REGISTRATE.item("cocoa_bark", Item::new).properties(p -> p.stacksTo(16)).lang("Cocoa Bark").register();

        // Fruits & Foods
        public static final ItemEntry<Item> MINT_LEAF = REGISTRATE.item("mint_leaf", Item::new).register();
        public static final ItemEntry<Item> MINT_SEEDS = REGISTRATE.item("mint_seeds", Item::new).register();
        public static final ItemEntry<Item> ORANGE = REGISTRATE.item("orange", Item::new).register();

        // Brass Whisk
        public static final ItemEntry<Item> BRASS_WHISK = REGISTRATE.item("brass_whisk", Item::new).register();

        public static void register() {}
}
