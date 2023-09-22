package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.custom.LogoItem;
import fr.cleboost.createchocolatefactory.item.custom.MacheteItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateChocolateFactory.MOD_ID);


    public static final RegistryObject<Item> LOGO = ITEMS.register("logo",
            () -> new LogoItem(new Item.Properties()));

    public static final RegistryObject<Item> MACHETE = ITEMS.register("machete",
            () -> new MacheteItem(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BEANS_WET = ITEMS.register("cocoa_beans_wet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BEANS_DIRTY = ITEMS.register("cocoa_beans_dirty",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_BARK = ITEMS.register("cocoa_bark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_CHOCOLATE_BUNNY = ITEMS.register("black_chocolate_bunny",
            () -> new Item(new Item.Properties().food(ModFoods.DARK_CHOCOLATE)));
    public static final RegistryObject<Item> BROWN_CHOCOLATE_BUNNY = ITEMS.register("brown_chocolate_bunny",
            () -> new Item(new Item.Properties().food(ModFoods.BROWN_CHOCOLATE)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
