package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ChocolateMixerBlockEntity extends BasinOperatingBlockEntity implements IHaveGoggleInformation {
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

    @Override
    public void tick() {
        super.tick();

        boolean wasRunning = isRunning;
        isRunning = canRun() && hasIngredients();

        if (runningTicks >= 40) {
            isRunning = false;
            runningTicks = 0;
            basinChecker.scheduleUpdate();
            return;
        }

        if (isRunning && level != null) {
            /*if (level.isClientSide && runningTicks == 20)
                renderParticles();*/
            if ((!level.isClientSide || isVirtual())) {
                CreateChocolateFactory.LOGGER.info(String.valueOf(isRunning));
                CreateChocolateFactory.LOGGER.info(String.valueOf(processingTicks));
                CreateChocolateFactory.LOGGER.info(String.valueOf(runningTicks));
                BasinBlockEntity basin = getBasin().get();
                if (processingTicks < 0) {
                    //startProcessingBasin();
                    updateBasin();
                    processingTicks = 20;
                } else {
                    processingTicks--;
                    if (processingTicks == 0) {
                        runningTicks = 0;
                        processingTicks = -1;
                        CreateChocolateFactory.LOGGER.info("RESULT :");
                        FluidStack output = makeChocolate();
                        CreateChocolateFactory.LOGGER.info(String.valueOf(output));
                        CreateChocolateFactory.LOGGER.info(String.valueOf(output.get(CCFDataComponents.CHOCOLATE)));
                        //basin.getTanks().get(true).getCapability().fill(output, IFluidHandler.FluidAction.EXECUTE);
                        CreateChocolateFactory.LOGGER.info(String.valueOf(basin.acceptOutputs(List.of(), List.of(output), false)));
                        updateBasin();
                    }
                }
                runningTicks++;
                if (!wasRunning) {
                    setChanged();
                    sendData();
                }
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
            } else if (runningTicks <= 40) {
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
        return Math.abs(getSpeed()) >= IRotate.SpeedLevel.FAST.getSpeedValue() && getHeatLevel() == BlazeBurnerBlock.HeatLevel.KINDLED;
    }

    protected boolean hasIngredients() {
        if (getBasin().isEmpty()) return false;
        BasinBlockEntity basin = getBasin().get();
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null) return false;
        boolean hasSugar = false, hasLiquor = false;
        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(CCFItems.CHOCOLATE_LIQUOR)) hasLiquor = true;
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

    public BlazeBurnerBlock.HeatLevel getHeatLevel() {
        if (getBasin().isEmpty()) return BlazeBurnerBlock.HeatLevel.NONE;
        return BasinBlockEntity.getHeatLevelOf(getBasin().get().getLevel()
                .getBlockState(getBasin().get().getBlockPos().below(1)));
    }

    protected FluidStack makeChocolate() {
        BasinBlockEntity basin = getBasin().get();
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        if (availableItems == null) return FluidStack.EMPTY;
        ItemStack liquor = null, sugar = null;
        int liquor_slot = -1, sugar_slot = -1;

        for (int i = 0; i < availableItems.getSlots(); i++) {
            ItemStack stack = availableItems.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(CCFItems.CHOCOLATE_LIQUOR)) {
                liquor = stack;
                liquor_slot = i;
            }
            if (stack.is(Items.SUGAR)) {
                sugar = stack;
                sugar_slot = i;
            }
            if (liquor != null && sugar != null) break;
        }
        CreateChocolateFactory.LOGGER.info("items :" + liquor);
        CreateChocolateFactory.LOGGER.info("items :" + sugar);
        if (liquor == null || sugar == null) return FluidStack.EMPTY;

        FluidStack cocoaButter = internalTanks.getCapability().getFluidInTank(COCOA_BUTTER_TANK), milk = internalTanks.getCapability().getFluidInTank(MILK_TANK);

        Chocolate chocolate;
        if (getFilter().isPresent()) {
            Chocolate old = getFilter().get();
            chocolate = new Chocolate(old.getStrength(), old.getSugar(), old.getCocoaButter(), old.getMilk());
        } else {
            chocolate = new Chocolate(liquor.getCount(), sugar.getCount(), cocoaButter.getAmount(), milk.getAmount());
        }
        int outputAmount = (int) Math.floor(computeMaxAmount(liquor.getCount(), sugar.getCount(), cocoaButter.getAmount(), milk.getAmount(), chocolate));
        //remove from input
        //liquor.shrink((int) Math.floor(outputAmount * chocolate.getStrength() / LIQUOR));
        // sugar.shrink((int) Math.floor(outputAmount * chocolate.getSugar() / SUGAR));
        availableItems.extractItem(liquor_slot, (int) Math.floor(outputAmount * chocolate.getStrength() / LIQUOR), false);
        availableItems.extractItem(sugar_slot, (int) Math.floor(outputAmount * chocolate.getSugar() / SUGAR), false);

        cocoaButter.shrink((int) Math.floor(outputAmount * chocolate.getCocoaButter()));
        milk.shrink((int) Math.floor(outputAmount * chocolate.getMilk()));
        //create ouput
        FluidStack output = new FluidStack(CCFFluids.CHOCOLATE.get(), outputAmount);
        output.set(CCFDataComponents.CHOCOLATE, chocolate);
        return output;
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
        ((TankSegmentHandler) this.internalTanks.getTanks()[MILK_TANK]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
            @Override
            public boolean test(FluidStack fluidStack) {
                return fluidStack.is(Tags.Fluids.MILK);
            }
        });
        ((TankSegmentHandler) this.internalTanks.getTanks()[COCOA_BUTTER_TANK]).create_Chocloate_Factory$getHandler().setValidator(new Predicate<FluidStack>() {
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
