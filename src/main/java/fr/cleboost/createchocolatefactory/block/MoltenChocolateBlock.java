package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MoltenChocolateBlock extends LiquidBlock {
    private static Map<BlockPos, Chocolate> chocolates = new HashMap<>();

    public MoltenChocolateBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        Chocolate chocolate = getChocolate(pos);
        ItemStack itemstack = super.pickupBlock(player, level, pos, state);
        if (itemstack.isEmpty()) return itemstack;
        itemstack.set(CCFDataComponents.CHOCOLATE, chocolate);
        return itemstack;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        Chocolate neighborCh = findNeighborChocolate(level, pos);
        setChocolate(pos, neighborCh);
    }

    private Chocolate findNeighborChocolate(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        if (level.getBlockState(above).getBlock() instanceof MoltenChocolateBlock) {
            Chocolate ch = getChocolate(above);
            if (ch.hasTaste() || ch.getStrength() != 1 || ch.getSugar() != 0 || ch.getCocoaButter() != 0 || ch.getMilk() != 0) {
                return ch;
            }
        }
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos neighbor = pos.relative(dir);
            if (level.getBlockState(neighbor).getBlock() instanceof MoltenChocolateBlock) {
                Chocolate ch = getChocolate(neighbor);
                if (ch.hasTaste() || ch.getStrength() != 1 || ch.getSugar() != 0 || ch.getCocoaButter() != 0 || ch.getMilk() != 0) {
                    return ch;
                }
            }
        }
        return new Chocolate();
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        chocolates.remove(pos);
    }

    public static Chocolate getChocolate(BlockPos pos) {
        if (chocolates.containsKey(pos)) return chocolates.get(pos);
        return new Chocolate();
    }

    public static void setChocolate(BlockPos pos, Chocolate chocolate) {
        if (chocolate==null) chocolate = new Chocolate();
        chocolates.put(pos, chocolate);
    }

    private static final int MAX_SEARCH_DEPTH = 8;

    public static Chocolate getChocolateOrFlow(BlockAndTintGetter level, BlockPos pos) {
        return getChocolateOrFlow(level, pos, new HashSet<>(), 0);
    }

    private static Chocolate getChocolateOrFlow(BlockAndTintGetter level, BlockPos pos, Set<BlockPos> visited, int depth) {
        if (depth > MAX_SEARCH_DEPTH || !visited.add(pos)) {
            return new Chocolate();
        }

        if (chocolates.containsKey(pos)) {
            return chocolates.get(pos);
        }

        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.getBlock() instanceof MoltenChocolateBlock) {
            Chocolate result = getChocolateOrFlow(level, above, visited, depth + 1);
            if (result.hasTaste() || result.getStrength() != 1 || result.getSugar() != 0 || result.getCocoaButter() != 0 || result.getMilk() != 0) {
                return result;
            }
        }

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos neighbor = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighbor);
            if (neighborState.getBlock() instanceof MoltenChocolateBlock) {
                Chocolate result = getChocolateOrFlow(level, neighbor, visited, depth + 1);
                if (result.hasTaste() || result.getStrength() != 1 || result.getSugar() != 0 || result.getCocoaButter() != 0 || result.getMilk() != 0) {
                    return result;
                }
            }
        }

        return new Chocolate();
    }
}
