package fr.cleboost.createchocolatefactory.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ModLangGenerator extends LanguageProvider {
    public ModLangGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        String lang = this.getName().replace("Languages: ", "");
        try {
            //get data from sheet
            URL url = new URL("https://sheetdb.io/api/v1/ylmy7zgiwbjrq\n");
            URLConnection request = url.openConnection();
            request.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
            //get as JSON
            String data = br.readLine();
            Gson gson = new Gson();
            JsonArray translates = gson.fromJson(data, JsonArray.class);
            for (int i = 0; i < translates.size(); i++) {
                JsonObject tr = translates.get(i).getAsJsonObject();
                //get information
                String key = tr.get("Full Unique Name (auto)").getAsString();
                String trans = tr.get(lang).getAsString();
                this.add(key, trans);
            }
            CreateChocolateFactory.LOGGER.info("Loaded " + translates.size() + " translations for language " + lang);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
