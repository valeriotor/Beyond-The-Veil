package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.ForgeHooks;

import java.util.UUID;

public class BloodPoolEntity {

    private final ConvalescentData convalescentData = new ConvalescentData();
    private final UUID uuid;

    public static BloodPoolEntity fromPatient(PatientType patientType, CompoundTag entityData, ConvalescentData convalescentData, TriggerData triggerData) {
        BloodPoolEntityType bloodPoolEntityType;
        if (patientType == PatientType.VILLAGER && triggerData != null) {
            bloodPoolEntityType = BTVEntities.getTriggerEntity(convalescentData, triggerData).getB();
        } else {
            bloodPoolEntityType = BloodPoolEntityType.fromPatientType(patientType);
        }
        entityData.putFloat("Health", bloodPoolEntityType.getMaxHealth());
        return new BloodPoolEntity(bloodPoolEntityType, entityData);
    }

    private final CompoundTag entityData;

    private final BloodPoolEntityType type;

    public BloodPoolEntity(BloodPoolEntityType type, CompoundTag entityData) {
        this.type = type;
        this.entityData = entityData;
        if (!entityData.contains("Health")) {
            entityData.putFloat("Health", type.getMaxHealth());
        }
        if (entityData.contains("convalescent")) {
            convalescentData.loadFromNBT(entityData.getCompound("convalescent"));
        }
        uuid = UUID.randomUUID();
    }

    public BloodPoolEntity(CompoundTag tag) {
        type = BloodPoolEntityType.valueOf(tag.getString("type"));
        entityData = tag.getCompound("entityData");
        if (!entityData.contains("Health")) {
            entityData.putFloat("Health", type.getMaxHealth());
        }
        if (entityData.contains("convalescent")) {
            convalescentData.loadFromNBT(entityData.getCompound("convalescent"));
        }
        if (tag.contains("uuid")) {
            uuid = tag.getUUID("uuid");
        } else {
            uuid = UUID.randomUUID();
        }
    }

    public void spawn(ServerPlayer player) {
        Mob mob = type.getMobFunction().apply(player.serverLevel());
        mob.readAdditionalSaveData(entityData);
        mob.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
            if (entityData.contains("convalescent")) {
                c.loadFromNBT(entityData.getCompound("convalescent"));
            }
        });
        mob.getCapability(TriggerDataProvider.TRIGGER_DATA).ifPresent(t -> {
            t.loadFromNBT(convalescentData.getTriggerData().saveToNBT(new CompoundTag()));
        });
        double angle = player.getRandom().nextDouble() * 2 * Math.PI;
        int dist = 2;
        double distX = Math.cos(angle) * dist;
        double distZ = Math.sin(angle) * dist;
        if (mob instanceof PlayerMinion minion) {
            minion.setMaster(player);
        }
        mob.setPos(player.getX() + distX, player.getY(), player.getZ() + distZ);
        player.level().addFreshEntity(mob);
        if (getType().isUndead()) {
            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.spawned_undead.name(), true, false);
        }
    }

    public String textDescription() {
        float health = entityData.getFloat("Health");
        StringBuilder sb = new StringBuilder(Component.translatable("gui.blood_pool.health").getString() +  health);
        TriggerData triggerData = convalescentData.getTriggerData();
        if (triggerData != null) {
            for (String s : RenderEvents.triggerDataDescription(triggerData)) {
                sb.append("\\0");
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public BloodPoolEntityType getType() {
        return type;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        tag.put("entityData", entityData);
        tag.putUUID("uuid", uuid);
        return tag;
    }

    public UUID getUuid() {
        return uuid;
    }
}
