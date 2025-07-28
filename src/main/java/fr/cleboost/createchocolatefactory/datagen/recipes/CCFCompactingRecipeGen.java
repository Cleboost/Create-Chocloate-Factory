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
    
    COCOA_CAKE_BUTTER = create("cocoa_cake_butter", b -> b
        .require(CCFItems.COCOA_NIBS) // need 4 nibs for 1 cake
        .require(CCFItems.COCOA_NIBS)
        .require(CCFItems.COCOA_NIBS)
        .require(CCFItems.COCOA_NIBS)
        .duration(400)
        .output(CCFItems.COCOA_CAKE)
        .output(CCFFluids.COCOA_BUTTER.get(), 250));

    public CCFCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
