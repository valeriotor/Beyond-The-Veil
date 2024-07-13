package com.valeriotor.beyondtheveil.capability.crossync;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Map;


public class CrossSync {

    private PatientType heldPatientType;
    private CompoundTag heldPatientData;
    private Mob heldPatientEntity; // used serverside when placing the entity back in the world, clientside for rendering on shoulder

    public <T extends Mob & SurgeryPatient> void setHeldPatient(T heldPatient, Player player) {
        if (heldPatient != null) {
            CompoundTag data = new CompoundTag();
            heldPatient.addAdditionalSaveData(data);
            data.putShort("HurtTime", (short) 0);
            data.putShort("DeathTime", (short) 0);
            setHeldPatient(heldPatient.getPatientType(), data, player);
        } else {
            setHeldPatient(null, null, player);
        }
    }

    public void setHeldPatient(PatientType heldPatientType, CompoundTag heldPatientData, Player player) {
        this.heldPatientType = heldPatientType;
        this.heldPatientData = heldPatientData;
        heldPatientEntity = null;
        sync(player);
    }

    public CompoundTag getHeldPatientData() {
        return heldPatientData;
    }

    public Mob getHeldPatientEntity(Level level) {
        if (heldPatientType == null) {
            return null;
        } else if (heldPatientEntity == null) {
            heldPatientEntity = heldPatientType.getMobFunction().apply(level);
            heldPatientEntity.readAdditionalSaveData(heldPatientData);
            ((SurgeryPatient) heldPatientEntity).setHeld(true);
        }
        return heldPatientEntity;
    }

    public void sync(Player player) {
        if (player != null && !player.level().isClientSide) {
            Messages.sendToTrackingAndSelf(GenericToClientPacket.crossSync(player, this), player);
        }
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        if (compoundTag.contains("heldPatientType")) {
            setHeldPatient(PatientType.valueOf(compoundTag.getString("heldPatientType")), compoundTag.getCompound("heldPatientData"), null);
        } else {
            setHeldPatient(null, null);
        }
    }

    public void saveToNBT(CompoundTag compoundTag) {
        if (heldPatientType != null) {
            compoundTag.putString("heldPatientType", heldPatientType.name());
            compoundTag.put("heldPatientData", heldPatientData);
        }
    }


}
