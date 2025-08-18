package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateProgressItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;

public class CCFItemProperties {
    public static final String EAT_PROGRESS = "eat_progress";

    public static void addCustomItemProperties() {
        makeChocolateProgressItem(CCFItems.CHOCOLATE_BAR.get());
    }

    private static void makeChocolateProgressItem(Item item) {
        ItemProperties.register(item, CreateChocolateFactory.asResource(EAT_PROGRESS), (pItemStack, pClientLevel, pEntity, pInt) -> {
            return pEntity != null && pItemStack.is(CCFItems.CHOCOLATE_BAR) ? ChocolateProgressItem.getEatProgress(pItemStack) : 0;
        });
    }
}
