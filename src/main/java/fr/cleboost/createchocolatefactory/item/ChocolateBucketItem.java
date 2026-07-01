package fr.cleboost.createchocolatefactory.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import java.util.List;

public class ChocolateBucketItem extends BucketItem {
    public ChocolateBucketItem(Fluid content, Properties properties) {
        super(content, properties);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, @Nonnull Level level, @Nonnull ItemStack containerStack, @Nonnull BlockPos pos) {
        super.checkExtraContent(player, level, containerStack, pos);
        if (!containerStack.has(CCFDataComponents.CHOCOLATE)) {
            MoltenChocolateBlock.setChocolate(pos, new Chocolate(0.2f,0.5f,0.1f,0.2f));
        } else {
            MoltenChocolateBlock.setChocolate(pos, containerStack.get(CCFDataComponents.CHOCOLATE));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<net.minecraft.network.chat.Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        if (stack.has(CCFDataComponents.CHOCOLATE)) {
            Chocolate chocolate = stack.get(CCFDataComponents.CHOCOLATE);
            tooltip.add(fr.cleboost.createchocolatefactory.core.CCFLangs.CHOCOLATE_COMPOSITION.getComponent(
                    String.format("%.2f", chocolate.getStrength()),
                    String.format("%.2f", chocolate.getSugar()),
                    String.format("%.2f", chocolate.getCocoaButter()),
                    String.format("%.2f", chocolate.getMilk()),
                    chocolate.getTasteText()
            ));
        }
    }
}
