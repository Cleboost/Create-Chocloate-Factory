package fr.cleboost.createchocolatefactory.recipe;

import com.mojang.serialization.MapCodec;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class IceCreamCraftingRecipe extends ShapedRecipe {
    public IceCreamCraftingRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack ballStack = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(CCFItems.ICE_CREAM_BALL.get())) {
                ballStack = stack;
                break;
            }
        }

        ItemStack result = super.assemble(input, registries);
        if (!ballStack.isEmpty() && ballStack.has(CCFDataComponents.CHOCOLATE)) {
            result.set(CCFDataComponents.CHOCOLATE, ballStack.get(CCFDataComponents.CHOCOLATE));
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static final RecipeSerializer<IceCreamCraftingRecipe> SERIALIZER = new RecipeSerializer<IceCreamCraftingRecipe>() {
        private static final MapCodec<IceCreamCraftingRecipe> CODEC = ShapedRecipe.Serializer.CODEC.xmap(
                sr -> new IceCreamCraftingRecipe(sr.getGroup(), sr.category(), sr.pattern, sr.getResultItem(null), sr.showNotification()),
                r -> new ShapedRecipe(r.getGroup(), r.category(), r.pattern, r.getResultItem(null), r.showNotification())
        );

        private static final StreamCodec<RegistryFriendlyByteBuf, IceCreamCraftingRecipe> STREAM_CODEC = ShapedRecipe.Serializer.STREAM_CODEC.map(
                sr -> new IceCreamCraftingRecipe(sr.getGroup(), sr.category(), sr.pattern, sr.getResultItem(null), sr.showNotification()),
                r -> new ShapedRecipe(r.getGroup(), r.category(), r.pattern, r.getResultItem(null), r.showNotification())
        );

        @Override
        public MapCodec<IceCreamCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IceCreamCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    };
}
