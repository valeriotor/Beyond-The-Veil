package com.valeriotor.beyondtheveil.entities;

import java.util.function.Supplier;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.util.WaterMoveHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMuray extends EntityMob implements IAnimatedAttacker{
	
	private boolean targetingLastTick = false;
	private Animation mouthAnim;
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityMuray.class, DataSerializers.BOOLEAN);
	
	public EntityMuray(World worldIn) {
		super(worldIn);
		this.moveHelper = new WaterMoveHelper(this);
        setPathPriority(PathNodeType.WALKABLE, -8.0F);
        setPathPriority(PathNodeType.BLOCKED, -8.0F);
        setPathPriority(PathNodeType.WATER, 16.0F);
	} 
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ATTACKING, false);
	}
	
	protected void initEntityAI() {	 	
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 1.4, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12D);
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}
	
	@Override
    public float getBlockPathWeight(BlockPos pos) {
        return world.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
    }
	
	@Override
	public void travel(float strafe, float vertical, float forward) {
		if (isServerWorld()) {
            if (isInWater()) {
                moveRelative(strafe, vertical, forward, 0.2F);
                move(MoverType.SELF, motionX, motionY, motionZ);
                motionX *= 0.9D;
                motionY *= 0.9D;
                motionZ *= 0.9D;

            } else {
                super.travel(strafe, vertical, forward);
            }
        } else {
            super.travel(strafe, vertical, forward);
        }
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(world.isRemote) {
			if(mouthAnim != null) {
				if(mouthAnim.isDone())
					mouthAnim = null;
				else mouthAnim.update();
			}
		} else {
			if(getAttackTarget() != null) {
				if(!dataManager.get(ATTACKING)) {
					//TODO mouth open anim
					dataManager.set(ATTACKING, true);
				}
			} else {
				if(dataManager.get(ATTACKING)) {
					//TODO mouth close anim
					dataManager.set(ATTACKING, true);
				}
			}
		}
	}
	
	
	
	@Override
	public void swingArm(EnumHand hand) {
		super.swingArm(hand);
		if(!this.world.isRemote)
			this.sendAnimation(MurayAttacks.values()[this.rand.nextInt(MurayAttacks.values().length)].ordinal());
	}

	@Override
	public void setAttackAnimation(int id) {
		mouthAnim = MurayAttacks.values()[id].getAnim();
	}

	@Override
	public Animation getAttackAnimation() {
		return mouthAnim;
	}
	
	public boolean isAttacking() {
		return dataManager.get(ATTACKING);
	}
	
	private enum MurayAttacks {
		LEFT_SWING(() -> new Animation(AnimationRegistry.muray_bite));
		
		private Supplier<Animation> func;
		private MurayAttacks(Supplier<Animation> func) {
			this.func = func;
		}
		
		private Animation getAnim() {
			return this.func.get();
		}
	}
	
}
