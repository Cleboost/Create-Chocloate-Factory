package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

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

    public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, RegistryFriendlyByteBuf buffer) {
		super(type, id, inv, buffer);
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

    @Override
	protected void initAndReadInventory(ChocolateAnalyserBlockEntity contentHolder) {
	}

    @Override
    protected void saveData(ChocolateAnalyserBlockEntity contentHolder) {
    }

    @Override
	protected ChocolateAnalyserBlockEntity createOnClient(RegistryFriendlyByteBuf extraData) {
		if (extraData == null) {
			return null;
		}
		ClientLevel world = Minecraft.getInstance().level;
		BlockEntity blockEntity = world.getBlockEntity(extraData.readBlockPos());
		if (blockEntity instanceof ChocolateAnalyserBlockEntity chocolateAnalyser) {
			return chocolateAnalyser;
		}
		return null;
	}

    @Override
	protected void addSlots() {
		int x = 0;
		int y = 0;

        addSlot(new SlotItemHandler(contentHolder.inventory, 0, 15, 65));
		addPlayerSlots(37, 161);
	}
}
