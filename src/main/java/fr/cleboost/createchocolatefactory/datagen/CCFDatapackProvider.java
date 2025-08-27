package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFRegistryKeys;
import fr.cleboost.createchocolatefactory.worldgen.CCFBiomeModifiers;
import fr.cleboost.createchocolatefactory.worldgen.CCFConfiguredFeatures;
import fr.cleboost.createchocolatefactory.worldgen.CCFPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CCFDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(CCFRegistryKeys.TASTE_REGISTRY_KEY, TasteProvider::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, CCFConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, CCFPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CCFBiomeModifiers::bootstrap);

    public CCFDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CreateChocolateFactory.MODID));
    }
}
