package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;

public class CCFLang {

    public static void registerTooltips() {
        CreateChocolateFactory.REGISTRATE.addRawLang("tooltip.createchocolatefactory.machete", "Use to cut cocoa pods");
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

    public static void register() {
        registerTooltips();
        registerGoggles();
        registerMessages();
    }
}
