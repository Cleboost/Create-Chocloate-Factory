package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Chocolate {
    private float strength = 50F;
    private float milk = 50F;
    private float sugar = 0F;
    private float cocoaButter = 0F;

    public Chocolate(CompoundTag tag) {
        if (!tag.contains(CreateChocolateFactory.MOD_ID)) return;
        if (!tag.getCompound(CreateChocolateFactory.MOD_ID).contains("chocolate")) return;
        CompoundTag info = tag.getCompound(CreateChocolateFactory.MOD_ID).getCompound("chocolate");
        this.strength = info.getFloat("strength");
        this.milk = info.getFloat("milk");
        this.sugar = info.getFloat("sugar");
        this.cocoaButter = info.getFloat("cocoaButter");
    }

    public Chocolate() {
    }

    public void addIngredients(String ingredient, int amount) {
        if (ingredient.matches("^(strenght|milk|sugar|cocoaButter)$")) return;
        if (boolToInt(ingredient.equals("strength")) * (amount + this.strength) > 100 || boolToInt(ingredient.equals("milk")) * (amount + this.milk) > 100 && boolToInt(ingredient.equals("sugar")) * (amount + this.sugar) > 100 && boolToInt(ingredient.equals("cocoaButter")) * (amount + this.cocoaButter) > 100)
            return;
        float coef = 1F - amount / 100F;
        this.strength = (coef * this.strength) + (amount * boolToInt(ingredient.equals("strength")));
        this.milk = (coef * this.milk) + (amount * boolToInt(ingredient.equals("milk")));
        this.sugar = (coef * this.sugar) + (amount * boolToInt(ingredient.equals("sugar")));
        this.cocoaButter = (coef * this.cocoaButter) + (amount * boolToInt(ingredient.equals("cocoaButter")));
    }

    //Getter
    public Tag getTag() {
        CompoundTag data = new CompoundTag();
        data.putFloat("strength", this.strength);
        data.putFloat("milk", this.milk);
        data.putFloat("sugar", this.sugar);
        data.putFloat("cocoaButter", this.cocoaButter);
        return new CompoundTag().put(CreateChocolateFactory.MOD_ID, Objects.requireNonNull(new CompoundTag().put("chocolate", data)));
    }

    public int getColor() {
        //alpha red green blue
        byte[] bytes = {
                120,
                castToByte((0.9*Math.pow(this.strength,0.8)+1.15*Math.pow(this.milk,1.11))*1.4-7),
                castToByte((0.405*this.strength+0.5*Math.pow(this.milk, 1.5))*0.5-13),
                castToByte((1.8*this.strength+1.1*Math.pow(this.milk,1.6))*0.11-16)
        }; //do not touch any of this calculation pls
        return ByteBuffer.wrap(bytes).getInt();
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

    private byte castToByte(double x) {
        int y = (int) Math.round(x);
        return (byte) (Math.max(Math.min(y,255),0));
    }
    private int boolToInt(Boolean bool) {
        return bool ? 1 : 0;
    }
}
