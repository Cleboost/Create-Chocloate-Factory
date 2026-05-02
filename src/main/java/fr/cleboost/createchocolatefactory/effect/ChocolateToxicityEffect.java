package fr.cleboost.createchocolatefactory.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ChocolateToxicityEffect extends MobEffect {
    public ChocolateToxicityEffect() {
        super(MobEffectCategory.HARMFUL, 0x3F220F);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.kill();
        return true;
    }
}
