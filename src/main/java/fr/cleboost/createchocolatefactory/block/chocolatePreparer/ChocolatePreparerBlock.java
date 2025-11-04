package fr.cleboost.createchocolatefactory.block.chocolatePreparer;

import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import fr.cleboost.createchocolatefactory.utils.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.block.IBE;

public class ChocolatePreparerBlock extends Block implements IBE<ChocolatePreparerBlockEntity> {
    public ChocolatePreparerBlock(Properties properties) {
        super(properties);
    }

    @Override
	protected InteractionResult useWithoutItem(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull BlockHitResult hitResult) {
		if (level.isClientSide)
			return InteractionResult.SUCCESS;
		withBlockEntityDo(level, pos,
				be -> player.openMenu(be, be::sendToMenu));
		return InteractionResult.SUCCESS;
	}

    @Override
	public Class<ChocolatePreparerBlockEntity> getBlockEntityClass() {
		return ChocolatePreparerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ChocolatePreparerBlockEntity> getBlockEntityType() {
		return CCFBlockEntities.CHOCOLATE_PREPARER.get();
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return TickableBlockEntity.getTickerHelper(pLevel);
	}
}
