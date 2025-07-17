package fr.cleboost.createchocolatefactory.ponder.scenes;

import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import fr.cleboost.createchocolatefactory.block.DryingKitBlock;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import net.createmod.catnip.math.Pointing;

public class DryingKitScene {
    public static void scene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("drying_kit", "Drying Kit");
        scene.configureBasePlate(0, 0, 3);

        // Usefully position
        BlockPos centerPos = util.grid().at(1, 1, 1);
        BlockPos centerPosUp = util.grid().at(1, 2, 1);
        Vec3 centerVec = new Vec3(centerPos.getX()+0.5, centerPos.getY()+0.15, centerPos.getZ()+0.5);
        Vec3 textVec = new Vec3(centerPos.getX()+0.15, centerPos.getY()+0.15, centerPos.getZ()+0.9);
        // -----

        scene.showBasePlate();
        scene.idle(20);

        scene.world().showSection(util.select().position(centerPos), Direction.DOWN);
        scene.idle(20);

        
        scene.overlay().showControls(centerVec, Pointing.DOWN, 40).rightClick()
            .withItem(new ItemStack(ModItems.COCOA_BEANS_WET.get(), 9));

        scene.idle(20);

        scene.world().modifyBlock(centerPos, (state) -> state.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DRYING), true);
        scene.idle(30);
        scene.overlay().showText(40).text("Drying...").attachKeyFrame().placeNearTarget().pointAt(textVec);
        scene.idle(50);

        scene.world().showSection(util.select().position(centerPosUp), Direction.UP);
        scene.idle(10);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, centerPosUp, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(centerPosUp)), 70);
        scene.idle(10);
        scene.overlay().showText(50).text("Stop drying").attachKeyFrame().placeNearTarget().pointAt(textVec);
        scene.idle(60);
        scene.world().hideSection(util.select().position(centerPosUp), Direction.UP);
        scene.idle(40);

        scene.world().modifyBlock(centerPos, (state) -> state.setValue(DryingKitBlock.STATE, DryingKitBlock.State.DRY), false);
        scene.idle(25);
        scene.overlay().showControls(centerVec, Pointing.DOWN,30).rightClick();
        scene.idle(30);

        scene.world().modifyBlock(centerPos, (state) -> state.setValue(DryingKitBlock.STATE, DryingKitBlock.State.EMPTY), false);
        scene.world().createItemEntity(centerVec, new Vec3(0,0,0), new ItemStack(ModItems.COCOA_BEANS_DIRTY.get(), 9));
        scene.overlay().showText(70).text("Dryed").attachKeyFrame().placeNearTarget().pointAt(textVec);

        scene.markAsFinished();
    }
}