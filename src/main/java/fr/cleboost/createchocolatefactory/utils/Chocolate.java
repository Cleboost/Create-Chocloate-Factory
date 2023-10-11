package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class Chocolate {
    private float strength = 100F;
    private float milk = 0F;
    private float sugar = 0F;
    private float cocoaButter = 0F;

    //private/public ??? color;
    public Chocolate(CompoundTag tag) {
        CompoundTag info = tag.getCompound(CreateChocolateFactory.MOD_ID).getCompound("chocolate");
        this.strength = info.getFloat("strength");
        this.milk = info.getFloat("milk");
        this.sugar = info.getFloat("sugar");
        this.cocoaButter = info.getFloat("cocoaButter");
    }

    public void addIngredients(String ingredient, int amount) {
        if (ingredient.matches("^(strenght|milk|sugar|cocoaButter)$")) return;
        if (
            boolToInt(ingredient.equals("strength")) * (amount + this.strength) > 100 ||
            boolToInt(ingredient.equals("milk")) * (amount + this.milk) > 100 &&
            boolToInt(ingredient.equals("sugar")) * (amount + this.sugar) > 100 &&
            boolToInt(ingredient.equals("cocoaButter")) * (amount + this.cocoaButter) > 100
        ) return;
        float coef = 1F - amount / 100F;
        this.strength = (coef * this.strength) + (amount * boolToInt(ingredient.equals("strength")));
        this.milk = (coef * this.milk) + (amount * boolToInt(ingredient.equals("milk")));
        this.sugar = (coef * this.sugar) + (amount * boolToInt(ingredient.equals("sugar")));
        this.cocoaButter = (coef * this.cocoaButter) + (amount * boolToInt(ingredient.equals("cocoaButter")));

    }

    private int boolToInt(Boolean bool) {
        return bool ? 1 : 0;
    }

    //Getter
    public Tag getTag() {
        CompoundTag data = new CompoundTag();
        data.putFloat("strength", this.strength);
        data.putFloat("milk", this.milk);
        data.putFloat("sugar", this.sugar);
        data.putFloat("cocoaButter", this.cocoaButter);
        return new CompoundTag().put(CreateChocolateFactory.MOD_ID, new CompoundTag().put("chocolate", data));
    }

    public int getCocoaButter() {
        return Math.round(this.cocoaButter);
    }

    public int getSugar() {
        return Math.round(this.sugar);
    }

    public int getMilk() {
        return Math.round(this.milk);
    }

    public int getStreght() {
        return Math.round(this.strength);
    }
}
