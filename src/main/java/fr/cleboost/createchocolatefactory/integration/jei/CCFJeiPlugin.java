package fr.cleboost.createchocolatefactory.integration.jei;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

@JeiPlugin
public class CCFJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = CreateChocolateFactory.asResource("plugin");
    private static IJeiRuntime runtime;

    
    public static IJeiRuntime getRuntime() {
        return runtime;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChocolateMixerCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChocolateMouldingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DryingKitCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MacheteCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ChocolateMixerCategory.TYPE, Collections.singletonList(ChocolateMixerRecipe.INSTANCE));

        java.util.List<ChocolateMouldingRecipe> mouldingRecipes = new java.util.ArrayList<>();
        mouldingRecipes.add(new ChocolateMouldingRecipe(CCFItems.CHOCOLATE_EGG_PACK.getMouldItems().get()));
        mouldingRecipes.add(new ChocolateMouldingRecipe(CCFItems.CHOCOLATE_BUNNY_PACK.getMouldItems().get()));
        registration.addRecipes(ChocolateMouldingCategory.TYPE, mouldingRecipes);

        registration.addRecipes(DryingKitCategory.TYPE, Collections.singletonList(DryingKitRecipe.INSTANCE));
        registration.addRecipes(MacheteCategory.TYPE, Collections.singletonList(MacheteRecipe.INSTANCE));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CCFBlocks.CHOCOLATE_MIXER.get()), ChocolateMixerCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(com.simibubi.create.AllBlocks.SPOUT.get()), ChocolateMouldingCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(CCFBlocks.DRYING_KIT.get()), DryingKitCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(CCFItems.MACHETE.get()), MacheteCategory.TYPE);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime runtime) {
        CCFJeiPlugin.runtime = runtime;
    }
}
