package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.WashingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CCFWashingRecipeGen extends WashingRecipeGen {
    GeneratedRecipe 
    
    COCOA_BEANS = create("cocoa_beans", b -> b
        .require(CCFItems.COCOA_BEANS_DIRTY)
        .duration(200)
        .output(Items.COCOA_BEANS)
        .output(Items.SHORT_GRASS));

    public CCFWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
