package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class MintCropBlock extends CropBlock {
    public static final int MAX_AGE = 1;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public MintCropBlock(Properties pProperties) {
        super(pProperties);
    }

    //Define the base seed of the crop
    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.MINT_SEEDS.get();
    }

    //Function for get age of the block (use only by Minecraft)
    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    //Function for get max age of the block (use only by Minecraft)
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    //Create block state definition model state
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}