package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChocolatePreparerBlockEntity extends SmartBlockEntity implements MenuProvider, TickableBlockEntity {
    public final ChocolatePreparerInventory inventory = new ChocolatePreparerInventory(this);

    private Chocolate chocolate = new Chocolate();

    public ChocolatePreparerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Chocolate getChocolate() {
        return this.chocolate;
    }

    public void setValue(Chocolate chocolate) {
        this.chocolate = chocolate;
        this.updateFilterFromValues();
        this.notifyUpdate();
    }

    public void loadValuesFromFilter() {
        ItemStack filterStack = inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
        if (!filterStack.is(CCFItems.CHOCOLATE_FILTER.get())) return;

        Chocolate chocolate = filterStack.get(CCFDataComponents.CHOCOLATE);
        if (chocolate != null) {
            setValue(chocolate);
        }
    }

    public void updateFilterFromValues() {
        ItemStack filterStack = inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
        if (!filterStack.is(CCFItems.CHOCOLATE_FILTER.get())) return;
        filterStack.set(CCFDataComponents.CHOCOLATE, this.chocolate);
        notifyUpdate();
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

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }
}
