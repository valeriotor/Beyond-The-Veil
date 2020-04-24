package com.valeriotor.beyondtheveil.tileEntities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.vecmath.Point3d;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.blocks.BlockDreamFocus;
import com.valeriotor.beyondtheveil.blocks.BlockDreamFocusFluids;
import com.valeriotor.beyondtheveil.blocks.BlockDreamFocusVillagers;
import com.valeriotor.beyondtheveil.blocks.ModBlockFacing;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamFluid;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamItem;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;
import com.valeriotor.beyondtheveil.entities.dreamfocus.IDreamEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileDreamFocus extends TileEntity implements ITickable{

	private List<Point3d> points = new ArrayList<>();
	private int counter = 0;
	private Set<Integer> ents = new HashSet<>();
	private UUID usingPlayer;
	private int playerCounter = 0;
	private boolean fletum = false;
	private FocusType type = FocusType.ITEM;
	
	public TileDreamFocus() {}
	public TileDreamFocus(FocusType type) {
		this.type = type;
	}
	
	@Override
	public void update() {
		if(this.world.isRemote) {
			BeyondTheVeil.proxy.renderEvents.renderDreamFocusPath(points, world);
		} else {
			this.counter++;
			if(this.counter > 150) {
				if(this.usingPlayer != null && this.world.getPlayerEntityByUUID(usingPlayer) == null) this.usingPlayer = null;
				if(this.type == FocusType.ITEM) {
					if(BlockDreamFocus.hasFletum(world, pos)) {
						EnumFacing facing = this.getState().getValue(ModBlockFacing.FACING).getOpposite();
						TileEntity te = world.getTileEntity(pos.offset(facing));
						if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
							IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
							for(int i = 0; i < cap.getSlots(); i++) {
								ItemStack stack = cap.extractItem(i, 64, false);
								if(!stack.isEmpty()) {
									BlockPos front = this.pos.offset(facing.getOpposite());
									EntityDreamItem edi =new EntityDreamItem(this.world, front.getX()+0.5, front.getY(), front.getZ()+0.5, stack, this.pos);
									this.ents.add(edi.getEntityId());
									this.world.spawnEntity(edi);
									break;
								}
							}
						}
					}
				} else if(this.type == FocusType.FLUID) {
					int fleti = BlockDreamFocusFluids.hasFleti(world, pos);
					if(fleti > 0) {
						TileEntity te = world.getTileEntity(pos.offset(EnumFacing.UP));
						if(te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
							IFluidHandler f = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);
							//FluidStack stack = f.drain(new FluidStac, doDrain)
							Fluid fluid = null;
							for(IFluidTankProperties ftp : f.getTankProperties()) {
								FluidStack test = ftp.getContents();
								if(test != null && test.amount > 0) {
									fluid = test.getFluid();
									break;
								}
							}
							if(fluid != null) {
								int a = 0;
								for(int i = 1; i < fleti+1; i++) a+=i;
								FluidStack toDrain = f.drain(new FluidStack(fluid, a*100), true);
								if(toDrain.amount > 0) {
									EntityDreamFluid edf = new EntityDreamFluid(this.world, toDrain, this.pos);
									edf.setPosition(this.pos.getX()+0.5, this.pos.getY()-1, this.pos.getZ()+0.5);
									this.ents.add(edf.getEntityId());
									this.world.spawnEntity(edf);
								}
							}
						}
						
					}
				} else if(this.type == FocusType.VILLAGER) {
					int redstone = 0;
					for(EnumFacing facing : EnumFacing.VALUES) {
						int a = world.getRedstonePower(pos, facing);
						if(a > redstone) redstone = a;
					}
					if(redstone < 15 && BlockDreamFocusVillagers.hasFletum(world, pos)) {
						IDreamEntity toMove = null;
						AxisAlignedBB bbox = new AxisAlignedBB(pos.add(-5, 0, -5), pos.add(6, 5, 6));
						List<EntityCrawlingVillager> worms = world.getEntitiesWithinAABB(EntityCrawlingVillager.class, bbox);
						for(EntityCrawlingVillager worm : worms) {
							if(worm.getFocus() == null) {
								worm.setFocus(this.pos);
								toMove = worm;
								break;
							}
						}
						if(toMove == null) {
							List<EntityVillager> ents = world.getEntitiesWithinAABB(EntityVillager.class, bbox, e -> !e.isChild());
							if(ents.size() > redstone) {
								EntityDreamVillager vil = new EntityDreamVillager(world, this.pos);
								EntityVillager v = ents.get(0);
								NBTTagCompound nbt = new NBTTagCompound();
								v.writeEntityToNBT(nbt);
								vil.setData(nbt);
								vil.setPosition(v.posX, v.posY, v.posZ);
								vil.setProfession(v.getProfession());
								toMove = vil;
								world.spawnEntity(vil);
								world.removeEntity(v);
							}
						}
						if(toMove instanceof Entity) {
							this.ents.add(((Entity)toMove).getEntityId());
						}
					}
				}
				this.counter = 0;
			}
			Iterator<Integer> iter = ents.iterator();
			//System.out.println("Size: " + ents.size());
			while(iter.hasNext()) {
				int id = iter.next();
				Entity e = world.getEntityByID(id);
				if(e instanceof IDreamEntity && e.getDistanceSq(this.getPos()) < 1000) {
					if(!((IDreamEntity)e).moveToNextPoint(points)) iter.remove();
				} else iter.remove();
			}
			
		}
	}
	
	public void clearList() {
		this.points.clear();
		if(this.world != null && !this.world.isRemote) {
			markDirty();
			this.sendUpdates(world);
		}
	}
	
	public void addPoint(double x, double y, double z) {
		this.points.add(new Point3d(x, y, z));
	}
	
	public void finish() {
		markDirty();
		this.usingPlayer = null;
		this.sendUpdates(world);
	}
	
	public boolean setPlayer(EntityPlayer p) {
		if(this.usingPlayer == null) {
			this.usingPlayer = p.getPersistentID();
			return true;
		}
		return false;
	}
	
	public void addDreamEntity(IDreamEntity e) {
		this.ents.add(((Entity)e).getEntityId());
	}
	
	public void removeDreamEntity(int id) {
		this.ents.remove(id);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(int i = 0; i < this.points.size(); i++) {
			Point3d p = this.points.get(i);
			compound.setDouble(String.format("pointx%d", i), p.x);
			compound.setDouble(String.format("pointy%d", i), p.y);
			compound.setDouble(String.format("pointz%d", i), p.z);
		}
		compound.setInteger("type", this.type.ordinal());
		compound.setInteger("counter", this.counter);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.clearList();
		int i = 0;
		while(compound.hasKey(String.format("pointx%d", i))) {
			double x = compound.getDouble(String.format("pointx%d", i));
			double y = compound.getDouble(String.format("pointy%d", i));
			double z = compound.getDouble(String.format("pointz%d", i));
			this.addPoint(x, y, z);
			i++;
		}
		this.type = FocusType.values()[compound.getInteger("type")];
		this.counter = compound.getInteger("counter");
		super.readFromNBT(compound);
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
	
	public enum FocusType {
		ITEM, FLUID, VILLAGER;
	}

}
