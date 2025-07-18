package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;

public class ChocolateMixerRender extends KineticBlockEntityRenderer<ChocolateMixerBlockEntity> {
    public ChocolateMixerRender(Context dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void renderSafe(ChocolateMixerBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);
    }
}
