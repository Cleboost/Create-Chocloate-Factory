package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

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

import javax.annotation.Nonnull;

public class ChocolatePreparerScreen extends AbstractSimiContainerScreen<ChocolatePreparerMenu> {

    private NumericEditBox strengthField;
    private NumericEditBox sugarField;
    private NumericEditBox cocoaButterField;
    private NumericEditBox milkField;
    private Chocolate lastSentChocolate;

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

        this.sugarField = new NumericEditBox(
                this.font,
                guiLeft + 80, guiTop + 20,
                60, 20,
                Component.literal("Sugar"),
                0, 100,
                this::onValueChanged
        );

        this.cocoaButterField = new NumericEditBox(
                this.font,
                guiLeft + 10, guiTop + 45,
                60, 20,
                Component.literal("Cocoa Butter"),
                0, 100,
                this::onValueChanged
        );

        this.milkField = new NumericEditBox(
                this.font,
                guiLeft + 80, guiTop + 45,
                60, 20,
                Component.literal("Milk"),
                0, 100,
                this::onValueChanged
        );

        this.addRenderableWidget(this.strengthField);
        this.addRenderableWidget(this.sugarField);
        this.addRenderableWidget(this.cocoaButterField);
        this.addRenderableWidget(this.milkField);

        if (menu.contentHolder != null) {
            ItemStack filterStack = menu.contentHolder.inventory.getStackInSlot(ChocolatePreparerInventory.SLOT_FILTER);
            Chocolate chocolate = filterStack.get(CCFDataComponents.CHOCOLATE);
            if (filterStack.isEmpty() || chocolate == null) chocolate = menu.contentHolder.getChocolate();
            this.strengthField.setNumericValue((int) (chocolate.getStrength() * 100));
            this.sugarField.setNumericValue((int) (chocolate.getSugar() * 100));
            this.cocoaButterField.setNumericValue((int) (chocolate.getCocoaButter() * 100));
            this.milkField.setNumericValue((int) (chocolate.getMilk() * 100));
            this.lastSentChocolate = chocolate;
        } else {
            this.strengthField.setNumericValue(100);
            this.sugarField.setNumericValue(0);
            this.cocoaButterField.setNumericValue(0);
            this.milkField.setNumericValue(0);
            this.lastSentChocolate=new Chocolate();
        }
    }

    private void onValueChanged() {
        if (menu.contentHolder == null) return;

        Chocolate chocolate = getChocolateValue();

        if (!chocolate.equals(lastSentChocolate)) {
            PacketDistributor.sendToServer(new UpdatePreparerValuesPacket(
                    menu.contentHolder.getBlockPos(),
                    chocolate
            ));
            this.lastSentChocolate = chocolate;
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

    private Chocolate getChocolateValue() {
        return new Chocolate(strengthField.getNumericValue() / 100f, sugarField.getNumericValue() / 100f, cocoaButterField.getNumericValue() / 100f, milkField.getNumericValue() / 100f);
    }
}
