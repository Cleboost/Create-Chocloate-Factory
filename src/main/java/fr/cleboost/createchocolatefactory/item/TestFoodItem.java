package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.world.item.Item;

public class TestFoodItem extends Item {
    public TestFoodItem(Properties pProperties) {
        super(pProperties);
        CreateChocolateFactory.LOGGER.info(this.getFoodProperties().toString());
    }


}
