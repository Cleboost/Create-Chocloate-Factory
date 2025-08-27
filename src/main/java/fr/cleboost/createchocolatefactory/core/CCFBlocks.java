package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.CocoaLogBlock;
import fr.cleboost.createchocolatefactory.block.CocoaPod;
import fr.cleboost.createchocolatefactory.block.FlammableRotatedPillarBlock;
import fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserBlock;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerBlock;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerItem;
import fr.cleboost.createchocolatefactory.block.dryingKit.DryingKitBlock;
import fr.cleboost.createchocolatefactory.worldgen.tree.CCFTreeGrower;
import fr.cleboost.createchocolatefactory.worldgen.tree.CCFWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class CCFBlocks {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final BlockEntry<CocoaPod> COCOA_POD = REGISTRATE
            .block("cocoa_pod", CocoaPod::new)
            .blockstate((ctx, prov) -> {
                prov.getVariantBuilder(ctx.getEntry())
                        .partialState().with(CocoaPod.OPENED, false)
                        .modelForState().modelFile(prov.models().getExistingFile(prov.modLoc("block/cocoa_pod/cocoa_pod_closed")))
                        .addModel()
                        .partialState().with(CocoaPod.OPENED, true)
                        .modelForState().modelFile(prov.models().getExistingFile(prov.modLoc("block/cocoa_pod/cocoa_pod_opened")))
                        .addModel();
            })
            .item()
            .model((ctx, prov) -> {
                prov.getBuilder(ctx.getName())
                        .parent(prov.getExistingFile(prov.mcLoc("item/generated")))
                        .texture("layer0", prov.modLoc("item/cocoa_pod"));
            })
            .build()
            .register();

    public static final BlockEntry<ChocolateMixerBlock> CHOCOLATE_MIXER = REGISTRATE
            .block("chocolate_mixer", ChocolateMixerBlock::new)
            .properties(p -> p.noOcclusion()
                    .mapColor(MapColor.STONE))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .item(ChocolateMixerItem::new)
            .model((ctx, prov) -> {
            })
            .build()
            .register();

    public static final BlockEntry<DryingKitBlock> DRYING_KIT = REGISTRATE
            .block("drying_kit", DryingKitBlock::new)
            .initialProperties(() -> Blocks.SCAFFOLDING)
            .properties(p -> p.noOcclusion().destroyTime(0.1F).speedFactor(0.9F).ignitedByLava())
            .blockstate((ctx, prov) -> {
                prov.getVariantBuilder(ctx.getEntry())
                        .partialState().with(DryingKitBlock.STATE, DryingKitBlock.State.EMPTY)
                        .modelForState().modelFile(prov.models().getExistingFile(prov.modLoc("block/drying_kit/drying_kit_empty")))
                        .addModel()
                        .partialState().with(DryingKitBlock.STATE, DryingKitBlock.State.DRYING)
                        .modelForState().modelFile(prov.models().getExistingFile(prov.modLoc("block/drying_kit/drying_kit_drying")))
                        .addModel()
                        .partialState().with(DryingKitBlock.STATE, DryingKitBlock.State.DRY)
                        .modelForState().modelFile(prov.models().getExistingFile(prov.modLoc("block/drying_kit/drying_kit_dry")))
                        .addModel();
            })
            .item()
            .model((ctx, prov) -> {
                prov.getBuilder(ctx.getName())
                        .parent(prov.getExistingFile(prov.modLoc("block/drying_kit/drying_kit_empty")));
            })
            .build()
            .register();

    //COCOA_TREE :
    public static final BlockEntry<CocoaLogBlock> COCOA_LOG = REGISTRATE.block("cocoa_log", CocoaLogBlock::new)
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG))
            .blockstate((ctx, prov) -> prov.axisBlock(ctx.get()))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<FlammableRotatedPillarBlock> COCOA_STRIPPED_LOG = REGISTRATE.block("cocoa_stripped_log", FlammableRotatedPillarBlock::new)
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG))
            .blockstate((ctx, prov) -> prov.axisBlock(ctx.get()))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<CocoaLogBlock> COCOA_WOOD = REGISTRATE.block("cocoa_wood", CocoaLogBlock::new)
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))
            .blockstate((ctx, prov) -> prov.axisBlock(ctx.get(),
                    CreateChocolateFactory.asResource("block/cocoa_log_side"),
                    CreateChocolateFactory.asResource("block/cocoa_log_side")))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<FlammableRotatedPillarBlock> COCOA_STRIPPED_WOOD = REGISTRATE.block("cocoa_stripped_wood", FlammableRotatedPillarBlock::new)
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD))
            .blockstate((ctx, prov) -> prov.axisBlock(ctx.get(),
                    CreateChocolateFactory.asResource("block/cocoa_stripped_log_side"),
                    CreateChocolateFactory.asResource("block/cocoa_stripped_log_side")
            ))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<? extends Block> COCOA_PLANKS = REGISTRATE.block("cocoa_planks", (p) -> {
                return new Block(p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 20;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 5;
                    }
                };
            })
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<? extends LeavesBlock> COCOA_LEAVES = REGISTRATE.block("cocoa_leaves", (p) -> {
                return new LeavesBlock(p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 60;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 30;
                    }
                };
            })
            //bz ca register pas ...
            //.loot((prov, ctx) -> prov.createOakLeavesDrops(ctx, CCFBlocks.COCOA_SAPLING.get(),))
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES))
            .blockstate((ctx, prov) -> prov.models().leaves(ctx.getName(), ctx.getId().withPrefix("block/")))
            .simpleItem().defaultLang().register();

    public static final BlockEntry<SaplingBlock> COCOA_SAPLING = REGISTRATE.block("cocoa_sapling", (p) -> new SaplingBlock(CCFTreeGrower.COCOA_GROWER, p))
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING))
            .blockstate((ctx, prov) -> prov.models().cross(ctx.getName(), ctx.getId().withPrefix("block/")))
            .item().defaultModel().build()//.model((ctx, prov)->prov.)
            .register();
    public static final BlockEntry<? extends StairBlock> COCOA_STAIRS = REGISTRATE.block("cocoa_stairs", (p) -> {
                return new StairBlock(CCFBlocks.COCOA_PLANKS.get().defaultBlockState(), p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 20;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 5;
                    }
                };
            })
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS))
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.get(), CCFBlocks.COCOA_PLANKS.getId().withPrefix("block/")))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<? extends SlabBlock> COCOA_SLAB = REGISTRATE.block("cocoa_slab", (p) -> {
                return new SlabBlock(p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 20;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 5;
                    }
                };
            })
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB))
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.get(), CCFBlocks.COCOA_PLANKS.getId(), CCFBlocks.COCOA_PLANKS.getId().withPrefix("block/")))
            .simpleItem().defaultLang().register();

    public static final BlockEntry<? extends FenceBlock> COCOA_FENCE = REGISTRATE.block("cocoa_fence", (p) -> {
                return new FenceBlock(p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 20;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 5;
                    }
                };
            })
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE))
            .blockstate((ctx, prov) -> prov.fenceBlock(ctx.get(), CCFBlocks.COCOA_PLANKS.getId().withPrefix("block/")))
            .simpleItem().defaultLang().register();
    public static final BlockEntry<? extends FenceGateBlock> COCOA_FENCE_GATE = REGISTRATE.block("cocoa_fence", (p) -> {
                return new FenceGateBlock(CCFWoodType.COCOA_TYPE, p) {
                    @Override
                    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return true;
                    }

                    @Override
                    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 20;
                    }

                    @Override
                    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                        return 5;
                    }
                };
            })
            .properties(p -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE))
            .blockstate((ctx, prov) -> prov.fenceGateBlock(ctx.get(), CCFBlocks.COCOA_PLANKS.getId().withPrefix("block/")))
            .simpleItem().defaultLang().register();



    public static final BlockEntry<ChocolateAnalyserBlock> CHOCOLATE_ANALYSER = REGISTRATE
        .block("chocolate_analyser", ChocolateAnalyserBlock::new)
        //.blockstate(BlockStateGen.horizontalBlockProvider(true))
        .blockstate((ctx, prov) -> {})
        .item()
        .model((ctx, prov) -> {})
        .build()
        .register();

    // public static final RegistryObject<Block> MINT_CROP =
    // BLOCKS.register("mint_crop",
    // () -> new
    // MintCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    // public static final DeferredBlock<Block> DRYING_KIT =
    // registerBlock("drying_kit",
    // () -> new
    // DryingKitBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).noOcclusion().destroyTime(0.1F).speedFactor(0.9F).ignitedByLava()));
    // public static final RegistryObject<LiquidBlock> COCOA_BUTTER_FLUID =
    // BLOCKS.register("source_cocoa_butter",
    // () -> new LiquidBlock(ModFluids.SOURCE_COCOA_BUTTER,
    // BlockBehaviour.Properties.copy(Blocks.WATER)));

    // public static final DeferredBlock<Block> CHOCOLATE_MIXER =
    // registerBlock("chocolate_mixer",
    // () -> new
    // ChocolateMixerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().destroyTime(0.1F).speedFactor(0.9F)));


    public static void register() {
    }
}