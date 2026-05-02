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
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DryingKitCategory implements IRecipeCategory<DryingKitRecipe> {
    public static final RecipeType<DryingKitRecipe> TYPE = RecipeType.create(CreateChocolateFactory.MODID, "drying_kit", DryingKitRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable dayIcon;
    private final IDrawable skyIcon;
    private final IDrawable rainIcon;
    private final IDrawable netherIcon;

    public DryingKitCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(177, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(CCFBlocks.DRYING_KIT.get()));
        this.dayIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.SUNFLOWER));
        this.skyIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.GLASS));
        this.rainIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.WATER_BUCKET));
        this.netherIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.NETHERRACK));
    }

    @Override
    public @NotNull RecipeType<DryingKitRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return CCFLangs.RECIPE_DRYING_KIT.getComponent();
    }

    @Override
    public int getWidth() {
        return 177;
    }

    @Override
    public int getHeight() {
        return 60;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DryingKitRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 17)
                .addItemStack(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 131, 17)
                .addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(@NotNull DryingKitRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 26, 16);
        AllGuiTextures.JEI_SLOT.render(guiGraphics, 130, 16);
        AllGuiTextures.JEI_ARROW.render(guiGraphics, 70, 20);
        
        guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, "150s", 78, 10, 0x808080, false);

        dayIcon.draw(guiGraphics, 41, 41);
        skyIcon.draw(guiGraphics, 67, 40);
        rainIcon.draw(guiGraphics, 93, 40);
        netherIcon.draw(guiGraphics, 119, 40);
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull List<Component> getTooltipStrings(@NotNull DryingKitRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseY >= 40 && mouseY <= 56) {
            if (mouseX >= 41 && mouseX <= 57) return Collections.singletonList(CCFLangs.DRYING_KIT_DAY_INFO.getComponent());
            if (mouseX >= 67 && mouseX <= 83) return Collections.singletonList(CCFLangs.DRYING_KIT_SKY_INFO.getComponent());
            if (mouseX >= 93 && mouseX <= 109) return Collections.singletonList(CCFLangs.DRYING_KIT_RAIN_INFO.getComponent());
            if (mouseX >= 119 && mouseX <= 135) return Collections.singletonList(CCFLangs.DRYING_KIT_BONUS_TEXT.getComponent());
        }
        return Collections.emptyList();
    }
}
