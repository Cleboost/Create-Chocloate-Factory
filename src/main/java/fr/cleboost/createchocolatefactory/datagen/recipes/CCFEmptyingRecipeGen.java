package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class CCFEmptyingRecipeGen extends EmptyingRecipeGen {
    /*GeneratedRecipe
    CHOCOLATE_BUCKET = create("chocolate_bucket", b -> b
        .require(Ingredient.of(CCFFluids.CHOCOLATE.get().getBucket()))
        .output(Items.BUCKET)
        .output(CCFFluids.CHOCOLATE.get(), 1000));*/

    public CCFEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
