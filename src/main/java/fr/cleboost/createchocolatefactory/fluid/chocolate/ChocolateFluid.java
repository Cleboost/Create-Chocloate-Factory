package fr.cleboost.createchocolatefactory.fluid.chocolate;

import com.simibubi.create.AllFluids.TintedFluidType;
import com.simibubi.create.content.fluids.VirtualFluid;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public class ChocolateFluid extends VirtualFluid {
    public ChocolateFluid(Properties properties, boolean source) {
		super(properties, source);
	}

    public static ChocolateFluid createSource(Properties properties) {
		return new ChocolateFluid(properties, true);
	}

	public static ChocolateFluid createFlowing(Properties properties) {
		return new ChocolateFluid(properties, false);
	}

    public static class ChocolateFluidType extends TintedFluidType {
		public ChocolateFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
			super(properties, stillTexture, flowingTexture);
		}

        @Override
        public int getTintColor(FluidStack stack) {
            return 0xFFFF00FF;
        }

        @Override
		protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
			return NO_TINT;
		}
    }
}
