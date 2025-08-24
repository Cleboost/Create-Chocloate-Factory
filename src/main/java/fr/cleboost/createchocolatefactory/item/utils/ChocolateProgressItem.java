package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFLangs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class ChocolateProgressItem extends ChocolateBaseItem {
    public final int maxStage;

    //Note: the amount is for 1 stage
    public ChocolateProgressItem(Properties properties, int stage, int amount) {
        super(properties.component(CCFDataComponents.EAT_PROGRESS, 0), amount);
        this.maxStage = stage - 1;
    }

    public int getMaxStage() {
        return maxStage;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof Player))
            return pStack;
        int eatProgress = getEatProgress(pStack);
        Player player = (Player) (pLivingEntity);
        applyEatEffects(pStack, pLevel, pLivingEntity);
        eatProgress++;
        if (eatProgress > this.getMaxStage()) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
            }
            if (player.isCreative()) {
                setEatProgress(pStack, 0);
            } else {
                pStack.shrink(1);
            }
        } else {
            setEatProgress(pStack, eatProgress);
        }
        return pStack;
    }

    public static int getEatProgress(ItemStack stack) {
        return stack.get(CCFDataComponents.EAT_PROGRESS).intValue();
    }

    private static void setEatProgress(ItemStack stack, int eatProgress) {
        stack.set(CCFDataComponents.EAT_PROGRESS, eatProgress);
    }

    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        tooltip.add(CCFLangs.PROGRESS.getComponent(getEatProgress(pStack), getMaxStage()));
        super.appendHoverText(pStack, pContext, tooltip, pIsAdvanced);
    }
}
