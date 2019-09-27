package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.AI.AIShoggothBuild;
import com.valeriotor.BTV.shoggoth.ShoggothBuilding;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShoggoth extends EntityMob{
	
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityShoggoth.class, DataSerializers.BYTE);
	
	private int animTicks = 0;
	private int aggroTicks = 100;
	private Animation openMouthAnim = null;
	private Animation eyeTentacleAnim = null;
	public NBTTagCompound map = null;
	public int progress = -1;
	public ShoggothBuilding building;
	private int talkCount = 0;
	private UUID master;
	private int aggressivity = 0;
	
	public EntityShoggoth(World worldIn) {
		this(worldIn, null);
	}
	
	public EntityShoggoth(World worldIn, EntityPlayer master) {
		super(worldIn);
		this.setSize(3, 3);
		if(master != null) this.master = master.getPersistentID();
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes(); 

	   getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
	   getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	   getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
	   getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
	   
	
	   //getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	   getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
	}
	
	 protected void initEntityAI() {
		//this.tasks.addTask(0, new EntityAISwimming(this));
		//this.tasks.addTask(1, new AISwim(this));
		this.tasks.addTask(0, new EntityAIAttackMelee(this, 2, true));
		this.tasks.addTask(2, new AIShoggothBuild(this));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, false, p -> (this.master != null && (this.world.getPlayerEntityByUUID(this.master) != p || this.aggressivity > 50))));
		 
	 }
	
	 
	 public void onUpdate() {
	        super.onUpdate();
	        if(!this.world.isRemote) {
	            this.setBesideClimbableBlock(this.collidedHorizontally);
	            if(this.getAttackTarget() instanceof EntityPlayer) {
	            	this.aggroTicks--;
	            	if(this.aggroTicks <= 0) {
	            		this.aggroTicks = 100;
	            		this.aggressivity += 10;
	            	}
	            }
	        } else {
				if(openMouthAnim != null) {
					openMouthAnim.update();
					if(openMouthAnim.isDone()) openMouthAnim = null;
				}
				if(eyeTentacleAnim != null) {
					eyeTentacleAnim.update();
					if(eyeTentacleAnim.isDone()) eyeTentacleAnim = null;
				}
				animTicks++;
				animTicks%=1000;
				if(animTicks == 999 && openMouthAnim == null) this.openMouthAnim = new Animation(AnimationRegistry.shoggoth_open_mouth);
				if(animTicks%400 == 0 && animTicks != 0 && eyeTentacleAnim == null) this.eyeTentacleAnim = new Animation(AnimationRegistry.shoggoth_eye_tentacle);
			}
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
	
}
