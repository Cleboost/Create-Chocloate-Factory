package fr.cleboost.createchocolatefactory.mixins.client;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BreakingItemParticle.class)
public abstract class BreakingItemParticleMixin extends TextureSheetParticle {

    protected BreakingItemParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDLnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void CCF$applyChocolateColor(ClientLevel pLevel, double pX, double pY, double pZ, ItemStack pStack, CallbackInfo ci) {
        if (pStack.has(CCFDataComponents.CHOCOLATE)) {
            Chocolate chocolate = pStack.get(CCFDataComponents.CHOCOLATE);
            if (chocolate != null) {
                int color = chocolate.getColor();
                float r = (float) (color >> 16 & 255) / 255.0F;
                float g = (float) (color >> 8 & 255) / 255.0F;
                float b = (float) (color & 255) / 255.0F;
                this.setColor(r, g, b);
            }
        }
    }
}
