package fr.cleboost.createchocolatefactory.integration.jei;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ChocolateMixerCategory implements IRecipeCategory<ChocolateMixerRecipe> {
    public static final RecipeType<ChocolateMixerRecipe> TYPE = RecipeType.create(CreateChocolateFactory.MODID, "chocolate_mixer", ChocolateMixerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ChocolateMixerCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(177, 70);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(CCFBlocks.CHOCOLATE_MIXER.get()));
    }

    @Override
    public RecipeType<ChocolateMixerRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.createchocolatefactory.chocolate_mixer");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChocolateMixerRecipe recipe, IFocusGroup focuses) {
        // Items
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 9)
                .addIngredients(recipe.getItemIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 31)
                .addIngredients(recipe.getItemIngredients().get(1));

        // Fluids
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 9)
                .addIngredients(NeoForgeTypes.FLUID_STACK, recipe.getFluidIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 31)
                .addIngredients(NeoForgeTypes.FLUID_STACK, recipe.getFluidIngredients().get(1));

        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 20)
                .addIngredients(NeoForgeTypes.FLUID_STACK, java.util.Collections.singletonList(recipe.getOutput()));
    }

    @Override
    public void draw(ChocolateMixerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 14, 8);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 14, 30);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 44, 8);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 44, 30);
        
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 133, 19);
        
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 80, 24);
    }
}
