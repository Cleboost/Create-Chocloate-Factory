package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.core.DataComponentsRegistry;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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

public class ChocolateBaseItem extends Item {
    private final float amount; // in mB

    public ChocolateBaseItem(Properties properties, float amount) {
        super(properties.component(DataComponentsRegistry.STRENGTH, 0.7F).component(DataComponentsRegistry.MILK, 0.1F).component(DataComponentsRegistry.SUGAR, 0.1F).component(DataComponentsRegistry.COCOA_BUTTER, 0.1F));
        this.amount = amount;
    }

    public float getAmount() {
        return this.amount;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        Chocolate chocolate = new Chocolate(pStack);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.composition",
                            chocolate.getStrength(), chocolate.getSugar(), chocolate.getCocoaButter(), chocolate.getMilk())
                    .withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!(pLivingEntity instanceof Player))
            return pStack;
        Player player = (Player) (pLivingEntity);
        Chocolate ch = new Chocolate(pStack);
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
}
