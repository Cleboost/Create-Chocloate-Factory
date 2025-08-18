package fr.cleboost.createchocolatefactory.fluid;

import com.simibubi.create.AllFluids;
import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public class ChocolateFluidType extends AllFluids.TintedFluidType {

    public ChocolateFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties, stillTexture, flowingTexture);
    }

    /*@Override
    public ItemStack getBucket(FluidStack fluidStack) {
        ItemStack itemStack = super.getBucket(fluidStack);
        if (fluidStack.has(CCFDataComponents.CHOCOLATE)) {
            itemStack.set(CCFDataComponents.CHOCOLATE, fluidStack.get(CCFDataComponents.CHOCOLATE));
        }
        return super.getBucket(fluidStack);
    }*/

    @Override
    public int getTintColor(FluidStack stack) {
        if (!stack.has(CCFDataComponents.CHOCOLATE)) return NO_TINT;
        Chocolate ch = stack.get(CCFDataComponents.CHOCOLATE);
        return ch.getColor();
    }

    @Override
    protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
        Chocolate ch = MoltenChocolateBlock.getChocolate(pos);
        //if (ch == null) return NO_TINT;
        return ch.getColor();
    }

    /*@Override
    public FluidState getStateForPlacement(BlockAndTintGetter getter, BlockPos pos, FluidStack stack) {
        return super.getStateForPlacement(getter, pos, stack);
    }*/
}
