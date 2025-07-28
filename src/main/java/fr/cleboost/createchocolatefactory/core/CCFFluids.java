package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.fluid.chocolate.ChocolateFluid;
import fr.cleboost.createchocolatefactory.fluid.chocolate.ChocolateFluid.ChocolateFluidType;

public class CCFFluids {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final FluidEntry<ChocolateFluid> CHOCOLATE =
		REGISTRATE.virtualFluid("chocolate", ChocolateFluidType::new, ChocolateFluid::createSource, ChocolateFluid::createFlowing)
            .tag(AllTags.commonFluidTag("chocolate"))
            .lang("Molten Chocolate")
			.register();

    public static final FluidEntry<VirtualFluid> COCOA_BUTTER =
        REGISTRATE.virtualFluid("cocoa_butter")
            .tag(AllTags.commonFluidTag("cocoa_butter"))
            .lang("Cocoa Butter")
            .register();

    public static void register() {}
}
