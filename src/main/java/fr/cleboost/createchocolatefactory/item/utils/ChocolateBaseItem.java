package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFItems;
import fr.cleboost.createchocolatefactory.core.CCFLang;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ChocolateBaseItem extends Item {
    private final float amount; // in mB

    public ChocolateBaseItem(Properties properties, float amount) {
        super(properties.component(CCFDataComponents.CHOCOLATE, new Chocolate(0.55f, 0.05f, 0.2f, 0.2f, CCFItems.MINT_LEAF)));
        this.amount = amount;
    }

    public float getAmount() {
        return this.amount;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        Chocolate chocolate = pStack.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null) return;
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.composition",
                            chocolate.getStrength(), chocolate.getSugar(), chocolate.getCocoaButter(), chocolate.getMilk())
                    .withStyle(ChatFormatting.GRAY));
        } else {
            CCFLang.hold_shift_tooltips(tooltip);
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        //EAT :
        applyEatEffects(pStack, pLevel, pLivingEntity);
        if (pLivingEntity instanceof Player player) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
            }
            if (!player.isCreative()) {
                pStack.consume(1, player);
            }
        } else {
            pStack.consume(1, pLivingEntity);
        }
        return pStack;
    }

    protected void applyEatEffects(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Chocolate ch = pStack.get(CCFDataComponents.CHOCOLATE);
        if (ch == null) return;
        if (ch.isBad()) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.OOZING, pLevel.random.nextInt(400, 1000), 2));
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, pLevel.random.nextInt(200, 500), 2));
        } else {
            if (ch.hasTaste()) {
                Optional<Taste> optionalTaste = Taste.get(pLevel, ch.getTasteItem());
                if (optionalTaste.isPresent()) {
                    Taste taste = optionalTaste.get();
                    for (Taste.ChocolateEffect effect : taste.getEffects()) {
                        pLivingEntity.addEffect(new MobEffectInstance(effect.getEffect(),
                                ch.getDuration(effect.getDuration_min(), effect.getDuration_max()),
                                ch.getAmplifier(effect.getAmplifier_min(), effect.getAmplifier_max())
                        ));
                    }
                }
            }
        }
        if (pLivingEntity instanceof Player player) {
            player.getFoodData().eat((int) (ch.getNutrition() * this.amount), ch.getSaturationModifier() * this.amount);
            player.awardStat(Stats.ITEM_USED.get(pStack.getItem()));
            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
        } else {
            pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.PLAYER_BURP, SoundSource.NEUTRAL, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
        }
        pLivingEntity.gameEvent(GameEvent.EAT);
    }
}
