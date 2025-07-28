package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.utils.TankSegmentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Predicate;

public class ChocolateMixerBlockEntity extends BasinOperatingBlockEntity implements IHaveGoggleInformation {
    private static final int TANK_SIZE = 500;
    public int runningTicks;
    public int processingTicks;
    public boolean running;

    private SmartFluidTankBehaviour milkTank;
    private SmartFluidTankBehaviour cocoaButterTank;
    private SmartFluidTankBehaviour internalTanks;

    // SmartFluidTankBehaviour milkTank;
    // SmartFluidTankBehaviour cocoaButterTank;
    public ChocolateMixerBlockEntity(BlockEntityType<? extends ChocolateMixerBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        // inputInv = new ItemStackHandler(1);
        // outputInv = new ItemStackHandler(9);
        // capability = LazyOptional.of(ChocolateMixerInventoryHandler::new);
    }

    @Override
    public void tick() {
        super.tick();

        boolean wasRunning = running;
        running = Math.abs(getSpeed()) > 0.5f && !this.internalTanks.isEmpty();

        if (running) {
            runningTicks++;

            if (!wasRunning) {
                setChanged();
                sendData();
            }
        } else {
            if (wasRunning) {
                runningTicks = 0;
                setChanged();
                sendData();
            }
        }
    }

    public float getRenderedHeadOffset(float partialTicks) {
        int localTick;
        float offset = 0;
        if (running) {
            if (runningTicks < 20) {
                localTick = runningTicks;
                float num = (localTick + partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            } else if (runningTicks <= 20) {
                offset = 1;
            } else {
                localTick = 40 - runningTicks;
                float num = (localTick - partialTicks) / 20f;
                num = ((2 - Mth.cos((float) (num * Math.PI))) / 2);
                offset = num - .5f;
            }
        }
        return offset + 7 / 16f;
    }

    @Override
    protected boolean isRunning() {
        return running;
    }

    @Override
    protected void onBasinRemoved() {
        running = false;
        runningTicks = 0;
        setChanged();
        sendData();
    }

    @Override
    protected boolean matchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe) {
        return true;
    }

    @Override
    protected Object getRecipeCacheKey() {
        return null;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CCFBlockEntities.CHOCOLATE_MIXER.get(),
                (be, direction) -> {
                    if (direction != Direction.DOWN && direction != Direction.UP)
                        return be.internalTanks.getCapability();
                    return null;
                }
        );
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        this.internalTanks = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, TANK_SIZE, true);
        ((TankSegmentHandler) this.internalTanks.getTanks()[0]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(Tags.Fluids.MILK);
            }
        });
        ((TankSegmentHandler) this.internalTanks.getTanks()[0]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(CCFFluids.CHOCOLATE);
            }
        });

        behaviours.add(this.internalTanks);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }

    /*@Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, level.getCapability(Capabilities.FluidHandler.BLOCK, worldPosition, null));
    }*/
}
