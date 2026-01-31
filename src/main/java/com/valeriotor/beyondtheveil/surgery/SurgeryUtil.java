package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class SurgeryUtil {

    public static CompoundTag heldPatientData(Mob mob) {
        CompoundTag data = new CompoundTag();
        mob.addAdditionalSaveData(data);
        mob.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
            data.put("convalescent", c.saveToNBT(new CompoundTag()));
        });
        data.putShort("HurtTime", (short) 0);
        data.putShort("DeathTime", (short) 0);
        return data;
    }


    public static Mob transformHeldPatient(Mob heldPatient, ServerLevel sl, UUID masterId) {
        if (heldPatient instanceof CrawlerEntity e) {
            LazyOptional<ConvalescentData> c = e.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
            if (c.isPresent()) {
                ConvalescentData data = c.resolve().get();
                TriggerData triggerData = data.getTriggerData();
                if (triggerData != null) {
                    triggerData.setMaster(masterId);
                    EntityType<?> type = BTVEntities.getTriggerEntity(data, triggerData).getA();

                    if (type == BTVEntities.CRAWLER.get()) {
                        return heldPatient;
                    } else {
                        Entity o = type.create(sl);
                        if (o instanceof Mob mob) {
                            mob.getCapability(TriggerDataProvider.TRIGGER_DATA).ifPresent(t -> {
                                t.loadFromNBT(triggerData.saveToNBT(new CompoundTag()));
                            });
                            return mob;
                        }
                    }
                }
            }
        }
        return heldPatient;
    }
}
