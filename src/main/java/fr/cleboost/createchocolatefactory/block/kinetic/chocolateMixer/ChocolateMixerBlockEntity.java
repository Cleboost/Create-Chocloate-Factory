package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class ChocolateMixerBlockEntity extends BasinOperatingBlockEntity {
    public int runningTicks;
    public int processingTicks;
    public boolean running;

    private FluidTank milkTank;
    private FluidTank butterCocoaTank;

    // SmartFluidTankBehaviour milkTank;
    // SmartFluidTankBehaviour butterCocoaTank;
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
        running = Math.abs(getSpeed()) > 0.5f;

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

    // public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    // 	event.registerBlockEntity(
    // 			Capabilities.FluidHandler.BLOCK,
    // 			CCFBlockEntities.CHOCOLATE_MIXER.get(),
    // 			(be, context) -> {
    // 				if (context != Direction.DOWN)
    // 					return be.milkTank.getCapability();
    // 				return null;
    // 			}
    // 	);
    // }

    // @Override
    // public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    // 	milkTank = SmartFluidTankBehaviour.single(this, 1000);
    // 	behaviours.add(milkTank);

    //     // butterCocoaTank = SmartFluidTankBehaviour.single(this, 1000);
    // 	// behaviours.add(butterCocoaTank);

    // 	// registerAwardables(behaviours, AllAdvancements.SPOUT, AllAdvancements.FOODS);
    // }

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
        CreateChocolateFactory.LOGGER.info("capability called");
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CCFBlockEntities.CHOCOLATE_MIXER.get(),
                (be, context) -> {
                    CreateChocolateFactory.LOGGER.info("capability for milk : " + context);
                    if (context != Direction.DOWN)
                        return be.milkTank;
                    return null;
                }
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CCFBlockEntities.CHOCOLATE_MIXER.get(),
                (be, context) -> {
                    if (context != Direction.DOWN)
                        return be.butterCocoaTank;
                    return null;
                }
        );
    }
}
