package fr.cleboost.createchocolatefactory.block.chocolateanalyser;

import javax.annotation.Nonnull;

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

public class ChocolateAnalyserMenu extends MenuBase<ChocolateAnalyserBlockEntity> {
	
	public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, RegistryFriendlyByteBuf extraData) {
		super(type, id, inv, extraData);
	}

	public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, ChocolateAnalyserBlockEntity be) {
		super(type, id, inv, be);
	}
	
	public static ChocolateAnalyserMenu create(int id, Inventory inv, ChocolateAnalyserBlockEntity be) {
		return new ChocolateAnalyserMenu(CCFMenu.CHOCOLATE_ANALYSER.get(), id, inv, be);
	}

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int containerSlots = 4;
            if (index < containerSlots) {
                if (!this.moveItemStackTo(stackInSlot, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                int chocolateSlot = ChocolateAnalyserInventory.SLOT_CHOCOLATE;
                int filterSlot = ChocolateAnalyserInventory.SLOT_FILTER;
                int fuelSlot = ChocolateAnalyserInventory.SLOT_FUEL;
                if (!this.moveItemStackTo(stackInSlot, chocolateSlot, chocolateSlot + 1, false)) {
                    if (!this.moveItemStackTo(stackInSlot, filterSlot, filterSlot + 1, false)) {
                        if (!this.moveItemStackTo(stackInSlot, fuelSlot, fuelSlot + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
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
		if (contentHolder == null) return false;
		var level = contentHolder.getLevel();
		if (level == null) return false;
		return level.getBlockEntity(contentHolder.getBlockPos()) == contentHolder
			&& player.distanceToSqr(contentHolder.getBlockPos().getX() + 0.5, 
				contentHolder.getBlockPos().getY() + 0.5, 
				contentHolder.getBlockPos().getZ() + 0.5) <= 64.0;
	}

	@Override
	protected ChocolateAnalyserBlockEntity createOnClient(RegistryFriendlyByteBuf extraData) {
		ClientLevel world = Minecraft.getInstance().level;
		if (world == null) return null;
		BlockEntity blockEntity = world.getBlockEntity(extraData.readBlockPos());
		if (blockEntity instanceof ChocolateAnalyserBlockEntity chocolateAnalyser) {
			return chocolateAnalyser;
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

		addSlot(new SlotItemHandler(contentHolder.inventory, ChocolateAnalyserInventory.SLOT_CHOCOLATE, x+39, y+66));
		addSlot(new SlotItemHandler(contentHolder.inventory, ChocolateAnalyserInventory.SLOT_FILTER, x+78, y+27));
		addSlot(new SlotItemHandler(contentHolder.inventory, ChocolateAnalyserInventory.SLOT_FUEL, x+78, y+105));
		addSlot(new SlotItemHandler(contentHolder.inventory, ChocolateAnalyserInventory.SLOT_OUTPUT, x+117, y+66));

		addPlayerSlots(10, 108);
	}

	@Override
	protected void saveData(ChocolateAnalyserBlockEntity contentHolder) {}

	@Override
	protected void initAndReadInventory(ChocolateAnalyserBlockEntity contentHolder) {}
	
	public int getProcessingProgress() {
		return contentHolder.getProcessingProgress();
	}

	public int getMaxProcessingTicks() {
		return contentHolder.getMaxProcessingTicks();
	}
}
