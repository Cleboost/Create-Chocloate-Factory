package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import fr.cleboost.createchocolatefactory.fluid.ChocolateFluidType;
import fr.cleboost.createchocolatefactory.item.ChocolateBucketItem;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class CCFFluids {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final FluidEntry<BaseFlowingFluid.Flowing> CHOCOLATE =
            REGISTRATE.standardFluid("chocolate", ChocolateFluidType::new)
                    .source(BaseFlowingFluid.Source::new)
                    .block(MoltenChocolateBlock::new).build()
                    .fluidProperties(p -> p.levelDecreasePerBlock(2).slopeFindDistance(2))
                    .tag(AllTags.commonFluidTag("chocolate"))
                    .lang("Molten Chocolate")
                    .bucket(ChocolateBucketItem::new)
                        .lang("Bucket of Molten Chocolate")
                        .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                        .model((ctx, prov) -> prov.generated(ctx, prov.modLoc("item/chocolate_bucket/overlay"), prov.modLoc("item/chocolate_bucket/bucket")))
                    .build()
                    .register();

    public static final FluidEntry<VirtualFluid> COCOA_BUTTER =
            REGISTRATE.virtualFluid("cocoa_butter")
                    .tag(AllTags.commonFluidTag("cocoa_butter"))
                    .lang("Cocoa Butter")
                    .register();

    public static void register() {
    }
}
