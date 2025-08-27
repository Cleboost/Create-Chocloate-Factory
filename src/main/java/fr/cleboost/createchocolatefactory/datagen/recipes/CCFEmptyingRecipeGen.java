package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class CCFEmptyingRecipeGen extends EmptyingRecipeGen {
    GeneratedRecipe
    COCOA_BUTTER_BOWL = create("cocoa_butter_bowl", b -> b
        .require(Ingredient.of(CCFItems.COCOA_BUTTER_BOWL.get()))
        .output(Items.BOWL)
        .output(CCFFluids.COCOA_BUTTER.get(), 500));

    public CCFEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
