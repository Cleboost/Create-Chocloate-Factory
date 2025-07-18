package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import java.util.function.Consumer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.ShaftVisual;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import fr.cleboost.createchocolatefactory.utils.ModPartialModel;
import net.minecraft.core.Direction;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

public class ChocolateMixerVisual extends ShaftVisual<ChocolateMixerBlockEntity> implements SimpleDynamicVisual {

	private final OrientedInstance mixerPole;
	private final RotatingInstance mixerHead;
	// private final ChocolateMixerBlockEntity mixer;

	public ChocolateMixerVisual(VisualizationContext context, ChocolateMixerBlockEntity blockEntity, float partialTick) {
		super(context, blockEntity, partialTick);

		mixerPole = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(AllPartialModels.MECHANICAL_MIXER_POLE))
			.createInstance();

		mixerHead = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(ModPartialModel.CHOCOLATE_MIXER_HEAD))
			.createInstance();

		mixerHead.setRotationAxis(Direction.Axis.Y);
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		animate(ctx.partialTick());
	}

	private void animate(float partialTick) {
		float renderedHeadOffset = blockEntity.getRenderedHeadOffset(partialTick);
		mixerPole.position(getVisualPosition())
			.translatePosition(0, -renderedHeadOffset, 0)
			.setChanged();
		mixerHead.setPosition(getVisualPosition())
			.nudge(0, -renderedHeadOffset, 0)
			.setRotationalSpeed(blockEntity.getSpeed() * 2 * RotatingInstance.SPEED_MULTIPLIER)
			.setChanged();

		// rotatingModel.setRotationalSpeed(20);
	}

	@Override
	public void updateLight(float partialTick) {
		super.updateLight(partialTick);
		relight(mixerPole);
		relight(mixerHead);
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		super.collectCrumblingInstances(consumer);
		consumer.accept(mixerPole);
		consumer.accept(mixerHead);
	}
	
	@Override
    protected void _delete() {
		super._delete();
		mixerPole.delete();
		mixerHead.delete();
	}
}