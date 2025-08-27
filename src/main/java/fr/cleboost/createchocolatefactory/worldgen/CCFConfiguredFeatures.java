package fr.cleboost.createchocolatefactory.worldgen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

import java.util.List;

public class CCFConfiguredFeatures {
    //Described the features (trees, ores, flower…)

    public static final ResourceKey<ConfiguredFeature<?,?>> COCOA_TREE_KEY = registerKey("cocoa_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        register(context,COCOA_TREE_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(CCFBlocks.COCOA_LOG.get()),
                new ForkingTrunkPlacer(4, 2, 2),
                BlockStateProvider.simple(CCFBlocks.COCOA_LEAVES.get()),
                new AcaciaFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2)
        )//.decorators(List.of(TreeDecorator.))
                .build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, CreateChocolateFactory.asResource(name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config){
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
