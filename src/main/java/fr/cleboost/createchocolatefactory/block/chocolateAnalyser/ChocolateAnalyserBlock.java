package fr.cleboost.createchocolatefactory.block.chocolateAnalyser;

import fr.cleboost.createchocolatefactory.block.chocolateMixer.ChocolateMixerBlockEntity;
import fr.cleboost.createchocolatefactory.core.CCFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public class ChocolateAnalyserBlock extends Block implements EntityBlock {
    public ChocolateAnalyserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ChocolateMixerBlockEntity(CCFBlockEntities.CHOCOLATE_MIXER.get(), pos, state);//new ChocolateAnalyserBlockEntity(CCFBlockEntities.CHOCOLATE_ANALYSER.get(), pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(@Nonnull ItemStack item, @Nonnull BlockState state, @Nonnull Level level,
                                              @Nonnull BlockPos pos,
                                              @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof ChocolateAnalyserBlockEntity analyserEntity) {
                serverPlayer.openMenu(analyserEntity);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}
