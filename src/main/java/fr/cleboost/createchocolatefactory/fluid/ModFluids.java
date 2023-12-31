package fr.cleboost.createchocolatefactory.fluid;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, CreateChocolateFactory.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_COCOA_BUTTER = FLUIDS.register("cocoa_butter",
            () -> new ForgeFlowingFluid.Source(ModFluids.COCOA_BUTTER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COCOA_BUTTER = FLUIDS.register("flowing_cocoa_butter",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.COCOA_BUTTER_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties COCOA_BUTTER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.COCOA_BUTTER_FLUID_TYPE, SOURCE_COCOA_BUTTER, FLOWING_COCOA_BUTTER)
            .slopeFindDistance(4).levelDecreasePerBlock(3).block(ModBlocks.COCOA_BUTTER_FLUID)
            .bucket(ModItems.COCOA_BUTTER_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
