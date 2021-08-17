package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class EntitySandflatter extends EntityIctya implements IAnimatedAttacker {

    private boolean mustBeRemoved = false;
    private static final DataParameter<Boolean> AMBUSHING = EntityDataManager.<Boolean>createKey(EntitySandflatter.class, DataSerializers.BOOLEAN);
    private Animation attackAnimation;

    public EntitySandflatter(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(AMBUSHING, true);
        setSize(3, 0.2F);
    }

    @Override
    protected void initEntityAI() {
//        super.initEntityAI();
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false, false));
        this.targetTasks.addTask(2, new AIRevenge(this, this::shouldDefend));
        this.tasks.addTask(0, getFleeingAI(this, EntityIctya.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
        this.tasks.addTask(2, new AITelegraphedAttack(this, 1.5D, true, ATTACK_LIST));
        this.tasks.addTask(0, new SandflatterAIAmbush(this, 1.5D, true, ATTACK_LIST));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12D);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.LARGE;
    }

    @Override
    public double getFoodValue() {
        return 400;
    }

    @Override
    public double getMaxFood() {
        return 500;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 0.1;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if(dimension == DimensionRegistry.ARCHE.getId()) {
            boolean success = false;
            for (int i = 0; i < posY-5; i++) {
                if(world.getBlockState(new BlockPos(posX, posY-i, posZ)).getBlock() == BlockRegistry.DarkSand) {
                    boolean goodSpot = true;
                    for (int j = -3; j <= 3; j++) {
                        for (int k = -3; k <= 3; k++) {
                            if(world.getBlockState(new BlockPos(posX+j, posY-i, posZ+k)).getBlock() != BlockRegistry.DarkSand
                            || (world.getBlockState(new BlockPos(posX+j, posY-i+1, posZ+k)).getBlock() != Blocks.WATER) && Math.abs(j) <= 1 && Math.abs(k) <= 1) {
                                goodSpot = false;
                                break;
                            }
                            if(!goodSpot) break;
                        }
                    }
                    if(!goodSpot) break;
                    setPosition(posX, posY-i+1, posZ);
                    if(!world.getEntities(EntitySandflatter.class, e -> e != null && (this.getDistance(e) < 20 && e != this && e.isAddedToWorld())).isEmpty()) {
                        mustBeRemoved = true;
                        break;
                    }
                    success = true;
                    break;
                }
            }
            if(!success) {
                mustBeRemoved = true;
            }
            return livingdata;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if(mustBeRemoved)
            world.removeEntity(this);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(world.isRemote) {
            if(attackAnimation != null) {
                attackAnimation.update();
                if(attackAnimation.isDone())
                    attackAnimation = null;
            }
        }
    }

    @Override
    protected void on32Ticks() {
        if(!world.isRemote) {
            if(getCurrentOverMaxFood() > 0.67)
                heal(10);

            if(currentFood >= getFoodPer32Ticks())
                currentFood -= getFoodPer32Ticks();
            else
                damageEntity(DamageSource.STARVE, 3);
        }
    }

    @Override
    public void setAttackAnimation(int id) {
        attackAnimation = new Animation(AnimationRegistry.getAnimationFromId(id));
    }

    @Override
    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity trueSource = source.getTrueSource();
        if (trueSource != null && trueSource.posY < posY - 0.5) {
            amount *= 3;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("ambushing", dataManager.get(AMBUSHING));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("ambushing")) {
            boolean ambushing = compound.getBoolean("ambushing");
            dataManager.set(AMBUSHING, ambushing);
            if (!ambushing) {
                setSize(5.2F, 2F);
            }
        }
    }

    public boolean isAmbushing() {
        return dataManager.get(AMBUSHING);
    }

    public void stopAmbushing() {
        dataManager.set(AMBUSHING, false);
        setSize(5.2F, 3F);
    }

    private static final AttackList ATTACK_LIST;

    static {
        AttackList attacks = new AttackList();
        AttackArea ambushArea = AttackArea.getBoundingBoxAttack(-4, 0, -4, 4, 4.75, 4);
        AttackArea clawArea = AttackArea.getConeAttack(5, 30, 30);

        TelegraphedAttackTemplate ambushAttack = new TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_ambush, 29, 14, 40, ambushArea, 3)
                .setPredicate((sandflatter, unused) -> ((EntitySandflatter) sandflatter).isAmbushing())
                .addPostAttackEffect(sandflatter -> ((EntitySandflatter) sandflatter).stopAmbushing())
                .addPostHitEffect((unused, target) -> target.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 30, 3))) //TODO maybe use setHealth?
                .build();

        TelegraphedAttackTemplate clawAttack = new TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_claw, 12, 6, 20, clawArea, 7)
                .setPredicate((sandflatter, unused) -> !((EntitySandflatter) sandflatter).isAmbushing())
                .build();


        attacks.addAttack(ambushAttack, 10);
        attacks.addAttack(clawAttack, 10);

        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);
    }

    private static class SandflatterAIAmbush extends AITelegraphedAttack {


        public <T extends EntityCreature & IAnimatedAttacker> SandflatterAIAmbush(T creature, double speedIn, boolean useLongMemory, AttackList attacks) {
            super(creature, speedIn, useLongMemory, attacks);
        }

        @Override
        public void startExecuting() {

        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && ((EntitySandflatter)attacker).isAmbushing();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && ((EntitySandflatter)attacker).isAmbushing();
        }

        @Override
        public void updateTask() {
            super.updateTask();
            this.attacker.getNavigator().clearPath();
            this.attacker.getLookHelper().setLookPosition(attacker.posX, attacker.posY, attacker.posZ, 0,0);
        }
    }


}
