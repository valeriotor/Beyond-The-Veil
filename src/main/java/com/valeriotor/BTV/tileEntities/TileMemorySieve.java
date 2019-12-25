package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.tileEntities.TileWateryCradle.PatientTypes;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileMemorySieve extends TileEntity implements ITickable{
	
	private ItemStack heldItem = new ItemStack(Items.AIR);
	private EntityItem itemEntity;
	
	public TileMemorySieve() {
	}
	
	public void addItem(ItemStack stack) {
		if(this.heldItem.getItem() == Items.AIR && stack.getItem() != ItemRegistry.memory_phial) {
			this.heldItem = stack.copy();
			stack.shrink(1);
			this.heldItem.setCount(1);
			itemEntity = new EntityItem(this.world, pos.getX(), pos.getY(), pos.getZ(), heldItem);
			if(!this.world.isRemote) {
				markDirty();
				this.sendUpdates(world);
			}
		}
	}
	
	public ItemStack getItem(ItemStack in) {
		if(in.getItem() != ItemRegistry.memory_phial) {
			ItemStack stack = this.heldItem;
			this.heldItem = new ItemStack(Items.AIR);
			itemEntity = null;
			if(!this.world.isRemote) {
				markDirty();
				this.sendUpdates(world);
			}
			return stack;
		}
		if(this.heldItem.getItem() != Items.AIR) {
			for(Memory m : Memory.values()) {
				if(heldItem.getItem() == m.getItem()) {
					ItemHelper.checkTagCompound(in).setString("memory", m.getDataName());
					this.heldItem = new ItemStack(Items.AIR);
					itemEntity = null;
					if(!this.world.isRemote) {
						markDirty();
						this.sendUpdates(world);
					}
					return new ItemStack(Items.AIR);
				}
			}
		}			
		return new ItemStack(Items.AIR);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("held_item")) {
			this.heldItem = new ItemStack(compound.getCompoundTag("held_item"));
			itemEntity = new EntityItem(this.world, pos.getX(), pos.getY(), pos.getZ(), heldItem);
		} else
			itemEntity = null;
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if(this.heldItem.getItem() != Items.AIR){
			NBTTagCompound nbt = new NBTTagCompound();
			this.heldItem.writeToNBT(nbt);
			compound.setTag("held_item", nbt);
		}
		return super.writeToNBT(compound);
	}
	
	@SideOnly(Side.CLIENT)
	public EntityItem getItemEntity() {
		return this.itemEntity;
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	public IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Override
	public void update() {
		if(world.isRemote)
			if(itemEntity != null)
				itemEntity.onUpdate();
	}
	
}
