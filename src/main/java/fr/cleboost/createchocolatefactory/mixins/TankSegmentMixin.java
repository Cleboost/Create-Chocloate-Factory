package fr.cleboost.createchocolatefactory.mixins;

import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import fr.cleboost.createchocolatefactory.utils.TankSegmentHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SmartFluidTankBehaviour.TankSegment.class)
public class TankSegmentMixin implements TankSegmentHandler {
    @Shadow
    protected SmartFluidTank tank;

    public SmartFluidTank create_Chocloate_Factory$getHandler() {
        return this.tank;
    }
}
