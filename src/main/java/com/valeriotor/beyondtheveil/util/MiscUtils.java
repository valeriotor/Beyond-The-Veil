package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.lib.BTVParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class MiscUtils {

    public static void doParticles(Level level, Entity e, ParticleOptions particles) {
        if (level instanceof ServerLevel sl) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = 0; y < 7; y++) {
                        sl.sendParticles(particles, e.getX() + x * 0.3, e.getY() + y * 0.2, e.getZ() + z * 0.3, 3, 0, 0, 0, 0.1);
                    }
                }
            }
        }
    }

}
