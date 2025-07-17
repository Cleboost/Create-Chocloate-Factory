package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.CocoaPod;
import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.item.CocoaPodItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateChocolateFactory.MODID);

    // public static final RegistryObject<Block> COCOA_POD_OPENED = registerBlock("cocoa_pod_opened",
    //     () -> new CocoaOpenedPod(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO).ignitedByLava()));
    public static final DeferredBlock<Block> COCOA_POD = registerBlock("cocoa_pod",
        () -> new CocoaPod(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO).ignitedByLava()));
    // public static final RegistryObject<Block> MINT_CROP = BLOCKS.register("mint_crop",
    //         () -> new MintCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final DeferredBlock<Block> DRYING_KIT = registerBlock("drying_kit",
            () -> new DryingKitBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).noOcclusion().destroyTime(0.1F).speedFactor(0.9F).ignitedByLava()));
    // public static final RegistryObject<LiquidBlock> COCOA_BUTTER_FLUID = BLOCKS.register("source_cocoa_butter",
    //         () -> new LiquidBlock(ModFluids.SOURCE_COCOA_BUTTER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    private static <T extends Block> DeferredBlock<Block> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<Block> block) {
        if (name.equals("cocoa_pod")) {
            ItemRegistry.ITEMS.register(name, () -> new CocoaPodItem(new Item.Properties()));
        } else {
            ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}