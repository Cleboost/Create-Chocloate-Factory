package fr.cleboost.createchocolatefactory.datagen;

import static net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition.hasBlockStateProperties;

import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.CropBlock;

public class CCFBlockLootProvider extends BlockLootSubProvider {

    protected CCFBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        this.add(
            CCFBlocks.MINT_CROP.get(),
            createCropDrops(
                CCFBlocks.MINT_CROP.get(),
                CCFItems.MINT_LEAF.get(),
                CCFItems.MINT_SEEDS.get(),
                hasBlockStateProperties(
                    CCFBlocks.MINT_CROP.get()
                ).setProperties(
                    StatePropertiesPredicate.Builder.properties().hasProperty(
                        CropBlock.AGE,
                        7
                    )
                )
            )
        );
    }

    @Override
    protected Iterable<net.minecraft.world.level.block.Block> getKnownBlocks() {
        return java.util.List.of(CCFBlocks.MINT_CROP.get());
    }
}
