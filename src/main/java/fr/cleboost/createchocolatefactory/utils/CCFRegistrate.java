package fr.cleboost.createchocolatefactory.utils;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;

public class CCFRegistrate extends CreateRegistrate {
    
    protected CCFRegistrate(String modid) {
        super(modid);
    }

    public static CCFRegistrate create(String modid) {
        return new CCFRegistrate(modid);
    }

    public <T extends Item> ItemBuilder<T, CreateRegistrate> chocolateItem(String name, NonNullFunction<Item.Properties, T> factory) {
        return item(self(), name, factory).model((ctx, prov) -> {
            prov.generated(ctx, prov.modLoc("item/chocolate/" + ctx.getName() + "/chocolate"));
        });
    }

    public <T extends Item> ItemBuilder<T, CreateRegistrate> chocolateMouldItem(String name, NonNullFunction<Item.Properties, T> factory) {
        return item(self(), name, factory).model((ctx, prov) -> {
            prov.generated(ctx, prov.modLoc("item/chocolate/" + ctx.getName().split("_mould")[0] + "/mould"));
        });
    }
}
