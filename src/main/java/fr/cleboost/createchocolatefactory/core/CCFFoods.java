package fr.cleboost.createchocolatefactory.core;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class CCFFoods {
    public static final FoodProperties MINT = new FoodProperties.Builder().nutrition(0).fast()
            .saturationModifier(0.2f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 200), 0.1f).build();
    public static final FoodProperties ORANGE_WEDGE = new FoodProperties.Builder().nutrition(1).fast()
            .saturationModifier(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.8f).build();
    public static final FoodProperties CHOCOLATE_FAST = new FoodProperties.Builder().fast().alwaysEdible().build();
    public static final FoodProperties CHOCOLATE_SLOW = new FoodProperties.Builder().alwaysEdible().build();
    public static final FoodProperties COCOA_BUTTER = new FoodProperties.Builder().saturationModifier(2f).nutrition(6).build();
}
