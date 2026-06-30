package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MintCropBlock extends CropBlock {
    public MintCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CCFItems.MINT_SEEDS.get();
    }
}
