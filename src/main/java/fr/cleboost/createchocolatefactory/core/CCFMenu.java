package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CCFMenu {
    public static final DeferredHolder<MenuType<?>, MenuType<ChocolateAnalyserMenu>> CHOCOLATE_ANALYSER = CreateChocolateFactory.MENUS
            .register("chocolate_analyser", () -> new MenuType<>(
                    (id, inventory) -> new ChocolateAnalyserMenu(id, inventory, inventory.player),
                    FeatureFlags.VANILLA_SET));
    
    public static void register() {}
}
