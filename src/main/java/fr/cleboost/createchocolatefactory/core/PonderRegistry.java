package fr.cleboost.createchocolatefactory.core;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PonderRegistry {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        // helper.forComponents(CCFBlocks.DRYING_KIT.getId())
        //     .addStoryBoard("drying_kit", DryingKitScene::scene);
    }
}
