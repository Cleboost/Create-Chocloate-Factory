package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

import com.simibubi.create.foundation.gui.menu.MenuBase;
import fr.cleboost.createchocolatefactory.core.CCFMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ChocolatePreparerMenu extends MenuBase<ChocolatePreparerBlockEntity> {

    public ChocolatePreparerMenu(MenuType<?> type, int id, Inventory inv, RegistryFriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public ChocolatePreparerMenu(MenuType<?> type, int id, Inventory inv, ChocolatePreparerBlockEntity be) {
        super(type, id, inv, be);
    }

    public static ChocolatePreparerMenu create(int id, Inventory inv, ChocolatePreparerBlockEntity be) {
        return new ChocolatePreparerMenu(CCFMenu.CHOCOLATE_PREPARER.get(), id, inv, be);
    }

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int containerSlots = 1; // Un seul slot maintenant
            if (index < containerSlots) {
                if (!this.moveItemStackTo(stackInSlot, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                int filterSlot = ChocolatePreparerInventory.SLOT_FILTER;
                if (!this.moveItemStackTo(stackInSlot, filterSlot, filterSlot + 1, false)) {
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

    @Override
    public boolean stillValid(Player player) {
        return contentHolder != null && contentHolder.getLevel() != null
                && contentHolder.getLevel().getBlockEntity(contentHolder.getBlockPos()) == contentHolder
                && player.distanceToSqr(contentHolder.getBlockPos().getX() + 0.5,
                contentHolder.getBlockPos().getY() + 0.5,
                contentHolder.getBlockPos().getZ() + 0.5) <= 64.0;
    }

    @Override
    protected ChocolatePreparerBlockEntity createOnClient(RegistryFriendlyByteBuf extraData) {
        ClientLevel world = Minecraft.getInstance().level;
        BlockEntity blockEntity = world.getBlockEntity(extraData.readBlockPos());
        if (blockEntity instanceof ChocolatePreparerBlockEntity chocolatePreparer) {
            return chocolatePreparer;
        }
        return null;
    }


    @Override
    protected void addSlots() {
        if (contentHolder == null) {
            return;
        }
        int x = 4;
        int y = -52;

        // Un seul slot pour le filter
        addSlot(new SlotItemHandler(contentHolder.inventory, ChocolatePreparerInventory.SLOT_FILTER, x + 39, y + 66));

        addPlayerSlots(10, 108);
    }

    @Override
    protected void saveData(ChocolatePreparerBlockEntity contentHolder) {
    }

    @Override
    protected void initAndReadInventory(ChocolatePreparerBlockEntity contentHolder) {
    }
}
