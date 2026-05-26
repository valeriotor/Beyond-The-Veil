package com.valeriotor.beyondtheveil.entity.dream_focus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface DreamFocusMovable {

    default boolean moveToNextPoint(List<Vec3> points, int counter) {
        if (this instanceof Entity e) {
            if (counter < points.size() - 1) {
                Vec3 p1 = e.position();
                Vec3 p2 = points.get(counter + 1);
                Vec3 angle = p2.subtract(p1).scale(0.8);//.normalize().scale(0.2);
                e.setDeltaMovement(angle.x, angle.y, angle.z);
                return true;
            }
        }
        return false;
    }

}
