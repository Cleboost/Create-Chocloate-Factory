package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolateAnalyserInventory extends ItemStackHandler {
    private final ChocolateAnalyserBlockEntity blockEntity;
    public ChocolateAnalyserInventory(ChocolateAnalyserBlockEntity blockEntity) {
        super(5);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }
}
