package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ConfigDataGenerator {
    static final ArrayList<RegistryObject<Block>> excludesBlocksGenerate = new ArrayList<>();
    static {
        excludesBlocksGenerate.add(ModBlocks.COCOA_BLOCK_OPENED);
        excludesBlocksGenerate.add(ModBlocks.COCOA_BLOCK_CLOSED);

        excludesBlocksGenerate.add(ModBlocks.MINT_CROP);
        excludesBlocksGenerate.add(ModBlocks.DRYING_KIT);

    }

    static final ArrayList<RegistryObject<Item>> excludesItemsGenerate = new ArrayList<>();
    static {
        excludesItemsGenerate.add(ModItems.MACHETE);
        excludesItemsGenerate.add(ModItems.LOGO);
    }
    static final ArrayList<String> langIndex = new ArrayList<>();
    static {
        //l'ordre est très import
        langIndex.add("fr_fr");
        langIndex.add("en_us");
    }
}
