package fr.cleboost.createchocolatefactory.utils;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "createchocolatefactory");

    public static final Supplier<DataComponentType<CompoundTag>> EAT_PROGRESS =
        REGISTRAR.registerComponentType(
            "eat_progress",
            builder -> builder.persistent(CompoundTag.CODEC)
        );

    public static void register(IEventBus bus) {
        REGISTRAR.register(bus);
    }
} 