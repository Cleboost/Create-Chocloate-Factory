package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CocoaButterBowlItem extends Item {
    public CocoaButterBowlItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) return super.useOn(pContext);
        BlockPos blockpos = pContext.getClickedPos();
        BlockState bs = level.getBlockState(blockpos);
        System.out.println(bs.getBlock().getName());
        if (bs.is(ModBlocks.COCOA_BUTTER_FLUID.get())) {
            Player player = pContext.getPlayer();
            assert player != null;
            level.destroyBlock(blockpos, false);
            if (!player.isCreative()) player.setItemInHand(pContext.getHand(), new ItemStack(Items.BOWL));
            super.useOn(pContext);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(pContext);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof Player))
            return pStack;
        pLivingEntity.eat(pLevel, pStack);
        return pStack;
    }
}
