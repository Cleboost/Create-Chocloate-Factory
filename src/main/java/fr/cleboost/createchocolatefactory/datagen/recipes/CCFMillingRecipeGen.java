package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.MillingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class CCFMillingRecipeGen extends MillingRecipeGen {
    GeneratedRecipe 
    
    COCOA_HUSK = create("cocoa_husk", b -> b
        .require(CCFItems.COCOA_BEANS_ROASTED)
        .duration(200)
        .output(CCFItems.COCOA_NIBS)
        .output(CCFItems.COCOA_HUSK)),
    
    COCOA_POWDER = create("cocoa_powder", b -> b
        .require(CCFItems.COCOA_CAKE)
        .duration(200)
        .output(CCFItems.COCOA_POWDER,4));

    public CCFMillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
