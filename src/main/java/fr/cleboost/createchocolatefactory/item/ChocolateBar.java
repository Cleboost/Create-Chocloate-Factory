package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.item.utils.ChocolateProgressItem;

public class ChocolateBar extends ChocolateProgressItem {
    public ChocolateBar(Properties properties) {
        super(properties, 3, (int)(Math.random() * 5), (int)(Math.random() * 5), (int)(Math.random() * 5));
    }
}
