package fr.cleboost.createchocolatefactory.item.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class ChocolateMouldItem extends Item {
    private final ChocolateBaseItem result;

    public ChocolateMouldItem(Properties properties, ChocolateBaseItem result) {
        super(properties.stacksTo(1).durability(16));
        this.result = result;
    }

    public ChocolateBaseItem getResultItem() {
        return result;
    }

    public ItemStack getResult(Chocolate chocolate) {
        ItemStack itemStack = new ItemStack(this.result);
        itemStack.set(CCFDataComponents.CHOCOLATE.get(), chocolate);
        return itemStack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable TooltipContext pContext, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag pIsAdvanced) {
        int amount = result.getAmount();
        tooltip.add(Component.literal(amount + "mB").withStyle(ChatFormatting.GOLD));
    }
}
