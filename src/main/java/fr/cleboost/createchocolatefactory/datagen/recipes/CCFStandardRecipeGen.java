package fr.cleboost.createchocolatefactory.datagen.recipes;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;

import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateMouldItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CCFStandardRecipeGen extends RecipeProvider {
    public CCFStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    static List<ItemEntry<ChocolateMouldItem>> allChocolateMouldItems = new ArrayList<>(
        List.of(CCFItems.CHOCOLATE_EGG_PACK.getMouldItems())
    );

    @Override
    public void buildRecipes(@Nonnull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(Items.COCOA_BEANS),
                        RecipeCategory.FOOD,
                        CCFItems.COCOA_BEANS_ROASTED,
                        0.35f,
                        100)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CCFBlocks.DRYING_KIT)
                .define('B', Items.BAMBOO)
                .define('S', Items.STRING)
                .pattern("BBB")
                .pattern("BSB")
                .pattern("BBB")
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(consumer, "drying_kit");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,CCFItems.MACHETE)
                .define('I', AllItems.IRON_SHEET)
                .define('S', Items.STICK)
                .pattern("I")
                .pattern("I")
                .pattern("S")
                .unlockedBy("has_iron_sheet", has(AllItems.IRON_SHEET.get()))
                .save(consumer, "machete");

        for (ItemEntry<ChocolateMouldItem> item : allChocolateMouldItems) {
            stonecutterResultFromBase(consumer, RecipeCategory.MISC, item, AllItems.COPPER_SHEET);
        }
    }
}