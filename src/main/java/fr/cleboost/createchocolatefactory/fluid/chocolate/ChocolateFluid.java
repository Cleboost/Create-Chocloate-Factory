package fr.cleboost.createchocolatefactory.fluid.chocolate;

import com.simibubi.create.AllFluids.TintedFluidType;
import com.simibubi.create.content.fluids.VirtualFluid;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public class ChocolateFluid extends VirtualFluid {
    public ChocolateFluid(Properties properties, boolean source) {
        super(properties, source);
    }

    @Override
    public Item getBucket() {
        return CCFItems.CHOCOLATE_BUCKET.get();
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
        public ItemStack getBucket(FluidStack fluidStack) {
            ItemStack itemStack = super.getBucket(fluidStack);
            if (fluidStack.has(CCFDataComponents.CHOCOLATE)) {
                itemStack.set(CCFDataComponents.CHOCOLATE, fluidStack.get(CCFDataComponents.CHOCOLATE));
            }
            return itemStack;
        }

        @Override
        public int getTintColor(FluidStack stack) {
            if (!stack.has(CCFDataComponents.CHOCOLATE)) return NO_TINT;
            Chocolate ch = stack.get(CCFDataComponents.CHOCOLATE);
            return ch.getColor();
        }

        @Override
        protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
            return NO_TINT;
        }
    }
}
