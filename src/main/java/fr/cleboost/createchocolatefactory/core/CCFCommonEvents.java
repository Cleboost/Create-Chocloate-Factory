package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.item.utils.ChocolateBaseItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class CCFCommonEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CCFCommands.register(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        if (!(target instanceof Cat || target instanceof Wolf)) {
            return;
        }

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ChocolateBaseItem || stack.is(Items.COOKIE)) {
            if (!player.level().isClientSide) {
                LivingEntity livingTarget = (LivingEntity) target;
                livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
                livingTarget.addEffect(new MobEffectInstance(CCFEffects.CHOCOLATE_TOXICITY, 100, 0));

                if (player instanceof ServerPlayer serverPlayer) {
                    var advancement = serverPlayer.server.getAdvancements().get(CreateChocolateFactory.asResource("toxic_chocolate"));
                    if (advancement != null) {
                        serverPlayer.getAdvancements().award(advancement, "manual");
                    }
                }

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }

            target.playSound(SoundEvents.GENERIC_EAT, 1.0f, 1.0f);

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
