package fr.cleboost.createchocolatefactory.fluid;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public class VirtualChocolateFluid extends VirtualFluid {

    public static VirtualChocolateFluid createSource(Properties properties) {
        return new VirtualChocolateFluid(properties, true);
    }

    public static VirtualChocolateFluid createFlowing(Properties properties) {
        return new VirtualChocolateFluid(properties, false);
    }

    public VirtualChocolateFluid(Properties properties, boolean source) {
        super(properties, source);
    }

    public static class  ChocolateFluidType extends AllFluids.TintedFluidType {
        public ChocolateFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        public int getTintColor(FluidStack stack) {
            if (!stack.has(CCFDataComponents.CHOCOLATE)) return NO_TINT;
            Chocolate ch = stack.get(CCFDataComponents.CHOCOLATE);
            return ch.getColor();
        }

        @Override
        protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
            Chocolate ch = MoltenChocolateBlock.getChocolate(pos);
            return ch.getColor();
        }
    }
}
