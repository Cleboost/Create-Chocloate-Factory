package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ConfigDataGenerator {
    static final ArrayList<RegistryObject<Block>> excludesBlocksGenerate = new ArrayList<>();
    static {
        excludesBlocksGenerate.add(ModBlocks.COCOA_BLOCK_OPENED);
        excludesBlocksGenerate.add(ModBlocks.COCOA_BLOCK_CLOSED);

        excludesBlocksGenerate.add(ModBlocks.DRYING_KIT_EMPTY);
        excludesBlocksGenerate.add(ModBlocks.DRYING_KIT_WET);
        excludesBlocksGenerate.add(ModBlocks.DRYING_KIT_DIRTY);

        excludesBlocksGenerate.add(ModBlocks.MINT_CROP);

    }

    static final ArrayList<RegistryObject<Item>> excludesItemsGenerate = new ArrayList<>();
    static {
        excludesItemsGenerate.add(ModItems.MACHETE);
        excludesItemsGenerate.add(ModItems.LOGO);
    }
}
