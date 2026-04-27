package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.FillingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CCFFillingRecipeGen extends FillingRecipeGen {
    GeneratedRecipe
    COCOA_BUTTER_BOWL = create("cocoa_butter_bowl", b -> b
        .require(Items.BOWL)
        .require(CCFFluids.COCOA_BUTTER.get(), 500)
        .output(CCFItems.COCOA_BUTTER_BOWL));

    public CCFFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
