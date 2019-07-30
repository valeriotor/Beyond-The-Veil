package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFletum extends EntityCreature{

	private int animationTicks = 0;
	private int tearTicks;
	
	public EntityFletum(World worldIn) {
		super(worldIn);
		this.tearTicks = worldIn.rand.nextInt(20*60*7)+20*60*4;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			this.animationTicks++;
			this.animationTicks%=200;
		}else {
			this.tearTicks--;
			if(this.tearTicks == 0) {
				this.tearTicks = world.rand.nextInt(20*60*7)+20*60*4;
				EntityItem item = new EntityItem(this.world, posX, posY, posZ, new ItemStack(ItemRegistry.tears));
				this.world.spawnEntity(item);
			}
		}
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(player.getHeldItem(hand).getItem() == Items.AIR) {
			if(!this.world.isRemote) {
				ItemStack item = new ItemStack(ItemRegistry.held_fletum);
				player.addItemStackToInventory(item);
				this.world.removeEntity(this);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	@SideOnly(Side.CLIENT)
	public float getAnimationTicks() {
		return this.animationTicks;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.tearTicks = compound.getInteger("tearTicks");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("tearTicks", this.tearTicks);
		return super.writeToNBT(compound);
	}
	

}
