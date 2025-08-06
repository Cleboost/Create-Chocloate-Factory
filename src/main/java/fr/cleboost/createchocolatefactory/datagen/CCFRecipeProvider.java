package fr.cleboost.createchocolatefactory.datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;

import fr.cleboost.createchocolatefactory.datagen.recipes.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

public final class CCFRecipeProvider extends RecipeProvider {

	static final List<RecipeProvider> GENERATORS = new ArrayList<>();

	public CCFRecipeProvider(PackOutput output,CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}


	public static void registerAllProcessing(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		GENERATORS.add(new CCFMillingRecipeGen(output, registries));
		GENERATORS.add(new CCFCompactingRecipeGen(output, registries));
		GENERATORS.add(new CCFCuttingRecipeGen(output, registries));
		GENERATORS.add(new CCFEmptyingRecipeGen(output, registries));
		GENERATORS.add(new CCFWashingRecipeGen(output, registries));
		GENERATORS.add(new CCFMechanicalCraftingRecipeGen(output, registries));
		GENERATORS.add(new CCFMixingRecipeGen(output, registries));

		gen.addProvider(true, new DataProvider() {
			@Override
			public String getName() {
				return "Create Chocolate Factory's Processing Recipes";
			}

			@Override
			public CompletableFuture<?> run(@Nonnull CachedOutput dc) {
				return CompletableFuture.allOf(GENERATORS.stream()
					.map(gen -> gen.run(dc))
					.toArray(CompletableFuture[]::new));
			}
		});
	}
}