package fr.cleboost.createchocolatefactory.block;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.custom.DryingKitBlock;
import fr.cleboost.createchocolatefactory.block.custom.MintCropBlock;
import fr.cleboost.createchocolatefactory.block.custom.PigBlock;
import fr.cleboost.createchocolatefactory.block.custom.cocoablock.CocoaBlockClosed;
import fr.cleboost.createchocolatefactory.block.custom.cocoablock.CocoaBlockOpened;
import fr.cleboost.createchocolatefactory.block.custom.dryingkit.DryingKitDirty;
import fr.cleboost.createchocolatefactory.block.custom.dryingkit.DryingKitEmpty;
import fr.cleboost.createchocolatefactory.block.custom.dryingkit.DryingKitWet;
import fr.cleboost.createchocolatefactory.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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


    //Define blocks here
    public static final RegistryObject<Block> COCOA_BLOCK_OPENED = registerBlock("cocoa_block_opened",
        () -> new CocoaBlockOpened(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> COCOA_BLOCK_CLOSED = registerBlock("cocoa_block_closed",
        () -> new CocoaBlockClosed(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> DRYING_KIT_EMPTY = registerBlock("drying_kit_empty",
            () -> new DryingKitEmpty(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO).noOcclusion()));
    public static final RegistryObject<Block> DRYING_KIT_WET = registerBlock("drying_kit_wet",
            () -> new DryingKitWet(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO)));
    public static final RegistryObject<Block> DRYING_KIT_DIRTY = registerBlock("drying_kit_dirty",
            () -> new DryingKitDirty(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(0.1F).sound(SoundType.BAMBOO)));
    public static final RegistryObject<Block> MINT_CROP = BLOCKS.register("mint_crop",
            () -> new MintCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> PIG_BLOCK = registerBlock("pig_block",
            () -> new PigBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> DRYING_KIT = registerBlock("drying_kit",
            () -> new DryingKitBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

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