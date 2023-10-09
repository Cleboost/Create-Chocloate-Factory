package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> MACHETE_BREAKABLE = tag("machete_breakable");
        public static final TagKey<Block> MACHETE_CLEANABLE = tag("machete_cleanable");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(CreateChocolateFactory.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MACHETE_LIKE = tag("machete_like");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(CreateChocolateFactory.MOD_ID, name));
        }
    }
}
