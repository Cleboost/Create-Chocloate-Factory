package fr.cleboost.createchocolatefactory.item.utils;

import org.jetbrains.annotations.NotNull;

import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import fr.cleboost.createchocolatefactory.utils.ModDataComponents;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ChocolateProgressItem extends ChocolateBaseItem {
    public final int maxStage;

    public ChocolateProgressItem(Properties properties, int progressStage, int sugarCount, int cocoaCount, int milkCount) {
        super(properties, sugarCount, cocoaCount, milkCount);
        this.maxStage = progressStage - 1;
    }

    public int getMaxStage() {
        return maxStage;
    }

    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
        if (!(entity instanceof Player)) {
            return stack;
        }
        Player player = (Player) entity;
        Chocolate ch = getChocolate();
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        player.gameEvent(GameEvent.EAT);
        int eatProgress = getEatProgress(stack) + 1;
        if (eatProgress > maxStage) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
            if (player.isCreative()) {
                setEatProgress(stack, 0);
            } else {
                stack.shrink(1);
            }
        } else {
            setEatProgress(stack, eatProgress);
        }
        return stack;
    }

    public static int getEatProgress(ItemStack item) {
        CompoundTag tag = item.get(ModDataComponents.EAT_PROGRESS.get());
        if (tag != null && tag.contains("eatProgress")) {
            return tag.getInt("eatProgress");
        }
        return 0;
    }

    private static void setEatProgress(ItemStack item, int eatProgress) {
        CompoundTag tag = item.get(ModDataComponents.EAT_PROGRESS.get());
        if (tag == null) tag = new CompoundTag();
        tag.putInt("eatProgress", eatProgress);
        item.set(ModDataComponents.EAT_PROGRESS.get(), tag);
    }

    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("tooltip.createchocolatefactory.progress", getEatProgress(pStack)));
        super.appendHoverText(pStack, pContext, tooltip, pIsAdvanced);
    }
}
