package fr.cleboost.createchocolatefactory.core;

import com.tterrag.registrate.builders.MenuBuilder.ForgeMenuFactory;
import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserMenu;
import fr.cleboost.createchocolatefactory.block.chocolateAnalyser.ChocolateAnalyserScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CCFMenu {
    public static final MenuEntry<ChocolateAnalyserMenu> CHOCOLATE_ANALYSER =
        register("chocolate_analyser", ChocolateAnalyserMenu::new, () -> ChocolateAnalyserScreen::new);

    private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
        String name, ForgeMenuFactory<C> factory, NonNullSupplier<ScreenFactory<C, S>> screenFactory) {
        return CreateChocolateFactory.registrate()
            .menu(name, factory, screenFactory)
            .register();
    }
    
    public static void register() {}
}
