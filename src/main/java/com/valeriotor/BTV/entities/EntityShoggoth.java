package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.AI.AIRevenge;
import com.valeriotor.BTV.entities.AI.AIShoggothBuild;
import com.valeriotor.BTV.entities.AI.AISpook;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.shoggoth.ShoggothBuilding;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShoggoth extends EntityMob implements ISpooker, IPlayerMinion{
	
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityShoggoth.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> SPOOKING = EntityDataManager.<Boolean>createKey(EntityShoggoth.class, DataSerializers.BOOLEAN);
	
	private int animTicks = 0;
	private int aggroTicks = 100;
	private int counter = 500;
	private Animation openMouthAnim = null;
	private Animation eyeTentacleAnim = null;
	public NBTTagCompound map = null;
	public int progress = -1;
	public ShoggothBuilding building;
	private int talkCount = 0;
	private UUID master;
	private int aggressivity = 0;
	private int spookCooldown = 300;
	
	public EntityShoggoth(World worldIn) {
		this(worldIn, null);
	}
	
	public EntityShoggoth(World worldIn, EntityPlayer master) {
		super(worldIn);
		this.setSize(2.5F, 2.5F);
		if(master != null) this.master = master.getPersistentID();
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return BTVSounds.shoggoth_hurt;
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}
	
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes(); 

	   getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
	   getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	   getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
	   getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	   
	
	   //getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	   getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
		this.dataManager.register(SPOOKING, false);
	}
	
	 protected void initEntityAI() {
		//this.tasks.addTask(0, new EntityAISwimming(this));
		//this.tasks.addTask(1, new AISwim(this));
	    this.tasks.addTask(1, new AISpook(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 2, true));
		this.tasks.addTask(3, new AIShoggothBuild(this));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new AIRevenge(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, false, p -> (this.master == null || this.world.getPlayerEntityByUUID(this.master) != p || this.aggressivity > 50)));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true));
		 
	 }
	
	 
	 public void onUpdate() {
	        super.onUpdate();
	        if(!this.world.isRemote) {
	        	this.counter--;
	        	if((this.counter & 31) == 0 && !this.dead)
	        		this.heal(1);
	        	if(this.counter < 0) {
	        		this.counter = 500;
	        		if(this.aggressivity > 0 && this.getAttackTarget() == null)
	        			this.aggressivity--;
	        	}
	            this.setBesideClimbableBlock(this.collidedHorizontally);
	            if(this.spookCooldown > 0)
	            	this.spookCooldown--;
	            if(this.getAttackTarget() instanceof EntityPlayer) {
	            	this.aggroTicks--;
	            	if(this.aggroTicks <= 0) {
	            		this.aggroTicks = 100;
	            		this.aggressivity += 3;
	            	}
	            }
	        } else {
	        	if(this.getDataManager().get(SPOOKING)) {
	        		this.world.getPlayers(EntityPlayer.class, p -> p.getDistanceSq(this) < 625).forEach(p -> {
						if(!p.capabilities.isCreativeMode && (p.getPersistentID() != this.master || this.aggressivity > 50))
							p.capabilities.isFlying = false;
					});
	        		if(this.openMouthAnim == null) {
	        			this.openMouthAnim = new Animation(AnimationRegistry.shoggoth_open_mouth);
	        		}
	        	}
				if(openMouthAnim != null) {
					openMouthAnim.update();
					if(openMouthAnim.isDone()) openMouthAnim = null;
				} else if(eyeTentacleAnim != null) {
					eyeTentacleAnim.update();
					if(eyeTentacleAnim.isDone()) eyeTentacleAnim = null;
				}
				animTicks++;
				animTicks%=1200;
				if(animTicks%400 == 0 && eyeTentacleAnim == null) this.eyeTentacleAnim = new Animation(AnimationRegistry.shoggoth_eye_tentacle);
			}
	 }
	 
	 @Override
	public boolean attackEntityAsMob(Entity e) {
		 boolean bool = super.attackEntityAsMob(e);
		 if(e instanceof EntityPlayer) {
			 EntityPlayer p = (EntityPlayer)e;
			 if(!p.isDead)
				 p.setHealth(p.getHealth()-1);
		 }
		 return bool;
	}
	 
	@SideOnly(Side.CLIENT)
	public int getAnimTicks() {
		return animTicks;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getOpenMouthAnim() {
		return this.openMouthAnim;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getEyeTentacleAnim() {
		return this.eyeTentacleAnim;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!player.world.isRemote && ItemHelper.checkTagCompound(stack).hasKey("schematic")) {
			if(this.map != null) {
				player.sendMessage(new TextComponentTranslation(String.format("shoggoth.hasmapalready%d", this.talkCount++)));
				if(this.talkCount > 3) this.talkCount = 3;
				return EnumActionResult.FAIL;
			}
			this.map = ItemHelper.checkTagCompound(stack).getCompoundTag("schematic").copy();
			this.progress = 0;
			this.building = null;
			return EnumActionResult.SUCCESS;
		}
		return super.applyPlayerInteraction(player, vec, hand);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		 if(this.master != null)
			 compound.setString("masterID", this.master.toString());
		if(map != null) {
			if(building != null) {
				map.setTag(String.format("b%d", progress), building.writeToNBT(new NBTTagCompound()));
			}
			compound.setTag("map", map);
		}
		compound.setInteger("progress", progress);
		compound.setInteger("aggressivity", aggressivity);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("map")) map = (NBTTagCompound) compound.getTag("map");
		this.progress = compound.getInteger("progress");
		if(compound.hasKey("masterID"))
			this.master = UUID.fromString(compound.getString("masterID"));
		this.aggressivity = compound.getInteger("aggressivity");
		super.readFromNBT(compound);
	}
	
	public boolean isBesideClimbableBlock() {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }
	
	public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }
	

	@Override
	public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
		if(this.isInWater()) return new PathNavigateSwimmer(this, worldIn);
		return new PathNavigateClimber(this, worldIn);
	}
	
	@Override
	public void onKillEntity(EntityLivingBase e) {
		if(e.isNonBoss()) {
			if(!(e instanceof EntityPlayer)) {
				this.aggressivity++;
			} else {
				this.aggressivity += 10;
			}
		}
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public void setSpooking(boolean spook) {
		this.dataManager.set(SPOOKING, spook);
		this.spookCooldown = 300;
	}

	@Override
	public SoundEvent getSound() {
		return BTVSounds.shoggoth_screech;
	}

	@Override
	public void spookSelf() {
		this.motionX = 0;
		this.motionZ = 0;
		if(this.getAttackTarget() != null)
			this.faceEntity(this.getAttackTarget(), 360, 360);
	}

	@Override
	public int getSpookCooldown() {
		return this.spookCooldown;
	}

	@Override
	public EntityPlayer getMaster() {
		return this.world.getMinecraftServer().getPlayerList().getPlayerByUUID(this.master);
	}

	@Override
	public UUID getMasterID() {
		return this.master;
	}

	@Override
	public void setMaster(EntityPlayer p) {		
	}
	
}
