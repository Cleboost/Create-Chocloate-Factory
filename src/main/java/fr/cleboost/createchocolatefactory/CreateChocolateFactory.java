package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.core.*;
import fr.cleboost.createchocolatefactory.datagen.CCFDatagen;
import fr.cleboost.createchocolatefactory.datagen.CCFDatapackProvider;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

import java.util.Set;

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
        modEventBus.addListener(CreateChocolateFactory::registerDatapackRegistries);
        modEventBus.addListener(CCFDatagen::gatherData);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        ChocolateMixerBlockEntity.registerCapabilities(event);
    }

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        // https://docs.neoforged.net/docs/concepts/registries/
        event.dataPackRegistry(
                CCFRegistryKeys.TASTE_REGISTRY_KEY,
                Taste.CODEC,
                Taste.CODEC,
                tasteRegistryBuilder -> tasteRegistryBuilder.maxId(256)
        );
    }

    @SubscribeEvent
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
