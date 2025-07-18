package fr.cleboost.createchocolatefactory.block.kinetic.chocolateMixer;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.processing.basin.BasinBlock;
import com.simibubi.create.foundation.block.IBE;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChocolateMixerBlock extends KineticBlock implements IBE<ChocolateMixerBlockEntity> {

    public ChocolateMixerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Z;
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return BasinBlock.isBasin(worldIn, pos.below());
    }

    @Override
    public Class<ChocolateMixerBlockEntity> getBlockEntityClass() {
        return ChocolateMixerBlockEntity.class;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CCFBlockEntities.CHOCOLATE_MIXER.get().create(pos, state);
    }

    @Override
    public BlockEntityType<? extends ChocolateMixerBlockEntity> getBlockEntityType() {
        return CCFBlockEntities.CHOCOLATE_MIXER.get();
    }
}