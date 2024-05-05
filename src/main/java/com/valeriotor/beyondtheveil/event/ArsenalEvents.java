package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.arsenal.TargetingType;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArsenalEvents {


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
                triggerEntity(attackedEntity);
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
                        triggerEntity(e);
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
                        triggerEntity(e);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void entityTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if ((entity.level().getGameTime() & 15) == 15) {
            // clean lists
            // if already triggered and potential targets == 0, untrigger it (but don't untransform former villagers)
            // if not already triggered, check if potential targets > 0 and trigger if so (transform normal villagers)
            // if triggered, add to global list, sorted by priority

        }
    }

    private static boolean triggerEntity(LivingEntity entity) {
        // if entity is not triggered already, then..
        return false; // if it was already triggered
    }

}
