package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;

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


}
