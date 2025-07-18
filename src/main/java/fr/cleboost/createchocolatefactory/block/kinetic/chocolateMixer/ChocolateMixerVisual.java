package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import java.util.function.Consumer;

import com.simibubi.create.content.kinetics.base.ShaftVisual;

import fr.cleboost.createchocolatefactory.utils.ModPartialModel;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;

public class ChocolateMixerVisual extends ShaftVisual<ChocolateMixerBlockEntity> implements SimpleDynamicVisual {

	private final OrientedInstance pole;
	// private final RotatingInstance mixerHead;
	// private final ChocolateMixerBlockEntity mixer;

	public ChocolateMixerVisual(VisualizationContext context, ChocolateMixerBlockEntity blockEntity, float partialTick) {
		super(context, blockEntity, partialTick);

		pole = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ModPartialModel.CHOCOLATE_MIXER_POLE))
			.createInstance();
		// pole.setRotationAxis(Direction.Axis.Z);
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		animate(ctx.partialTick());
	}

	private void animate(float partialTick) {
		pole.position(getVisualPosition())
		.translatePosition(0, 0, 0)
		.setChanged();
	}


	@Override
	public void updateLight(float partialTick) {
		super.updateLight(partialTick);
		relight(pole);
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		super.collectCrumblingInstances(consumer);
		consumer.accept(pole);
	}

	
	@Override
    protected void _delete() {
		super._delete();
		pole.delete();
	}
}