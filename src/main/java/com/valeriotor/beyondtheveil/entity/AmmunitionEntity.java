package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;

import java.util.List;

public interface AmmunitionEntity {

    TriggerData getTriggerData();

    List<GenericToClientPacket> getAnimationPackets();

    void startBurst();

    boolean bursting();

}
