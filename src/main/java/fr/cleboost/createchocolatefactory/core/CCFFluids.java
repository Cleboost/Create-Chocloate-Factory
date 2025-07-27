package fr.cleboost.createchocolatefactory.core;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.fluid.chocolate.ChocolateFluid;

public class CCFFluids {
    private static final CreateRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    public static final FluidEntry<ChocolateFluid> CHOCOLATE =
		REGISTRATE.virtualFluid("chocolate", 
            CreateChocolateFactory.asResource("fluid/chocolate_still"), 
            CreateChocolateFactory.asResource("fluid/chocolate_flow"), 
            ChocolateFluid.ChocolateFluidType::new, ChocolateFluid::createSource, ChocolateFluid::createFlowing)
            .tag(AllTags.commonFluidTag("chocolate"))
            .properties(p -> p.viscosity(1000).density(1000))
            .lang("Molten Chocolate")
			.register();

    public static void register() {}
}
