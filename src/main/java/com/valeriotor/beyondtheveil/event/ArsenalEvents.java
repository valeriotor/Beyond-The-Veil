package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.arsenal.TargetingType;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArsenalEvents {

    private static final Map<Level, List<Mob>> triggeredEntities = new HashMap<>();
    private static final Map<Level, Map<DyeColor, Map<LivingEntity, Mob>>> mutex = new HashMap<>();

    @SubscribeEvent
    public static void entityHitEvent(LivingAttackEvent event) {
        LivingEntity attackedEntity = event.getEntity();
        Level level = attackedEntity.level();
        if (level.isClientSide) {
            return;
        }
        Entity attackingEntity = event.getSource().getEntity();
        if (attackedEntity.getCapability(TriggerDataProvider.TRIGGER_DATA).isPresent() && attackingEntity instanceof LivingEntity attacker) {
            TriggerData triggerData = attackedEntity.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
            if (triggerData.getTargetType() == TargetingType.WAS_HIT) {
                triggerData.addRestrictedTarget(attacker);
            }
            if (triggerData.getTriggerType() == TargetingType.WAS_HIT) {
                triggerData.addRestrictedTriggerer(attacker);
                triggerEntity((Mob) attackedEntity, triggerData);
            }
        }
        if (attackingEntity instanceof Player master) {
            AABB aabb = MathHelperBTV.surroundingChunks(attackingEntity.blockPosition(), 20, 20, 1);
            List<Entity> entities = level.getEntities(((Entity) null), aabb, e -> e instanceof LivingEntity && e.getCapability(TriggerDataProvider.TRIGGER_DATA).isPresent());
            for (Entity entity : entities) {
                LivingEntity e = (LivingEntity) entity;
                TriggerData triggerData = e.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
                if (master.getUUID().equals(triggerData.getMaster())) {
                    if (triggerData.getTargetType() == TargetingType.MASTER_ATTACKED) {
                        triggerData.addRestrictedTarget(attackedEntity);
                    }
                    if (triggerData.getTriggerType() == TargetingType.MASTER_ATTACKED) {
                        triggerData.addRestrictedTriggerer(attackedEntity);
                        triggerEntity((Mob) e, triggerData);
                    }
                }
            }
        }
        if (attackedEntity instanceof Player master && attackingEntity instanceof LivingEntity attacker) {
            AABB aabb = MathHelperBTV.surroundingChunks(master.blockPosition(), 20, 20, 1);
            List<Entity> entities = level.getEntities(((Entity) null), aabb, e -> e instanceof LivingEntity && e.getCapability(TriggerDataProvider.TRIGGER_DATA).isPresent());
            for (Entity entity : entities) {
                LivingEntity e = (LivingEntity) entity;
                TriggerData triggerData = e.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
                if (master.getUUID().equals(triggerData.getMaster())) {
                    if (triggerData.getTargetType() == TargetingType.MASTER_WAS_HIT) {
                        triggerData.addRestrictedTarget(attacker);
                    }
                    if (triggerData.getTriggerType() == TargetingType.MASTER_WAS_HIT) {
                        triggerData.addRestrictedTriggerer(attacker);
                        triggerEntity((Mob) e, triggerData);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void entityTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity e = event.getEntity();
        if ((e.level().getGameTime() & 15) == 15 && e.getCapability(TriggerDataProvider.TRIGGER_DATA).isPresent() && e instanceof Mob entity) {
            TriggerData triggerData = entity.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
            triggerData.cleanLists(entity);
            if (triggerData.isTriggered() && triggerData.getPotentialTargets(entity).isEmpty()) {
                untriggerEntity(entity, triggerData);
            } else if (!triggerData.isTriggered() && !triggerData.getTriggerers(entity).isEmpty()) {
                entity = triggerEntity(entity, triggerData);
            }
            if (triggerData.isTriggered()) {
                triggeredEntities.computeIfAbsent(entity.level(), x -> new ArrayList<>()).add(entity);
            }

            // clean lists
            // if already triggered and potential targets == 0, untrigger it (but don't untransform former villagers)
            // if not already triggered, check if potential targets > 0 and trigger if so (transform normal villagers)
            // if triggered, add to global list, sorted by priority

        }
    }

    @SubscribeEvent
    public static void levelTickEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && (event.level.getGameTime() & 15) == 0 && triggeredEntities.containsKey(event.level)) {
            // TODO clear mutexes with ammunition that are no longer alive or
            List<Mob> triggeredMobs = triggeredEntities.get(event.level);
            triggeredMobs.removeIf(e -> !e.isAlive());
            triggeredMobs.sort(Comparator.comparingInt(ArsenalEvents::getPriority));
            for (Mob triggeredEntity : triggeredMobs) {
                TriggerData triggerData = triggeredEntity.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
                List<LivingEntity> potentialTargets = triggerData.getPotentialTargets(triggeredEntity);
                if (!potentialTargets.isEmpty() && (triggeredEntity.getTarget() == null || triggeredEntity.getTarget().isDeadOrDying())) {
                    Map<LivingEntity, Mob> mutexes = triggerData.getMutex() != null ? mutex.computeIfAbsent(event.level, x -> new HashMap<>()).computeIfAbsent(triggerData.getMutex(), x -> new HashMap<>()) : new HashMap<>();
                    for (LivingEntity potentialTarget : potentialTargets) {
                        if (!mutexes.containsKey(potentialTarget)) {
                            mutexes.put(potentialTarget, triggeredEntity);
                            triggeredEntity.setTarget(potentialTarget);
                            break;
                        }
                    }
                    clearMutexes(event.level);
                }
            }
            triggeredMobs.clear();
        }
    }

    private static void clearMutexes(Level level) {
        if (!mutex.containsKey(level)) {
            return;
        }
        for (Map.Entry<DyeColor, Map<LivingEntity, Mob>> mutexes : mutex.get(level).entrySet()) {
            Iterator<Map.Entry<LivingEntity, Mob>> iterator = mutexes.getValue().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<LivingEntity, Mob> next = iterator.next();
                if (next.getKey().isDeadOrDying() || next.getValue().isDeadOrDying()) {
                    iterator.remove();
                } else {
                    TriggerData triggerData = next.getValue().getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
                    if (!triggerData.isTriggered() || !triggerData.getPotentialTargets(next.getValue()).contains(next.getKey())) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    private static int getPriority(Mob entity) {
        TriggerData triggerData = entity.getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalStateException::new);
        return triggerData.getPriority();
    }

    @SubscribeEvent
    public static void serverCloseEvent(ServerStoppedEvent event) {
        triggeredEntities.clear();
        mutex.clear();
    }

    private static Mob triggerEntity(Mob entity, TriggerData triggerData) {
        triggerData.setTriggered(true);
        // TODO if entity is not triggered already, then..
        // TODO turn villagers and pillagers into suicidal versions
        return entity; // if it was already triggered
    }

    private static boolean untriggerEntity(Mob entity, TriggerData triggerData) {
        triggerData.setTriggered(false);
        triggerData.clearRestrictedTriggerers();
        entity.setTarget(null);
        // TODO if entity is triggered, then..
        return false; // if it was already not triggered
    }

}
