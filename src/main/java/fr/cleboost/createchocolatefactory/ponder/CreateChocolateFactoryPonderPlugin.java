package fr.cleboost.createchocolatefactory.ponder;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.registry.PonderRegister;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import javax.annotation.Nonnull;

public class CreateChocolateFactoryPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateChocolateFactory.MODID;
    }

    @Override
    public void registerScenes(@Nonnull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderRegister.register(helper);
    }

    // @Override
    // public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
    //     helper.addToTag(AllCreatePonderTags.DECORATION, ModBlocks.BRONZE_BELL.getId());
    // }
}
