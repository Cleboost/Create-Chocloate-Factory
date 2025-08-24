package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.ponder.scenes.ChocolateMixerScene;
import fr.cleboost.createchocolatefactory.ponder.scenes.DryingKitScene;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CCFPounders {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(CCFBlocks.DRYING_KIT.getId())
            .addStoryBoard("drying_kit", DryingKitScene::scene);
        helper.forComponents(CCFBlocks.CHOCOLATE_MIXER.getId())
            .addStoryBoard("chocolate_mixer", ChocolateMixerScene::scene);
    }
}
