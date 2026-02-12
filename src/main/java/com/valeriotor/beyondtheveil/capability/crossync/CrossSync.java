package com.valeriotor.beyondtheveil.capability.crossync;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.event.PlayerEvents;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgeryUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class CrossSync {

    private PatientType heldPatientType;
    private CompoundTag heldPatientData;
    private Mob heldPatientEntity; // used serverside when placing the entity back in the world, clientside for rendering on shoulder
    private PlayerTransformation transformation;
    private boolean crawling;

    public <T extends Mob & SurgeryPatient> void setHeldPatient(T heldPatient, Player player) {
        if (heldPatient != null) {
            CompoundTag data = SurgeryUtil.heldPatientData(heldPatient);
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

    public PatientType getHeldPatientType() {
        return heldPatientType;
    }

    public Mob getHeldPatientEntity(Level level) {
        if (heldPatientType == null) {
            return null;
        } else if (heldPatientEntity == null) {
            heldPatientEntity = heldPatientType.getMobFunction().apply(level);
            heldPatientEntity.readAdditionalSaveData(heldPatientData);
            ((SurgeryPatient) heldPatientEntity).setHeld(true);
            heldPatientEntity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                if (heldPatientData.contains("convalescent")) {
                    c.loadFromNBT(heldPatientData.getCompound("convalescent"));
                }
            });
        }
        return heldPatientEntity;
    }

    public void setTransformation(PlayerTransformation transformation, Player player) {
        this.transformation = transformation;
        sync(player);
    }

    public PlayerTransformation getTransformation() {
        return transformation;
    }

    public void setCrawling(boolean crawling, Player player) {
        if (crawling != this.crawling) {
            this.crawling = crawling;
            PlayerEvents.addCrawlingAttributes(player);
            player.refreshDimensions();
            sync(player);
        }
    }

    public boolean isCrawling() {
        return crawling;
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
        if (compoundTag.contains("transformation")) {
            setTransformation(PlayerTransformation.valueOf(compoundTag.getString("transformation")), null);
        } else {
            setTransformation(null, null);
        }
        crawling = compoundTag.getBoolean("crawling");
    }

    public CompoundTag saveToNBT(CompoundTag compoundTag) {
        saveToNBTForRespawn(compoundTag);
        if (transformation != null) {
            compoundTag.putString("transformation", transformation.name());
        }
        compoundTag.putBoolean("crawling", crawling);
        return compoundTag;
    }

    public CompoundTag saveToNBTForRespawn(CompoundTag compoundTag) {
        if (heldPatientType != null) {
            compoundTag.putString("heldPatientType", heldPatientType.name());
            compoundTag.put("heldPatientData", heldPatientData);
        }
        return compoundTag;
    }


}
