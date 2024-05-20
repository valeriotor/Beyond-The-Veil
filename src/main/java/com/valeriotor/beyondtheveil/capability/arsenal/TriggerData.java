package com.valeriotor.beyondtheveil.capability.arsenal;

import com.valeriotor.beyondtheveil.surgery.arsenal.TargetingType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

import javax.swing.*;
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
    private boolean triggered = false;
    private int priority;
    private DyeColor mutex = null;

    public List<LivingEntity> getPotentialTargets(LivingEntity triggerableEntity) {
        if (targetType == null) {
            return new ArrayList<>();
        }
        if (targetType.isRestricted()) {
            return restrictedTargets.stream().map(uuid -> fromUUID((ServerLevel) triggerableEntity.level(), uuid)).toList();
        }
        return targetType.getPotentialTargets(triggerableEntity, this);
    }

    public List<LivingEntity> getTriggerers(LivingEntity triggerableEntity) {
        if (triggerType == null) {
            return new ArrayList<>();
        }
        if (triggerType.isRestricted()) {
            return restrictedTriggerers.stream().map(uuid -> fromUUID((ServerLevel) triggerableEntity.level(), uuid)).toList();
        }
        return triggerType.getPotentialTargets(triggerableEntity, this);
    }


    public void clearRestrictedTargets() {
        restrictedTargets.clear();
    }
    public void clearRestrictedTriggerers() {
        restrictedTriggerers.clear();
    }

    public void cleanLists(LivingEntity triggerableEntity) {
        if (triggerableEntity.level() instanceof ServerLevel level) {
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

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public DyeColor getMutex() {
        return mutex;
    }

    public void setMutex(DyeColor mutex) {
        this.mutex = mutex;
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
        tag.put("triggerers", triggerersTag);
        tag.put("targets", targetsTag);
        tag.put("ignoredPlayers", ignoredPlayersTag);

        if (master != null) {
            tag.putString("master", master.toString());
        }
        tag.putInt("seeingDistance", seeingDistance);

        if (triggerType != null) {
            tag.putString("triggerType", triggerType.name());
            tag.putString("targetType", targetType.name());
        }

        if (mutex != null) {
            tag.putInt("mutex", mutex.getId());
        }

        tag.putBoolean("triggered", triggered);
        tag.putInt("priority", priority);

    }

    private static final boolean DEBUG = true;

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
            ignoredPlayers.add(master);
        }
        seeingDistance = tag.getInt("seeingDistance");

        if (tag.contains("triggerType")) {
            String triggerType1 = tag.getString("triggerType");
            String targetType1 = tag.getString("targetType");
            triggerType = TargetingType.valueOf(triggerType1);
            targetType = TargetingType.valueOf(targetType1);
        }

        if (tag.contains("mutex")) {
            mutex = DyeColor.byId(tag.getInt("mutex"));
        }

        triggered = tag.getBoolean("triggered");
        priority = tag.getInt("priority");

        if (DEBUG) {
            triggerType = TargetingType.WAS_HIT;
            targetType = TargetingType.HOSTILE_NEARBY;
            mutex = DyeColor.BLACK;
        }

    }
}
