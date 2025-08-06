package fr.cleboost.createchocolatefactory.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Chocolate {
    public static final Codec<Chocolate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("strength").forGetter(Chocolate::getStrength),
            Codec.FLOAT.fieldOf("sugar").forGetter(Chocolate::getSugar),
            Codec.FLOAT.fieldOf("cocoaButter").forGetter(Chocolate::getCocoaButter),
            Codec.FLOAT.fieldOf("milk").forGetter(Chocolate::getMilk)
    ).apply(instance, Chocolate::new));

    public static final StreamCodec<ByteBuf, Chocolate> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, Chocolate::getStrength,
            ByteBufCodecs.FLOAT, Chocolate::getSugar,
            ByteBufCodecs.FLOAT, Chocolate::getCocoaButter,
            ByteBufCodecs.FLOAT, Chocolate::getMilk,
            Chocolate::new
    );

    private final float strength;
    private final float sugar;
    private final float cocoaButter;
    private final float milk;

    public Chocolate(float strength, float sugar, float cocoaButter, float milk) {
        float coef = 1 / (strength + sugar + cocoaButter + milk);
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
                castToByte((0.9 * Math.pow((this.strength + this.cocoaButter) * 100, 0.7) + 1.15 * Math.pow((this.milk + this.sugar) * 100, 1.11)) * 1.4 - 7),
                castToByte((0.405 * (this.strength + this.cocoaButter) * 100 + 0.5 * Math.pow((this.milk + this.sugar) * 100, 1.5)) * 0.5 - 13),
                castToByte((1.8 * (this.strength + this.cocoaButter) * 100 + 1.1 * Math.pow((this.milk + this.sugar) * 100, 1.6)) * 0.11 - 16)
        }; //do not touch any of this calculation pls
        return ByteBuffer.wrap(bytes).getInt();
    }

    //will need balancing
    public int getNutrition() {
        return Math.round(this.cocoaButter / 10);
    }

    //will need balancing
    public float getSaturationModifier() {
        return (1 + this.cocoaButter / 100) * (1 + this.sugar / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chocolate chocolate)) return false;
        return Float.compare(strength, chocolate.strength) == 0 && Float.compare(sugar, chocolate.sugar) == 0 && Float.compare(cocoaButter, chocolate.cocoaButter) == 0 && Float.compare(milk, chocolate.milk) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength, sugar, cocoaButter, milk);
    }
}
