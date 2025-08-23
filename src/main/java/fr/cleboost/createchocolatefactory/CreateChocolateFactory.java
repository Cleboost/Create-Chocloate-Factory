package fr.cleboost.createchocolatefactory;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.core.*;
import fr.cleboost.createchocolatefactory.datagen.CCFDatagen;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(CreateChocolateFactory.MODID)
public class CreateChocolateFactory {
    public static final String MODID = "createchocolatefactory";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, MODID);
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CCFTab = REGISTRATE.object("create_chocolate_factory")
            .defaultCreativeTab(tab -> tab.icon(CCFItems.LOGO::asStack))
            .register();


    public CreateChocolateFactory(IEventBus modEventBus) {
        CCFLangs.register();
        REGISTRATE.registerEventListeners(modEventBus);
        CCFBlocks.register();
        CCFBlockEntities.register();
        CCFItems.register();
        CCFFluids.register();
        CCFMenu.register();
        MENUS.register(modEventBus);

        CCFDataComponents.register(modEventBus);
        CCFLootModifiers.register(modEventBus);

        modEventBus.addListener(CreateChocolateFactory::registerCapabilities);
        modEventBus.addListener(CreateChocolateFactory::registerDatapackRegistries);
        modEventBus.addListener(CCFDatagen::gatherData);
        modEventBus.addListener(CreateChocolateFactory::registerMenuScreens);
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
    public static void registerMenuScreens(net.neoforged.neoforge.client.event.RegisterMenuScreensEvent event) {
        event.<fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserMenu, net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserMenu>>register(
            CCFMenu.CHOCOLATE_ANALYSER.get(), 
            (menu, inventory, title) -> new net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserMenu>(menu, inventory, title) {
                @Override
                protected void renderBg(net.minecraft.client.gui.GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
                }
            });
    }

    @SubscribeEvent
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
