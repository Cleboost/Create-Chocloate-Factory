package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CreateChocolateFactory.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
    }
}
