package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;

import fr.cleboost.createchocolatefactory.core.BlockRegistry;
import fr.cleboost.createchocolatefactory.core.BlocksEntityRegistry;
import fr.cleboost.createchocolatefactory.core.DataComponentsRegistry;
import fr.cleboost.createchocolatefactory.core.ItemRegistry;
import fr.cleboost.createchocolatefactory.core.LootModifiersRegistry;
import fr.cleboost.createchocolatefactory.core.TabRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CreateChocolateFactory.MODID)
public class CreateChocolateFactory {
    public static final String MODID = "createchocolatefactory";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateChocolateFactory(IEventBus modEventBus) {
        TabRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);
        BlocksEntityRegistry.register(modEventBus);
        DataComponentsRegistry.register(modEventBus);
        LootModifiersRegistry.register(modEventBus);
    }
}
