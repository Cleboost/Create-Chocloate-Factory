package fr.cleboost.createchocolatefactory.datagen;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.io.*;
import java.util.Objects;

public class ModLangGenerator extends LanguageProvider {
    public ModLangGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        String lang = this.getName().replace("Languages: ", "");
        File csvF = new File(System.getProperty("user.dir")+"/../../src/main/resources/assets/createchocolatefactory/lang/langs.csv");
        try {
            FileReader fr = new FileReader(csvF);
            BufferedReader br = new BufferedReader(fr);
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.contains("Full Unique Name (auto)")) continue;
                String[] sLine = line.split(",");
                //CreateChocolateFactory.LOGGER.info(">>>"+sLine[0]+"=>"+sLine[3+ConfigDataGenerator.langIndex.indexOf(lang)]);
                this.add(sLine[0],sLine[3+ConfigDataGenerator.langIndex.indexOf(lang)]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
