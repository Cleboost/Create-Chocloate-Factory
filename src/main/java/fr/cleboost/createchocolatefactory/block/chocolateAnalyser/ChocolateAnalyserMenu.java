package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.core.CCFMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ChocolateAnalyserMenu extends AbstractContainerMenu {
	private final Container container;
	private final ContainerData data;

	private static final int DATA_COOKING_PROGRESS = 2;
	private static final int DATA_COOKING_TOTAL_TIME = 3;
	
	public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, ChocolateAnalyserBlockEntity be) {
		super(type, id);
		checkContainerSize(be, 3);
		checkContainerDataCount(be, 4);
		this.container = be;
		this.data = be;
		
		this.addSlot(new Slot(be, ChocolateAnalyserInventory.SLOT_INPUT, 56, 17));
		this.addSlot(new Slot(be, ChocolateAnalyserInventory.SLOT_FUEL, 56, 53));
		this.addSlot(new OutputOnlySlot(be, ChocolateAnalyserInventory.SLOT_OUTPUT, 116, 35));
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
		}
		
		this.addDataSlots(be);
	}
	
	public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, RegistryFriendlyByteBuf buffer) {
		this(type, id, inv, new SimpleContainer(3), new SimpleContainerData(4));
	}
	
	private ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, Container container, ContainerData data) {
		super(type, id);
		checkContainerSize(container, 3);
		checkContainerDataCount(data, 4);
		this.container = container;
		this.data = data;
		
		this.addSlot(new Slot(container, ChocolateAnalyserInventory.SLOT_INPUT, 56, 17));
		this.addSlot(new Slot(container, ChocolateAnalyserInventory.SLOT_FUEL, 56, 53));
		this.addSlot(new OutputOnlySlot(container, ChocolateAnalyserInventory.SLOT_OUTPUT, 116, 35));
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
		}
		
		this.addDataSlots(data);
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

            if (index < 3) {
                if (!this.moveItemStackTo(stackInSlot, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
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
	public boolean stillValid(@Nonnull Player player) {
		return this.container.stillValid(player);
	}

	public int getProcessingProgress() {
		return this.data.get(DATA_COOKING_PROGRESS);
	}

	public int getMaxProcessingTicks() {
		return this.data.get(DATA_COOKING_TOTAL_TIME);
	}
	
}
