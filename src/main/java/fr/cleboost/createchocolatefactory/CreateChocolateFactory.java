package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModBlocksEntity;
import fr.cleboost.createchocolatefactory.utils.ModDataComponents;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import fr.cleboost.createchocolatefactory.utils.ModLootModifiers;
import fr.cleboost.createchocolatefactory.utils.ModTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CreateChocolateFactory.MODID)
public class CreateChocolateFactory {
    public static final String MODID = "createchocolatefactory";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateChocolateFactory(IEventBus modEventBus) {
        ModTab.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlocksEntity.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModLootModifiers.register(modEventBus);
    }
}
