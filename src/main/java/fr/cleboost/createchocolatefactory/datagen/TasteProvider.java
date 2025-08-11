package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.core.CCFRegistryKeys;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TasteProvider /*implements DataProvider*/ {
    private static final ArrayList<Taste> tastes = new ArrayList<>();

    static {
        register(TasteBuilder.from(Items.SWEET_BERRIES)
                .addEffect(MobEffects.REGENERATION, 40, 200, 1, 4));
        register(TasteBuilder.from(CCFItems.MINT_LEAF.get())
                .addEffect(MobEffects.DIG_SPEED, 40, 400, 2, 6));
        register(TasteBuilder.from(CCFItems.CARAMEL_NUGGET.get())
                .addEffect(MobEffects.ABSORPTION, 200, 1000, 2, 5));
        register(TasteBuilder.from(Items.GLOW_BERRIES)
                .addEffect(MobEffects.GLOWING, 100, 2000, 1, 2));
        register(TasteBuilder.from(Items.HONEY_BOTTLE)
                .addEffect(MobEffects.HEAL, 20, 100, 2, 10));
        register(TasteBuilder.from(Items.CORNFLOWER)
                .addEffect(MobEffects.SATURATION, 1000, 5000, 1, 3));
        register(TasteBuilder.from(CCFItems.HAZELNUT.get())
                .addEffect(MobEffects.JUMP, 200, 600, 1, 4));
        register(TasteBuilder.from(Items.CHORUS_FRUIT)
                .addEffect(MobEffects.SLOW_FALLING, 100, 400, 3, 6)
                .addEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 300, 1, 4));
    }
    public static Taste register(TasteBuilder taste) {
        Taste built = taste.build();
        for (Taste t : TasteProvider.getTastes()) {
            if (t.getItemHolder().equals(built.getItemHolder())) {
                throw new IllegalStateException("Taste " + built.getItemHolder().getRegisteredName() + " already exists");
            }
        }
        tastes.add(built);
        return built;
    }

    protected static List<Taste> getTastes() {
        return tastes;
    }

    public static void bootstrap(BootstrapContext<Taste> context) {
        for (Taste taste : TasteProvider.getTastes()) {
            context.register(ResourceKey.create(
                    CCFRegistryKeys.TASTE_REGISTRY_KEY,
                    Taste.getAssetName(taste.getItem())
            ), taste);
        }
    }

    /*@Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return null;
    }*/

    //@Override
    public String getName() {
        return "Taste (" + CreateChocolateFactory.MODID + ")";
    }

    public static class TasteBuilder {
        private final Item item;
        private final ArrayList<Taste.ChocolateEffect> effects;

        private TasteBuilder(Item item) {
            this.item = item;
            this.effects = new ArrayList<>();
        }

        public static TasteBuilder from(Item item) {
            return new TasteBuilder(item);
        }

        public TasteBuilder addEffect(Holder<MobEffect> effect, int durationMin, int durationMax, int amplifierMin, int amplifierMax) {
            this.effects.add(Taste.ChocolateEffect.create(effect, durationMin, durationMax, amplifierMin, amplifierMax));
            return this;
        }

        public Taste build() {
            return Taste.create(this.item, this.effects.toArray(new Taste.ChocolateEffect[0]));
        }

    }

    private static String name(Item item) {
        return item.getDescriptionId().split("\\.")[2];
    }
}
