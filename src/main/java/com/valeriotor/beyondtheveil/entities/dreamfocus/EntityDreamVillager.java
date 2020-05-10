package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDreamVillager extends EntityLiving implements IDreamEntity{
	private int pointCounter = 0;
	private BlockPos focus;
	private NBTTagCompound villagerData;
	private boolean loadEntity = false;
	private boolean turnBack = false;
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.<Integer>createKey(EntityDreamVillager.class, DataSerializers.VARINT);
	
	public EntityDreamVillager(World worldIn) {
		super(worldIn);
	}
	
	public EntityDreamVillager(World worldIn, BlockPos focus) {
		super(worldIn);
		this.focus = focus;
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public Point3d getNextPoint(List<Point3d> ps) {
		if(this.pointCounter < ps.size()) {
			Point3d p = ps.get(pointCounter);
			this.pointCounter++;
			return p;
		}
		this.turnBack = true;
		return null;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PROFESSION, 0);
	}
	
	public void setData(NBTTagCompound vilData) {
		this.villagerData = vilData;
	}
	
	public void setProfession(int id) {
		this.dataManager.set(PROFESSION, Integer.valueOf(id));
	}
	
	public net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession getProfession(){
		return net.minecraftforge.fml.common.registry.VillagerRegistry.getById(this.dataManager.get(PROFESSION));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if(compound.hasKey("villagerData")) this.villagerData = compound.getCompoundTag("villagerData");
		if(compound.hasKey("professionid")) this.setProfession(compound.getInteger("professionid"));
		this.pointCounter = compound.getInteger("pointCounter");
		if(compound.hasKey("focusPos")) {
			this.focus = BlockPos.fromLong(compound.getLong("focusPos"));
			this.loadEntity = true;
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if(this.villagerData != null) compound.setTag("villagerData", this.villagerData);
		compound.setInteger("professionid", this.dataManager.get(PROFESSION));
		if(this.focus != null) compound.setLong("focusPos", focus.toLong());
		compound.setInteger("pointCounter", this.pointCounter);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(this.world.isRemote) return;
		if(loadEntity) {
			TileEntity te = this.world.getTileEntity(focus);
			if(!(te instanceof TileDreamFocus)) {
				this.turnBack = true;
			}
			loadEntity = false;
		}
		

		if(this.focus != null) {
			TileEntity te = this.world.getTileEntity(focus);
			if(te instanceof TileDreamFocus) {
				List<Point3d> ps = ((TileDreamFocus)te).getPoints();
				this.moveToNextPoint(ps);
			} else this.turnBack = true;
		}
		if(turnBack) {
			EntityVillager vil = new EntityVillager(this.world);
			vil.setPosition(posX, posY, posZ);
			if(villagerData != null) vil.readEntityFromNBT(villagerData);
			this.world.spawnEntity(vil);
			this.world.removeEntity(this);
		}
	}
	
	public void knockout() {
		EntityCrawlingVillager worm = new EntityCrawlingVillager(this.world, this.focus, this.pointCounter);
		worm.setPosition(this.posX, this.posY, this.posZ);
		worm.setProfession(this.dataManager.get(PROFESSION));
		this.world.spawnEntity(worm);
		this.world.removeEntity(this);
	}
	

}
