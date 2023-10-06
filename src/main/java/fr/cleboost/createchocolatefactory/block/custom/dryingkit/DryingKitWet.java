package fr.cleboost.createchocolatefactory.block.custom.dryingkit;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingKitWet extends DirectionalBlock {

    private int tickLevel = 0;
    public static final int MAX_AGE = 1;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public DryingKitWet(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_154346_, @NotNull BlockGetter p_154347_, @NotNull BlockPos p_154348_, @NotNull CollisionContext p_154349_) {
        return Block.box(3.0D, 0.0D, 6.0D, 14.7D, 4.0D, 10.0D);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(AGE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.createchocolatefactory.cocoa_block_closed"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }


    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }
    public boolean isRandomlyTicking(@NotNull BlockState pState) {
        return true;
    }

    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        this.tick(pState, pLevel, pPos, pRandom);
        pLevel.players().forEach(player -> {
            player.displayClientMessage(Component.literal(String.valueOf(tickLevel)), true);
        });
        //if (!pLevel.canSeeSky(pPos)) return;

        if (tickLevel >= 100) {
            pLevel.setBlockAndUpdate(pPos, ModBlocks.DRYING_KIT_DIRTY.get().defaultBlockState());
        } else {
            tickLevel++;
        }
    }
}
