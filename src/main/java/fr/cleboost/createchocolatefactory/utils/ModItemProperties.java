package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.ItemRegistry;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateProgressItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeChocolateProgressItem(ItemRegistry.CHOCOLATE_BAR.get());
    }

    private static void makeChocolateProgressItem(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID, "eatprogress"), (pItemStack, pClientLevel, pEntity, pInt) -> {
            return pEntity != null && pItemStack.is(ItemRegistry.CHOCOLATE_BAR.get()) ? ChocolateProgressItem.getEatProgress(pItemStack) : 0;
        });
    }
}
