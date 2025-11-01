package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ChocolateAnalyserScreen extends AbstractContainerScreen<ChocolateAnalyserMenu> {
    private static final ResourceLocation BACKGROUND = ResourceLocation.parse("minecraft:textures/gui/container/furnace.png");

    public ChocolateAnalyserScreen(ChocolateAnalyserMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.inventoryLabelY = this.imageHeight - 94;
		this.titleLabelY = 6;
	}

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		
		guiGraphics.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
		
		int processingProgress = this.menu.getProcessingProgress();
		int maxProcessingTicks = this.menu.getMaxProcessingTicks();
		if (processingProgress > 0 && maxProcessingTicks > 0) {
			int flameHeight = 14;
			int flameX = 56;
			int flameY = 36;
			guiGraphics.blit(BACKGROUND, i + flameX, j + flameY, 176, 0, 14, flameHeight);
		}
	}

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}
}

