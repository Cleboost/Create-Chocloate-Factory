package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolateAnalyserBlockEntity extends SmartBlockEntity implements MenuProvider, TickableBlockEntity {
    public final ChocolateAnalyserInventory inventory = new ChocolateAnalyserInventory(this);
    private int processingTicks = 0;
    private static final int TICKS_PER_PROCESS = 100;

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
        super.tick();
        
        Level level = this.getLevel();
        if (level == null || level.isClientSide) {
            return;
        }

        ItemStack chocolateStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_CHOCOLATE);
        ItemStack filterStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FILTER);
        ItemStack fuelStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FUEL);
        ItemStack outputStack = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_OUTPUT);
        
        boolean canProcess = canProcess(chocolateStack, filterStack, fuelStack, outputStack);

        if (canProcess) {
            processingTicks++;
            
            if (processingTicks >= TICKS_PER_PROCESS) {
                processChocolate();
                processingTicks = 0;
                notifyUpdate();
            } else {
                setChanged();
            }
        } else {
            if (processingTicks > 0) {
                processingTicks = 0;
                notifyUpdate();
            }
        }
    }

    private boolean canProcess(ItemStack chocolateStack, ItemStack filterStack, ItemStack fuelStack, ItemStack outputStack) {
        if (chocolateStack.isEmpty() || filterStack.isEmpty() || fuelStack.isEmpty() || !outputStack.isEmpty()) return false;
        return true;
    }

    private void processChocolate() {
        ItemStack chocolateInput = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_CHOCOLATE);
        ItemStack filterInput = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FILTER);
        ItemStack fuelInput = inventory.getStackInSlot(ChocolateAnalyserInventory.SLOT_FUEL);

        Chocolate chocolate = chocolateInput.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null) {
            return;
        }

        ItemStack filterOutput = new ItemStack(CCFItems.CHOCOLATE_FILTER.get());
        filterOutput.set(CCFDataComponents.CHOCOLATE, chocolate);

        inventory.setStackInSlot(ChocolateAnalyserInventory.SLOT_OUTPUT, filterOutput);

        chocolateInput.shrink(1);
        filterInput.shrink(1);
        fuelInput.shrink(1);

        setChanged();
    }

    public int getMaxProcessingTicks() {
        return TICKS_PER_PROCESS;
    }
    
    public int getProcessingProgress() {
        return processingTicks;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}
}
