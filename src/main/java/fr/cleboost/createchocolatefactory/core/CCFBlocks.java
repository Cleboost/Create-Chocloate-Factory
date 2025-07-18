package fr.cleboost.createchocolatefactory.core;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.CocoaPod;
import fr.cleboost.createchocolatefactory.block.dryingKit.DryingKitBlock;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerBlock;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;

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
        .blockstate((ctx, prov) -> {})
        .item()
        .model((ctx, prov) -> {})
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



    public static void register() {}
}