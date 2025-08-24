package fr.cleboost.createchocolatefactory.ponder.scenes;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.core.CCFBlocks;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChocolateMixerScene {
    public static void scene(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("chocolate_mixer", CCFBlocks.CHOCOLATE_MIXER.asItem().getDescription().getString());
        scene.configureBasePlate(0, 0, 5);

        // Usefully position
        BlockPos blazeBurnerBloc = util.grid().at(2, 1, 2);
        BlockPos funnelBloc = util.grid().at(2, 2, 1);
        BlockPos pipeOut =  util.grid().at(2, 2, 3);


        scene.world().showSection(util.select().cuboid(util.grid().at(0, 0, 0), new Vec3i(5, 0, 4)), Direction.DOWN);
        scene.idle(20);

        scene.world().showSection(util.select().cuboid(blazeBurnerBloc, new Vec3i(0, 3, 0)), Direction.DOWN);
        scene.idle(40);

        scene.world().modifyBlock(blazeBurnerBloc, (state) -> state.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED), true);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.BLUE, blazeBurnerBloc, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(blazeBurnerBloc)), 20);
        scene.idle(20);
        scene.overlay().showText(40).text("Heat...").attachKeyFrame().placeNearTarget().pointAt(new Vec3(blazeBurnerBloc.getX() + 0.5, blazeBurnerBloc.getY() + 0.15, blazeBurnerBloc.getZ() + 0.5));
        scene.idle(40);

        //Show Belt
        scene.world().showSection(util.select().cuboid(util.grid().at(2, 1, 0), new Vec3i(0, 1, 1)), Direction.SOUTH);
        scene.idle(20);
        scene.overlay().showText(40).text("Input...").attachKeyFrame().placeNearTarget().pointAt(new Vec3(funnelBloc.getX() + 0.5, funnelBloc.getY() + 0.15, funnelBloc.getZ() + 0.5));
        scene.idle(40);

        //Show double tanks
        scene.world().showSection(util.select().cuboid(util.grid().at(0, 1, 2), new Vec3i(1, 3, 0)), Direction.EAST);
        scene.world().showSection(util.select().cuboid(util.grid().at(4, 1, 2), new Vec3i(-1, 3, 0)), Direction.WEST);
        scene.idle(20);

        //Show all mech
        scene.world().showSection(util.select().cuboid(util.grid().at(3, 1, 0), new Vec3i(1, 0, 0)), Direction.WEST);
        scene.world().showSection(util.select().cuboid(util.grid().at(1, 4, 3), new Vec3i(2, 0, 1)), Direction.WEST);
        scene.idle(20);

        scene.world().createItemOnBelt(util.grid().at(2, 1, 0), Direction.UP, CCFItems.COCOA_POWDER.asStack());
        scene.idle(30);
        scene.world().removeItemsFromBelt(util.grid().at(2, 1, 1));
        scene.world().flapFunnel(util.grid().at(2, 1, 1).above(), false);
        scene.idle(10);

        scene.world().createItemOnBelt(util.grid().at(2, 1, 0), Direction.UP, new ItemStack(Items.SUGAR));
        scene.idle(30);
        scene.world().removeItemsFromBelt(util.grid().at(2, 1, 1));
        scene.world().flapFunnel(util.grid().at(2, 1, 1).above(), false);

        Class<ChocolateMixerBlockEntity> type = ChocolateMixerBlockEntity.class;
        scene.world().modifyBlockEntity(blazeBurnerBloc.above(), type, ChocolateMixerBlockEntity::startProcessingBasin);

        scene.idle(60);

        //***********************
        //TODO: Creation chocolat animation
        //***********************

        scene.world().hideSection(util.select().cuboid(util.grid().at(0, 1, 2), new Vec3i(1, 3, 0)), Direction.WEST);
        scene.world().hideSection(util.select().cuboid(util.grid().at(4, 1, 2), new Vec3i(-1, 3, 0)), Direction.EAST);
        scene.world().hideSection(util.select().cuboid(util.grid().at(1, 4, 3), new Vec3i(2, 0, 1)), Direction.UP);
        scene.world().hideSection(util.select().cuboid(util.grid().at(2, 1, 0), new Vec3i(2, 1, 1)), Direction.UP);
        scene.idle(50);
        scene.rotateCameraY(180);


        scene.idle(40);
        scene.world().showSection(util.select().cuboid(util.grid().at(0, 1, 3), new Vec3i(2, 2, 1)), Direction.UP);
        scene.overlay().showText(40).text("Out...").attachKeyFrame().placeNearTarget().pointAt(new Vec3(pipeOut.getX() + 0.5, pipeOut.getY() + 0.15, pipeOut.getZ() + 0.5));
        scene.markAsFinished();
    }
}