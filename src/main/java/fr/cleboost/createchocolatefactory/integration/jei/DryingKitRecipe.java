package fr.cleboost.createchocolatefactory.integration.jei;

import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.world.item.ItemStack;

public class DryingKitRecipe {
    public static final DryingKitRecipe INSTANCE = new DryingKitRecipe();

    public ItemStack getInput() {
        return new ItemStack(CCFItems.COCOA_BEANS_WET.get(), 9);
    }

    public ItemStack getOutput() {
        return new ItemStack(CCFItems.COCOA_BEANS_DIRTY.get(), 9);
    }
}
