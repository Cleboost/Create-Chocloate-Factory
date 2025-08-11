package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CCFLang {
    public static List<LangEntry> langs = new ArrayList<>();

    public static final MutableComponent TOOLTIPS_HOLD_SHIFT = LangEntry.withComponent(LangCategory.TOOLTIP, "hold_shift", "Hold [Shift] for information").withStyle(ChatFormatting.DARK_GRAY);
    public static final LangEntry MACHETE = new LangEntry(LangCategory.TOOLTIP, "machete", "Use to cut cocoa pods");
    public static final LangEntry PROGRESS = new LangEntry(LangCategory.TOOLTIP, "progress", "%s/%s");
    public static final LangEntry CHOCOLATE_COMPOSITION = new LangEntry(LangCategory.TOOLTIP, "chocolate.composition", "Chocolate composition :\nStrength : %s\nSugar : %s\nCocoa Butter : %s\nMilk : %s\nTaste : %s");

    public static final LangEntry DRYING_KIT_STATUS = new LangEntry(LangCategory.GOGGLE, "dryingkit.status", "Status : %s");
    public static final LangEntry DRYING_KIT_INFO = new LangEntry(LangCategory.GOGGLE, "dryingkit.info", "Drying Kit");
    public static final LangEntry DRYING_KIT_BONUS = new LangEntry(LangCategory.GOGGLE, "dryingkit.bonus", "★ Bonus");
    


    public static void registerGoggles() {
        CreateChocolateFactory.REGISTRATE.addRawLang("goggle.createchocolatefactory.dryingkit.bonus_text", "+100% drying speed in the Nether");
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
    public static void hold_shift_tooltips(@Nonnull List<Component> tooltip) {
        tooltip.add(TOOLTIPS_HOLD_SHIFT);
    }

    public static void register() {
        for (LangEntry entry : langs) {
            if (!entry.isRegistered()) entry.register();
        }
    }

    public enum LangCategory {
        EMPTY(""),
        TOOLTIP("tooltip"),
        GOGGLE("goggle"),
        MESSAGE("message"),
        COMMANDS("commands");

        private final String name;

        LangCategory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class LangEntry {
        private final String key;
        private final String raw;
        private boolean registered = false;

        public LangEntry(LangCategory category, String name, String raw) {
            this.key = category.getName() + "." + CreateChocolateFactory.MODID + "." + name;
            this.raw = raw;
            langs.add(this);
        }

        public static MutableComponent withComponent(LangCategory category, String name, String raw) {
            LangEntry le = new LangEntry(category, name, raw);
            return le.register();
        }

        public String getKey() {
            return key;
        }

        public String getRaw() {
            return raw;
        }

        public boolean isRegistered() {
            return this.registered;
        }

        public MutableComponent register() {
            this.registered = true;
            return CreateChocolateFactory.REGISTRATE.addRawLang(this.key, this.raw);
        }

        public MutableComponent getComponent(Object... args) {
            return Component.translatable(this.key, args);
        }
    }
}
