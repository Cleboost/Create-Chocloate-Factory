package fr.cleboost.createchocolatefactory.fluid;

import org.joml.Vector3f;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation COCOA_OVERLAY_RL = new ResourceLocation(CreateChocolateFactory.MOD_ID, "misc/in_soap_water");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreateChocolateFactory.MOD_ID);

    public static final RegistryObject<FluidType> COCOA_BUTTER_FLUID_TYPE = register("cocoa_butter_fluid",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(15).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK));


    private static RegistryObject<FluidType> register(String pName, FluidType.Properties pProperties) {
        return FLUID_TYPES.register(pName, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, COCOA_OVERLAY_RL,
                0xA1E038D0, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), pProperties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
