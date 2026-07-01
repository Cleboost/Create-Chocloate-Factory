package fr.cleboost.createchocolatefactory.utils;

import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import net.minecraft.world.item.crafting.Recipe;

public final class TasteMixingMarker {
    public static final Recipe<?> INSTANCE = new StandardProcessingRecipe.Builder<>(
            MixingRecipe::new, CreateChocolateFactory.asResource("taste_mixing"))
            .duration(100)
            .requiresHeat(HeatCondition.NONE)
            .build();

    private TasteMixingMarker() {
    }
}
