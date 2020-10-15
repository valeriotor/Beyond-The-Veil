package com.valeriotor.beyondtheveil.entities.ictya;

import com.google.common.base.Predicate;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.util.WaterMoveHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityIctya extends EntityMob implements IDamageCapper{
	protected double currentFood = getMaxFood();
	public EntityIctya(World worldIn) {
		super(worldIn);
		moveHelper = new WaterMoveHelper(this);
        setPathPriority(PathNodeType.WALKABLE, -8.0F);
        setPathPriority(PathNodeType.BLOCKED, -8.0F);
        setPathPriority(PathNodeType.WATER, 16.0F);
	}
	
	public abstract IctyaSize getSize();
	
	public int getSizeInt() {
		return getSize().getSizeInt();
	}
	
	public int compareSizeTo(EntityLivingBase entity) {
		if(entity instanceof EntityPlayer)
			return getSizeInt() - 2;
		else if(entity instanceof EntityIctya)
			return getSizeInt() - ((EntityIctya)entity).getSizeInt();
		return getSizeInt();
	}
	
	public abstract double getFoodValue();
	public abstract double getMaxFood();
	public abstract double getFoodPer32Ticks();
	
	public double getCurrentOverMaxFood() {
		return currentFood / getMaxFood();
	}
	
	@Override
	protected void initEntityAI() {
		this.targetTasks.addTask(1, new AINearestAttackableTargetArche<>(this, EntityPlayer.class, 10, true, false, this::shouldAttack));
		this.targetTasks.addTask(1, new AINearestAttackableTargetArche<>(this, EntityIctya.class, 10, true, false, this::shouldAttack));
        this.targetTasks.addTask(2, new AIRevenge(this, this::shouldDefend));
        this.tasks.addTask(0, getFleeingAI(this, EntityIctya.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
		this.tasks.addTask(0, getFleeingAI(this, EntityPlayer.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
	}
	
	protected <T extends EntityLivingBase> EntityAIAvoidEntity<T> getFleeingAI(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate <? super T > avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
		return new EntityAIAvoidEntity<>(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
	}
	
	protected double getSpeed() {
		return getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
	}
	
	protected boolean shouldAttack(EntityLivingBase attacked) {
		if(getSize() == IctyaSize.PREY) return false;
		int diff = this.compareSizeTo(attacked);
		if(diff >= 2) {
			if(getCurrentOverMaxFood() < 0.85) return true;
		} else if(diff == 1) {
			if(getCurrentOverMaxFood() < 0.67) return true;
		} else if(diff == 0) {
			if(getCurrentOverMaxFood() < 0.25) return true;
		} else if(diff == -1) {
			if(getCurrentOverMaxFood() < 0.1 && attacked.getHealth() / attacked.getMaxHealth() < 0.3F) return true;
		}
		return false;
	}
	
	protected boolean shouldDefend(EntityLivingBase attacker) {
		if(getSize() == IctyaSize.PREY) return false;
		int diff = this.compareSizeTo(attacker);
		if(diff >= -1) return true;
		return false;
	}
	
	protected boolean shouldFlee(EntityLivingBase spooker) {
		return this.compareSizeTo(spooker) <= -2;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
			
		if((ticksExisted & 31) == 0) 
			on32Ticks();
	}
	
	protected void on32Ticks() {
		if(!world.isRemote && getSize() != IctyaSize.PREY) {
			if(getCurrentOverMaxFood() > 0.67)
				heal(1);
			
			if(currentFood >= getFoodPer32Ticks())
				currentFood -= getFoodPer32Ticks();
			else
				damageEntity(DamageSource.STARVE, 3);
		}
	}
	
	@Override
	public void onKillEntity(EntityLivingBase e) {
		if(e instanceof EntityIctya)
			currentFood = Math.min(getMaxFood(), currentFood + ((EntityIctya)e).getFoodValue());
		else if(e instanceof EntityPlayer)
			currentFood = Math.min(getMaxFood(), currentFood + 200);
		super.onKillEntity(e);
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
	public boolean getCanSpawnHere() {
		return this.posY > 15.0D && this.posY < (double)this.world.getSeaLevel()-7 && world.getBlockState(getPosition()).getBlock() == Blocks.WATER;
	}
	
	@Override
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
		return type == EnumCreatureType.MONSTER;
	}
	
	@Override
	public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setDouble("food", currentFood);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("food"))
			currentFood = compound.getDouble("food");
		super.readFromNBT(compound);
	}
	
	@Override
	public float getMaxDamage() {
		return 30;
	}
	
	private static class AINearestAttackableTargetArche<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {

		public AINearestAttackableTargetArche(EntityCreature creature, Class<T> classTarget, int chance,
				boolean checkSight, boolean onlyNearby, Predicate<? super T> targetSelector) {
			super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
		}
		
		@Override
		protected AxisAlignedBB getTargetableArea(double targetDistance) {
	        return this.taskOwner.getEntityBoundingBox().grow(targetDistance);
	    }
		
	}
	
}
