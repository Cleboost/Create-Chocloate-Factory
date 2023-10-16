package fr.cleboost.createchocolatefactory.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ModLangGenerator extends LanguageProvider {
    public ModLangGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        String lang = this.getName().replace("Languages: ", "");
        String os = System.getProperty("os.name").toLowerCase();
        File csvF;
        if (os.contains("win")) {
            csvF = new File("../src/main/resources/assets/createchocolatefactory/lang/langs.csv");
        } else if (os.contains("mac")) {
            csvF = new File("../../src/main/resources/assets/createchocolatefactory/lang/langs.csv");
        } else {
            throw new RuntimeException("OS not supported");
        }

        try {
            FileReader fr = new FileReader(csvF);
            BufferedReader br = new BufferedReader(fr);
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.contains("Full Unique Name (auto)")) continue;
                String[] sLine = line.split(",");
                this.add(sLine[0],sLine[3+ConfigDataGenerator.langIndex.indexOf(lang)]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
