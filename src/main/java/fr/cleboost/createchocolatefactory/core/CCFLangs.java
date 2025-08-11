package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CCFLangs {
    public static List<LangEntry> langs = new ArrayList<>();

    public static final MutableComponent TOOLTIPS_HOLD_SHIFT = LangEntry.withComponent(LangCategory.TOOLTIP, "hold_shift", "Hold [Shift] for information").withStyle(ChatFormatting.DARK_GRAY);
    public static final LangEntry MACHETE = new LangEntry(LangCategory.TOOLTIP, "machete", "Use to cut cocoa pods");
    public static final LangEntry PROGRESS = new LangEntry(LangCategory.TOOLTIP, "progress", "%s/%s");
    public static final LangEntry CHOCOLATE_COMPOSITION = new LangEntry(LangCategory.TOOLTIP, "chocolate.composition", "Chocolate composition :\nStrength : %s\nSugar : %s\nCocoa Butter : %s\nMilk : %s\nTaste : %s");

    public static final LangEntry DRYING_KIT_STATUS = new LangEntry(LangCategory.GOGGLE, "dryingkit.status", "Status : %s");
    public static final LangEntry DRYING_KIT_BONUS = new LangEntry(LangCategory.GOGGLE, "dryingkit.bonus", "★ Bonus");
    public static final LangEntry DRYING_KIT_BONUS_TEXT = new LangEntry(LangCategory.GOGGLE, "dryingkit.bonus_text", "+100% drying speed in the Nether");
    public static final LangEntry DRYING_KIT_DRYING = new LangEntry(LangCategory.GOGGLE, "dryingkit.drying", "Drying");
    public static final LangEntry DRYING_KIT_DRY = new LangEntry(LangCategory.GOGGLE, "dryingkit.dry", "Dry");
    public static final LangEntry DRYING_KIT_EMPTY = new LangEntry(LangCategory.GOGGLE, "dryingkit.empty", "Empty");
    public static final LangEntry DRYING_KIT_PROGRESS = new LangEntry(LangCategory.GOGGLE, "dryingkit.progress", "Progress : %s");
    public static final LangEntry DRYING_KIT_TIME_REMAINING = new LangEntry(LangCategory.GOGGLE, "dryingkit.time_remaining", "Time remaining : %s");
    public static final LangEntry DRYING_KIT_SKY_BLOCKED = new LangEntry(LangCategory.GOGGLE, "dryingkit.blocked_sky", "Sky is blocked - drying is paused");
    public static final LangEntry DRYING_KIT_NIGHT = new LangEntry(LangCategory.GOGGLE, "dryingkit.night", "Drying is paused during the night");
    public static final LangEntry DRYING_KIT_RAINING = new LangEntry(LangCategory.GOGGLE, "dryingkit.raining", "Drying is reset due to rain or thunderstorm");

    public static final LangEntry MESSAGE_NEED_MORE_COCOA_BEANS = new LangEntry(LangCategory.MESSAGE, "dryingkit.need_more_cocoa_beans", "You need more damp cocoa beans to dry them (%s/9)");

    public static final LangEntry PONDER_DRYING_KIT_HEADER = new LangEntry(LangCategory.PONDER, "drying_kit.header", "Drying Kit");
    public static final LangEntry PONDER_DRYING_KIT_TEXT_1 = new LangEntry(LangCategory.PONDER, "drying_kit.text_1", "Drying 9 damp cocoa beans");
    public static final LangEntry PONDER_DRYING_KIT_TEXT_2 = new LangEntry(LangCategory.PONDER, "drying_kit.text_2", "Drying kit needs active light & work only during day");
    public static final LangEntry PONDER_DRYING_KIT_TEXT_3 = new LangEntry(LangCategory.PONDER, "drying_kit.text_3", "Get your dried but dirty cocoa beans");

    public static final LangEntry COMMAND_FAIL_NO_CHOCOLATE = new LangEntry(LangCategory.COMMANDS,"fail.no_chocolate","This item is not made from chocolate");
    public static final LangEntry COMMAND_SET_CHOCOLATE = new LangEntry(LangCategory.COMMANDS,"set.chocolate_success","The new chocolate values have been set successfully");
    public static final LangEntry COMMAND_SET_TASTE = new LangEntry(LangCategory.COMMANDS,"set.taste_success","The new taste item has been set successfully");


    public static void holdShiftTooltips(@Nonnull List<Component> tooltip) {
        tooltip.add(TOOLTIPS_HOLD_SHIFT);
    }

    public static void register() {
        for (LangEntry entry : langs) {
            if (!entry.isRegistered()) entry.register();
        }
    }

    public static MutableComponent raw(LangCategory category, String name, Object... args) {
        LangEntry le = new LangEntry(category, name, "");
        return le.getComponent(args);
    }

    public enum LangCategory {
        EMPTY(""),
        TOOLTIP("tooltip"),
        GOGGLE("goggle"),
        MESSAGE("message"),
        COMMANDS("commands"),
        PONDER("ponder", true);

        private final String name;
        private final boolean end;

        LangCategory(String name) {
            this.name = name;
            this.end = false;
        }

        LangCategory(String name, boolean end) {
            this.name = name;
            this.end = true;
        }

        public String apply(String namespace) {
            if (end) return namespace + "." + name + ".";
            return name + "." + namespace + ".";
        }
    }

    public static class LangEntry {
        private final String key;
        private final String raw;
        private boolean registered = false;

        public LangEntry(LangCategory category, String name, String raw) {
            this.key = category.apply(CreateChocolateFactory.MODID) + name;
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
