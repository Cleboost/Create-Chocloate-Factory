package fr.cleboost.createchocolatefactory.item.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuelItem extends Item {
    private int burnTime = 0;

    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }
    
    @Override
    public int getBurnTime(@NotNull ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
