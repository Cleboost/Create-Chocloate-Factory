package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ChocolateItem extends Item {
    public ChocolateItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof Player))
            return pStack;
        Player player = (Player) (pLivingEntity);
        Chocolate ch = new Chocolate(pStack.getTag());
        //EAT :
        player.getFoodData().eat(ch.getNutrition(), ch.getSaturationModifier());
        player.awardStat(Stats.ITEM_USED.get(pStack.getItem()));
        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
        player.gameEvent(GameEvent.EAT);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
        }
        if (!player.isCreative()) {
            pStack.shrink(1);
        }
        return pStack;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide()) return;
        if (Chocolate.hasChocolateProperties(pStack.getTag())) return;
        Chocolate ch;
        if (((Player) (pEntity)).isCreative()) ch = new Chocolate(true);
        else ch = new Chocolate(pStack.getTag());
        ch.saveTag(pStack);
    }
}
