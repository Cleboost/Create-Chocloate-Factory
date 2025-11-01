package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OutputOnlySlot extends Slot {
    public OutputOnlySlot(Container container, int index, int xPosition, int yPosition) {
        super(container, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return false;
    }
}

