package fr.cleboost.createchocolatefactory.registry;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import fr.cleboost.createchocolatefactory.ponder.scenes.DryingKitScene;
import fr.cleboost.createchocolatefactory.utils.ModBlocks;

public class PonderRegister {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModBlocks.DRYING_KIT.getId())
            .addStoryBoard("drying_kit", DryingKitScene::scene);
    }
}
