package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
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
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> STRENGTH =
            DATA_COMPONENTS.registerComponentType(
                    "strength",
                    dataComponentType -> dataComponentType.persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT)
            );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> SUGAR =
            DATA_COMPONENTS.registerComponentType(
                    "sugar",
                    dataComponentType -> dataComponentType.persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT)
            );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> COCOA_BUTTER =
            DATA_COMPONENTS.registerComponentType(
                    "cocoa_butter",
                    dataComponentType -> dataComponentType.persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT)
            );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> MILK =
            DATA_COMPONENTS.registerComponentType(
                    "milk",
                    dataComponentType -> dataComponentType.persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT)
            );

    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
    }
} 