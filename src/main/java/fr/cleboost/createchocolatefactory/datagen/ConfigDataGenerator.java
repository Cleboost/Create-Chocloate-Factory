package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class ConfigDataGenerator {
    static final ArrayList<Holder<Block>> excludesBlocksGenerate = new ArrayList<>();

    static {
        excludesBlocksGenerate.add(ModBlocks.COCOA_POD_CLOSED);
    }

    static final ArrayList<Holder<Item>> excludesItemsGenerate = new ArrayList<>();

    static {
        excludesItemsGenerate.add(ModItems.MACHETE);
        excludesItemsGenerate.add(ModItems.LOGO);
    }

    static final ArrayList<String> langIndex = new ArrayList<>();
}
