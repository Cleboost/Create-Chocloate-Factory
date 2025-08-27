package fr.cleboost.createchocolatefactory.worldgen.tree;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.worldgen.CCFConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class CCFTreeGrower {
    public static final TreeGrower COCOA_GROWER = new TreeGrower(
            CreateChocolateFactory.asResource("cocoa").toString(),
            Optional.empty(), Optional.of(CCFConfiguredFeatures.COCOA_TREE_KEY), Optional.empty()
    );
}
