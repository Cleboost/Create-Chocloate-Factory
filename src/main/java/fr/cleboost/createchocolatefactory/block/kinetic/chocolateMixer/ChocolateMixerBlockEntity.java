package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.TankSegmentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ChocolateMixerBlockEntity extends BasinOperatingBlockEntity implements IHaveGoggleInformation {
    private static final int TANK_SIZE = 500;
    public int runningTicks;
    public int processingTicks;
    public boolean isRunning;

    private SmartFluidTankBehaviour milkTank;
    private SmartFluidTankBehaviour cocoaButterTank;
    private SmartFluidTankBehaviour internalTanks;

    public ChocolateMixerBlockEntity(BlockEntityType<? extends ChocolateMixerBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        boolean wasRunning = isRunning;
        isRunning = Math.abs(getSpeed()) > 0.5f && !this.internalTanks.isEmpty();

        if (isRunning) {
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
        if (isRunning) {
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
        return isRunning;
    }

    @Override
    public void startProcessingBasin() {
        if (isRunning && runningTicks <= 20)
            return;
        super.startProcessingBasin();
        this.runningTicks = 0;
        this.isRunning = true;
    }

    @Override
    protected void onBasinRemoved() {
        isRunning = false;
        runningTicks = 0;
        setChanged();
        sendData();
    }

    @Override
    protected List<Recipe<?>> getMatchingRecipes() {
        List<Recipe<?>> matchingRecipes = super.getMatchingRecipes();
        Optional<BasinBlockEntity> basin = getBasin();
        if (!basin.isPresent())
            return matchingRecipes;
        BasinBlockEntity basinBlockEntity = basin.get();
        CreateChocolateFactory.LOGGER.info(String.valueOf(basinBlockEntity.getFilter().getFilter()));
        if (basinBlockEntity.isEmpty() || !basinBlockEntity.getFilter().getFilter().is(CCFItems.CHOCOLATE_FILTER.get()))
            return matchingRecipes;
        ItemStack chocolateFilter = basinBlockEntity.getFilter().getFilter();
        return super.getMatchingRecipes();
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

        /*this.internalTanks.whenFluidUpdates(()->{

        });*/
        this.internalTanks = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, TANK_SIZE, false);
        ((TankSegmentHandler) this.internalTanks.getTanks()[0]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(Tags.Fluids.MILK);
            }
        });
        ((TankSegmentHandler) this.internalTanks.getTanks()[1]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(Tags.Fluids.WATER);
            }
        });
        this.internalTanks.allowExtraction().allowInsertion();
        behaviours.add(this.internalTanks);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, worldPosition, null);
        if (fluidHandler == null) return false;
        boolean empty = true;
        CreateChocolateFactory.LOGGER.info("#####");
        CreateChocolateFactory.LOGGER.info(String.valueOf(fluidHandler.getTanks()));
        for (int i = 0; i < fluidHandler.getTanks(); i++) {
            empty &= fluidHandler.getFluidInTank(i).isEmpty();
            CreateChocolateFactory.LOGGER.info(String.valueOf(i));
            CreateChocolateFactory.LOGGER.info(String.valueOf(fluidHandler.getFluidInTank(i)));
            CreateChocolateFactory.LOGGER.info(String.valueOf(fluidHandler.getTankCapacity(i)));
        }
        if (empty) return false;
        return containedFluidTooltip(tooltip, isPlayerSneaking, fluidHandler);
        //return false;
    }
}
