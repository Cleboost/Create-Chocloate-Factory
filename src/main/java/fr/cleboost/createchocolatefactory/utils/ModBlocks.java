package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.block.MintCropBlock;
import fr.cleboost.createchocolatefactory.block.cocoablock.CocoaClosedPod;
import fr.cleboost.createchocolatefactory.block.cocoablock.CocoaOpenedPod;
import fr.cleboost.createchocolatefactory.fluid.ModFluids;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateChocolateFactory.MOD_ID);

    //custom block :
    public static final RegistryObject<Block> COCOA_POD_OPENED = registerBlock("cocoa_pod_opened",
        () -> new CocoaOpenedPod(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO).ignitedByLava()));
    public static final RegistryObject<Block> COCOA_POD_CLOSED = registerBlock("cocoa_pod_closed",
        () -> new CocoaClosedPod(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO).ignitedByLava()));
    public static final RegistryObject<Block> MINT_CROP = BLOCKS.register("mint_crop",
            () -> new MintCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> DRYING_KIT = registerBlock("drying_kit",
            () -> new DryingKitBlock(BlockBehaviour.Properties.copy(Blocks.SCAFFOLDING).noOcclusion().destroyTime(0.1F).speedFactor(0.9F).ignitedByLava()));
    public static final RegistryObject<LiquidBlock> COCOA_BUTTER_FLUID = BLOCKS.register("source_cocoa_butter",
            () -> new LiquidBlock(ModFluids.SOURCE_COCOA_BUTTER, BlockBehaviour.Properties.copy(Blocks.WATER)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}