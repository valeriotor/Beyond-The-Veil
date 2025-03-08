package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PatientPodBE extends BlockEntity {
    private LivingEntity toRender;

    public PatientPodBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.PATIENT_POD_BE.get(), pPos, pBlockState);
    }

    public boolean interact(Player p, ItemStack in, InteractionHand hand) {
        if (level == null) {
            return false;
        }
        if (level instanceof ServerLevel sl && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent() && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().isPresent()) {
            CrossSyncData csData = p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
            CrossSync crossSync = csData.getCrossSync();
            Mob heldEntity = crossSync.getHeldPatientEntity(level);
            LifeEconomyData.PodData podData = LifeEconomyData.getInstance(sl).getPodData(worldPosition);
            PatientType patient = podData.getPatient();
            if (patient == null) {
                if (heldEntity != null) {
                    PatientType heldPatientType = crossSync.getHeldPatientType();
                    CompoundTag heldPatientData = crossSync.getHeldPatientData();
                    podData.setPatient(heldPatientType, heldPatientData);
                    crossSync.setHeldPatient(null, p);
                    sync();
                    return true;
                }
            } else {
                if (heldEntity == null) {
                    crossSync.setHeldPatient(podData.getPatient(), podData.getEntity(), p);
                    podData.setPatient(null, null);
                    sync();
                    return true;
                }
            }
        }
        return true;
    }

    public void sync() {
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (level instanceof ServerLevel sl) {
            LifeEconomyData.PodData podData = LifeEconomyData.getInstance(sl).getPodData(worldPosition);
            if (podData != null) {
                podData.save(tag);
            }
        }
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        changeEntityClientSide(tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag() != null) {
            changeEntityClientSide(pkt.getTag());
        }
    }

    private void changeEntityClientSide(CompoundTag tag) {
        if (tag.contains("type") && level != null && level.isClientSide) {
            PatientType type = PatientType.valueOf(tag.getString("type"));
            toRender = type.getPodFunction().apply(level);
            CompoundTag entityData = tag.getCompound("entity");
            toRender.readAdditionalSaveData(entityData);
            if (toRender instanceof WeeperEntity w) {
                w.setInPod(true);
            }
        } else {
            toRender = null;
        }
    }

    public LivingEntity getToRender() {
        return toRender;
    }
}
