package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateChocolateFactory.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        /*this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
            ModBlocks.SAPPHIRE_BLOCK.get(),
            /ModBlocks.RAW_SAPPHIRE_BLOCK.get()
        );*/

        /*this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
            ModBlocks.COCOA_BLOCK.get()
        );*/

        /*this.tag(BlockTags.MINEABLE_WITH_HOE).add(
            ModBlocks.COCOA_BLOCK.get()
        );*/

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
            ModBlocks.COCOA_BLOCK_OPENED.get()
        );

        /*this.tag(BlockTags.NEEDS_STONE_TOOL).add(
            ModBlocks.RAW_SAPPHIRE_BLOCK.get()
        );*/

        /*this.tag(BlockTags.NEEDS_IRON_TOOL).add(
            ModBlocks.SAPPHIRE_BLOCK.get()
        );*/

        /*this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            ModBlocks.RAW_SAPPHIRE_BLOCK.get()
        );*/

        /*this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
            .add(ModBlocks.SOUND_BLOCK.get());*/


        //Custom tags ->


        /*this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLE)
            .add(ModBlocks.SAPPHIRE_BLOCK.get())
            .addTag(Tags.Blocks.ORES);*/
    }
}
