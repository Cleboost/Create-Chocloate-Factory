package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolateAnalyserInventory extends ItemStackHandler {
    private final ChocolateAnalyserBlockEntity blockEntity;
    
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_FUEL = 1;
    public static final int SLOT_OUTPUT = 2;
    
    public ChocolateAnalyserInventory(ChocolateAnalyserBlockEntity blockEntity) {
        super(3);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == SLOT_OUTPUT) {
            return false;
        }
        if (slot == SLOT_FUEL) {
            return stack.getItem() == fr.cleboost.createchocolatefactory.core.CCFItems.CHOCOLATE_FILTER.get();
        }
        return true;
    }
}
