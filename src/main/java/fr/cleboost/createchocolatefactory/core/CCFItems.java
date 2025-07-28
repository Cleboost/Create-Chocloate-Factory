package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;

import com.simibubi.create.AllTags.AllItemTags;
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
        public static final ItemEntry<Item> COCOA_BARK = REGISTRATE.item("cocoa_bark", Item::new).properties(p -> p.stacksTo(16)).register();
        public static final ItemEntry<Item> COCOA_POWDER = REGISTRATE.item("cocoa_powder", Item::new).register();
        public static final ItemEntry<Item> COCOA_CAKE = REGISTRATE.item("cocoa_cake", Item::new).properties(p -> p.stacksTo(1)).register();
        public static final ItemEntry<Item> COCOA_NIBS = REGISTRATE.item("cocoa_nibs", Item::new).register();
        public static final ItemEntry<Item> COCOA_HUSK = REGISTRATE.item("cocoa_husk", Item::new).lang("Cocoa Husk").register();

        // Fruits & Foods
        public static final ItemEntry<Item> MINT_LEAF = REGISTRATE.item("mint_leaf", Item::new).register();
        public static final ItemEntry<Item> MINT_SEEDS = REGISTRATE.item("mint_seeds", Item::new).register();
        public static final ItemEntry<Item> ORANGE = REGISTRATE.item("orange", Item::new).register();
        public static final ItemEntry<Item> PEANUT = REGISTRATE.item("peanut", Item::new).lang("Peanut").register();
        public static final ItemEntry<Item> HAZELNUT = REGISTRATE.item("hazelnut", Item::new).lang("Hazelnut").register();
        public static final ItemEntry<Item> CARAMEL = REGISTRATE.item("caramel", Item::new).lang("Caramel").register();
        public static final ItemEntry<Item> CARAMEL_NUGGET = REGISTRATE.item("caramel_nugget", Item::new).lang("Caramel Nugget").register();

        // Brass Whisk
        public static final ItemEntry<Item> BRASS_WHISK = REGISTRATE.item("brass_whisk", Item::new).register();

        // Seau de chocolat avec un chemin de texture personnalisé
        public static final ItemEntry<Item> CHOCOLATE_BUCKET = REGISTRATE.item("chocolate_bucket", Item::new)
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((ctx, prov) -> prov.generated(ctx, prov.modLoc("item/chocolate_bucket/bucket"), prov.modLoc("item/chocolate_bucket/overlay")))
            .register();

        public static void register() {}
}
