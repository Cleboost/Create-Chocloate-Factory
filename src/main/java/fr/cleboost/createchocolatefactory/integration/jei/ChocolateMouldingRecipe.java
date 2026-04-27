package fr.cleboost.createchocolatefactory.integration.jei;

import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateMouldItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ChocolateMouldingRecipe {
    private final ChocolateMouldItem mould;
    private final int amount;

    public ChocolateMouldingRecipe(ChocolateMouldItem mould) {
        this.mould = mould;
        this.amount = mould.getResultItem().getAmount();
    }

    public ItemStack getMould() {
        return new ItemStack(mould);
    }

    public FluidStack getFluid() {
        return new FluidStack(CCFFluids.CHOCOLATE.get(), amount);
    }

    public ItemStack getOutput() {
        return new ItemStack(mould.getResultItem());
    }
}
