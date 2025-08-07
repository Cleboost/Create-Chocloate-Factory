package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class CCFCompactingRecipeGen extends CompactingRecipeGen {
    GeneratedRecipe 
    
    COCOA_CAKE = create("cocoa_cake", b -> b
        .require(CCFItems.CHOCOLATE_LIQUOR)
        .duration(400)
        .output(CCFItems.COCOA_CAKE)
        .output(CCFFluids.COCOA_BUTTER.get(), 100));

    public CCFCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
