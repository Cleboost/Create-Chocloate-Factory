package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.block.MoltenChocolateBlock;
import fr.cleboost.createchocolatefactory.core.CCFDataComponents;
import fr.cleboost.createchocolatefactory.utils.Chocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class ChocolateBucketItem extends BucketItem {
    public ChocolateBucketItem(Fluid content, Properties properties) {
        super(content, properties);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
        //quand un nouveau bloc est pose
        super.checkExtraContent(player, level, containerStack, pos);
        if (!containerStack.has(CCFDataComponents.CHOCOLATE)) {
            MoltenChocolateBlock.setChocolate(pos, new Chocolate(0.2f,0.5f,0.1f,0.2f));
        } else {
            MoltenChocolateBlock.setChocolate(pos, containerStack.get(CCFDataComponents.CHOCOLATE));
        }
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if () {

        }
        return super.use(level, player, hand);
    }*/
}
