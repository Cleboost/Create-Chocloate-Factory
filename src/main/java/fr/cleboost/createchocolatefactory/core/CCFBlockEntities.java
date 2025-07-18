package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.dryingKit.DryingKitBlockEntity;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerRender;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerVisual;

import com.simibubi.create.AllPartialModels;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CCFBlockEntities {
        // public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES =
        // DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,
        // CreateChocolateFactory.MODID);

        // public static final DeferredHolder<BlockEntityType<?>,
        // BlockEntityType<DryingKitBlockEntity>> DRYING_KIT_ENTITY =
        // BLOCKS_ENTITIES.register("drying_kit_entity", () ->
        // BlockEntityType.Builder.of(DryingKitBlockEntity::new,
        // BlockRegistry.DRYING_KIT.get()).build(null));

        // public static final DeferredHolder<BlockEntityType<?>,
        // BlockEntityType<ChocolateMixerBlockEntity>> CHOCOLATE_MIXER_ENTITY =
        // BLOCKS_ENTITIES.register("chocolate_mixer_entity", () ->
        // BlockEntityType.Builder.of(ChocolateMixerBlockEntity::new,
        // BlockRegistry.CHOCOLATE_MIXER.get()).build(null));

        // public static final BlockEntityEntry<DryingKitBlockEntity> DRYING_KIT = CreateChocolateFactory.REGISTRATE
        //                 .blockEntity("drying_kit", DryingKitBlockEntity::new)
        //                 .validBlocks(CCFBlock.DRYING_KIT)
        //                 .register();

        public static final BlockEntityEntry<ChocolateMixerBlockEntity> CHOCOLATE_MIXER = CreateChocolateFactory.REGISTRATE
                        .blockEntity("chocolate_mixer", ChocolateMixerBlockEntity::new)
                        .visual(() -> ChocolateMixerVisual::new)
                        .validBlocks(CCFBlocks.CHOCOLATE_MIXER)
                        .register();

        public static final BlockEntityEntry<DryingKitBlockEntity> DRYING_KIT = CreateChocolateFactory.REGISTRATE
                        .blockEntity("drying_kit", DryingKitBlockEntity::new)
                        .validBlocks(CCFBlocks.DRYING_KIT)
                        .register();

        public static void register() {}
}
