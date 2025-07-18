package fr.cleboost.createchocolatefactory.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.resources.ResourceLocation;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class ModPartialModel {
    public static final PartialModel CHOCOLATE_MIXER_HEAD = block("chocolate_mixer/head");
    public static final PartialModel CHOCOLATE_MIXER_POLE = block("chocolate_mixer/pole");

    private static PartialModel block(String path) {
		return PartialModel.of(CreateChocolateFactory.asResource("block/" + path));
	}
}