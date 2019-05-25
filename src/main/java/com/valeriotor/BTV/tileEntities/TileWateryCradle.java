package com.valeriotor.BTV.tileEntities;

import javax.annotation.Nullable;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.entities.render.RenderCrawlingVillager;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileWateryCradle extends TileEntity{
	
	private PatientTypes patient = PatientTypes.NONE;
	private boolean spineless = false;
	private boolean filledBrain = false;
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
			this.filledBrain = false;
			this.heartless = false;
			this.villagerProfession = 0;
		} else {
			this.patient = status.getPatientType();
			this.spineless = status.isSpineless();
			this.filledBrain = status.hasFilledBrain();
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
	
	public boolean doesPatientHaveFilledBrain() {
		return this.filledBrain;
	}
	
	public boolean isPatientHeartless() {
		return this.heartless;
	}
	
	
	public PatientStatus getPatientStatus() {
		return new PatientStatus(this.patient, this.spineless, this.filledBrain, this.heartless, this.villagerProfession);
	}
	
	public ItemStack getPatientItem() {
		if(this.patient == PatientTypes.VILLAGER) {
			if(!this.filledBrain) {
				ItemStack stack = new ItemStack(ItemRegistry.held_villager);
				NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
				nbt.setBoolean("spineless", this.spineless);
				nbt.setBoolean("heartless", this.heartless);
				nbt.setInteger("profession", this.villagerProfession);
				return stack;
			}
		}
		return new ItemStack(Items.AIR);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("patientType", getIdByType(this.patient));
		compound.setBoolean("spineless", this.spineless);
		compound.setBoolean("filledBrain", this.filledBrain);
		compound.setBoolean("heartless", this.heartless);
		compound.setInteger("profession", this.villagerProfession);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.patient = PatientTypes.values()[compound.getInteger("patientType")];
		this.spineless = compound.getBoolean("spineless");
		this.filledBrain = compound.getBoolean("filledBrain");
		this.heartless = compound.getBoolean("heartless");
		this.villagerProfession = compound.getInteger("profession");
		this.dummyEntity = null;
		super.readFromNBT(compound);
	}

	private EntityLiving getInternalDummyEntity(PatientTypes type) {
		switch(type) {
		case VILLAGER: EntityCrawlingVillager vil = new EntityCrawlingVillager(this.world);
						vil.setProfession(this.villagerProfession);
						return vil;
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
		ZOMBIE,
		NONE;
	}
	
	public static class PatientStatus {
		private final PatientTypes patient;
		private final boolean spineless;
		private final boolean filledBrain;
		private final boolean heartless;
		private final int villagerProfession;
		
		private PatientStatus(PatientTypes type, boolean spineless, boolean filledBrain, boolean heartless, int villagerProfession) {
			this.patient = type;
			this.spineless = spineless;
			this.filledBrain = filledBrain;
			this.heartless = heartless;
			this.villagerProfession = villagerProfession;
		}

		public PatientTypes getPatientType() {return this.patient;}
		public boolean isSpineless() {return this.spineless;}
		public boolean hasFilledBrain() {return this.filledBrain;}
		public boolean isHeartless() {return this.heartless;}
		public int getVillagerProfession() {return this.villagerProfession;}
		
		@Override
		public String toString() {
			String first = this.spineless ? "spineless " : "";
			String second = this.filledBrain ? "filled brain " : "";
			String third = this.heartless ? "heartless" : "";
			return String.format("Patient type: %s. Traits: %s%s%s ", this.patient.name(), first, second, third);
		}

		public static PatientStatus getPatientFromItem(ItemStack stack) {
			if(stack.getItem() == ItemRegistry.held_villager) {
				boolean spineless = ItemHelper.checkBooleanTag(stack, "spineless", false);
				boolean filledBrain = false; // Filled brain should be a different held_villager item
				boolean heartless = ItemHelper.checkBooleanTag(stack, "heartless", false);
				int profession = ItemHelper.checkIntTag(stack, "profession", 0);
				return new PatientStatus(PatientTypes.VILLAGER, spineless, filledBrain, heartless, profession);
			}
			return null;
		}
		
		public static PatientStatus getNoPatientStatus() {
			return new PatientStatus(PatientTypes.NONE, false, false, false, 0);
		}
		
		public PatientStatus withSpineless(boolean spineless) {return new PatientStatus(this.patient, spineless, this.filledBrain, this.heartless, this.villagerProfession);}
		public PatientStatus withFilledBrain(boolean filledBrain) {return new PatientStatus(this.patient, this.spineless, filledBrain, this.heartless, this.villagerProfession);}
		public PatientStatus withHeartless(boolean heartless) {return new PatientStatus(this.patient, this.spineless, this.filledBrain, heartless, this.villagerProfession);}
		
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
	
}
