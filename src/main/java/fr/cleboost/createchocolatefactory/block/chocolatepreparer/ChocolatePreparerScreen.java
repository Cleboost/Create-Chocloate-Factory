package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFGuiTextures;
import fr.cleboost.createchocolatefactory.network.UpdatePreparerValuesPacket;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class ChocolatePreparerScreen extends AbstractSimiContainerScreen<ChocolatePreparerMenu> {

    private NumericEditBox strengthField;
    private NumericEditBox sugarField;
    private NumericEditBox cocoaButterField;
    private NumericEditBox milkField;
    private int lastSentStrength = -1;
    private int lastSentSugar = -1;
    private int lastSentCocoaButter = -1;
    private int lastSentMilk = -1;

    public ChocolatePreparerScreen(ChocolatePreparerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 179;
		this.imageHeight = 141;
		this.inventoryLabelY = this.imageHeight - 94;
		this.titleLabelY = 6;
	}

    @Override
    protected void init() {
        super.init();
        
        int guiLeft = (this.width - this.imageWidth) / 2;
        int guiTop = (this.height - (this.imageHeight + 5 + AllGuiTextures.PLAYER_INVENTORY.getHeight())) / 2;
        
        this.strengthField = new NumericEditBox(
            this.font,
            guiLeft + 10, guiTop + 20,
            60, 20,
            Component.literal("Strength"),
            0, 100,
            this::onValueChanged
        );
        this.strengthField.setNumericValue(0);
        
        this.sugarField = new NumericEditBox(
            this.font,
            guiLeft + 80, guiTop + 20,
            60, 20,
            Component.literal("Sugar"),
            0, 100,
            this::onValueChanged
        );
        this.sugarField.setNumericValue(0);
        
        this.cocoaButterField = new NumericEditBox(
            this.font,
            guiLeft + 10, guiTop + 45,
            60, 20,
            Component.literal("Cocoa Butter"),
            0, 100,
            this::onValueChanged
        );
        this.cocoaButterField.setNumericValue(0);
        
        this.milkField = new NumericEditBox(
            this.font,
            guiLeft + 80, guiTop + 45,
            60, 20,
            Component.literal("Milk"),
            0, 100,
            this::onValueChanged
        );
        this.milkField.setNumericValue(0);
        
        this.addRenderableWidget(this.strengthField);
        this.addRenderableWidget(this.sugarField);
        this.addRenderableWidget(this.cocoaButterField);
        this.addRenderableWidget(this.milkField);
        
        if (menu.contentHolder != null) {
            ItemStack filterStack = menu.contentHolder.inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
            if (!filterStack.isEmpty()) {
                Chocolate chocolate = filterStack.get(CCFDataComponents.CHOCOLATE);
                if (chocolate != null) {
                    this.strengthField.setNumericValue((int) (chocolate.getStrength() * 100));
                    this.sugarField.setNumericValue((int) (chocolate.getSugar() * 100));
                    this.cocoaButterField.setNumericValue((int) (chocolate.getCocoaButter() * 100));
                    this.milkField.setNumericValue((int) (chocolate.getMilk() * 100));
                    this.lastSentStrength = (int) (chocolate.getStrength() * 100);
                    this.lastSentSugar = (int) (chocolate.getSugar() * 100);
                    this.lastSentCocoaButter = (int) (chocolate.getCocoaButter() * 100);
                    this.lastSentMilk = (int) (chocolate.getMilk() * 100);
                } else {
                    this.strengthField.setNumericValue(menu.contentHolder.getStrength());
                    this.sugarField.setNumericValue(menu.contentHolder.getSugar());
                    this.cocoaButterField.setNumericValue(menu.contentHolder.getCocoaButter());
                    this.milkField.setNumericValue(menu.contentHolder.getMilk());
                    this.lastSentStrength = menu.contentHolder.getStrength();
                    this.lastSentSugar = menu.contentHolder.getSugar();
                    this.lastSentCocoaButter = menu.contentHolder.getCocoaButter();
                    this.lastSentMilk = menu.contentHolder.getMilk();
                }
            } else {
                this.strengthField.setNumericValue(menu.contentHolder.getStrength());
                this.sugarField.setNumericValue(menu.contentHolder.getSugar());
                this.cocoaButterField.setNumericValue(menu.contentHolder.getCocoaButter());
                this.milkField.setNumericValue(menu.contentHolder.getMilk());
                this.lastSentStrength = menu.contentHolder.getStrength();
                this.lastSentSugar = menu.contentHolder.getSugar();
                this.lastSentCocoaButter = menu.contentHolder.getCocoaButter();
                this.lastSentMilk = menu.contentHolder.getMilk();
            }
        }
    }

    private void onValueChanged() {
        if (menu.contentHolder == null) return;
        
        int strength = strengthField.getNumericValue();
        int sugar = sugarField.getNumericValue();
        int cocoaButter = cocoaButterField.getNumericValue();
        int milk = milkField.getNumericValue();
        
        if (strength != lastSentStrength || sugar != lastSentSugar || cocoaButter != lastSentCocoaButter || milk != lastSentMilk) {
            PacketDistributor.sendToServer(new UpdatePreparerValuesPacket(
                menu.contentHolder.getBlockPos(),
                strength,
                sugar,
                cocoaButter,
                milk
            ));
            lastSentStrength = strength;
            lastSentSugar = sugar;
            lastSentCocoaButter = cocoaButter;
            lastSentMilk = milk;
        }
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int h = (this.width - AllGuiTextures.PLAYER_INVENTORY.getWidth()) / 2;
		int j = (this.height - (this.imageHeight + 5 + AllGuiTextures.PLAYER_INVENTORY.getHeight())) / 2;
		
		guiGraphics.blit(CCFGuiTextures.CHOCOLATE_PREPARER, i, j, 0, 0, this.imageWidth, this.imageHeight);
	    renderPlayerInventory(guiGraphics, h, j + this.imageHeight + 5);
	}

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}
}
