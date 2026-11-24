package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.Animation;
import com.valeriotor.beyondtheveil.entity.ai.goals.*;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class BloodSkeletonEntity extends Monster implements PlayerMinion {

    private Animation attackAnimation;
    private UUID masterId;
    private boolean isFollowing;

    public BloodSkeletonEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));
        this.targetSelector.addGoal(1, new MinionHelpMasterTargetGoal<>(this, false));
        this.targetSelector.addGoal(2, new MinionDefendMasterTargetGoal<>(this, false));
        this.targetSelector.addGoal(3, new MinionHurtByTargetGoal<>(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true, l -> masterId == null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, l -> masterId == null));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, IronGolem.class, true, l -> masterId == null));
        this.goalSelector.addGoal(3, new MinionFollowMasterGoal<>(this, 1.2D, 10.0F, 2.0F, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, BloodPoolEntityType.BLOOD_SKELETON.getMaxHealth())
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2);
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide()) {
            if (attackAnimation != null) {
                attackAnimation.update();
                if (attackAnimation.isDone()) {
                    attackAnimation = null;
                }
            } else {
                //attackAnimation = new Animation(AnimationRegistry.blood_skeleton_swing);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (masterId != null) {
            pCompound.putUUID("master", masterId);
        }
        pCompound.putBoolean("isFollowing", isFollowing);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("master", Tag.TAG_INT_ARRAY)) {
            masterId = pCompound.getUUID("master");
        }
        isFollowing = pCompound.getBoolean("isFollowing");
    }

    @Override
    public UUID getMasterID() {
        return masterId;
    }

    @Override
    public void setMasterID(UUID uuid) {
        masterId = uuid;
    }

    @Override
    public void orderToFollow(boolean follow) {
        isFollowing = follow;
    }

    @Override
    public boolean isOrderedToFollow() {
        return isFollowing;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack held = pPlayer.getItemInHand(pHand);
        if (toggleFollow(pPlayer, held)) {
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setPersistenceRequired();
    }
}
