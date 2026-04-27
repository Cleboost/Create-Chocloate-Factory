package fr.cleboost.createchocolatefactory.integration.jei;

import javax.annotation.Nonnull;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
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
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ChocolateMixerCategory.TYPE, Collections.singletonList(ChocolateMixerRecipe.INSTANCE));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CCFBlocks.CHOCOLATE_MIXER.get()), ChocolateMixerCategory.TYPE);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime runtime) {
        CCFJeiPlugin.runtime = runtime;
    }
}
