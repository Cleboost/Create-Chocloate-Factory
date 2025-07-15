package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateChocolateFactory.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeferredHolder<Block, ? extends Block> blockHolder : ModBlocks.BLOCKS.getEntries()) {
            if (ConfigDataGenerator.excludesBlocksGenerate.contains(blockHolder)) {
                continue;
            }
            blockWithItem(blockHolder);
        }
    }

    private void blockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        simpleBlockWithItem(blockHolder.get(), cubeAll(blockHolder.get()));
    }
}
