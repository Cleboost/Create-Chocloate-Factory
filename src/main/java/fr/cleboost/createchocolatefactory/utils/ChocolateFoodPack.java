package fr.cleboost.createchocolatefactory.utils;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import fr.cleboost.createchocolatefactory.core.CCFFoods;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateBaseItem;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateMouldItem;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChocolateFoodPack {
    private final ItemEntry<ChocolateBaseItem> chocolateItem;
    private final ItemEntry<ChocolateMouldItem> mouldItem;

    public ChocolateFoodPack(CCFRegistrate registrate, String name, int amount) {
        this(registrate, name, "", amount);
    }

    public ChocolateFoodPack(CCFRegistrate registrate, String name, String lang, int amount) {
        ItemBuilder<ChocolateBaseItem, CreateRegistrate> builderChocolate = registrate.chocolateItem(name, (properties) ->
                new ChocolateBaseItem(properties.food(CCFFoods.CHOCOLATE_SLOW).stacksTo(16), amount));
        if (!lang.isEmpty()) builderChocolate.lang(lang);
        chocolateItem = builderChocolate.register();


        ItemBuilder<ChocolateMouldItem, CreateRegistrate> builderMould = registrate.chocolateMouldItem(name+"_mould", (p) -> new ChocolateMouldItem(p, chocolateItem.get()));
        if (!lang.isEmpty()) {
            builderMould.lang(lang+" Mould");
        } else {
            String formattedName = Arrays.stream(name.split("_"))
                    .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" ")) + " Mould";

            builderMould.lang(formattedName);
        }

        mouldItem = builderMould.register();
    }

    public ItemEntry<ChocolateBaseItem> getChocolateItems() {
        return chocolateItem;
    }

    public ItemEntry<ChocolateMouldItem> getMouldItems() {
        return mouldItem;
    }
}
