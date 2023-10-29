package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Chocolate {
    private float strength = 70F;
    private float milk = 30F;
    private float sugar = 0F;
    private float cocoaButter = 0F;

    public Chocolate(CompoundTag tag) {
        if (!Chocolate.hasChocolateProperties(tag)) return;
        CompoundTag info = tag.getCompound(CreateChocolateFactory.MOD_ID).getCompound("chocolate");
        this.strength = info.getFloat("strength");
        this.milk = info.getFloat("milk");
        this.sugar = info.getFloat("sugar");
        this.cocoaButter = info.getFloat("cocoaButter");
    }
    public Chocolate() {
    }
    public Chocolate(boolean creative) {
        if (!creative) return;
        Random rand = new Random();
        float sum = 0F;
        this.strength = rand.nextFloat(0F,101F);
        sum+=this.strength;
        this.milk = rand.nextFloat(0F,101F-sum);
        sum+=this.milk;
        this.sugar = rand.nextFloat(0F,101F-sum);
        sum+=this.sugar;
        this.cocoaButter = rand.nextFloat(0F,101F-sum);
    }

    public void addIngredients(String ingredient, int amount) {
        if (!ingredient.matches("^(strenght|milk|sugar|cocoaButter)$")) return;
        if (boolToInt(ingredient.equals("strength")) * (amount + this.strength) > 100 || boolToInt(ingredient.equals("milk")) * (amount + this.milk) > 100 && boolToInt(ingredient.equals("sugar")) * (amount + this.sugar) > 100 && boolToInt(ingredient.equals("cocoaButter")) * (amount + this.cocoaButter) > 100)
            return;
        float coef = 1F - amount / 100F;
        this.strength = (coef * this.strength) + (amount * boolToInt(ingredient.equals("strength")));
        this.milk = (coef * this.milk) + (amount * boolToInt(ingredient.equals("milk")));
        this.sugar = (coef * this.sugar) + (amount * boolToInt(ingredient.equals("sugar")));
        this.cocoaButter = (coef * this.cocoaButter) + (amount * boolToInt(ingredient.equals("cocoaButter")));
    }

    //Getter
    public void saveTag(ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag data = new CompoundTag();
        data.putFloat("strength", this.strength);
        data.putFloat("milk", this.milk);
        data.putFloat("sugar", this.sugar);
        data.putFloat("cocoaButter", this.cocoaButter);
        if (tag.contains(CreateChocolateFactory.MOD_ID)) {
            if (tag.getCompound(CreateChocolateFactory.MOD_ID).contains("chocolate")) tag.getCompound(CreateChocolateFactory.MOD_ID).remove("chocolate");
            tag.getCompound(CreateChocolateFactory.MOD_ID).put("chocolate", data);
            return;
        }
        CompoundTag chTag = new CompoundTag();
        chTag.put("chocolate", data);
        tag.put(CreateChocolateFactory.MOD_ID, chTag);
    }

    public int getColor() {
        //alpha red green blue
        byte[] bytes = {
                120,
                castToByte((0.9*Math.pow(this.strength+this.cocoaButter,0.7)+1.15*Math.pow(this.milk+this.sugar,1.11))*1.4-7),
                castToByte((0.405*(this.strength+this.cocoaButter)+0.5*Math.pow(this.milk+this.sugar, 1.5))*0.5-13),
                castToByte((1.8*(this.strength+this.cocoaButter)+1.1*Math.pow(this.milk+this.sugar,1.6))*0.11-16)
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
    public static boolean hasChocolateProperties(CompoundTag tag) {
        if (tag == null || tag.isEmpty()) return false;
        if (!tag.contains(CreateChocolateFactory.MOD_ID)) return false;
        if (!tag.getCompound(CreateChocolateFactory.MOD_ID).contains("chocolate")) return false;
        CompoundTag chocolateProp = tag.getCompound(CreateChocolateFactory.MOD_ID).getCompound("chocolate");
        if (!chocolateProp.contains("strength") || !chocolateProp.contains("milk") || !chocolateProp.contains("sugar") || !chocolateProp.contains("cocoaButter")) {
            tag.getCompound(CreateChocolateFactory.MOD_ID).remove("chocolate");
            return false;
        }
        return true;
    }

    //values here might change
    public int getNutrition() {
        return Math.round(this.cocoaButter/10);
    }
    public float getSaturationModifier() {
        return (1+this.cocoaButter/100)*(1+this.milk/100);
    }
}
