package com.valeriotor.beyondtheveil.surgery.arsenal;

import com.valeriotor.beyondtheveil.entity.Minion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BurstRegistry {

    static final Map<String, BurstType> REGISTRY = new HashMap<>();

    public static final BurstType BASE = register(new BurstType("base") {

        @Override
        public List<LivingEntity> getHitEntities(Mob attacker, int extension) {
            double radius = (extension + 1);
            Vec3 burstCentre = burstCentre(attacker, radius);
            AABB filter = new AABB(burstCentre.x - 10, burstCentre.y - 10, burstCentre.z - 10, burstCentre.x + 10, burstCentre.y + 10, burstCentre.z + 10);
            Player master = attacker instanceof Minion ? ((Minion) attacker).getMaster() : null;
            List<LivingEntity> entities = attacker.level().getEntities(EntityTypeTest.forClass(LivingEntity.class), filter, e -> (e.getX() - burstCentre.x) * (e.getX() - burstCentre.x) + (e.getZ() - burstCentre.z) * (e.getZ() - burstCentre.z) < 4 * radius * radius && e.getY() - burstCentre.y < radius * 5 && e != attacker && e != master && (!(e instanceof Minion) || ((Minion) e).getMaster() != master));
            return entities;
        }

        private Vec3 burstCentre(Mob attacker, double radius) {
            double diffX = - radius * Math.sin(Math.toRadians(attacker.getYRot()));
            double diffZ = radius * Math.cos(Math.toRadians(attacker.getYRot()));
            return new Vec3(attacker.getX() + diffX, attacker.getY(), attacker.getZ() + diffZ);
        }
    });

    private static BurstType register(BurstType type) {
        REGISTRY.put(type.getName(), type);
        return type;
    }


}
