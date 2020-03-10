package com.valeriotor.beyondtheveil.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.items.ItemRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileBarrel extends TileEntity{
	
	public static final int MAX_COUNT = 256;
	private int fishCount;
	private FishType fishType;
	
	public TileBarrel() {
		this.fishCount = 0;
		this.fishType = FishType.NONE;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.fishType = FishType.values()[compound.getInteger("type")];
		this.fishCount = compound.getInteger("count");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("type", this.fishType.ordinal());
		compound.setInteger("count", this.fishCount);
		return compound;
	}
	
	
	/** Sets the type of fish in the barrel.
	 * @param Type: 0 for cod, 1 for slug
	 * 
	 */
	public void setFishType(FishType type) {
		this.fishType = type;
		markDirty();
		
	}
	
	public void setFishCount(int newCount) {
		this.fishCount = newCount;
		markDirty();
		sendUpdates(world);
	}
	
	public ItemStack decreaseFishCount() {
		if(this.fishCount > 0) {
			this.setFishCount(this.fishCount - 1);
			ItemStack stack = this.fishType.getItemStack();
			if(this.fishCount == 0)
				this.setFishType(FishType.NONE);
			return stack;
		}
		return new ItemStack(Items.AIR);
	}
	
	public void increaseFishCount(ItemStack stack) {
		FishType t = getTypeFromStack(stack);
		if(t == null) return;
		if(t == this.fishType || this.fishType == FishType.NONE) {
			this.fishType = t;
			int transferred = Math.min(MAX_COUNT - this.fishCount, stack.getCount());
			this.setFishCount(fishCount + transferred);
			stack.shrink(transferred);
		}
	}
	
	public boolean canIncrease(ItemStack stack) {
		return getTypeFromStack(stack) != null && Math.min(MAX_COUNT - this.fishCount, stack.getCount()) > 0;
	}
	
	public FishType getTypeFromStack(ItemStack stack) {
		for(FishType f : FishType.values()) {
			if(stack.getItem() == f.i && stack.getMetadata() == f.meta) {
				return f;
			}
		}
		return null;
	}
	
	public ItemStack getContents() {
		ItemStack stack = this.fishType.getItemStack();
		stack.setCount(this.fishCount);
		this.fishType = FishType.NONE;
		this.setFishCount(0);
		return stack;
	}
	
	public int getFishCount() {
		return this.fishCount;
	}
	
	public FishType getFishType() {
		return this.fishType;
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
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	public static enum FishType {
		NONE(Items.AIR, 0),
		COD(Items.FISH, 0), 
		SALMON(Items.FISH, 1),
		CLOWNFISH(Items.FISH, 2),
		PUFFERFISH(Items.FISH, 3), 
		SLUGS(ItemRegistry.slug, 0);
		
		private Item i;
		private int meta;
		private FishType(Item i, int meta) {
			this.i = i;
			this.meta = meta;
		}
		
		public ItemStack getItemStack() {
			return new ItemStack(i, 1, meta);
		}
	}

}
