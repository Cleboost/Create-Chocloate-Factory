package fr.cleboost.createchocolatefactory.ponder.scenes;


import com.simibubi.create.foundation.ponder.CreateSceneBuilder;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DryingKitScene {
    public static void scene(SceneBuilder scene, SceneBuildingUtil util) {
        CreateSceneBuilder createSceneBuilder = new CreateSceneBuilder(scene);
        scene.title("drying_kit", "Drying Kit");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(5);

        //Define
        BlockPos groundDryingKit = util.grid().at(2,1,2);

        //Show
        scene.world().showSection(util.select().position(groundDryingKit), Direction.UP);

        scene.idle(50);
    }
}