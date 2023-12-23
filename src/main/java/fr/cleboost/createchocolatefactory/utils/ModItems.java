package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.fluid.ModFluids;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateItem;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateProgressItem;
import fr.cleboost.createchocolatefactory.item.utils.FuelItem;
import fr.cleboost.createchocolatefactory.item.LogoItem;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateChocolateFactory.MOD_ID);

    //custom Items
    public static final RegistryObject<Item> LOGO = ITEMS.register("logo",
            () -> new LogoItem(new Item.Properties()));
    public static final RegistryObject<Item> MACHETE = ITEMS.register("machete",
            MacheteItem::new);
    public static final RegistryObject<Item> COCOA_BEANS_WET = ITEMS.register("cocoa_beans_wet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BEANS_DIRTY = ITEMS.register("cocoa_beans_dirty",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BARK = ITEMS.register("cocoa_bark",
            () -> new FuelItem(new Item.Properties(), 100));
    public static final RegistryObject<Item> MINT_SEEDS = ITEMS.register("mint_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MINT_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> MINT_LEAF = ITEMS.register("mint_leaf",
            () -> new Item(new Item.Properties().food(ModFoods.MINT)));
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ORANGE_WEDGE = ITEMS.register("orange_wedge",
            () -> new Item(new Item.Properties().food(ModFoods.ORANGE_WEDGE).stacksTo(16)));
    public static final RegistryObject<Item> ORANGE_JUICE = ITEMS.register("orange_juice",
            () -> new Item(new Item.Properties().food(ModFoods.ORANGE_WEDGE)));
    public static final RegistryObject<Item> BARS = ITEMS.register("chocolate_bar",
            () -> new ChocolateProgressItem(new Item.Properties().food(ModFoods.CHOCOLATE_FAST).stacksTo(1), 3));
    public static final RegistryObject<Item> BUNNY = ITEMS.register("chocolate_bunny",
            () -> new ChocolateItem(new Item.Properties().food(ModFoods.CHOCOLATE_FAST).stacksTo(1)));
    public static final RegistryObject<Item> EGG = ITEMS.register("chocolate_egg",
            () -> new ChocolateItem(new Item.Properties().food(ModFoods.CHOCOLATE_SLOW).stacksTo(1)));
    public static final RegistryObject<Item> ROASTED_COCOA = ITEMS.register("cocoa_beans_roasted",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BUTTER_BUCKET = ITEMS.register("cocoa_butter_bucket",
            () -> new BucketItem(ModFluids.SOURCE_COCOA_BUTTER,
                    new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
