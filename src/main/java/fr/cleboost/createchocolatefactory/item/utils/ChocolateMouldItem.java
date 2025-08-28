package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChocolateMouldItem extends Item {
    private final ChocolateBaseItem result;

    public ChocolateMouldItem(Properties properties, ChocolateBaseItem result) {
        super(properties.stacksTo(1).durability(16));
        this.result = result;
    }

    public ChocolateBaseItem getResultItem() {
        return result;
    }

    public ItemStack getResult(Chocolate chocolate) {
        ItemStack itemStack = new ItemStack(this.result);
        itemStack.set(CCFDataComponents.CHOCOLATE.get(), chocolate);
        return itemStack;
    }
}
