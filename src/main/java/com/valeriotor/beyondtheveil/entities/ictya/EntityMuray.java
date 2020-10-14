package com.valeriotor.beyondtheveil.entities.ictya;

import java.util.function.Supplier;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
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

public class EntityMuray extends EntityIctya implements IAnimatedAttacker{
	
	private Animation mouthAnim;
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityMuray.class, DataSerializers.BOOLEAN);
	
	public EntityMuray(World worldIn) {
		super(worldIn);
        setSize(1.2F, 1.2F);
	} 
	
	@Override
	public IctyaSize getSize() {
		return IctyaSize.SMALL;
	}
	
	@Override
	public double getFoodValue() {
		return 100;
	}

	@Override
	public double getMaxFood() {
		return 200;
	}

	@Override
	public double getFoodPer32Ticks() {
		return 2.0F;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ATTACKING, false);
	}
	
	protected void initEntityAI() {	
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 1.4, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);	
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7D);
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
					dataManager.set(ATTACKING, false);
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
