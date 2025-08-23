package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserBlockEntity;
import fr.cleboost.createchocolatefactory.block.dryingKit.DryingKitBlockEntity;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerRender;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerVisual;

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

        /*public static final BlockEntityEntry<ChocolateAnalyserBlockEntity> CHOCOLATE_ANALYSER = CreateChocolateFactory.REGISTRATE
                        .blockEntity("chocolate_analyser", ChocolateAnalyserBlockEntity::new)
                        .validBlocks(CCFBlocks.CHOCOLATE_ANALYSER)
                        .register();*/

        public static void register() {}
}
