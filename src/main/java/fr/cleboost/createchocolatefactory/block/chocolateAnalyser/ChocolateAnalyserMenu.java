package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.core.CCFMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ChocolateAnalyserMenu extends AbstractContainerMenu {
    private final ChocolateAnalyserBlockEntity blockEntity;

    public ChocolateAnalyserMenu(int id, Inventory playerInventory, Player player) {
        super(CCFMenu.CHOCOLATE_ANALYSER.get(), id);
        
        this.blockEntity = null;
        
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
    }

    public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory playerInventory, BlockPos blockPos) {
        super(type, id);
        
        BlockEntity tileEntity = playerInventory.player.level().getBlockEntity(blockPos);
        if (tileEntity instanceof ChocolateAnalyserBlockEntity analyserEntity) {
            this.blockEntity = analyserEntity;
        } else {
            throw new IllegalStateException("BlockEntity not found at position: " + blockPos);
        }

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 80, 35));

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return blockEntity != null;
    }

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        if (blockEntity == null) {
            return ItemStack.EMPTY;
        }
        
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(stackInSlot, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}
