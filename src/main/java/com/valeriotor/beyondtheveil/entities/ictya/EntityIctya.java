package com.valeriotor.beyondtheveil.entities.ictya;

import com.google.common.base.Predicate;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.util.WaterMoveHelper;

import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.generic.GenericMessageKey;
import com.valeriotor.beyondtheveil.network.generic.MessageGenericToClient;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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
		if(entity instanceof EntityPlayer || entity instanceof EntityDeepOne)
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
		this.targetTasks.addTask(1, new AINearestAttackableTargetArche<>(this, EntityDeepOne.class, 10, true, false, this::shouldAttack));
        this.targetTasks.addTask(0, new AIRevenge(this, this::shouldDefend));
        this.tasks.addTask(0, getFleeingAI(this, EntityIctya.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
		this.tasks.addTask(0, getFleeingAI(this, EntityPlayer.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
		this.tasks.addTask(0, getFleeingAI(this, EntityDeepOne.class, this::shouldFlee, 5, getSpeed()*2, getSpeed()*2));
	}
	
	protected <T extends EntityLivingBase> EntityAIAvoidEntity<T> getFleeingAI(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate <? super T > avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
		return new EntityAIAvoidEntity<>(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
	}
	
	protected double getSpeed() {
		return getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
	}
	
	protected boolean shouldAttack(EntityLivingBase attacked) {
		if(getSize() == IctyaSize.TINY) return false;
		int diff = this.compareSizeTo(attacked);
		if(diff >= 2) {
			if(getCurrentOverMaxFood() < 0.85) return true;
		} else if(diff == 1) {
			if(getCurrentOverMaxFood() < 0.67) return true;
		} else if(diff == 0) {
			if(getCurrentOverMaxFood() < 0.5) return true;
		} else if(diff == -1) {
			if(getCurrentOverMaxFood() < 0.1 && attacked.getHealth() / attacked.getMaxHealth() < 0.3F) return true;
		}
		return false;
	}
	
	protected boolean shouldDefend(EntityLivingBase attacker) {
		if(getSize() == IctyaSize.TINY) return false;
		int diff = this.compareSizeTo(attacker);
		if(diff >= -1) return true;
		return false;
	}
	
	protected boolean shouldFlee(EntityLivingBase spooker) {
		if(spooker instanceof EntityAdeline) return false;
		int sizeCompare = this.compareSizeTo(spooker);
		return sizeCompare <= -2 || (this.getSize() == IctyaSize.TINY && sizeCompare <= -1);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
			
		if((ticksExisted & 31) == 0) 
			on32Ticks();
	}
	
	protected void on32Ticks() {
		if(!world.isRemote && getSize() != IctyaSize.TINY) {
			if(getCurrentOverMaxFood() > 0.67)
				heal(Math.max(3, Math.min(getMaxHealth()/20, 12)));
			
			if(currentFood >= getFoodPer32Ticks())
				currentFood -= getFoodPer32Ticks();
			else
				damageEntity(DamageSource.STARVE, 3);
		}
	}
	
	@Override
	public void onKillEntity(EntityLivingBase e) {
		if(e instanceof EntityIctya) {
			currentFood = Math.min(getMaxFood(), currentFood + ((EntityIctya)e).getFoodValue());
			heal((float)((EntityIctya)e).getFoodValue()/5);
		}
		else if(e instanceof EntityPlayer || e instanceof EntityDeepOne)
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
	
	@Override
	public void onDeath(DamageSource cause) {
		if(cause.getTrueSource() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer)cause.getTrueSource();
			p.getFoodStats().addStats((int)getFoodValue()/20, (float)getFoodValue()/20);
			if(getSizeInt() >= IctyaSize.MEDIUM.getSizeInt()) {
				p.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 10*(int)Math.pow(getSizeInt(), 3), 0, false, false));
			}
			String resourcePath = ForgeRegistries.ENTITIES.getKey(EntityRegistry.getEntry(getClass())).getResourcePath();
			String string = PlayerDataLib.ICTYA_BY_TYPE.apply(resourcePath);
			IPlayerData data = PlayerDataLib.getCap(p);
			if(!data.getString(string)) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageGenericToClient(GenericMessageKey.ICTYARY_ENTRY, resourcePath), (EntityPlayerMP) p);
				SyncUtil.addStringDataOnServer(p, false, string);
				SyncUtil.incrementIntDataOnServer(p, false, PlayerDataLib.ICTYA_BY_SIZE.apply(getSize().name().toLowerCase()), 1, 1);
			}
		}
		super.onDeath(cause);
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

	protected static class AIAttackMeleeAgainstLargeCreatures extends EntityAIAttackMelee {

		public AIAttackMeleeAgainstLargeCreatures(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}

		@Override
		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			double attackReachSqr = super.getAttackReachSqr(attackTarget);
			if(attackTarget instanceof EntityIctya && ((EntityIctya)attackTarget).getSizeInt() > IctyaSize.MEDIUM.ordinal()) {
				attackReachSqr += attackTarget.width*0.6F;
			}
			return attackReachSqr;
		}
	}
	
}
