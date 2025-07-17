package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.blockentity.DryingKitBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class BlocksEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateChocolateFactory.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DryingKitBlockEntity>> DRYING_KIT_ENTITY =
            BLOCKS_ENTITIES.register("drying_kit_entity", () -> BlockEntityType.Builder.of(DryingKitBlockEntity::new, BlockRegistry.DRYING_KIT.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKS_ENTITIES.register(eventBus);
    }
}
