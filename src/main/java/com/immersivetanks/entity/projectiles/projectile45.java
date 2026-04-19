package com.immersivetanks.entity.projectiles;

import com.ibm.icu.util.CodePointTrie;
import com.immersivetanks.ImmersiveTanks;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class projectile45 extends SmallFireball {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("immersivetanks", "projectile45"), "main");

    public projectile45(Level level, LivingEntity shooter) {
        super(level, shooter, 0.0f, 0.0f, 0.0f);
    }

    public projectile45(EntityType<Entity> entity, Level level) {
        super(EntityType.SMALL_FIREBALL, level);
    }


    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            Vec3 startPos = this.position();
            // Calculamos dónde terminará la bala en este tick
            Vec3 velocity = this.getDeltaMovement();
            Vec3 endPos = startPos.add(velocity);

            if (!this.level().isClientSide) {
                ServerLevel serverLevel = (ServerLevel) this.level();
                ChunkPos chunkPos = new ChunkPos(this.blockPosition());

                // Forzamos la carga del chunk donde está el proyectil
                // El 'true' añade un ticket de carga
                serverLevel.setChunkForced(chunkPos.x, chunkPos.z, true);

                // OPCIONAL: Como somos ingenieros limpios, hay que liberar
                // el chunk anterior para no quemar la RAM del servidor
                ChunkPos oldPos = new ChunkPos((int)Math.round(this.xOld/16), (int)Math.round(this.zOld/16));
                if (oldPos.x != chunkPos.x || oldPos.z != chunkPos.z) {
                    serverLevel.setChunkForced(oldPos.x, oldPos.z, false);
                }
            }

            // Trazamos un rayo desde el inicio al fin del trayecto de este tick
            HitResult hitResult = this.level().clip(new ClipContext(
                    startPos, endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
            ));

            // Si el rayo toca algo, forzamos el impacto inmediatamente
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onHit(hitResult);
                return; // Detenemos el movimiento para que no atraviese el bloque
            }
        }
        super.tick();
    }

    //@Override
    //public Item getItem() {
    //    return Items.IRON_NUGGET;
    //}

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            result.getEntity().hurt(this.damageSources().explosion(this, this.getOwner()), 50.0F);
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    protected float getGravity() {
        return 9.81f / 20;
    }

}
