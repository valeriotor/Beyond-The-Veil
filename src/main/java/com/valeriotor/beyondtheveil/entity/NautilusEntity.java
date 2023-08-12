package com.valeriotor.beyondtheveil.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class NautilusEntity extends Entity {

    private float deltaRotation;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private boolean inputJump;

    public NautilusEntity(EntityType<? extends Entity> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        noCulling = true;

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2);
    }

    protected void doPlayerRide(Player pPlayer) {
        if (!this.level.isClientSide) {
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.startRiding(this);
        }

    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        doPlayerRide(pPlayer);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return canVehicleCollide(this, pEntity);
    }

    public static boolean canVehicleCollide(Entity pVehicle, Entity pEntity) {
        return (pEntity.canBeCollidedWith() || pEntity.isPushable()) && !pVehicle.isPassengerOfSameVehicle(pEntity);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return -0.1D;
    }


    /*public void push(Entity pEntity) {
        if (pEntity instanceof Boat) {
            if (pEntity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(pEntity);
            }
        } else if (pEntity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(pEntity);
        }

    }*/
    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.lerpYRot = (double) pYaw;
        this.lerpXRot = (double) pPitch;
        this.lerpSteps = 10;
    }

    @Override
    public Direction getMotionDirection() {
        return this.getDirection().getClockWise();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {

        //if (this.getDamage() > 0.0F) {
        //    this.setDamage(this.getDamage() - 1.0F);
        //}

        super.tick();
        this.tickLerp();
        if (this.isControlledByLocalInstance()) {

            this.floatBoat();
            if (this.level.isClientSide) {
                this.controlBoat();
                //this.level.sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }


        this.checkInsideBlocks();

    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    private void floatBoat() {
        double d0 = (double)-0.04F;
        double d1 = this.isNoGravity() || this.isInWater() ? 0.0D : (double)-0.04F;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, vec3.y + d1, vec3.z);

    }

    private void controlBoat() {
        if (this.isVehicle()) {
            float f = 0.0F;
            float vertical = 0.0F;
            if (this.inputLeft) {
                this.deltaRotation -= 0.25F;
            }

            if (this.inputRight) {
                this.deltaRotation += 0.25F;
            }

            if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                f += 0.005F;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);
            if (this.inputUp) {
                f += 0.04F;
            }

            if (this.inputDown) {
                f -= 0.005F;
            }

            if (this.inputJump && this.isUnderWater()) {
                vertical += 0.005;
            }

            this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f), vertical, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f)));
            //this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
        }
    }


    public void setInput(boolean pInputLeft, boolean pInputRight, boolean pInputUp, boolean pInputDown, boolean inputJump) {
        this.inputLeft = pInputLeft;
        this.inputRight = pInputRight;
        this.inputUp = pInputUp;
        this.inputDown = pInputDown;
        this.inputJump = inputJump;
    }


    public void positionRider(Entity pPassenger) {
        if (this.hasPassenger(pPassenger)) {
            float f = 0.0F;
            float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(pPassenger);
                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (pPassenger instanceof Animal) {
                    f += 0.2F;
                }
            }

            Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            pPassenger.setPos(this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
            pPassenger.setYRot(pPassenger.getYRot() + this.deltaRotation);
            pPassenger.setYHeadRot(pPassenger.getYHeadRot() + this.deltaRotation);
            //this.clampRotation(pPassenger);
            if (pPassenger instanceof Animal && this.getPassengers().size() > 1) {
                int j = pPassenger.getId() % 2 == 0 ? 90 : 270;
                pPassenger.setYBodyRot(((Animal)pPassenger).yBodyRot + (float)j);
                pPassenger.setYHeadRot(pPassenger.getYHeadRot() + (float)j);
            }

        }
    }

    protected void clampRotation(Entity pEntityToUpdate) {
        pEntityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(pEntityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        pEntityToUpdate.yRotO += f1 - f;
        pEntityToUpdate.setYRot(pEntityToUpdate.getYRot() + f1 - f);
        pEntityToUpdate.setYHeadRot(pEntityToUpdate.getYRot());
    }

    public void onPassengerTurned(Entity pEntityToUpdate) {
        //this.clampRotation(pEntityToUpdate);
    }

}
