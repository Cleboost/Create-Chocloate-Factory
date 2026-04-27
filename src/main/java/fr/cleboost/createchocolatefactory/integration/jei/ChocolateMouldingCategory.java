package fr.cleboost.createchocolatefactory.integration.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
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

import java.util.Collections;

public class ChocolateMouldingCategory implements IRecipeCategory<ChocolateMouldingRecipe> {
    public static final RecipeType<ChocolateMouldingRecipe> TYPE = RecipeType.create(CreateChocolateFactory.MODID, "chocolate_moulding", ChocolateMouldingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ChocolateMouldingCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(177, 50);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AllBlocks.SPOUT.get()));
    }

    @Override
    public RecipeType<ChocolateMouldingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return fr.cleboost.createchocolatefactory.core.CCFLangs.RECIPE_CHOCOLATE_MOULDING.getComponent();
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
    public void setRecipe(IRecipeLayoutBuilder builder, ChocolateMouldingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 17)
                .addItemStack(recipe.getMould());
        
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 17)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Collections.singletonList(recipe.getFluid()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 131, 17)
                .addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(ChocolateMouldingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 26, 16);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 50, 16);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 130, 16);
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 85, 18);
    }
}
