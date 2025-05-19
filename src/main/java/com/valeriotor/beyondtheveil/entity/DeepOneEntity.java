package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.DeepOneContact1Goal;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class DeepOneEntity extends Monster implements AnimatedEntity {

    private boolean searchingForLand;
    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;
    private ContactType contactType;
    private Player contactPlayer;
    private static final EntityDataAccessor<Integer> CONTACT_MOVE = SynchedEntityData.defineId(DeepOneEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> CONTACT_TRADE_ROT = SynchedEntityData.defineId(DeepOneEntity.class, EntityDataSerializers.FLOAT);
    private double constantY;
    private boolean startedTrade = false; // CLIENT ONLY
    private Animation mainAnimation;
    private int extraCounterOffset;
    public static final int MAX_CONTACT_MOVE_LIFETIME = 250;
    private boolean toRemove;
    private int ticksBeforeSwimDown = -1;
    private boolean finishedTradeClient;


    public DeepOneEntity(EntityType<? extends Monster> type, Level world) {
        super(BTVEntities.DEEP_ONE.get(), world);
        this.setMaxUpStep(1.0F);
        this.moveControl = new DeepOneMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.waterNavigation = null;//new WaterBoundPathNavigation(this, world);
        this.groundNavigation = null;//new GroundPathNavigation(this, world);
        noCulling = true;
        //contactType = ContactType.MOVE3;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DeepOneContact1Goal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));
        //this.goalSelector.addGoal(6, new DeepOneSwimUpGoal(this, 1.0D, this.level().getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false, null));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (toRemove) {
                discard();
                return;
            }
            if (ticksBeforeSwimDown > 0) {
                ticksBeforeSwimDown--;
                if (ticksBeforeSwimDown == 9) {
                    ejectPassengers();
                } else if (ticksBeforeSwimDown == 8) {
                    setDeltaMovement(0, -1, 0);

                } else if (ticksBeforeSwimDown == 0) {
                    discard();
                    return;
                }
            }
            if (contactType == ContactType.TRADE) {
                setDeltaMovement(0, 0, 0);
                if (getFirstPassenger() instanceof CanoeEntity canoe) {
                    setYRot(canoe.getYRot() - 90);
                    if (!(canoe.getFirstPassenger() instanceof Player player) && ticksBeforeSwimDown < 0) {
                        terminateTrade();
                    }
                } else if(ticksBeforeSwimDown < 0){
                    terminateTrade();
                }

            }
            if (contactType != null && contactType.isMove()) {
                if (tickCount > MAX_CONTACT_MOVE_LIFETIME) {
                    discard();
                    return;
                }
            }
        } else {
            int contactType = entityData.get(CONTACT_MOVE);
            if (mainAnimation != null) {
                mainAnimation.update();
                if (mainAnimation.isDone()) {
                    mainAnimation = null;
                }
            }
            if (contactType == ContactType.TRADE.ordinal()) {
                if (!startedTrade) {
                    mainAnimation = new Animation(AnimationRegistry.deep_one_trade);
                    startedTrade = true;
                    yBodyRot = 0;
                    yRotO = 0;
                    yHeadRot = 0;
                    yHeadRotO = 0;
                    //mainAnimation = new Animation(AnimationRegistry.deep_one_trade_1);
                }
                if (tickCount == 60) {
                    mainAnimation = new Animation(AnimationRegistry.deep_one_trade2);
                }
                if (tickCount >= 110 && (tickCount - 110) % 120 == 0) {
                    mainAnimation = new Animation(AnimationRegistry.deep_one_trade3);
                }
            }

        }
    }

    @Override
    public boolean startRiding(Entity pVehicle) {
        if (pVehicle instanceof CanoeEntity) {
            return false;
        }
        return super.startRiding(pVehicle);
    }

    public void terminateTrade() {
        // do animation, unmount canoe, start to move and disappear
        Messages.sendToTracking(GenericToClientPacket.startAnimation(AnimationRegistry.deep_one_trade4, getId(), 0), this);
        ticksBeforeSwimDown = 16;
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        if (pPassenger instanceof CanoeEntity canoe) {
            double d0 = this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() - 0.8;
            pCallback.accept(pPassenger, this.getX(), d0, this.getZ());
        } else {
            super.positionRider(pPassenger, pCallback);
        }
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new AmphibiousPathNavigation(this, pLevel);
    }


    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    boolean wantsToSwim() {
        if (this.searchingForLand) {
            return true;
        } else {
            LivingEntity livingentity = this.getTarget();
            return livingentity != null && livingentity.isInWater();
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater() && this.wantsToSwim()) {
            this.moveRelative(0.04F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }

    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if ((this.isEffectiveAi() && this.isUnderWater() && this.wantsToSwim()) || contactType != null) {
                //this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                //this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }

    }


    public boolean isVisuallySwimming() {
        return this.isSwimming();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CONTACT_MOVE, -1);
        this.entityData.define(CONTACT_TRADE_ROT, 0F);
    }

    public void setContact(ContactType contactType, Player contactPlayer) {
        this.contactType = contactType;
        this.contactPlayer = contactPlayer;
        entityData.set(CONTACT_MOVE, contactType.ordinal());
        if (contactType == ContactType.TRADE) {
            constantY = getY();
            if (getFirstPassenger() instanceof CanoeEntity canoe) {
                entityData.set(CONTACT_TRADE_ROT, canoe.getYRot());
            }
        }
    }

    public void setExtraCounterOffset(int extraCounterOffset) {
        this.extraCounterOffset = extraCounterOffset;
    }

    public int getExtraCounterOffset() {
        return extraCounterOffset;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public int getContactMove() {
        return entityData.get(CONTACT_MOVE);
    }

    public float getContactTradeRot() {
        return entityData.get(CONTACT_TRADE_ROT);
    }

    public int getCanoeAskew() {
        if (ticksBeforeSwimDown > 0) {
            return Mth.clamp(ticksBeforeSwimDown - 5, 0, 13);
        }
        return Mth.clamp((tickCount - 9) * 13 / 2, 0, 13);
    }

    public Animation getMainAnimation() {
        return mainAnimation;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (contactType != null) {
            pCompound.putBoolean("toRemove", true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        toRemove = pCompound.contains("toRemove");
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        switch (channel) {
            case 0 -> mainAnimation = new Animation(animationTemplate);
        }
        if (animationTemplate == AnimationRegistry.deep_one_trade4) {
            finishedTradeClient = true;
        }
    }

    public boolean isFinishedTradeClient() {
        return finishedTradeClient;
    }

    static class DeepOneMoveControl extends MoveControl {

        private final DeepOneEntity deepOne;

        private int wasInWater;

        public DeepOneMoveControl(DeepOneEntity deepOne) {
            super(deepOne);
            this.deepOne = deepOne;
        }

        public void tick() {
            LivingEntity livingentity = this.deepOne.getTarget();
            if (this.deepOne.isInWater()) {
                wasInWater = 20;
                if (livingentity != null && livingentity.getY() > this.deepOne.getY() || this.deepOne.searchingForLand) {
                    this.deepOne.setDeltaMovement(this.deepOne.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
                }

                if (this.operation != MoveControl.Operation.MOVE_TO || this.deepOne.getNavigation().isDone()) {
                    this.deepOne.setSpeed(0.0F);
                    return;
                }

                double d0 = this.wantedX - this.deepOne.getX();
                double d1 = this.wantedY - this.deepOne.getY();
                double d2 = this.wantedZ - this.deepOne.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.deepOne.setYRot(this.rotlerp(this.deepOne.getYRot(), f, 90.0F));
                this.deepOne.yBodyRot = this.deepOne.getYRot();
                float f1 = (float) (this.speedModifier * this.deepOne.getAttributeValue(Attributes.MOVEMENT_SPEED) * 10);
                float f2 = Mth.lerp(0.125F, this.deepOne.getSpeed(), f1);
                this.deepOne.setSpeed(f2);
                this.deepOne.setDeltaMovement(this.deepOne.getDeltaMovement().add((double) f2 * d0 * 0.005D, (double) f2 * d1 * 0.1D, (double) f2 * d2 * 0.005D));
            } else {
                wasInWater--;

                if (wasInWater > 0 && !deepOne.isUnderWater()) {
                    double dX = this.wantedX - this.deepOne.getX();
                    double dZ = this.wantedZ - this.deepOne.getZ();
                    float f1 = (float) (this.speedModifier * this.deepOne.getAttributeValue(Attributes.MOVEMENT_SPEED) * 10);
                    float f2 = Mth.lerp(0.125F, this.deepOne.getSpeed(), f1);
                    this.deepOne.setDeltaMovement(this.deepOne.getDeltaMovement().add((double) f2 * dX * 0.005D, 0, (double) f2 * dZ * 0.005D));
                }
                if (!this.deepOne.onGround()) {
                    this.deepOne.setDeltaMovement(this.deepOne.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
                }


                super.tick();
            }

        }

    }

    public enum ContactType {
        MOVE1(3, 40), MOVE2(1.5F, 0), MOVE3(2.5F, 70), TRADE(1, 1, false);

        private final float factor;
        private final int startOffset;
        private final boolean move;

        ContactType(float factor, int startOffset) {
            this(factor, startOffset, true);
        }

        ContactType(float factor, int startOffset, boolean move) {
            this.factor = factor;
            this.startOffset = startOffset;
            this.move = move;
        }

        public float getFactor() {
            return factor;
        }

        public int getStartOffset() {
            return startOffset;
        }

        public boolean isMove() {
            return move;
        }
    }


}
