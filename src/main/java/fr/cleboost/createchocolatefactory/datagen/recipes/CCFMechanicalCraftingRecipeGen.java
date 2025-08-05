package fr.cleboost.createchocolatefactory.datagen.recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class CCFMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
    GeneratedRecipe

    CHOCOLATE_MIXER = create(CCFBlocks.CHOCOLATE_MIXER::get).returns(1).recipe(b -> b
            .key('E', AllItems.ELECTRON_TUBE.get())
            .key('P', AllItems.PRECISION_MECHANISM.get())
            .key('C', AllBlocks.COGWHEEL.get())
            .key('S', AllBlocks.SHAFT.get())
            .key('T', AllBlocks.FLUID_TANK.get())
            .key('B', AllBlocks.BRASS_CASING.get())
            .key('F', AllBlocks.MECHANICAL_PUMP.get())
            .key('W', CCFItems.BRASS_WHISK.get())
            .patternLine(" E ")
            .patternLine("PCS")
            .patternLine("TBF")
            .patternLine(" W ")
    );

    public CCFMechanicalCraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateChocolateFactory.MODID);
    }
}
