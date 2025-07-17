package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.BlockRegistry;
import fr.cleboost.createchocolatefactory.core.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateChocolateFactory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Holder<Item> item : ItemRegistry.ITEMS.getEntries()) {
            if (ConfigDataGenerator.excludesItemsGenerate.contains(item)) {
                continue;
            }
            ResourceLocation id = item.unwrapKey().orElseThrow().location();
            if (BlockRegistry.BLOCKS.getEntries().stream().anyMatch(block -> block.getId().equals(id))) {
                if (BlockRegistry.BLOCKS.getEntries().stream()
                        .filter(block -> block.getId().equals(id))
                        .anyMatch(ConfigDataGenerator.excludesBlocksGenerate::contains)) {
                    continue;
                }
                blockItem(item);
            } else {
                simpleItem(item);
            }
        }
    }

    private void simpleItem(Holder<Item> item) {
        String path = item.unwrapKey().orElseThrow().location().getPath();
        withExistingParent(path,
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID,
                                "item/" + path));
    }

    private void blockItem(Holder<Item> item) {
        String path = item.unwrapKey().orElseThrow().location().getPath();
        withExistingParent(path,
                ResourceLocation.fromNamespaceAndPath(CreateChocolateFactory.MODID,
                        "block/" + path));
    }
}
