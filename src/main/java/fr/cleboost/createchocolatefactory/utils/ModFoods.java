package fr.cleboost.createchocolatefactory.utils;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties DARK_CHOCOLATE = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300), 0.3f).build();
    public static final FoodProperties BROWN_CHOCOLATE = new FoodProperties.Builder().nutrition(4)
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.3f).build();
    public static final FoodProperties MINT = new FoodProperties.Builder().nutrition(0).fast()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();
    public static final FoodProperties ORANGE_WEDGE = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.8f).build();
    public static final FoodProperties BARS = new FoodProperties.Builder().nutrition(1)
            .saturationMod(0.2f).fast().build();
}
