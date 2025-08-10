package com.valeriotor.beyondtheveil.entity.ictya;

import com.mojang.datafixers.DataFixUtils;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.Path;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SepiidEntity extends IctyaEntity {

    @Nullable
    private SepiidEntity leader;
    private int schoolSize = 1;

    public SepiidEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new FollowFlockLeaderGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.8D, 1.8D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IctyaEntity.class, 12.0F, 1.4F, 1.4F, this::shouldFlee));

    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.65F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 1.15D);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.TINY;
    }

    @Override
    public double getFoodValue() {
        return 20;
    }

    @Override
    public double getMaxFood() {
        return 20;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 0;
    }

    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return 14;
    }

    protected boolean canRandomSwim() {
        return !this.isFollower();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public SepiidEntity startFollowing(SepiidEntity pLeader) {
        this.leader = pLeader;
        pLeader.addFollower();
        return pLeader;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.schoolSize;
    }

    private void removeFollower() {
        --this.schoolSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        Path path = getNavigation().getPath();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends SepiidEntity> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }

    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }

    }

    public void addFollowers(Stream<? extends SepiidEntity> pFollowers) {
        pFollowers.limit((long) (this.getMaxSchoolSize() - this.schoolSize)).filter((p_27538_) -> {
            return p_27538_ != this;
        }).forEach((p_27536_) -> {
            p_27536_.startFollowing(this);
        });
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pSpawnData == null) {
            pSpawnData = new SepiidEntity.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((SepiidEntity.SchoolSpawnGroupData) pSpawnData).leader);
        }

        return pSpawnData;
    }

    public static class SchoolSpawnGroupData implements SpawnGroupData {
        public final SepiidEntity leader;

        public SchoolSpawnGroupData(SepiidEntity pLeader) {
            this.leader = pLeader;
        }
    }

    public static class FollowFlockLeaderGoal extends Goal {
        private static final int INTERVAL_TICKS = 200;
        private final SepiidEntity mob;
        private int timeToRecalcPath;
        private int nextStartTick;

        public FollowFlockLeaderGoal(SepiidEntity pFish) {
            this.mob = pFish;
            this.nextStartTick = this.nextStartTick(pFish);
        }

        protected int nextStartTick(SepiidEntity pTaskOwner) {
            return reducedTickDelay(200 + pTaskOwner.getRandom().nextInt(200) % 20);
        }

        public boolean canUse() {
            if (this.mob.hasFollowers()) {
                return false;
            } else if (this.mob.isFollower()) {
                return true;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                Predicate<SepiidEntity> predicate = (p_25258_) -> {
                    return p_25258_.canBeFollowed() || !p_25258_.isFollower();
                };
                List<? extends SepiidEntity> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
                SepiidEntity sepiid = DataFixUtils.orElse(list.stream().filter(SepiidEntity::canBeFollowed).findAny(), this.mob);
                sepiid.addFollowers(list.stream().filter((p_25255_) -> !p_25255_.isFollower()));
                return this.mob.isFollower();
            }
        }

        public boolean canContinueToUse() {
            return this.mob.isFollower() && this.mob.inRangeOfLeader();
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
            this.mob.stopFollowing();
        }

        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.mob.pathToLeader();
            }
        }
    }
}
