package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.CuttingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class CCFCuttingRecipeGen extends CuttingRecipeGen {
    GeneratedRecipe 
    
    COCOA_BEANS_WET = create("cocoa_beans_wet", b -> b
        .require(CCFBlocks.COCOA_POD)
        .duration(400)
        .output(CCFItems.COCOA_BEANS_WET)
        .output(CCFItems.COCOA_BARK, 5));

    public CCFCuttingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
