package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.BarItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBars(ModItems.BARS.get());
    }

    public static void makeBars(Item item) {
        ItemProperties.register(item, new ResourceLocation("eatprogress"), (pItemStack, pClientLevel, pEntity, pInt) -> {
            return pEntity != null && pItemStack.is(ModItems.BARS.get()) ? BarItem.getEatProgress(pItemStack) : 0;
        });
    }
}
