package fr.cleboost.createchocolatefactory.core;

import com.tterrag.registrate.util.entry.ItemEntry;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.MacheteItem;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateProgressItem;
import fr.cleboost.createchocolatefactory.utils.CCFRegistrate;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.ChocolateFoodPack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Tiers;
public class CCFItems {
    private static final CCFRegistrate REGISTRATE = CreateChocolateFactory.registrate();

    // Logo
    public static final ItemEntry<Item> LOGO = REGISTRATE.item("logo", Item::new).lang("Logo")
            .model((ctx, prov) -> {
                prov.generated(ctx, prov.modLoc("item/" + ctx.getName()))
                        .transforms()
                        .transform(ItemDisplayContext.FIXED)
                        .rotation(0, 180, 0)
                        .translation(0, 0, 0.75f)
                        .scale(3, 3, 3)
                        .end();
            })
            .register();

    // Tools
    public static final ItemEntry<MacheteItem> MACHETE = REGISTRATE.item("machete", MacheteItem::new)
            .properties(p->p.stacksTo(1).attributes(AxeItem.createAttributes(Tiers.IRON,4f, -2.6f)))
            .model((ctx, prov) -> prov.handheld(ctx)).register();

    // Cocoa Stuff
    public static final ItemEntry<Item> COCOA_BEANS_WET = REGISTRATE.item("cocoa_beans_wet", Item::new).lang("Damp Cocoa Beans").register();
    public static final ItemEntry<Item> COCOA_BEANS_DIRTY = REGISTRATE.item("cocoa_beans_dirty", Item::new).lang("Dirty Cocoa Beans").register();
    public static final ItemEntry<Item> COCOA_BEANS_ROASTED = REGISTRATE.item("cocoa_beans_roasted", Item::new).lang("Roasted Cocoa Beans").register();
    public static final ItemEntry<Item> COCOA_BARK = REGISTRATE.item("cocoa_bark", Item::new).properties(p -> p.stacksTo(16)).register();
    public static final ItemEntry<Item> COCOA_POWDER = REGISTRATE.item("cocoa_powder", Item::new).register();
    public static final ItemEntry<Item> COCOA_CAKE = REGISTRATE.item("cocoa_cake", Item::new).properties(p -> p.stacksTo(1)).register();
    public static final ItemEntry<Item> COCOA_NIBS = REGISTRATE.item("cocoa_nibs", Item::new).register();
    public static final ItemEntry<Item> COCOA_HUSK = REGISTRATE.item("cocoa_husk", Item::new).lang("Cocoa Husk").register();
    public static final ItemEntry<Item> CHOCOLATE_LIQUOR = REGISTRATE.item("chocolate_liquor", Item::new).lang("Chocolate Liquor").register();

    //Chocolate Items
    public static final ItemEntry<ChocolateProgressItem> CHOCOLATE_BAR = REGISTRATE.item("chocolate_bar", (properties) ->
                    new ChocolateProgressItem(properties.stacksTo(1).food(CCFFoods.CHOCOLATE_FAST), 3, 100)).lang("Chocolate Bar")
            .model((ctx, prov) -> {
                prov.generated(ctx, prov.modLoc("item/" + ctx.getName() + "/bar0"), prov.modLoc("item/" + ctx.getName() + "/bar_paper"))
                        .override()
                        .predicate(prov.modLoc(CCFItemProperties.EAT_PROGRESS), 1f)
                        .model(
                                prov.withExistingParent("item/" + ctx.getName() + "/" + ctx.getName() + "1", prov.modLoc("item/" + ctx.getName()))
                                        .texture("layer1", prov.modLoc("item/" + ctx.getName() + "/bar_paper"))
                                        .texture("layer0", prov.modLoc("item/" + ctx.getName() + "/bar1"))
                        )
                        .end()
                        .override()
                        .predicate(prov.modLoc(CCFItemProperties.EAT_PROGRESS), 2f)
                        .model(
                                prov.withExistingParent("item/" + ctx.getName() + "/" + ctx.getName() + "2", prov.modLoc("item/" + ctx.getName()))
                                        .texture("layer1", prov.modLoc("item/" + ctx.getName() + "/bar_paper"))
                                        .texture("layer0", prov.modLoc("item/" + ctx.getName() + "/bar2"))
                        ).end();
            }).register();

    public static final ItemEntry<Item> COOKIE = REGISTRATE.item("cookie", Item::new).model((ctx, prov) -> {
        prov.generated(ctx, prov.modLoc("item/chocolate/" + ctx.getName() + "/layer0"),
        prov.modLoc("item/chocolate/" + ctx.getName() + "/layer1"));
    }).register();

    public static final ChocolateFoodPack CHOCOLATE_EGG_PACK = new ChocolateFoodPack(REGISTRATE, "chocolate_egg", 300);
    public static final ChocolateFoodPack CHOCOLATE_BUNNY_PACK = new ChocolateFoodPack(REGISTRATE, "chocolate_bunny", 600);

    // Fruits & Foods
    public static final ItemEntry<Item> MINT_LEAF = REGISTRATE.item("mint_leaf", Item::new).properties(p -> p.food(CCFFoods.MINT)).register();
    public static final ItemEntry<Item> MINT_SEEDS = REGISTRATE.item("mint_seeds", Item::new).register();
    public static final ItemEntry<Item> ORANGE = REGISTRATE.item("orange", Item::new).register();
    public static final ItemEntry<Item> PEANUT = REGISTRATE.item("peanut", Item::new).lang("Peanut").register();
    public static final ItemEntry<Item> HAZELNUT = REGISTRATE.item("hazelnut", Item::new).lang("Hazelnut").register();
    public static final ItemEntry<Item> CARAMEL = REGISTRATE.item("caramel", Item::new).lang("Caramel").register();
    public static final ItemEntry<Item> CARAMEL_NUGGET = REGISTRATE.item("caramel_nugget", Item::new).lang("Caramel Nugget").register();

    // Other
    public static final ItemEntry<Item> BRASS_WHISK = REGISTRATE.item("brass_whisk", Item::new).register();
    public static final ItemEntry<Item> CHOCOLATE_FILTER = REGISTRATE.item("chocolate_filter", Item::new)
    .model((ctx, prov) -> prov.generated(ctx, prov.modLoc("item/chocolate_filter/layer0"),prov.modLoc("item/chocolate_filter/layer1"))).properties(p -> p.component(CCFDataComponents.CHOCOLATE, new Chocolate())).lang("Chocolate Filter").register();
    public static final ItemEntry<Item> COCOA_BUTTER_BOWL = REGISTRATE.item("cocoa_butter_bowl", Item::new).lang("Cocoa Butter Bowl").register();

    // Seau de chocolat avec un chemin de texture personnalisé
    //public static final Item CHOCOLATE_BUCKET = CCFFluids.CHOCOLATE.get().getBucket();
            /*REGISTRATE.item("chocolate_bucket", Item::new)
            .tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .model((ctx, prov) -> prov.generated(ctx, prov.modLoc("item/chocolate_bucket/bucket"), prov.modLoc("item/chocolate_bucket/overlay")))
            .register();*/

    public static void register() {
    }
}
