package fr.cleboost.createchocolatefactory.datagen.advancements;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class CCFAdvancements implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(CCFItems.LOGO.get()),
                        Component.translatable("advancements.createchocolatefactory.root.title"),
                        Component.translatable("advancements.createchocolatefactory.root.description"),
                        Optional.of(ResourceLocation.withDefaultNamespace("textures/block/dirt.png")),
                        AdvancementType.TASK,
                        true, true, false
                ))
                .addCriterion("has_cocoa_beans", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COCOA_BEANS))
                .save(saver, CreateChocolateFactory.asResource("root").toString());

        AdvancementHolder chocolateBar = Advancement.Builder.advancement()
                .parent(root)
                .display(new DisplayInfo(
                        new ItemStack(CCFItems.CHOCOLATE_BAR.get()),
                        Component.translatable("advancements.createchocolatefactory.chocolate_bar.title"),
                        Component.translatable("advancements.createchocolatefactory.chocolate_bar.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, false
                ))
                .addCriterion("has_chocolate_bar", InventoryChangeTrigger.TriggerInstance.hasItems(CCFItems.CHOCOLATE_BAR.get()))
                .save(saver, CreateChocolateFactory.asResource("chocolate_bar").toString());

        AdvancementHolder eatChocolate = Advancement.Builder.advancement()
                .parent(chocolateBar)
                .display(new DisplayInfo(
                        new ItemStack(CCFItems.CHOCOLATE_BAR.get()),
                        Component.translatable("advancements.createchocolatefactory.eat_chocolate.title"),
                        Component.translatable("advancements.createchocolatefactory.eat_chocolate.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, false
                ))
                .addCriterion("eat_chocolate", ConsumeItemTrigger.TriggerInstance.usedItem(CCFItems.CHOCOLATE_BAR.get()))
                .save(saver, CreateChocolateFactory.asResource("eat_chocolate").toString());

        AdvancementHolder toxicChocolate = Advancement.Builder.advancement()
                .parent(root)
                .display(new DisplayInfo(
                        new ItemStack(Items.BONE),
                        Component.translatable("advancements.createchocolatefactory.toxic_chocolate.title"),
                        Component.translatable("advancements.createchocolatefactory.toxic_chocolate.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, true // Hidden advancement
                ))
                .addCriterion("manual", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AIR)) 
                .save(saver, CreateChocolateFactory.asResource("toxic_chocolate").toString());
    }
}
