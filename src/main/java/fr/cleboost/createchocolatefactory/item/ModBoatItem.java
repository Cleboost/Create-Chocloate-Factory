package fr.cleboost.createchocolatefactory.item;

import fr.cleboost.createchocolatefactory.entity.custom.ModBoatEntity;
import fr.cleboost.createchocolatefactory.entity.custom.ModChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final ModBoatEntity.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean pHasChest, ModBoatEntity.Type pType, Item.Properties pProperties) {
        super(pProperties);
        this.hasChest = pHasChest;
        this.type = pType;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        HitResult hitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = pPlayer.getViewVector(1.0F);
            List<Entity> list = pLevel.getEntities(pPlayer, pPlayer.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = pPlayer.getEyePosition();

                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Boat boat = this.getBoat(pLevel, hitresult);
                if(boat instanceof ModChestBoatEntity chestBoat) {
                    chestBoat.setVariant(this.type);
                } else if(boat instanceof ModBoatEntity) {
                    ((ModBoatEntity)boat).setVariant(this.type);
                }
                boat.setYRot(pPlayer.getYRot());
                if (!pLevel.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!pLevel.isClientSide) {
                        pLevel.addFreshEntity(boat);
                        pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                        if (!pPlayer.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private Boat getBoat(Level level, HitResult hitResult) {
        return (Boat)(this.hasChest ? new ModChestBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) :
                new ModBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z));
    }
}