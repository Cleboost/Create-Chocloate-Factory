package fr.cleboost.createchocolatefactory.block.chocolateanalyser;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;

import fr.cleboost.createchocolatefactory.core.CCFGuiTextures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ChocolateAnalyserScreen extends AbstractSimiContainerScreen<ChocolateAnalyserMenu> {

    public ChocolateAnalyserScreen(ChocolateAnalyserMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, Component.translatable("block.createchocolatefactory.chocolate_analyser"));
		this.imageWidth = 179;
		this.imageHeight = 141;
		this.inventoryLabelY = this.imageHeight - 94;
		this.titleLabelX = 8;
		this.titleLabelY = -52;
	}

    @Override
    protected void renderLabels(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int h = (this.width - AllGuiTextures.PLAYER_INVENTORY.getWidth()) / 2;
		int j = (this.height - (this.imageHeight + 5 + AllGuiTextures.PLAYER_INVENTORY.getHeight())) / 2;
		
		guiGraphics.blit(CCFGuiTextures.CHOCOLATE_ANALYSER, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int progress = menu.getProcessingProgress();
        int maxProgress = menu.getMaxProcessingTicks();
        if (maxProgress > 0) {
            int arrowWidth = (progress * 34) / maxProgress;
            guiGraphics.blit(CCFGuiTextures.CHOCOLATE_ANALYSER, i + 75, j + 70, 180, 0, arrowWidth, 17);
        }

        renderPlayerInventory(guiGraphics, h, j + this.imageHeight + 5);
	}

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}
}

