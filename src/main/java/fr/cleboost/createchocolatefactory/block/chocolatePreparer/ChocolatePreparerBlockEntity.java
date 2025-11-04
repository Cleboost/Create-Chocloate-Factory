package fr.cleboost.createchocolatefactory.block.chocolatePreparer;

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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ChocolatePreparerBlockEntity extends SmartBlockEntity implements MenuProvider, TickableBlockEntity {
    public final ChocolatePreparerInventory inventory = new ChocolatePreparerInventory(this);
    private int processingTicks = 0;
    private static final int TICKS_PER_PROCESS = 100;
    
    private int strength = 0;
    private int sugar = 0;
    private int cocoaButter = 0;
    private int milk = 0;

    public ChocolatePreparerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    public int getStrength() {
        return strength;
    }
    
    public int getSugar() {
        return sugar;
    }
    
    public int getCocoaButter() {
        return cocoaButter;
    }
    
    public int getMilk() {
        return milk;
    }
    
    public void setValues(int strength, int sugar, int cocoaButter, int milk) {
        this.strength = strength;
        this.sugar = sugar;
        this.cocoaButter = cocoaButter;
        this.milk = milk;
        this.updateFilterFromValues();
        this.setChanged();
        this.notifyUpdate();
    }
    
    public void loadValuesFromFilter() {
        ItemStack filterStack = inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
        if (filterStack.isEmpty() || filterStack.getItem() != CCFItems.CHOCOLATE_FILTER.get()) {
            return;
        }
        
        Chocolate chocolate = filterStack.get(CCFDataComponents.CHOCOLATE);
        if (chocolate != null) {
            this.strength = (int) (chocolate.getStrength() * 100);
            this.sugar = (int) (chocolate.getSugar() * 100);
            this.cocoaButter = (int) (chocolate.getCocoaButter() * 100);
            this.milk = (int) (chocolate.getMilk() * 100);
            setChanged();
            notifyUpdate();
        }
    }
    
    public void updateFilterFromValues() {
        ItemStack filterStack = inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
        if (filterStack.isEmpty() || filterStack.getItem() != CCFItems.CHOCOLATE_FILTER.get()) {
            return;
        }
        
        Chocolate newChocolate = new Chocolate(
            (float) strength,
            (float) sugar,
            (float) cocoaButter,
            (float) milk
        );
        
        filterStack.set(CCFDataComponents.CHOCOLATE, newChocolate);
        setChanged();
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
		return ChocolatePreparerMenu.create(id, inv, this);
	}

    @Override
    public void tick() {
        super.tick();
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
