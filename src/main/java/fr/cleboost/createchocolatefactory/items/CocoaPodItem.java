package fr.cleboost.createchocolatefactory.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CocoaPodItem extends BlockItem {
    
    public CocoaPodItem(Properties properties) {
        super(fr.cleboost.createchocolatefactory.utils.ModBlocks.COCOA_POD.get(), properties);
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.createchocolatefactory.cocoa_pod"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
} 