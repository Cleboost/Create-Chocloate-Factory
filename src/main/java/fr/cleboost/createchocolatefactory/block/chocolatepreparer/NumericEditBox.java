package fr.cleboost.createchocolatefactory.block.chocolatepreparer;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class NumericEditBox extends EditBox {
    private final int minValue;
    private final int maxValue;
    private final Runnable onValueChanged;

    public NumericEditBox(Font font, int x, int y, int width, int height, Component message, int minValue, int maxValue, Runnable onValueChanged) {
        super(font, x, y, width, height, message);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.onValueChanged = onValueChanged;
        this.setFilter(this::isNumeric);
    }

    private boolean isNumeric(String text) {
        if (text.isEmpty()) {
            return true;
        }
        try {
            int value = Integer.parseInt(text);
            return value >= minValue && value <= maxValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (super.charTyped(codePoint, modifiers)) {
            onValueChanged.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            onValueChanged.run();
            return true;
        }
        return false;
    }

    public int getNumericValue() {
        String text = getValue();
        if (text.isEmpty()) {
            return minValue;
        }
        try {
            return Math.max(minValue, Math.min(maxValue, Integer.parseInt(text)));
        } catch (NumberFormatException e) {
            return minValue;
        }
    }

    public void setNumericValue(int value) {
        setValue(String.valueOf(Math.max(minValue, Math.min(maxValue, value))));
    }
}

