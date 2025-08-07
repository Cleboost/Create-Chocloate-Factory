package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.core.CCFRegistryKeys;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TasteProvider {
    private static final ArrayList<Taste> tastes = new ArrayList<>();

    static {
        register(TasteBuilder.from(Items.SWEET_BERRIES)
                .addEffect(MobEffects.REGENERATION, 20, 200, 1, 4));
        register(TasteBuilder.from(CCFItems.MINT_LEAF.get())
                .addEffect(MobEffects.DIG_SPEED, 40, 400, 1, 5));
    }

    public static Taste register(TasteBuilder taste) {
        Taste built = taste.build();
        for (Taste t : TasteProvider.getTastes()) {
            if (t.getItem().equals(built.getItem())) {
                throw new IllegalStateException("Taste " + built.getItem().getRegisteredName() + " already exists");
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
                    ResourceLocation.fromNamespaceAndPath("", name(taste.getItem().value()))
            ), taste);
        }
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
