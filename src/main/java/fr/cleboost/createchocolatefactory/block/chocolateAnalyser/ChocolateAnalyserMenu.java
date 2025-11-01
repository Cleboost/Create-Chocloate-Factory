package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.gui.menu.MenuBase;

import fr.cleboost.createchocolatefactory.core.CCFMenu;
import fr.cleboost.createchocolatefactory.utils.OutputOnlySlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.entity.BlockEntity;

public class ChocolateAnalyserMenu extends MenuBase<ChocolateAnalyserBlockEntity> {
	private final Container container;
	private final ContainerData data;

	private static final int DATA_COOKING_PROGRESS = 2;
	private static final int DATA_COOKING_TOTAL_TIME = 3;

	private static final int OFFSET_X = 79-78+3;
	private static final int OFFSET_Y = 37-105+16;
	
	public ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, @Nonnull ChocolateAnalyserBlockEntity be) {
		super(type, id);
		checkContainerSize(be, 3);
		checkContainerDataCount(be, 4);
		this.container = be;
		this.data = be;
		
		this.addSlot(new Slot(be, ChocolateAnalyserInventory.SLOT_INPUT, 39+OFFSET_X, 66+OFFSET_Y)); //Filter Input
		this.addSlot(new Slot(be, ChocolateAnalyserInventory.SLOT_INPUT, 78+OFFSET_X, 27+OFFSET_Y)); //Chocolate Input
		this.addSlot(new Slot(be, ChocolateAnalyserInventory.SLOT_FUEL, 78+OFFSET_X, 105+OFFSET_Y));
		this.addSlot(new OutputOnlySlot(be, ChocolateAnalyserInventory.SLOT_OUTPUT, 117+OFFSET_X, 66+OFFSET_Y));
		
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
		this(type, id, inv, new SimpleContainer(4), new SimpleContainerData(5));
	}
	
	private ChocolateAnalyserMenu(MenuType<?> type, int id, Inventory inv, Container container, ContainerData data) {
		super(type, id);
		checkContainerSize(container, 4);
		checkContainerDataCount(data, 5);
		this.container = container;
		this.data = data;
		
		this.addSlot(new Slot(container, ChocolateAnalyserInventory.SLOT_INPUT, 39+OFFSET_X, 66+OFFSET_Y)); //Filter Input
		this.addSlot(new Slot(container, ChocolateAnalyserInventory.SLOT_INPUT, 78+OFFSET_X, 27+OFFSET_Y)); //Chocolate Input
		this.addSlot(new Slot(container, ChocolateAnalyserInventory.SLOT_FUEL, 78+OFFSET_X, 105+OFFSET_Y));
		this.addSlot(new OutputOnlySlot(container, ChocolateAnalyserInventory.SLOT_OUTPUT, 117+OFFSET_X, 66+OFFSET_Y));
		
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
	
	@Override
	protected void saveData(@Nonnull ChocolateAnalyserBlockEntity be) {

	}

	@Override
	protected void initAndReadInventory(@Nonnull ChocolateAnalyserBlockEntity be) {
	}
		
	@Override
	protected ChocolateAnalyserBlockEntity createOnClient(RegistryFriendlyByteBuf extraData) {
		BlockPos readBlockPos = extraData.readBlockPos();
		CompoundTag readNbt = extraData.readNbt();

		ClientLevel world = Minecraft.getInstance().level;
		BlockEntity blockEntity = world.getBlockEntity(readBlockPos);
		if (blockEntity instanceof ChocolateAnalyserBlockEntity c) {
			c.readClient(readNbt, extraData.registryAccess());
			return c;
		}

		return null;
	}
}
