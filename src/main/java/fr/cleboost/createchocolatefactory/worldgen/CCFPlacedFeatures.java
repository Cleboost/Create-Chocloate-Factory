package fr.cleboost.createchocolatefactory.worldgen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CCFPlacedFeatures {
    // public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    //     var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
    // }

    public static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, CreateChocolateFactory.asResource(name));
    }

    // private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> config, List<PlacementModifier> modifiers) {
    //     context.register(key, new PlacedFeature(config, List.copyOf(modifiers)));
    // }
}
