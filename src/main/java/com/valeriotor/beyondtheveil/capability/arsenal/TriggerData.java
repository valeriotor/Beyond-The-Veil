package com.valeriotor.beyondtheveil.capability.arsenal;

import com.valeriotor.beyondtheveil.surgery.arsenal.TargetingType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TriggerData {
    private TargetingType triggerType;
    private TargetingType targetType;
    private List<UUID> restrictedTriggerers = new ArrayList<>();
    private List<UUID> restrictedTargets = new ArrayList<>();
    private List<UUID> ignoredPlayers = new ArrayList<>();
    private UUID master = null;
    private int seeingDistance = 32;

    public List<LivingEntity> getPotentialTargets(LivingEntity triggerableEntity) {
        if (targetType == null) {
            return null;
        }
        if (targetType.isRestricted()) {
            return restrictedTargets.stream().map(uuid -> fromUUID((ServerLevel) triggerableEntity.level(), uuid)).toList();
        }
        return targetType.getPotentialTargets(triggerableEntity, this);
    }

    public List<LivingEntity> getTriggerers(LivingEntity triggerableEntity) {
        if (triggerType == null) {
            return null;
        }
        if (triggerType.isRestricted()) {
            return restrictedTriggerers.stream().map(uuid -> fromUUID((ServerLevel) triggerableEntity.level(), uuid)).toList();
        }
        return triggerType.getPotentialTargets(triggerableEntity, this);
    }

    public void cleanLists(LivingEntity triggerableEntity) {
        ServerLevel level = (ServerLevel) triggerableEntity.level();
        Iterator<UUID> iterator = restrictedTargets.listIterator();
        while (iterator.hasNext()) {
            LivingEntity target = fromUUID(level, iterator.next());
            if (target == null || target.isDeadOrDying() || target.distanceToSqr(triggerableEntity) > seeingDistance * seeingDistance) {
                iterator.remove();
            }
        }
        iterator = restrictedTriggerers.listIterator();
        while (iterator.hasNext()) {
            LivingEntity target = fromUUID(level, iterator.next());
            if (target == null || target.isDeadOrDying() || target.distanceToSqr(triggerableEntity) > seeingDistance * seeingDistance) {
                iterator.remove();
            }
        }
    }

    private LivingEntity fromUUID(ServerLevel level, UUID uuid) {
        Entity entity = level.getEntity(uuid);
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void addRestrictedTriggerer(LivingEntity triggerer) {
        UUID uuid = triggerer.getUUID();
        if (!restrictedTriggerers.contains(uuid)) {
            restrictedTriggerers.add(uuid);
        }
    }

    public void addRestrictedTarget(LivingEntity target) {
        UUID uuid = target.getUUID();
        if (!restrictedTargets.contains(uuid)) {
            restrictedTargets.add(uuid);
        }
    }

    public TargetingType getTriggerType() {
        return triggerType;
    }

    public TargetingType getTargetType() {
        return targetType;
    }

    public void setTriggerType(TargetingType triggerType) {
        this.triggerType = triggerType;
    }

    public void setTargetType(TargetingType targetType) {
        this.targetType = targetType;
    }

    public UUID getMaster() {
        return master;
    }

    public void setMaster(UUID master) {
        this.master = master;
    }

    public List<UUID> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public void saveToNBT(CompoundTag tag) {
        CompoundTag triggerersTag = new CompoundTag();
        CompoundTag targetsTag = new CompoundTag();
        CompoundTag ignoredPlayersTag = new CompoundTag();
        for (UUID uuid : restrictedTriggerers) {
            triggerersTag.putBoolean(uuid.toString(), true);
        }
        for (UUID uuid : restrictedTargets) {
            targetsTag.putBoolean(uuid.toString(), true);
        }
        for (UUID uuid : ignoredPlayers) {
            ignoredPlayersTag.putBoolean(uuid.toString(), true);
        }
        if (master != null) {
            tag.putString("master", master.toString());
        }
        tag.putInt("seeingDistance", seeingDistance);

        if (triggerType != null) {
            tag.putString("triggerType", triggerType.name());
            tag.putString("targetType", targetType.name());
        }

    }

    public void loadFromNBT(CompoundTag tag) {
        restrictedTriggerers.clear();
        restrictedTargets.clear();
        ignoredPlayers.clear();

        if (!tag.contains("triggerers")) {
            return;
        }

        for (String uuid : tag.getCompound("triggerers").getAllKeys()) {
            restrictedTriggerers.add(UUID.fromString(uuid));
        }
        for (String uuid : tag.getCompound("targets").getAllKeys()) {
            restrictedTargets.add(UUID.fromString(uuid));
        }
        for (String uuid : tag.getCompound("ignoredPlayers").getAllKeys()) {
            ignoredPlayers.add(UUID.fromString(uuid));
        }
        if (tag.contains("master")) {
            master = UUID.fromString(tag.getString("master"));
        }
        seeingDistance = tag.getInt("seeingDistance");

        if (tag.contains("triggerType")) {
            String triggerType1 = tag.getString("triggerType");
            String targetType1 = tag.getString("targetType");
            triggerType = TargetingType.valueOf(triggerType1);
            targetType = TargetingType.valueOf(targetType1);
        }

    }
}
