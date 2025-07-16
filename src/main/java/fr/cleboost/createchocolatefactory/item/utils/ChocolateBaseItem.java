package fr.cleboost.createchocolatefactory.item.utils;

import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChocolateBaseItem extends Item {
    private final Chocolate chocolate;

    public ChocolateBaseItem(Properties properties, int sugarCount, int cocoaCount, int milkCount) {
        super(properties);
        this.chocolate = new Chocolate(sugarCount, cocoaCount, milkCount);
    }

    public Chocolate getChocolate() {
        return chocolate;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.composition",
                chocolate.getSugarCount(), chocolate.getCocoaCount(), chocolate.getMilkCount())
                .withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.createchocolatefactory.chocolate.hold_shift")
                .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
