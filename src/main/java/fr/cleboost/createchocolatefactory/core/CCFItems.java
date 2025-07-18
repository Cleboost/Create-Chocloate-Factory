package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.ChocolateBar;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import fr.cleboost.createchocolatefactory.item.utils.FuelItem;
import fr.cleboost.createchocolatefactory.utils.ModFoods;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemDisplayContext;

public class CCFItems {
        // public static final DeferredRegister.Items ITEMS =
        // DeferredRegister.createItems(CreateChocolateFactory.MODID);

        // //Items
        // public static final DeferredItem<Item> LOGO = ITEMS.register("logo",() -> new
        // Item(new Item.Properties()));

        // //Cocoa Beans
        // public static final DeferredItem<Item> COCOA_BEANS_WET =
        // ITEMS.register("cocoa_beans_wet",() -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> COCOA_BEANS_DIRTY =
        // ITEMS.register("cocoa_beans_dirty",() -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> COCOA_BEANS_ROASTED =
        // ITEMS.register("cocoa_beans_roasted", () -> new Item(new Item.Properties()));

        // //Tools
        // public static final DeferredItem<Item> MACHETE = ITEMS.register("machete", ()
        // -> new MacheteItem(new Item.Properties()));

        // //Fruits & Foods
        // public static final DeferredItem<Item> MINT_LEAF =
        // ITEMS.register("mint_leaf", () -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> MINT_SEEDS =
        // ITEMS.register("mint_seeds", () -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> ORANGE = ITEMS.register("orange", ()
        // -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> ORANGE_JUICE =
        // ITEMS.register("orange_juice", () -> new Item(new Item.Properties()));
        // public static final DeferredItem<Item> ORANGE_WEDGE =
        // ITEMS.register("orange_wedge", () -> new Item(new Item.Properties()));

        // //Fuel
        // public static final DeferredItem<Item> COCOA_BARK =
        // ITEMS.register("cocoa_bark", () -> new FuelItem(new Item.Properties(), 100));

        // //Chocolate
        // public static final DeferredItem<Item> CHOCOLATE_BAR =
        // ITEMS.register("chocolate_bar", () -> new ChocolateBar(new
        // Item.Properties().food(ModFoods.CHOCOLATE_BAR)));

        // public static void register(IEventBus eventBus) {
        // ITEMS.register(eventBus);
        // }

        private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

        // Static block removed - items are added to creative tab via
        // RegistrateDisplayItemsGenerator

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
        public static final ItemEntry<Item> COCOA_BARK = REGISTRATE.item("cocoa_bark", Item::new).lang("Cocoa Bark").register();

        // Fruits & Foods
        public static final ItemEntry<Item> MINT_LEAF = REGISTRATE.item("mint_leaf", Item::new).register();
        public static final ItemEntry<Item> MINT_SEEDS = REGISTRATE.item("mint_seeds", Item::new).register();
        public static final ItemEntry<Item> ORANGE = REGISTRATE.item("orange", Item::new).register();

        // Brass Whisk
        public static final ItemEntry<Item> BRASS_WHISK = REGISTRATE.item("brass_whisk", Item::new).register();

        public static void register() {}
}
