package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.core.BlockRegistry;
import fr.cleboost.createchocolatefactory.core.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class ConfigDataGenerator {
    static final ArrayList<Holder<Block>> excludesBlocksGenerate = new ArrayList<>();

    static {
        excludesBlocksGenerate.add(BlockRegistry.COCOA_POD);
        excludesBlocksGenerate.add(BlockRegistry.DRYING_KIT);
    }

    static final ArrayList<Holder<Item>> excludesItemsGenerate = new ArrayList<>();

    static {
        excludesItemsGenerate.add(ItemRegistry.MACHETE);
        excludesItemsGenerate.add(ItemRegistry.LOGO);
        excludesItemsGenerate.add(ItemRegistry.CHOCOLATE_BAR);
    }
}
