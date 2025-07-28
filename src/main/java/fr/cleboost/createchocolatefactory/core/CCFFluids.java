package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;

public class CCFFluids {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final FluidEntry<VirtualFluid> CHOCOLATE =
		REGISTRATE.virtualFluid("chocolate")
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
