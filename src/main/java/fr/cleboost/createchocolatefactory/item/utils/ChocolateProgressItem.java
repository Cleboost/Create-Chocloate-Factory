package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class ChocolateProgressItem extends ChocolateBaseItem {
    public final int maxStage;

    public ChocolateProgressItem(Properties properties, int progressStage, float amount) {
        super(properties.component(CCFDataComponents.EAT_PROGRESS, 0), amount);
        this.maxStage = progressStage - 1;
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
        Chocolate ch = pStack.get(CCFDataComponents.CHOCOLATE);
        //EAT :
        player.getFoodData().eat(ch.getNutrition(), ch.getSaturationModifier());
        player.awardStat(Stats.ITEM_USED.get(pStack.getItem()));
        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
        player.gameEvent(GameEvent.EAT);
        if (eatProgress++ > this.getMaxStage()) {
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
        tooltip.add(Component.translatable("tooltip.createchocolatefactory.progress", getEatProgress(pStack)));
        super.appendHoverText(pStack, pContext, tooltip, pIsAdvanced);
    }
}
