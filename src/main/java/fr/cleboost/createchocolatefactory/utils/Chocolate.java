package fr.cleboost.createchocolatefactory.utils;

import net.minecraft.world.item.ItemStack;

import java.nio.ByteBuffer;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateBaseItem;

public class Chocolate {
    private final float strength;
    private final float sugar;
    private final float cocoaButter;
    private final float milk;

    public Chocolate(float strength, float sugar, float cocoaButter, float milk) {
        float coef = (strength + sugar + cocoaButter + milk) / 4;
        this.strength = strength * coef;
        this.sugar = sugar * coef;
        this.cocoaButter = cocoaButter * coef;
        this.milk = milk * coef;
    }

    public Chocolate() {
        this.strength = 1;
        this.sugar = 0;
        this.cocoaButter = 0;
        this.milk = 0;
    }

    public Chocolate(ItemStack stack) {
        if ((stack.getItem() instanceof ChocolateBaseItem)) {
            this.strength = stack.get(CCFDataComponents.STRENGTH).floatValue();
            this.sugar = stack.get(CCFDataComponents.SUGAR).floatValue();
            this.cocoaButter = stack.get(CCFDataComponents.COCOA_BUTTER).floatValue();
            this.milk = stack.get(CCFDataComponents.MILK).floatValue();
        } else {
            this.strength = 1;
            this.sugar = 0;
            this.cocoaButter = 0;
            this.milk = 0;
        }
    }

    public float getStrength() {
        return strength;
    }

    public float getSugar() {
        return sugar;
    }

    public float getCocoaButter() {
        return cocoaButter;
    }

    public float getMilk() {
        return milk;
    }

    private byte castToByte(double x) {
        int y = (int) Math.round(x);
        return (byte) (Math.max(Math.min(y, 255), 0));
    }

    public int getColor() {
        //alpha red green blue
        byte[] bytes = {
                castToByte(255),
                castToByte((0.9 * Math.pow(this.strength + this.cocoaButter, 0.7) + 1.15 * Math.pow(this.milk + this.sugar, 1.11)) * 1.4 - 7),
                castToByte((0.405 * (this.strength + this.cocoaButter) + 0.5 * Math.pow(this.milk + this.sugar, 1.5)) * 0.5 - 13),
                castToByte((1.8 * (this.strength + this.cocoaButter) + 1.1 * Math.pow(this.milk + this.sugar, 1.6)) * 0.11 - 16)
        }; //do not touch any of this calculation pls
        return ByteBuffer.wrap(bytes).getInt();
    }

    public int getNutrition() {
        return Math.round(this.cocoaButter / 10);
    }

    public float getSaturationModifier() {
        return (1 + this.cocoaButter / 100) * (1 + this.sugar / 100);
    }

    public void save(ItemStack stack) {
        stack.set(CCFDataComponents.STRENGTH, this.strength);
        stack.set(CCFDataComponents.MILK, this.strength);
        stack.set(CCFDataComponents.COCOA_BUTTER, this.strength);
        stack.set(CCFDataComponents.SUGAR, this.strength);
    }
}
