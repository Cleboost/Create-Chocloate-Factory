package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;

import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.core.*;
import fr.cleboost.createchocolatefactory.datagen.CCFDatagen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
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
        CCFFluids.register();

        CCFDataComponents.register(modEventBus);
        CCFLootModifiers.register(modEventBus);

        modEventBus.addListener(CreateChocolateFactory::registerCapabilities);
        modEventBus.addListener(CCFDatagen::gatherData);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        ChocolateMixerBlockEntity.registerCapabilities(event);
    }

    @SubscribeEvent
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
