package fr.cleboost.createchocolatefactory.loot;

import javax.annotation.Nonnull;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import fr.cleboost.createchocolatefactory.core.BlockRegistry;
import fr.cleboost.createchocolatefactory.core.ItemRegistry;

public class CocoaLootModifier extends LootModifier {
    public static final MapCodec<CocoaLootModifier> CODEC = RecordCodecBuilder.mapCodec(
        inst -> LootModifier.codecStart(inst).apply(inst, CocoaLootModifier::new)
    );

    public CocoaLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public @Nonnull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected @Nonnull ObjectArrayList<ItemStack> doApply(@Nonnull ObjectArrayList<ItemStack> generatedLoot, @Nonnull LootContext context) {
        generatedLoot.clear();
        if (context.hasParam(LootContextParams.BLOCK_STATE)) {
            BlockState state = context.getParam(LootContextParams.BLOCK_STATE);
            if (state.getBlock() instanceof CocoaBlock && state.getValue(CocoaBlock.AGE) == 2) {
                ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
                if (tool != null && tool.getItem() == ItemRegistry.MACHETE.get()) {
                    generatedLoot.add(new ItemStack(BlockRegistry.COCOA_POD.get().asItem(), 1));
                }
            }
        }

        return generatedLoot;
    }
}