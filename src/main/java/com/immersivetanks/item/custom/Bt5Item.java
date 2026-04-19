package com.immersivetanks.item.custom;

import com.immersivetanks.ImmersiveTanks;
import com.immersivetanks.entity.custom.BT5;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.sound.SoundEvent;

import java.util.List;
import java.util.function.Predicate;

public class Bt5Item extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public Bt5Item(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (!level.isClientSide()) {
            // solo en el servidor
            BlockPos pos = context.getClickedPos();
            Direction face = context.getClickedFace();
            BlockPos spawn_pos = pos.relative(face);

            Entity tank_a = ImmersiveTanks.BT5_ENTITY.get().create(level);
            if (tank_a instanceof BT5 tank) {
                tank.moveTo(spawn_pos.getX(), spawn_pos.getY() + 1.0d, spawn_pos.getZ());

                level.playSound(null, spawn_pos, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.BLOCKS);

                level.addFreshEntity(tank);

                if (player != null && !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
/*
    public InteractionResultHolder<ItemStack> use(Level p_40622_, Player p_40623_, InteractionHand p_40624_) {
        ItemStack itemstack = p_40623_.getItemInHand(p_40624_);
        HitResult hitresult = getPlayerPOVHitResult(p_40622_, p_40623_, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = p_40623_.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = p_40622_.getEntities(p_40623_, p_40623_.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = p_40623_.getEyePosition();

                for (Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Entity tank_a = ImmersiveTanks.BT5_ENTITY.get().create(p_40622_);
                if (tank_a instanceof BT5 tank) {
                    if (!p_40622_.noCollision(tank, tank.getBoundingBox())) {
                        return InteractionResultHolder.fail(itemstack);
                    } else {
                        if (!p_40622_.isClientSide) {
                            p_40622_.addFreshEntity(tank);
                            p_40622_.gameEvent(p_40623_, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                            if (!p_40623_.getAbilities().instabuild) {
                                itemstack.shrink(1);
                            }
                        }

                        p_40623_.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(itemstack, p_40622_.isClientSide());
                    }
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
        return null;
    }
    */
}
