package fr.cleboost.createchocolatefactory.block.chocolatemixer;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFFluids;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.TankSegmentHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ChocolateMixerBlockEntity extends BasinOperatingBlockEntity {
    private static final int TANK_SIZE = 500;
    protected static final float LIQUOR = 250;
    protected static final float SUGAR = 100;
    private static final int COCOA_BUTTER_TANK = 0;
    private static final int MILK_TANK = 1;
    public int runningTicks;
    public int processingTicks;
    public boolean isRunning;

    private SmartFluidTankBehaviour internalTanks;

    public ChocolateMixerBlockEntity(BlockEntityType<? extends ChocolateMixerBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @SuppressWarnings("null")
    @Override
    public void tick() {
        super.tick();

        if (runningTicks >= 40) {
            isRunning = false;
            runningTicks = 0;
            basinChecker.scheduleUpdate();
            return;
        }
        if (isRunning && level != null) {
            if ((!level.isClientSide || isVirtual()) && runningTicks == 20) {
                BasinBlockEntity basin = getBasin().get();

                if (processingTicks < 0) {
                    processingTicks = 60;
                    CreateChocolateFactory.LOGGER.info("Start making chocolate " + processingTicks);
                    if (level != null) {
                        level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
                                SoundSource.BLOCKS, .75f, speed < 65 ? .75f : 1.5f);
                    }
                } else {
                    processingTicks--;
                    if (processingTicks == 0 && level != null) {
                        runningTicks++;
                        processingTicks = -1;
                        CreateChocolateFactory.LOGGER.info("RESULT :");
                        FluidStack output = makeChocolate();
                        CreateChocolateFactory.LOGGER.info(String.valueOf(output));
                        CreateChocolateFactory.LOGGER.info(String.valueOf(output.get(CCFDataComponents.CHOCOLATE)));
                        CreateChocolateFactory.LOGGER.info(String.valueOf(basin.acceptOutputs(List.of(), List.of(output), false)));
                        internalTanks.sendDataImmediately();
                        sendData();
                        basin.notifyChangeOfContents();
                    }
                }
            }
            if (runningTicks != 20)
                runningTicks++;
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

    protected boolean canRun() {
        return Math.abs(getSpeed()) >= IRotate.SpeedLevel.FAST.getSpeedValue() && getHeatLevel() == BlazeBurnerBlock.HeatLevel.KINDLED && hasEnoughOutputSpace();
    }

    protected boolean hasEnoughOutputSpace() {
        if (getBasin().isEmpty()) return false;
        BasinBlockEntity basin = getBasin().get();
        return basin.getTanks().both(SmartFluidTankBehaviour::isEmpty);
    }

    @SuppressWarnings("null")
    protected boolean hasIngredients() {
        if (getBasin().isEmpty()) return false;
        BasinBlockEntity basin = getBasin().get();
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null) return false;
        boolean hasSugar = false, hasLiquor = false;
        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(CCFItems.COCOA_POWDER)) hasLiquor = true;
            if (stack.is(Items.SUGAR)) hasSugar = true;
            if (hasLiquor && hasSugar) break;
        }
        return !this.internalTanks.getCapability().getFluidInTank(COCOA_BUTTER_TANK).isEmpty() && !this.internalTanks.getCapability().getFluidInTank(MILK_TANK).isEmpty() && hasLiquor && hasSugar;
    }

    protected float computeOutputAmount(int liquor, int sugar, int cocoaButter, int milk) {
        return liquor * LIQUOR + sugar * SUGAR + cocoaButter + milk;
    }

    protected float computeMaxAmount(int liquor, int sugar, int cocoaButter, int milk, Chocolate goal) {
        float max = computeOutputAmount(liquor, sugar, cocoaButter, milk), scale = 1f;
        float[] ingredients = new float[]{liquor * LIQUOR, sugar * SUGAR, cocoaButter, milk};
        float[] goals = new float[]{goal.getStrength() * max, goal.getSugar() * max, goal.getCocoaButter() * max, goal.getMilk() * max};
        for (int i = 0; i < 4; i++) {
            if (ingredients[i] < goals[i] * scale) {
                scale = ingredients[i] / (goals[i] * scale);
            }
        }
        return scale * max;
    }

    public Optional<Chocolate> getFilter() {
        if (getBasin().isEmpty())
            return Optional.empty();
        BasinBlockEntity basinBlockEntity = getBasin().get();
        if (basinBlockEntity.isEmpty() || !basinBlockEntity.getFilter().getFilter().is(CCFItems.CHOCOLATE_FILTER.get()))
            return Optional.empty();
        return Optional.of(basinBlockEntity.getFilter().getFilter().get(CCFDataComponents.CHOCOLATE));
    }

    @SuppressWarnings("null")
    public BlazeBurnerBlock.HeatLevel getHeatLevel() {
        if (getBasin().isEmpty()) return BlazeBurnerBlock.HeatLevel.NONE;
        BasinBlockEntity basin = getBasin().get();
        if (basin.getLevel() == null) return BlazeBurnerBlock.HeatLevel.NONE;
        return BasinBlockEntity.getHeatLevelOf(basin.getLevel()
                .getBlockState(basin.getBlockPos().below(1)));
    }

    @SuppressWarnings("null")
    protected FluidStack makeChocolate() {
        BasinBlockEntity basin = getBasin().get();
        if (basin.getLevel() == null) return FluidStack.EMPTY;
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null) return FluidStack.EMPTY;
        ItemStack cocoa = null, sugar = null;
        int cocoa_slot = -1, sugar_slot = -1;

        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(CCFItems.COCOA_POWDER)) {
                cocoa = stack;
                cocoa_slot = i;
            }
            if (stack.is(Items.SUGAR)) {
                sugar = stack;
                sugar_slot = i;
            }
            if (cocoa != null && sugar != null) break;
        }
        if (cocoa == null || sugar == null) return FluidStack.EMPTY;

        FluidStack cocoaButter = internalTanks.getCapability().getFluidInTank(COCOA_BUTTER_TANK), milk = internalTanks.getCapability().getFluidInTank(MILK_TANK);

        Chocolate chocolate;
        if (getFilter().isPresent()) {
            Chocolate old = getFilter().get();
            chocolate = new Chocolate(old.getStrength(), old.getSugar(), old.getCocoaButter(), old.getMilk());
        } else {
            chocolate = new Chocolate(cocoa.getCount(), sugar.getCount(), cocoaButter.getAmount(), milk.getAmount());
        }
        int outputAmount = (int) Math.floor(computeMaxAmount(cocoa.getCount(), sugar.getCount(), cocoaButter.getAmount(), milk.getAmount(), chocolate));

        IFluidHandler outputTank = basin.getTanks().get(true).getCapability();
        if (outputTank != null) {
            FluidStack currentOutput = outputTank.getFluidInTank(0);
            int availableSpace = 1000 - currentOutput.getAmount();
            if (availableSpace <= 0) {
                return FluidStack.EMPTY;
            }
            outputAmount = Math.min(outputAmount, availableSpace);
        }

        availableItems.extractItem(cocoa_slot, (int) Math.floor(outputAmount * chocolate.getStrength() / LIQUOR), false);
        availableItems.extractItem(sugar_slot, (int) Math.floor(outputAmount * chocolate.getSugar() / SUGAR), false);

        cocoaButter.shrink((int) Math.floor(outputAmount * chocolate.getCocoaButter()));
        milk.shrink((int) Math.floor(outputAmount * chocolate.getMilk()));
  
        FluidStack output = new FluidStack(CCFFluids.CHOCOLATE.get(), outputAmount);
        output.set(CCFDataComponents.CHOCOLATE, chocolate);
        return output;
    }

    @Override
    public void startProcessingBasin() {
        if (isRunning && runningTicks <= 20)
            return;
        CreateChocolateFactory.LOGGER.info("starting basin block entity");
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
    public boolean continueWithPreviousRecipe() {
        runningTicks = 20;
        return true;
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

        this.internalTanks = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, TANK_SIZE, false);
        ((TankSegmentHandler) this.internalTanks.getTanks()[MILK_TANK]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(Tags.Fluids.MILK);
            }
        });
        ((TankSegmentHandler) this.internalTanks.getTanks()[COCOA_BUTTER_TANK]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.getFluid().isSame(CCFFluids.COCOA_BUTTER.get());
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

    @SuppressWarnings("null")
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (level == null) return false;
        IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, worldPosition, Direction.NORTH);
        if (fluidHandler == null) return false;
        boolean empty = true;
        for (int i = 0; i < fluidHandler.getTanks(); i++) {
            empty &= fluidHandler.getFluidInTank(i).isEmpty();
        }
        if (empty) return false;
        return containedFluidTooltip(tooltip, isPlayerSneaking, fluidHandler);
    }

    @SuppressWarnings("null")
    @Override
    protected boolean updateBasin() {
        if (!isSpeedRequirementFulfilled())
            return true;
        if (getSpeed() == 0)
            return true;
        if (isRunning())
            return true;
        if (level == null || (level != null && level.isClientSide))
            return true;
        Optional<BasinBlockEntity> basin = getBasin();
        if (!basin.filter(BasinBlockEntity::canContinueProcessing)
                .isPresent())
            return true;
        CreateChocolateFactory.LOGGER.info(String.valueOf(canRun()));
        CreateChocolateFactory.LOGGER.info(String.valueOf(hasIngredients()));
        if (!canRun() || !hasIngredients()) return true;
        startProcessingBasin();
        sendData();
        return true;
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        isRunning = compound.getBoolean("Running");
        runningTicks = compound.getInt("Ticks");
        super.read(compound, registries, clientPacket);

        if (clientPacket && hasLevel())
            getBasin().ifPresent(bte -> bte.setAreFluidsMoving(isRunning && runningTicks <= 20));
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putBoolean("Running", isRunning);
        compound.putInt("Ticks", runningTicks);
        super.write(compound, registries, clientPacket);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        boolean slow = Math.abs(getSpeed()) < 65;
        if (slow && AnimationTickHolder.getTicks() % 2 == 0)
            return;
        if (runningTicks == 20)
            AllSoundEvents.MIXING.playAt(level, worldPosition, .75f, 1, true);
    }
}
