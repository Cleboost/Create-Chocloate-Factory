package fr.cleboost.createchocolatefactory.utils;

public class Chocolate {
    private final int sugarCount;
    private final int cocoaCount;
    private final int milkCount;

    public Chocolate(int sugarCount, int cocoaCount, int milkCount) {
        this.sugarCount = sugarCount;
        this.cocoaCount = cocoaCount;
        this.milkCount = milkCount;
    }

    public int getSugarCount() {
        return sugarCount;
    }

    public int getCocoaCount() {
        return cocoaCount;
    }

    public int getMilkCount() {
        return milkCount;
    }

    public int getColor() {
        int red = (int) Math.max(0, Math.min(255,
            0.9 * Math.pow(this.sugarCount + this.cocoaCount, 0.7) + 1.15 * Math.pow(this.milkCount + this.sugarCount, 1.11) * 1.4 - 7
        ));
        int green = (int) Math.max(0, Math.min(255,
            0.405 * (this.sugarCount + this.cocoaCount) + 0.5 * Math.pow(this.milkCount + this.sugarCount, 1.5) * 0.5 - 13
        ));
        int blue = (int) Math.max(0, Math.min(255,
            1.8 * (this.sugarCount + this.cocoaCount) + 1.1 * Math.pow(this.milkCount + this.sugarCount, 1.6) * 0.11 - 16
        ));
        int alpha = 255;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
