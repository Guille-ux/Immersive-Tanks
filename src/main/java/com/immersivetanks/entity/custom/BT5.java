package com.immersivetanks.entity.custom;

import com.immersivetanks.entity.projectiles.projectile45;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import com.immersivetanks.ImmersiveTanks;
import net.minecraft.world.phys.Vec3;

public class BT5 extends TankBase {
    public BT5(EntityType<? extends Boat> type, Level level) {
        super(type, level);
        this.maxForwardSpeed = 14.44f;
        this.maxReverseSpeed = 2.77f;
        this.acceleration = 7.5f;
        this.turnSpeed = 24.0f;
        this.yawRotSpeed = 13.17f;
        this.pitchRotSpeed = 3.4f;
        this.gunMaxPitch = -0.436f;
        this.gunDepression = 0.139f;
        this.recoil = 3.3f;
        this.protection = 0.10f;
        this.shell_speed = 100.0f/20;
        this.tank_item = ImmersiveTanks.BT5_ITEM;
        this.current_turn = this.getYRot();
    }
    @Override
    public void fireMainGun() {
        if (!this.level().isClientSide && this.getControllingPassenger() instanceof Player player) {
                    // Combinamos la rotación del tanque con la de la torret

                    float totalYawRad = (this.getYRot() + this.current_yaw) * ((float)Math.PI / 180F);
                    float totalPitchRad = this.current_pitch * ((float)Math.PI/180.0f);

                    // 2. Vector de dirección (Normalizado)
                    double vx = -Math.sin(totalYawRad) * Mth.cos(totalPitchRad);
                    double vy = -Math.sin(totalPitchRad);
                    double vz = Math.cos(totalYawRad) * Mth.cos(totalPitchRad);


                    Vec3 spawnPos = this.position().add(vx * 2.5, 1.5 + (vy * 2.5), vz * 2.5);

                    projectile45 shell = new projectile45(this.level(), player);
                    shell.setPos(spawnPos.x, spawnPos.y, spawnPos.z);


                    shell.shoot(vx, vy, vz, this.shell_speed, 0.0f);

                    this.level().addFreshEntity(shell);


                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }
}
