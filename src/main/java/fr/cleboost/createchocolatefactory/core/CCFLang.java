package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class CCFLang {
    public static void hold_shift_tooltips(@Nonnull List<Component> tooltip) {
        tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.hold_shift")
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    public static void registerTooltips() {
        CreateChocolateFactory.REGISTRATE.addRawLang("tooltip.createchocolatefactory.chocolate.hold_shift", "[Hold Shift for information]");
        CreateChocolateFactory.REGISTRATE.addRawLang("tooltip.createchocolatefactory.machete", "Use to cut cocoa pods");
        CreateChocolateFactory.REGISTRATE.addRawLang("tooltip.createchocolatefactory.progress", "%s/%s");
        CreateChocolateFactory.REGISTRATE.addRawLang("tooltip.createchocolatefactory.chocolate.composition", "Strength : %s - Sugar : %s - Cocoa Butter : %s - Milk : %s");
    }

    public static void registerGoggles() {
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.status", "Status : %s");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.info", "Drying Kit");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.bonus", "★ Bonus");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.bonus_text", "+100% of drying speed in the Nether");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.drying", "Drying");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.dry", "Dry");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.empty", "Empty");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.progress", "Progress : %s");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.time_remaining", "Time remaining : %s");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.blocked_sky", "Sky is blocked - drying is paused");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.night", "Drying is paused during the night");
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.raining", "Drying is reset due to rain or thunderstorm");
    }

    public static void registerMessages() {
        CreateChocolateFactory.REGISTRATE.addRawLang("message.createchocolatefactory.dryingkit.need_more_cocoa_beans", "You need more damp cocoa beans to dry them (%s/9)");
    }

    public static void registerPonder() {
        CreateChocolateFactory.REGISTRATE.addRawLang("createchocolatefactory.ponder.drying_kit.header", "Drying Kit");
        CreateChocolateFactory.REGISTRATE.addRawLang("createchocolatefactory.ponder.drying_kit.text_1", "Drying 9 damp cocoa beans");
        CreateChocolateFactory.REGISTRATE.addRawLang("createchocolatefactory.ponder.drying_kit.text_2", "Drying kit needs active light & work only during day");
        CreateChocolateFactory.REGISTRATE.addRawLang("createchocolatefactory.ponder.drying_kit.text_3", "Get your dried but dirty cocoa beans");
    }

    public static void register() {
        registerTooltips();
        registerGoggles();
        registerMessages();
        registerPonder();
    }
}
