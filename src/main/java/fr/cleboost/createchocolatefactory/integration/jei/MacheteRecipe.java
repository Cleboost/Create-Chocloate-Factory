package fr.cleboost.createchocolatefactory.integration.jei;

import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MacheteRecipe {
    public static final MacheteRecipe INSTANCE = new MacheteRecipe();

    public ItemStack getMachete() {
        return CCFItems.MACHETE.asStack();
    }

    public ItemStack getPod() {
        return CCFBlocks.COCOA_POD.asStack();
    }

    public List<ItemStack> getOutputsStep1() {
        return Arrays.asList(CCFItems.COCOA_BARK.asStack(2));
    }

    public List<ItemStack> getOutputsStep2() {
        return Arrays.asList(CCFItems.COCOA_BARK.asStack(2), CCFItems.COCOA_BEANS_WET.asStack());
    }
}
