package fr.cleboost.createchocolatefactory.block.chocolateMixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import fr.cleboost.createchocolatefactory.core.CCFPartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

public class ChocolateMixerRender extends KineticBlockEntityRenderer<ChocolateMixerBlockEntity> {
    public ChocolateMixerRender(Context dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void renderSafe(ChocolateMixerBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);

		if (VisualizationManager.supportsVisualization(blockEntity.getLevel()))
			return;

		BlockState blockState = blockEntity.getBlockState();
		float renderedHeadOffset = blockEntity.getRenderedHeadOffset(partialTicks);

		SuperByteBuffer headRender = CachedBuffers.partialFacing(CCFPartialModel.CHOCOLATE_MIXER_HEAD, blockState,
			blockState.getValue(HorizontalKineticBlock.HORIZONTAL_FACING));
		headRender.translate(0, -renderedHeadOffset, 0)
			.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.cutout()));
    }

    @Override
	protected BlockState getRenderedBlockState(ChocolateMixerBlockEntity be) {
		return shaft(getRotationAxisOf(be));
	}

    @Override
	public boolean shouldRenderOffScreen(ChocolateMixerBlockEntity be) {
		return true;
	}
}
