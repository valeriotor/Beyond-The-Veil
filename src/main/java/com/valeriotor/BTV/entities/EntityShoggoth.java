package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.animations.AnimationRegistry;
import com.valeriotor.BTV.entities.AI.AIShoggothBuild;
import com.valeriotor.BTV.shoggoth.ShoggothBuilding;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShoggoth extends EntityLiving{
	
	private int animTicks = 0;
	private Animation openMouthAnim = null;
	private Animation eyeTentacleAnim = null;
	public NBTTagCompound map = null;
	public int progress = -1;
	public ShoggothBuilding building;
	
	public EntityShoggoth(World worldIn) {
		super(worldIn);
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	/*@Override
	public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }*/
	
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes(); 

	    // standard attributes registered to EntityLivingBase
	   getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
	   getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	   getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
	   getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
	   
	
	    // need to register any additional attributes
	   getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	   getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
	}
	
	 protected void initEntityAI() {
        //this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(1, new AISwim(this));
		 this.tasks.addTask(0, new AIShoggothBuild(this));
		 this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		 this.tasks.addTask(8, new EntityAILookIdle(this));
	 }
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(world.isRemote) {
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
		if(stack.getTagCompound().hasKey("schematic")) {
			this.map = ItemHelper.checkTagCompound(stack).getCompoundTag("schematic");
			return EnumActionResult.SUCCESS;
		}
		return super.applyPlayerInteraction(player, vec, hand);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(map != null) {
			if(building != null) {
				map.setTag(String.format("b%d", progress), building.writeToNBT(new NBTTagCompound()));
			}
			compound.setTag("map", map);
		}
		compound.setInteger("progress", progress);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("map")) map = (NBTTagCompound) compound.getTag("map");
		this.progress = compound.getInteger("progress");
		super.readFromNBT(compound);
	}
	
}
