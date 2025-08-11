package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.core.Holder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TasteDataProvider implements DataProvider {
    private final PackOutput packOutput;
    private static final ArrayList<Taste> tastes = new ArrayList<>();

    static {
        register(TasteBuilder.from(Items.SWEET_BERRIES)
                .addEffect(MobEffects.REGENERATION, 20, 200, 1, 4));
        register(TasteBuilder.from(CCFItems.MINT_LEAF.get())
                .addEffect(MobEffects.DIG_SPEED, 40, 400, 1, 5));
    }

    public TasteDataProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    public static Taste register(TasteBuilder taste) {
        Taste built = taste.build();
        for (Taste t : TasteDataProvider.getTastes()) {
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

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        for (Taste taste : getTastes()) {
            String name = name(taste.getItemHolder().value());
            ResourceLocation location = CreateChocolateFactory.asResource("taste/" + name);
            
            // Convertir le Taste en JSON
            String json = convertTasteToJson(taste);
            
            // Écrire le fichier
            Path path = packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/" + location.getPath() + ".json");
            try {
                JsonElement jsonElement = JsonParser.parseString(json);
                DataProvider.saveStable(cachedOutput, jsonElement, path);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save taste data", e);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    private String convertTasteToJson(Taste taste) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"item\": \"").append(taste.getItemHolder().unwrapKey().orElseThrow().location()).append("\",\n");
        
        if (!taste.getEffects().isEmpty()) {
            json.append("  \"effects\": [\n");
            for (int i = 0; i < taste.getEffects().size(); i++) {
                Taste.ChocolateEffect effect = taste.getEffects().get(i);
                json.append("    {\n");
                json.append("      \"effect\": \"").append(effect.getEffect().unwrapKey().orElseThrow().location()).append("\",\n");
                json.append("      \"duration_min\": ").append(effect.getDuration_min()).append(",\n");
                json.append("      \"duration_max\": ").append(effect.getDuration_max()).append(",\n");
                json.append("      \"amplifier_min\": ").append(effect.getAmplifier_min()).append(",\n");
                json.append("      \"amplifier_max\": ").append(effect.getAmplifier_max()).append("\n");
                json.append("    }");
                if (i < taste.getEffects().size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("  ]\n");
        }
        
        json.append("}");
        return json.toString();
    }

    @Override
    public String getName() {
        return "Taste Data";
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

        public TasteBuilder addEffect(MobEffect effect, int durationMin, int durationMax, int amplifierMin, int amplifierMax) {
            this.effects.add(Taste.ChocolateEffect.create(effect, durationMin, durationMax, amplifierMin, amplifierMax));
            return this;
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
        String descriptionId = item.getDescriptionId();
        String[] parts = descriptionId.split("\\.");
        return parts[parts.length - 1]; // Prend le dernier élément
    }
}
