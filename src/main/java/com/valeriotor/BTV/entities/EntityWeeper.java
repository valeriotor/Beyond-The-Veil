package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWeeper extends EntityLiving{
	
	private int animationTicks = 0;
	private boolean increase = true;
	private boolean heartless = false;
	
	public EntityWeeper(World worldIn) {
		super(worldIn);
	}
	
	public void setHeartless(boolean heartless) {
		this.heartless = heartless;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			this.animationTicks++;
			this.animationTicks%=200;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public float getAnimationTicks() {
		return this.animationTicks;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("heartless", this.heartless);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.heartless = compound.getBoolean("heartless");
		super.readFromNBT(compound);
	}
	
	public ItemStack getItemForm() {
		ItemStack stack = new ItemStack(ItemRegistry.held_weeper);
		ItemHelper.checkTagCompound(stack).setBoolean("heartless", this.heartless);
		return stack;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(player.getHeldItem(hand).getItem() == ItemRegistry.blackjack) {
			if(!this.world.isRemote) {
				ItemStack item = this.getItemForm();
				ItemHelper.checkTagCompound(item).setBoolean("heartless", this.heartless);
				player.addItemStackToInventory(item);
				this.world.removeEntity(this);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

}
