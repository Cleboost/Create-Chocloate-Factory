package fr.cleboost.createchocolatefactory.datagen.loot;

import fr.cleboost.createchocolatefactory.block.ModBlocks;
import fr.cleboost.createchocolatefactory.block.custom.MintCropBlock;
import fr.cleboost.createchocolatefactory.block.custom.StrawberryCropBlock;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
            this.dropSelf(block.get());
        }

        /*this.add(ModBlocks.SAPPHIRE_BLOCK.get(),
                block -> numberDrop(ModBlocks.SAPPHIRE_BLOCK.get(), ModItems.RAW_SAPPHIRE.get(),2.0F, 5.0F));*/

        //Lootable for Strawberry Crop
        {
            LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

            this.add(ModBlocks.STRAWBERRY_CROP.get(), createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.LOGO.get(),
                    ModItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));
        }

        //Lootable for Mint
        {
            LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(ModBlocks.MINT_CROP.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MintCropBlock.AGE, 1));

            this.add(ModBlocks.MINT_CROP.get(), createCropDrops(ModBlocks.MINT_CROP.get(), ModItems.LOGO.get(),
                    ModItems.MINT_SEEDS.get(), lootitemcondition$builder));
        }


    }

    protected LootTable.Builder numberDrop(Block pBlock, Item item, float minDrop, float maxDrop) {
        return createSilkTouchDispatchTable(pBlock,
            this.applyExplosionCondition(pBlock, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrop, maxDrop)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
