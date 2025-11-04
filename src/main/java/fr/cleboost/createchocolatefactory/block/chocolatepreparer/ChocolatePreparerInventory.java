package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolatePreparerInventory extends ItemStackHandler {
    private final ChocolatePreparerBlockEntity blockEntity;
    
    public static final int SLOT_FILTER = 0;
    
    public ChocolatePreparerInventory(ChocolatePreparerBlockEntity blockEntity) {
        super(1);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == SLOT_FILTER) {
            return stack.getItem() == CCFItems.CHOCOLATE_FILTER.get();
        }
        return false;
    }
    
    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (slot == SLOT_FILTER) {
            blockEntity.loadValuesFromFilter();
        }
    }
}
