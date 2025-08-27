package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.jline.reader.Widget;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;

import fr.cleboost.createchocolatefactory.core.CCFGuiTextures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ChocolateAnalyserScreen extends AbstractSimiContainerScreen<ChocolateAnalyserMenu> {
    private static final ResourceLocation BACKGROUND = CCFGuiTextures.CHOCOLATE_ANALYSER;
    private final List<Widget> placementSettingWidgets;

    public ChocolateAnalyserScreen(ChocolateAnalyserMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		placementSettingWidgets = new ArrayList<>();
	}

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, this.imageWidth, this.imageHeight);
    }
}
