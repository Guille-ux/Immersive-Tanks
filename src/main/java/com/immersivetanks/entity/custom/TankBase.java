package com.immersivetanks.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

public class TankBase extends Boat {
    public static final EntityDataAccessor<Float> DATA_YAW = SynchedEntityData.defineId(TankBase.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_PITCH = SynchedEntityData.defineId(TankBase.class, EntityDataSerializers.FLOAT);
    protected float maxForwardSpeed; // en m/s
    protected float maxReverseSpeed;
    protected float acceleration;
    protected float yawRotSpeed;
    protected float pitchRotSpeed;
    protected float turnSpeed;
    protected float gunMaxPitch;
    protected float gunDepression;
    protected float recoil;
    protected float protection;
    protected RegistryObject<Item> tank_item;
    protected float shell_speed;

    protected boolean pleft;
    protected boolean pdown;
    protected boolean pright;
    protected boolean pup;

    public float recoil_timer = 0.0f;
    public float current_turn = 0.0F;
    public float current_speed = 0.0F;
    public float current_pitch = 0.0F;
    public float current_yaw = 0.0F;

    public TankBase(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.25F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_YAW, 0.0f);
        this.entityData.define(DATA_PITCH, 0.0f);
    }

    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event) {
        if (event.getEntityBeingMounted() instanceof TankBase) {
            event.getEntityMounting().noPhysics = event.isMounting();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) return false;
        if (!this.level().isClientSide && !this.isRemoved()) {
            if (this.getControllingPassenger() instanceof Player player) {
                player.hurt(source, amount * this.protection);
            } else {
                this.spawnAtLocation(this.tank_item.get());
                this.discard();
            }
        }
        return true;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            // Ajusta esto: positivo suele ser hacia adelante, negativo hacia atrás
            float forwardOffset = 0.8F;
            float sideOffset = 0.0F; // Por si quieres que vaya a un lado (conductor)

            float height = (float)((this.isRemoved() ? 0.01F : this.getPassengersRidingOffset()));

            // Creamos el vector de posición relativa
            Vec3 posRelativa = new Vec3(sideOffset, 0.0D, forwardOffset);

            // Lo rotamos según el chasis.
            // ¡Ojo! Prueba con yRot(-this.getYRot() * rad) si esto sale invertido.
            float rads = this.getYRot() * ((float)Math.PI / 180.0F);
            posRelativa = posRelativa.yRot(-rads);

            moveFunction.accept(passenger,
                    this.getX(),
                    this.getY() + (double)height,
                    this.getZ() + 1.0d
            );
        }
    }
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    public float getYawAim() {
        if (this.getControllingPassenger() instanceof Player player) {
            float target_yaw = Mth.wrapDegrees(player.getViewYRot(1.0F) - this.getYRot());
            return target_yaw;
        }
        return this.current_yaw;
    }

    public float getPitchAim() {
        if (this.getControllingPassenger() instanceof Player player) {
            float target_pitch = player.getViewXRot(1.0F - this.getXRot());
            target_pitch = Mth.wrapDegrees(target_pitch);
            target_pitch = Mth.clamp(target_pitch, this.gunMaxPitch, this.gunDepression);
            return target_pitch;
        }
        return this.current_pitch;
    }

    @Override
    public void tick() {
        this.controlTank();

        // Gravedad
        Vec3 vel = this.getDeltaMovement();
        double gravity = this.onGround() ? 0.0 : vel.y - (9.81 / 400.0);


        float targetYaw = this.getYawAim();
        float targetPitch = this.getPitchAim();

        float delta_yaw = Mth.wrapDegrees(targetYaw - this.current_yaw);
        float delta_pitch = Mth.clamp(targetPitch - this.current_pitch, -this.pitchRotSpeed, this.pitchRotSpeed);

        delta_yaw = Mth.clamp(delta_yaw, -this.yawRotSpeed, this.yawRotSpeed);

        this.current_yaw = this.getYawAim();
        this.current_pitch = this.getPitchAim();




        if (this.getControllingPassenger() instanceof Player player) {
            // aqui codigo disparos

            if (player.swinging && this.recoil_timer <= 0) {
                this.fireMainGun();
                recoil_timer = recoil * 20;
            }
            if (recoil_timer > 0) {
                --recoil_timer;
            }
            if (!this.level().isClientSide) {
                this.entityData.set(DATA_YAW, this.current_yaw);
                this.entityData.set(DATA_PITCH, this.current_pitch);
            }

            this.setDeltaMovement(new Vec3(vel.x, gravity, vel.z));
            this.move(MoverType.SELF, this.getDeltaMovement());

        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return 1.25d;
    }

    @Override
    public void onPassengerTurned(Entity passenger) {

    }

    public void fireMainGun() {
        // nada
    }

    @Override
    public float getGroundFriction() {
        return 0.0F;
    }
    @Override
    public void setInput(boolean p_left, boolean p_right, boolean p_up, boolean p_down) {
        this.pleft = p_left;
        this.pright = p_right;
        this.pup = p_up;
        this.pdown = p_down;
        super.setInput(p_left, p_right, p_up, p_down); // x si acaso
    }
    public void controlTank() {
        if (this.isVehicle()) {
            float speedInput = 0.0f;
            float rotationInput = 0.0f;

            if (this.pup) speedInput -= this.acceleration / 20.0f;
            if (this.pdown) speedInput += this.acceleration / 20.0f;
            if (this.pleft) rotationInput += this.turnSpeed / 20.0f;
            if (this.pright) rotationInput -= this.turnSpeed / 20.0f;


            this.current_speed += speedInput;
            this.current_speed *= 0.9F;
            this.current_speed = Mth.clamp(this.current_speed, -this.maxReverseSpeed, this.maxForwardSpeed);


            this.setYRot(this.getYRot() + rotationInput);
            this.yRotO = this.getYRot();


            float rads = this.getYRot() * ((float)Math.PI / 180.0F);


            double dx = Math.sin(rads) * (this.current_speed / 20.0);
            double dz = Math.cos(rads) * (this.current_speed / 20.0);

            this.setDeltaMovement(dx, this.getDeltaMovement().y, dz);
        }
    }
}
