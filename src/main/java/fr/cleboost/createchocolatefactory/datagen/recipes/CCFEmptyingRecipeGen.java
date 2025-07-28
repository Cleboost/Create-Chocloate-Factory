package fr.cleboost.createchocolatefactory.datagen.recipes;

import java.util.concurrent.CompletableFuture;

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CCFEmptyingRecipeGen extends EmptyingRecipeGen {
    GeneratedRecipe 
    
    CHOCOLATE_BUCKET = create("chocolate_bucket", b -> b
        .require(CCFItems.CHOCOLATE_BUCKET)
        .output(Items.GLASS_BOTTLE)
        .output(CCFFluids.CHOCOLATE.get(), 250));

    public CCFEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
