package fr.cleboost.createchocolatefactory.datagen.recipes;

import com.simibubi.create.AllItems;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class CCFStandardRecipeGen extends RecipeProvider {
    public CCFStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(@Nonnull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(Items.COCOA_BEANS),
                        RecipeCategory.FOOD,
                        CCFItems.COCOA_BEANS_ROASTED,
                        0.35f,
                        100
                )
                .unlockedBy("has_cocoa_beans", has(Items.COCOA_BEANS))
                .save(consumer, "cocoa_beans_roasted");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CCFItems.BRASS_WHISK)
                .define('A', AllItems.ANDESITE_ALLOY.get())
                .define('B', AllItems.BRASS_SHEET.get())
                .pattern(" A ")
                .pattern("BAB")
                .pattern("BBB")
                .unlockedBy("has_brass_sheet", has(AllItems.BRASS_SHEET.get()))
                .save(consumer, "brass_whisk");
    }
}