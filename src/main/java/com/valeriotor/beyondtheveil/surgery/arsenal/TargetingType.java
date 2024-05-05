package com.valeriotor.beyondtheveil.surgery.arsenal;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;

import java.util.List;

public enum TargetingType {
    PLAYER_NEARBY(false),
    HOSTILE_NEARBY(false), // TODO consider NEUTRAL_NEARBY
    WAS_HIT(true),
    MASTER_WAS_HIT(true),
    MASTER_ATTACKED(true);

    private final boolean restricted;

    TargetingType(boolean restricted) {
        this.restricted = restricted;
    }

    public List<LivingEntity> getPotentialTargets(LivingEntity ammunition, TriggerData data) {
        AABB aabb = MathHelperBTV.surroundingChunks(ammunition.blockPosition(), 20, 20, 1);
        if (this == PLAYER_NEARBY) {
            return ammunition.level().getEntities(ammunition, aabb, e -> e instanceof Player && !data.getIgnoredPlayers().contains(e.getUUID())).stream().map(e -> (LivingEntity) e).toList();
        } else if (this == HOSTILE_NEARBY) {
            return ammunition.level().getEntities(ammunition, aabb, e -> e instanceof LivingEntity && e instanceof Enemy).stream().map(e -> (LivingEntity) e).toList();
        }
        return null;
    }

    public boolean isRestricted() {
        return restricted;
    }
}
