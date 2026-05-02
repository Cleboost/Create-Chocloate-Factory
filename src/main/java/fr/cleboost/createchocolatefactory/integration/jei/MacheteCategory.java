package fr.cleboost.createchocolatefactory.integration.jei;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFLangs;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MacheteCategory implements IRecipeCategory<MacheteRecipe> {
    public static final RecipeType<MacheteRecipe> TYPE = RecipeType.create(CreateChocolateFactory.MODID, "machete_cutting", MacheteRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MacheteCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(177, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(fr.cleboost.createchocolatefactory.core.CCFItems.MACHETE.get()));
    }

    @Override
    public @NotNull RecipeType<MacheteRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return CCFLangs.RECIPE_MACHETE_CUTTING.getComponent();
    }

    @Override
    public int getWidth() {
        return 177;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MacheteRecipe recipe, @NotNull IFocusGroup focuses) {
        // Step 1
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 15).addItemStack(recipe.getMachete());
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 15).addItemStack(recipe.getPod());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 105, 15).addItemStacks(recipe.getOutputsStep1());

        // Step 2
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 50).addItemStack(recipe.getMachete());
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 50).addItemStack(recipe.getPod()); // Represents opened pod
        builder.addSlot(RecipeIngredientRole.OUTPUT, 105, 50).addItemStacks(recipe.getOutputsStep2());
    }

    @Override
    public void draw(@NotNull MacheteRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 9, 14);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 34, 14);
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 58, 18);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 104, 14);

        AllGuiTextures.JEI_SLOT.render(guiGraphics, 9, 49);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 34, 49);
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 58, 53);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 104, 49);

        guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, CCFLangs.MACHETE_STEP_1.getComponent(), 10, 4, 0x808080, false);
        guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, CCFLangs.MACHETE_STEP_2.getComponent(), 10, 39, 0x808080, false);
    }
}
