package fr.cleboost.createchocolatefactory.block.chocolateanalyser;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolateAnalyserInventory extends ItemStackHandler {
    public static final int SLOT_CHOCOLATE = 0;
    public static final int SLOT_FILTER = 1;
    public static final int SLOT_FUEL = 2;
    public static final int SLOT_OUTPUT = 3;
    
    public ChocolateAnalyserInventory(ChocolateAnalyserBlockEntity blockEntity) {
        super(4);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == SLOT_OUTPUT) {
            return false;
        }
        if (slot == SLOT_FILTER) {
            return stack.getItem() == CCFItems.CHOCOLATE_FILTER.get();
        } 
        if (slot == SLOT_FUEL) {
            return stack.getItem() == Items.COAL;
        }
        if (slot == SLOT_CHOCOLATE) {
            return stack.has(CCFDataComponents.CHOCOLATE) && (stack.getItem() != CCFItems.CHOCOLATE_FILTER.get());
        }
        return true;
    }
}
