package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface IDreamEntity {
	
	public default boolean moveToNextPoint(List<Point3d> ps) {
		if(this instanceof Entity) {
			Point3d p = this.getNextPoint(ps);
			if(p != null) {
				Entity e = (Entity)this;
				Vec3d vec = new Vec3d(p.x-e.posX, p.y-e.posY, p.z-e.posZ);
				e.motionX += vec.x/20;
				e.motionY = vec.y/3;
				e.motionZ += vec.z/20;
				return true;
			}
			return false;
		}
		return false;
	}
	
	public Point3d getNextPoint(List<Point3d> ps);
}
