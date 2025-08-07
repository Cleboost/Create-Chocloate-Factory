package fr.cleboost.createchocolatefactory.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Taste {
    public static final Codec<Taste> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("item").forGetter(Taste::getItem),
            Codec.list(ChocolateEffect.CODEC).optionalFieldOf("effects", List.of()).forGetter(Taste::getEffects)
    ).apply(instance, Taste::new));

    private final Holder<Item> item;
    private final List<ChocolateEffect> effects;

    public Taste(Holder<Item> item, List<ChocolateEffect> effects) {
        this.item = item;
        this.effects = effects;
    }

    public static Taste create(Item item, ChocolateEffect... effects) {
        return new Taste(BuiltInRegistries.ITEM.wrapAsHolder(item), List.of(effects));
    }

    public Holder<Item> getItem() {
        return item;
    }

    public List<ChocolateEffect> getEffects() {
        return effects;
    }

    public boolean hasEffects() {
        return effects.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Taste taste)) return false;
        return Objects.equals(item, taste.item) && Objects.deepEquals(effects, taste.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, effects);
    }

    public static ResourceLocation getFromItem(Item item) {
        return CreateChocolateFactory.asResource(name(item));
    }

    private static String name(Item item) {
        return item.getDescriptionId().split("\\.")[2];
    }

    public static class ChocolateEffect {
        public static final Codec<ChocolateEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryFixedCodec.create(Registries.MOB_EFFECT).fieldOf("effect").forGetter(ChocolateEffect::getEffect),
                Codec.INT.fieldOf("duration_min").forGetter(ChocolateEffect::getDuration_min),
                Codec.INT.fieldOf("duration_max").forGetter(ChocolateEffect::getDuration_max),
                Codec.BYTE.fieldOf("amplifier_min").forGetter(ChocolateEffect::getAmplifier_min),
                Codec.BYTE.fieldOf("amplifier_max").forGetter(ChocolateEffect::getAmplifier_max)
        ).apply(instance, ChocolateEffect::new));

        private final Holder<MobEffect> effect;
        private final int duration_min;
        private final int duration_max;
        private final byte amplifier_min;
        private final byte amplifier_max;


        public ChocolateEffect(Holder<MobEffect> effect, int durationMin, int durationMax, byte amplifierMin, byte amplifierMax) {
            this.effect = effect;
            this.duration_min = Math.max(durationMin, 0);
            this.duration_max = Math.max(durationMax, durationMin);
            this.amplifier_min = (byte) Math.clamp(amplifierMin, 0, 255);
            this.amplifier_max = (byte) Math.clamp(amplifierMax, amplifierMin, 255);
        }

        public static ChocolateEffect create(MobEffect effect, int durationMin, int durationMax, int amplifierMin, int amplifierMax) {
            return new ChocolateEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), durationMin, durationMax, (byte) amplifierMin, (byte) amplifierMax);
        }
        public static ChocolateEffect create(Holder<MobEffect> effect, int durationMin, int durationMax, int amplifierMin, int amplifierMax) {
            return new ChocolateEffect(effect, durationMin, durationMax, (byte) amplifierMin, (byte) amplifierMax);
        }

        public int getDuration_max() {
            return duration_max;
        }

        public int getDuration_min() {
            return duration_min;
        }

        public byte getAmplifier_max() {
            return amplifier_max;
        }

        public byte getAmplifier_min() {
            return amplifier_min;
        }

        public Holder<MobEffect> getEffect() {
            return effect;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChocolateEffect that)) return false;
            return duration_max == that.duration_max && duration_min == that.duration_min && amplifier_max == that.amplifier_max && amplifier_min == that.amplifier_min && Objects.equals(effect, that.effect);
        }

        @Override
        public int hashCode() {
            return Objects.hash(duration_max, duration_min, amplifier_max, amplifier_min, effect);
        }
    }
}
