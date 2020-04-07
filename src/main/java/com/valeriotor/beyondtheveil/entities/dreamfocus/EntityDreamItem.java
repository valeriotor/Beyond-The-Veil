package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDreamItem extends EntityItem implements IDreamEntity{
	private int i = 0;
	public EntityDreamItem(World w) {
		super(w);
	}
	public EntityDreamItem(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}

	@Override
	public Point3d getNextPoint(List<Point3d> ps) {
		if(this.i < ps.size()) {
			Point3d p = ps.get(i);
			this.i++;
			return p;
		}
		EntityItem e = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItem());
		this.world.removeEntity(this);
		return null;
	}

}
