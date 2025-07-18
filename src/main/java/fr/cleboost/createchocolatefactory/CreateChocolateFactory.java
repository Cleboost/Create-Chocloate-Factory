package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;

import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import fr.cleboost.createchocolatefactory.core.DataComponentsRegistry;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.core.CCFLang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;

import org.slf4j.Logger;

@Mod(CreateChocolateFactory.MODID)
public class CreateChocolateFactory {
    public static final String MODID = "createchocolatefactory";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CCFTab = REGISTRATE.object("create_chocolate_factory")
            .defaultCreativeTab(tab -> tab.icon(CCFItems.LOGO::asStack))
            .register();


    public CreateChocolateFactory(IEventBus modEventBus) {
        CCFLang.register();
        REGISTRATE.registerEventListeners(modEventBus);
        CCFBlocks.register();
        CCFBlockEntities.register();
        CCFItems.register();

        DataComponentsRegistry.register(modEventBus);
        // LootModifiersRegistry.register(modEventBus);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}
}
