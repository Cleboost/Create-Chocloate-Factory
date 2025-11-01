package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolateAnalyserBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity, Container, ContainerData {
    public final ChocolateAnalyserInventory inventory = new ChocolateAnalyserInventory(this);
    private int processingTicks = 0;
    private static final int TICKS_PER_PROCESS = 100;
    
    private static final int DATA_LIT_TIME = 0;
    private static final int DATA_LIT_DURATION = 1;
    private static final int DATA_COOKING_PROGRESS = 2;
    private static final int DATA_COOKING_TOTAL_TIME = 3;
    
    public ChocolateAnalyserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Info Block");
    }

    @Nullable
    @Override
	public AbstractContainerMenu createMenu(int id, @Nonnull Inventory inv, @Nonnull Player player) {
		return ChocolateAnalyserMenu.create(id, inv, this);
	}

    @Override
    public void tick() {
        Level level = this.getLevel();
        if (level == null || level.isClientSide) {
            return;
        }

        ItemStack inputStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_INPUT);
        ItemStack fuelStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FUEL);
        ItemStack outputStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_OUTPUT);

        boolean canProcess = canProcess(inputStack, fuelStack, outputStack);

        if (canProcess) {
            processingTicks++;
            
            if (processingTicks >= TICKS_PER_PROCESS) {
                processChocolate();
                processingTicks = 0;
                setChanged();
                syncToClient();
            } else {
                setChanged();
            }
        } else {
            if (processingTicks > 0) {
                processingTicks = 0;
                setChanged();
                syncToClient();
            }
        }
    }

    private boolean canProcess(ItemStack inputStack, ItemStack fuelStack, ItemStack outputStack) {
        if (inputStack.isEmpty() || !inputStack.has(CCFDataComponents.CHOCOLATE)) {
            return false;
        }

        if (fuelStack.isEmpty() || fuelStack.getItem() != CCFItems.CHOCOLATE_FILTER.get()) {
            return false;
        }

        if (!outputStack.isEmpty()) {
            return false;
        }

        return true;
    }

    private void processChocolate() {
        ItemStack inputStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_INPUT);
        ItemStack fuelStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FUEL);

        if (inputStack.isEmpty() || !inputStack.has(CCFDataComponents.CHOCOLATE)) {
            return;
        }

        Chocolate chocolate = inputStack.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null) {
            return;
        }

        ItemStack filterStack = new ItemStack(CCFItems.CHOCOLATE_FILTER.get());
        filterStack.set(CCFDataComponents.CHOCOLATE, chocolate);

        inventory.setStackInSlot(ChocolateAnalyserInventory.SLOT_OUTPUT, filterStack);

        inputStack.shrink(1);
        fuelStack.shrink(1);

        setChanged();
    }


    public int getMaxProcessingTicks() {
        return TICKS_PER_PROCESS;
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("processingTicks", processingTicks);
    }

    @Override
    protected void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        processingTicks = tag.getInt("processingTicks");
    }

    @Override
    public @Nonnull CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Override
    public @Nonnull ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void syncToClient() {
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }
    
    public int getProcessingProgress() {
        return processingTicks;
    }
    
    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty() 
            && inventory.getStackInSlot(1).isEmpty() 
            && inventory.getStackInSlot(2).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    @Nonnull
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (!stack.isEmpty()) {
            ItemStack result = stack.split(amount);
            setChanged();
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, 
            (double)this.worldPosition.getY() + 0.5D, 
            (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < 3; i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    
    @Override
    public int get(int index) {
        return switch (index) {
            case DATA_LIT_TIME -> processingTicks > 0 ? 1 : 0;
            case DATA_LIT_DURATION -> TICKS_PER_PROCESS;
            case DATA_COOKING_PROGRESS -> processingTicks;
            case DATA_COOKING_TOTAL_TIME -> TICKS_PER_PROCESS;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
    }

    @Override
    public int getCount() {
        return 4;
    }
}
