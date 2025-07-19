package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.dryingKit.DryingKitBlockEntity;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerRender;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerVisual;

import com.simibubi.create.AllPartialModels;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CCFBlockEntities {

        public static final BlockEntityEntry<ChocolateMixerBlockEntity> CHOCOLATE_MIXER = CreateChocolateFactory.REGISTRATE
                        .blockEntity("chocolate_mixer", ChocolateMixerBlockEntity::new)
                        .visual(() -> ChocolateMixerVisual::new)
                        .renderer(() -> ChocolateMixerRender::new)
                        .validBlocks(CCFBlocks.CHOCOLATE_MIXER)
                        .register();

        public static final BlockEntityEntry<DryingKitBlockEntity> DRYING_KIT = CreateChocolateFactory.REGISTRATE
                        .blockEntity("drying_kit", DryingKitBlockEntity::new)
                        .validBlocks(CCFBlocks.DRYING_KIT)
                        .register();

        public static void register() {}
}
