package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDreamItem extends EntityItem implements IDreamEntity{
	private int pointCounter = 0;
	private BlockPos focus = null;
	private boolean loadEntity = false;
	public EntityDreamItem(World w) {
		super(w);
	}
	public EntityDreamItem(World worldIn, double x, double y, double z, ItemStack stack, BlockPos focusPos) {
		super(worldIn, x, y, z, stack);
		this.focus = focusPos;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(loadEntity) {
			TileEntity te = this.world.getTileEntity(focus);
			if(te instanceof TileDreamFocus) {
				TileDreamFocus tdf = (TileDreamFocus)te;
				tdf.addDreamEntity(this);
			}
		}
	}
	
	@Override
	public Point3d getNextPoint(List<Point3d> ps) {
		if(this.pointCounter < ps.size()) {
			Point3d p = ps.get(pointCounter);
			this.pointCounter++;
			if(this.getItem().getItem() instanceof ItemSword) {
				this.world.getEntities(EntityLiving.class, e -> e.getDistanceSq(this) < 0.5)
				.forEach(e -> e.attackEntityFrom(DamageSource.GENERIC, ((ItemSword)this.getItem().getItem()).getAttackDamage()));
			}
			return p;
		}
		EntityItem e = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItem());
		this.world.spawnEntity(e);
		this.world.removeEntity(this);		// move to onupdate!!
		return null;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("point", pointCounter);
		if(focus != null) compound.setLong("focus", focus.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.pointCounter = compound.getInteger("point");
		if(compound.hasKey("focus")) {
			this.focus = BlockPos.fromLong(compound.getLong("focus"));
			this.loadEntity = true;
			
		}
		super.readFromNBT(compound);
	}

}
