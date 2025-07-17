package fr.cleboost.createchocolatefactory.ponder;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.BlockRegistry;
import fr.cleboost.createchocolatefactory.core.PonderRegistry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import javax.annotation.Nonnull;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;

public class CreateChocolateFactoryPonderPlugin implements PonderPlugin {
    @Override
    public @Nonnull String getModId() {
        return CreateChocolateFactory.MODID;
    }

    @Override
    public void registerScenes(@Nonnull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderRegistry.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.LOGISTICS, BlockRegistry.DRYING_KIT.getId());
    }
}
