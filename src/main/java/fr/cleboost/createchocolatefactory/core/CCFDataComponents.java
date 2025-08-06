package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CCFDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CreateChocolateFactory.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> EAT_PROGRESS =
            DATA_COMPONENTS.registerComponentType(
                    "eat_progress",
                    dataComponentType -> dataComponentType.persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Chocolate>> CHOCOLATE =
            DATA_COMPONENTS.registerComponentType(
                    "chocolate",
                    dataComponentType -> dataComponentType.persistent(Chocolate.CODEC)
            );

    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
    }
} 