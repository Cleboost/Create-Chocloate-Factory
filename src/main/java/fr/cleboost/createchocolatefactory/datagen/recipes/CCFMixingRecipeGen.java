package fr.cleboost.createchocolatefactory.datagen.recipes;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class CCFMixingRecipeGen extends MixingRecipeGen {
    GeneratedRecipe

    COCOA_LIQUOR = create("cocoa_liquor", b -> b
            .require(CCFItems.COCOA_NIBS)
            .require(CCFItems.COCOA_NIBS)
            .require(CCFItems.COCOA_NIBS)
            .require(CCFItems.COCOA_NIBS)
            .require(Tags.Fluids.WATER, 20)
            .output(CCFItems.CHOCOLATE_LIQUOR)
            .requiresHeat(HeatCondition.HEATED)
    ),

    CARAMEL = create("caramel", b -> b
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(Tags.Fluids.WATER, 100)
            .output(CCFItems.CARAMEL)
            .requiresHeat(HeatCondition.HEATED)
    ),

    COOKIE = create("cookie", b -> b
            .require(AllItems.WHEAT_FLOUR.get())
            .require(AllItems.WHEAT_FLOUR.get())
            .require(CCFFluids.CHOCOLATE.get(), 50)
            .output(CCFItems.COOKIE, 8)
    );

    public CCFMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
