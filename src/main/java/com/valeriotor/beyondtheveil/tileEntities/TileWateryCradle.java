package com.valeriotor.beyondtheveil.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.blocks.BlockWateryCradle;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.entities.EntityWeeper;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.util.ItemHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileWateryCradle extends TileEntity{
	
	private PatientTypes patient = PatientTypes.NONE;
	private boolean spineless = false;
	private boolean heartless = false;
	private int villagerProfession = 0;
	private EntityLiving dummyEntity = null;
	
	public TileWateryCradle() {
		
	}
	
	public void setPatient(PatientStatus status) {
		if(status == null) return;
		if(status.getPatientType() == PatientTypes.NONE) {
			this.patient = PatientTypes.NONE;
			this.spineless = false;
			this.heartless = false;
			this.villagerProfession = 0;
		} else {
			this.patient = status.getPatientType();
			this.spineless = status.isSpineless();
			this.heartless = status.isHeartless();
			this.villagerProfession = status.villagerProfession;
		}
		this.markDirty();
		this.sendUpdates(this.world);
	}
	
	public PatientTypes getPatientType() {
		return this.patient;
	}
	
	public boolean isPatientSpineless() {
		return this.spineless;
	}
	
	public boolean isPatientHeartless() {
		return this.heartless;
	}
	
	
	public PatientStatus getPatientStatus() {
		return new PatientStatus(this.patient, this.spineless, this.heartless, this.villagerProfession);
	}
	
	public ItemStack getPatientItem() {
		if(this.patient == PatientTypes.NONE) return new ItemStack(Items.AIR);
		ItemStack stack = null;
		if(this.patient == PatientTypes.VILLAGER) {
			stack = new ItemStack(ItemRegistry.held_villager);
		}else if(this.patient == PatientTypes.WEEPER) {
			stack = new ItemStack(ItemRegistry.held_weeper);
		}
		if(stack == null) return new ItemStack(Items.AIR);
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		nbt.setBoolean("spineless", this.spineless);
		nbt.setBoolean("heartless", this.heartless);
		nbt.setInteger("profession", this.villagerProfession);
		return stack;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("patientType", getIdByType(this.patient));
		compound.setBoolean("spineless", this.spineless);
		compound.setBoolean("heartless", this.heartless);
		compound.setInteger("profession", this.villagerProfession);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.patient = PatientTypes.values()[compound.getInteger("patientType")];
		this.spineless = compound.getBoolean("spineless");
		this.heartless = compound.getBoolean("heartless");
		this.villagerProfession = compound.getInteger("profession");
		this.dummyEntity = null;
		super.readFromNBT(compound);
	}

	private EntityLiving getInternalDummyEntity(PatientTypes type) {
		switch(type) {
		case VILLAGER:	EntityCrawlingVillager vil = new EntityCrawlingVillager(this.world);
						vil.setProfession(this.villagerProfession);
						return vil;
		case WEEPER:  	EntityCrawlingVillager weeper = new EntityCrawlingVillager(this.world);
						weeper.setWeeper(true);
						return weeper;				
		default: return null;
		}
	}
	
	public EntityLiving getDummyEntity() {
		if(this.dummyEntity == null)
			this.dummyEntity = this.getInternalDummyEntity(patient);
		return this.dummyEntity;
	}
	
	private int getIdByType(PatientTypes type) {
		for(int i = 0; i < PatientTypes.values().length; i++) {
			if(PatientTypes.values()[i] == type) return i;
		}
		return -1;
	}
	
	
	public enum PatientTypes {
		VILLAGER,
		WEEPER,
		NONE;
	}
	
	public static class PatientStatus {
		private final PatientTypes patient;
		private final boolean spineless;
		private final boolean heartless;
		private final int villagerProfession;
		
		private PatientStatus(PatientTypes type, boolean spineless, boolean heartless, int villagerProfession) {
			this.patient = type;
			this.spineless = spineless;
			this.heartless = heartless;
			this.villagerProfession = villagerProfession;
		}

		public PatientTypes getPatientType() {return this.patient;}
		public boolean isSpineless() {return this.spineless;}
		public boolean isHeartless() {return this.heartless;}
		public int getVillagerProfession() {return this.villagerProfession;}
		
		@Override
		public String toString() {
			String first = this.spineless ? "spineless " : "";
			String second = this.heartless ? "heartless" : "";
			return String.format("Patient type: %s. Traits: %s%s ", this.patient.name(), first, second);
		}

		public static PatientStatus getPatientFromItem(ItemStack stack) {
			if(stack.getItem() == ItemRegistry.held_villager) {
				boolean spineless = ItemHelper.checkBooleanTag(stack, "spineless", false);
				boolean heartless = ItemHelper.checkBooleanTag(stack, "heartless", false);
				int profession = ItemHelper.checkIntTag(stack, "profession", 0);
				return new PatientStatus(PatientTypes.VILLAGER, spineless, heartless, profession);
			}else if(stack.getItem() == ItemRegistry.held_weeper) {
				boolean spineless = ItemHelper.checkBooleanTag(stack, "spineless", false);
				boolean heartless = ItemHelper.checkBooleanTag(stack, "heartless", false);
				return new PatientStatus(PatientTypes.WEEPER, spineless, heartless, 0);
			}
			return null;
		}
		
		public static PatientStatus getNoPatientStatus() {
			return new PatientStatus(PatientTypes.NONE, false, false, 0);
		}
		
		public PatientStatus withSpineless(boolean spineless) {return new PatientStatus(this.patient, spineless, this.heartless, this.villagerProfession);}
		public PatientStatus withHeartless(boolean heartless) {return new PatientStatus(this.patient, this.spineless, heartless, this.villagerProfession);}
		public PatientStatus withPatient(PatientTypes type) {return new PatientStatus(type, this.spineless, heartless, this.villagerProfession);}
		
		public EntityLiving getEntity(World w) {
			return getEntity(w, this);
		}
		
		public static EntityLiving getEntityFromStack(World w, ItemStack stack) {
			return getEntity(w, getPatientFromItem(stack));
		}
		
		public static EntityLiving getEntity(World w, PatientStatus ps) {
			EntityLiving ent = null;
			if(ps.patient == PatientTypes.VILLAGER) {
				ent = new EntityCrawlingVillager(w, !ps.spineless, ps.heartless);
				((EntityCrawlingVillager)ent).setProfession(ps.villagerProfession);
			} else if(ps.patient == PatientTypes.WEEPER) {
				ent = new EntityWeeper(w, ps.spineless);
				((EntityWeeper)ent).setHeartless(ps.heartless);
			}
			return ent;
		}
		
	}
	
	
	// These next few methods are standard tile entity syncing, been using them for a long long time
	
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
		PatientTypes type = PatientTypes.values()[pkt.getNbtCompound().getInteger("patientType")];
	}
	
	private void sendUpdates(World worldObj) {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
	}
	
	public IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private AxisAlignedBB BBOX;
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(BBOX == null) {
			BBOX = new AxisAlignedBB(pos.add(-2, 0, -2), pos.add(2, 0, 2));
		}
		return BBOX;
	}
	
}
