package com.valeriotor.BTV.tileEntities;

import java.util.List;

import javax.annotation.Nullable;

import com.valeriotor.BTV.entities.IWeepingEntity;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.fluids.TearTank;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileLacrymatory extends TileFluidHandler{
	
	private IWeepingEntity weeper;
	private int amount = 0;
	
	public TileLacrymatory() {
		this.tank = new TearTank(4000, this);
	}
	
	public boolean setWeeper(IWeepingEntity weeper) {
		if(weeper == null) {
			this.weeper = null;
			return true;
		}
		if(this.weeper == null) {
			this.weeper = weeper;
			return true;
		}	
		return false;
		
	}
	
	public IWeepingEntity getWeeper() {
		return this.weeper;
	}
	
	public static void fillWithTears(EntityLiving e) {
		if(!(e instanceof IWeepingEntity)) return;
		IWeepingEntity e1 = (IWeepingEntity) e;
		BlockPos lacrymatory = e1.getLacrymatory();
		if(lacrymatory != null) {
			TileEntity te = e.world.getTileEntity(lacrymatory);
			if(!(te instanceof TileLacrymatory)) e1.setLacrymatory(null);
			else {
				TileLacrymatory tl = (TileLacrymatory) te;
				double dist = lacrymatory.distanceSq(e.getPosition());
				if(tl.getWeeper() == null && dist < 16) tl.setWeeper(e1);
				else if(tl.getWeeper() == e1 && dist < 16) {
					List<EntityLiving> ents = e.world.getEntities(EntityLiving.class, ent -> ent.getDistanceSq(e.getPosition()) < 9 && ent != e && ent instanceof IWeepingEntity);
					if(ents.isEmpty()) tl.tank.fill(new FluidStack(ModFluids.tears, 50), true);
				}else {
					e1.setLacrymatory(null);
					tl.setWeeper(null);
				}
			}
		}
	}
	
	@Nullable
	public IFluidHandler getFluidHandler() {
		return this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setInteger("amount", tank.getFluidAmount());
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if(tag.hasKey("amount")) this.amount = tag.getInteger("amount");
		super.readFromNBT(tag);
	}
	
	public void sendUpdates() {
		markDirty();
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
		//this.amount = pkt.getNbtCompound().getInteger("amount");
	}
	
	/** Used for rendering
	 * 
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public int getAmount() {
		return this.amount;
	}

}
