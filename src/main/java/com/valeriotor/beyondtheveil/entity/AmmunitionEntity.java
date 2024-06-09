package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface AmmunitionEntity {

    default TriggerData getTriggerData() {
        return ((LivingEntity)this).getCapability(TriggerDataProvider.TRIGGER_DATA).orElseThrow(IllegalArgumentException::new);
    }

    List<GenericToClientPacket> getAnimationPackets();

    void startBurst();

    boolean bursting();

}
