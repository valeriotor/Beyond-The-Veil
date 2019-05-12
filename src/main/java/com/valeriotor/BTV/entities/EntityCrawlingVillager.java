package com.valeriotor.BTV.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCrawlingVillager extends EntityCreature{
	
	private boolean unconscious = false;
	private int ticksToFall = 0;
	public static final int DEFAULTTICKS = 12;
	private static final DataParameter<Integer> TICKSTOFALL = EntityDataManager.<Integer>createKey(EntityCrawlingVillager.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.<Integer>createKey(EntityVillager.class, DataSerializers.VARINT);
    
	public EntityCrawlingVillager(World w) {
		this(w, false);
	}
	
	
	public EntityCrawlingVillager(World worldIn, boolean blackjack) {
		super(worldIn);
		this.unconscious = blackjack;
		this.ticksToFall = blackjack ? DEFAULTTICKS : 0;
		this.dataManager.set(TICKSTOFALL, this.ticksToFall);
	}
	
	@Override
	public void onAddedToWorld() {
		if(this.world.isRemote) this.ticksToFall = this.dataManager.get(TICKSTOFALL);
		super.onAddedToWorld();
	}
	
	@Override
	protected void entityInit() {
		this.dataManager.register(TICKSTOFALL, Integer.valueOf(0));
		this.dataManager.register(PROFESSION, Integer.valueOf(0));
		super.entityInit();
	}
	
	@Override
	public boolean isAIDisabled() {
		return super.isAIDisabled() || this.unconscious;
	}
	
	@Override
	public void onEntityUpdate() {
		if(this.world.isRemote &&this.ticksToFall > 0) {
			this.ticksToFall--;
		}
		super.onEntityUpdate();
	}
	
	@SideOnly(Side.CLIENT)
	public int getTicksToFall() {
		return this.ticksToFall;
	}
	
	public void setTicksToFall(int ticks) {
		this.ticksToFall = ticks;
		this.dataManager.set(TICKSTOFALL, this.ticksToFall);
	}
	
	public void setProfession(int id) {
		this.dataManager.set(PROFESSION, Integer.valueOf(id));
	}
	
	public net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession getProfession(){
		return net.minecraftforge.fml.common.registry.VillagerRegistry.getById(this.dataManager.get(PROFESSION));
	}
	
	public int getProfessionID() {
		return this.dataManager.get(PROFESSION);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("profession", this.dataManager.get(PROFESSION));
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.setProfession(compound.getInteger("profession"));
		super.readFromNBT(compound);
	}
	
	
	

}
