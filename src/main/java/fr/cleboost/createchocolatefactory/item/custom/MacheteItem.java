package fr.cleboost.createchocolatefactory.item.custom;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MacheteItem extends Item {

    public MacheteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.is(Blocks.COCOA)) {
            if (blockState.getValue(BlockStateProperties.AGE_2) == 2) {
                Player player = pContext.getPlayer();
                if (player != null) {
                    if (!world.isClientSide()) {
                        Random random = new Random();
                        world.destroyBlock(blockPos, false);
                        Block.popResource(world, blockPos, new ItemStack(ModBlocks.COCOA_BLOCK_CLOSED.get(), random.nextInt(1, 2)));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (blockState.is(ModBlocks.COCOA_BLOCK_CLOSED.get())) {
            Player player = pContext.getPlayer();
            if (player != null) {
                if (!world.isClientSide()) {
                    Random random = new Random();
                    world.destroyBlock(blockPos, false);
                    world.setBlockAndUpdate(blockPos, ModBlocks.COCOA_BLOCK_OPENED.get().defaultBlockState());
                    Block.popResource(world, blockPos, new ItemStack(ModItems.COCOA_BARK.get(), random.nextInt(1, 3)));
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.is(ModBlocks.COCOA_BLOCK_OPENED.get())) {
            Player player = pContext.getPlayer();
            if (player != null) {
                if (!world.isClientSide()) {
                    Random random = new Random();
                    world.destroyBlock(blockPos, false);
                    Block.popResource(world, blockPos, new ItemStack(ModItems.COCOA_BEANS_WET.get(), 9));
                    Block.popResource(world, blockPos, new ItemStack(ModItems.COCOA_BARK.get(), random.nextInt(1, 3)));
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(pContext);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.createchocolatefactory.machete"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
