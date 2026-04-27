package fr.cleboost.createchocolatefactory.integration.jei;

import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChocolateMixerRecipe {
    public static final ChocolateMixerRecipe INSTANCE = new ChocolateMixerRecipe();

    public List<Ingredient> getItemIngredients() {
        return Arrays.asList(
                Ingredient.of(CCFItems.COCOA_POWDER.get()),
                Ingredient.of(Items.SUGAR)
        );
    }

    public List<List<FluidStack>> getFluidIngredients() {
        List<FluidStack> cocoaButter = Collections.singletonList(new FluidStack(CCFFluids.COCOA_BUTTER.get(), 100));
        
        List<FluidStack> milk = BuiltInRegistries.FLUID.getOrCreateTag(Tags.Fluids.MILK)
                .stream()
                .map(holder -> new FluidStack(holder.value(), 100))
                .collect(Collectors.toList());
        
        if (milk.isEmpty()) {
            // Fallback if no milk fluid is registered (though Create usually adds it)
            // We can't easily find a vanilla milk fluid because it doesn't exist.
            // But Tags.Fluids.MILK should have something if the mod is running with Create.
        }

        return Arrays.asList(cocoaButter, milk);
    }

    public FluidStack getOutput() {
        return new FluidStack(CCFFluids.CHOCOLATE.get(), 1000);
    }
}
