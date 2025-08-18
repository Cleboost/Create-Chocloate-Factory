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
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MoltenChocolateBlock extends LiquidBlock {
    //pas propre, systeme provisoire
    private static Map<BlockPos, Chocolate> chocolates = new HashMap<>();

    public MoltenChocolateBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.pickupBlock(player, level, pos, state);
        if (itemstack.isEmpty()) return itemstack;
        itemstack.set(CCFDataComponents.CHOCOLATE, getChocolate(pos));
        return itemstack;
    }

    /*@Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        //setChocolate(pos,);
    }*/

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
}
