package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.fluid.VirtualChocolateFluid;
import fr.cleboost.createchocolatefactory.item.ChocolateBucketItem;
import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class CCFFluids {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final FluidEntry<BaseFlowingFluid.Flowing> CHOCOLATE =
            REGISTRATE.standardFluid("chocolate", VirtualChocolateFluid.ChocolateFluidType::new)
                    .tag(AllTags.commonFluidTag("chocolate"))
                    .properties(b -> b.viscosity(1500).density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(25).slopeFindDistance(3).explosionResistance(100f))
                    .lang("Molten Chocolate")
                    .source(BaseFlowingFluid.Source::new)
                    .block(MoltenChocolateBlock::new)
                    .build()
                    .bucket(ChocolateBucketItem::new)
                    .lang("Bucket of Molten Chocolate")
                    .tag(net.neoforged.neoforge.common.Tags.Items.BUCKETS)
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
