package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.core.CCFLangs;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChocolateFilterItem extends Item {
    public ChocolateFilterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        Chocolate chocolate = stack.get(CCFDataComponents.CHOCOLATE);
        if (chocolate == null) {
            return;
        }

        tooltip.add(CCFLangs.FILTER_STRENGTH.getComponent(String.format("%.2f", chocolate.getStrength())).withStyle(ChatFormatting.GRAY));
        tooltip.add(CCFLangs.FILTER_MILK.getComponent(String.format("%.2f", chocolate.getMilk())).withStyle(ChatFormatting.GRAY));
        tooltip.add(CCFLangs.FILTER_SUGAR.getComponent(String.format("%.2f", chocolate.getSugar())).withStyle(ChatFormatting.GRAY));
        tooltip.add(CCFLangs.FILTER_COCOA_BUTTER.getComponent(String.format("%.2f", chocolate.getCocoaButter())).withStyle(ChatFormatting.GRAY));
    }
}

