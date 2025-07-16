package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.ChocolateBar;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import fr.cleboost.createchocolatefactory.item.utils.FuelItem;
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
import net.minecraft.nbt.CompoundTag;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateChocolateFactory.MODID);

    //Items
    public static final DeferredItem<Item> LOGO = ITEMS.register("logo",() -> new Item(new Item.Properties()));

    //Cocoa Beans
    public static final DeferredItem<Item> COCOA_BEANS_WET = ITEMS.register("cocoa_beans_wet",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COCOA_BEANS_DIRTY = ITEMS.register("cocoa_beans_dirty",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COCOA_BEANS_ROASTED = ITEMS.register("cocoa_beans_roasted", () -> new Item(new Item.Properties()));

    //Tools
    public static final DeferredItem<Item> MACHETE = ITEMS.register("machete", () -> new MacheteItem(new Item.Properties()));

    //Fruits/Foods
    public static final DeferredItem<Item> MINT_LEAF = ITEMS.register("mint_leaf", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MINT_SEEDS = ITEMS.register("mint_seeds", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORANGE = ITEMS.register("orange", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORANGE_JUICE = ITEMS.register("orange_juice", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORANGE_WEDGE = ITEMS.register("orange_wedge", () -> new Item(new Item.Properties()));

    //Fuel/Other
    public static final DeferredItem<Item> COCOA_BARK = ITEMS.register("cocoa_bark", () -> new FuelItem(new Item.Properties(), 100));

    //Chocolate
    public static final DeferredItem<Item> CHOCOLATE_BAR = ITEMS.register("chocolate_bar", () -> new ChocolateBar(new Item.Properties().food(ModFoods.CHOCOLATE_BAR)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SuppressWarnings("null")
    public static void registerItemProperties() {
        ItemProperties.register(
            ModItems.CHOCOLATE_BAR.get(),
            ResourceLocation.tryParse(CreateChocolateFactory.MODID + ":eatprogress"),
            (ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) -> {
                CompoundTag tag = stack.get(ModDataComponents.EAT_PROGRESS.get());
                if (tag != null && tag.contains("eatProgress")) {
                    return tag.getInt("eatProgress");
                }
                return 0;
            }
        );
    }
}
