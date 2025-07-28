package fr.cleboost.createchocolatefactory.datagen;

import java.util.concurrent.CompletableFuture;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.datagen.recipes.CCFStandardRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CCFDatagen {
    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(CreateChocolateFactory.MODID)) return;
        
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		//ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(true, new CCFStandardRecipeGen(packOutput, lookupProvider));
        
        if (event.includeServer()) {
            CCFRecipeProvider.registerAllProcessing(gen, packOutput, lookupProvider);
        }
    }
}
