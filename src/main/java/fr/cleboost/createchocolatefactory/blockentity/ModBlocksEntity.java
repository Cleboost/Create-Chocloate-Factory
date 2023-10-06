package fr.cleboost.createchocolatefactory.blockentity;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocksEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateChocolateFactory.MOD_ID);


    //Define blocks entities here
    public static final RegistryObject<BlockEntityType<PigBlockEntity>> PIG_BLOCK_ENTITY =
            BLOCKS_ENTITIES.register("pig_block_entity", () -> BlockEntityType.Builder.of(PigBlockEntity::new, ModBlocks.PIG_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<DryingKitEntity>> DRYING_KIT_ENTITY =
            BLOCKS_ENTITIES.register("drying_kit_entity", () -> BlockEntityType.Builder.of(DryingKitEntity::new, ModBlocks.DRYING_KIT.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKS_ENTITIES.register(eventBus);
    }
}
