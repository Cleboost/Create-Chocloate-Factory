package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFRegistryKeys;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CCFDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final ResourceKey<Taste> TASTE = ResourceKey.create(
            CCFRegistryKeys.TASTE_REGISTRY_KEY,
            CreateChocolateFactory.asResource("taste")
    );

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(CCFRegistryKeys.TASTE_REGISTRY_KEY, TasteProvider::bootstrap);

    public CCFDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CreateChocolateFactory.MODID));
    }
}
