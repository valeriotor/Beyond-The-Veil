package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class SacrificeAltarBE extends BlockEntity {

    private PatientStatus patientStatus; // To simplify stuff we model this as a patient in chest position
    private Mob entity; // Exists only client side.
    private CompoundTag entityData; // Exists only server side

    public SacrificeAltarBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.SACRIFICE_ALTAR_BE.get(), pPos, pBlockState);
    }

    public boolean interact(Player p, ItemStack in, InteractionHand hand) {
        if (level == null) {
            return false;
        }
        if (level.isClientSide)
            return true;
        if (entityData != null) {
            if (in.isEmpty() && !p.isShiftKeyDown()) {
                if (p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent() && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().isPresent()) {
                    CrossSyncData csData = p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
                    CrossSync crossSync = csData.getCrossSync();
                    if (crossSync.getHeldPatientData() == null) {
                        crossSync.setHeldPatient(patientStatus.getPatientType(), entityData, p);
                        entityData = null;
                        patientStatus = null;
                        setChanged();
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                        return true;
                    }
                }

            }
        } else {
            if (p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent() && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().isPresent()) {
                CrossSyncData csData = p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
                CrossSync crossSync = csData.getCrossSync();
                Mob heldPatientEntity = crossSync.getHeldPatientEntity(level);
                if (heldPatientEntity != null) {
                    entityData = crossSync.getHeldPatientData();
                    patientStatus = new PatientStatus(crossSync.getHeldPatientType());
                    patientStatus.setLevelAndCoords((ServerLevel) level, getBlockPos());
                    // TODO if(itemTag.contains("status")) { BUT DO WE WANT THIS
                    // TODO patientStatus.loadFromNBT(itemTag.getCompound("status"));
                    // TODO }

                    // TODO patientStatus.setPatientType(crossSync.getHeldPatientType()); WE DO WANT *THIS*
                    patientStatus.setExposedLocation(SurgicalLocation.CHEST);
                    crossSync.setHeldPatient(null, p);
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                }
            }
        }
        return false;

    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag != null)
            super.load(pTag);
        loadPatient(pTag);
    }

    private void loadPatient(CompoundTag pTag) {
        if (pTag != null && pTag.contains("entity")) {
            PatientType type = pTag.contains("type") ? PatientType.valueOf(pTag.getString("type")) : PatientType.VILLAGER;
            boolean differentType = patientStatus == null || type != patientStatus.getPatientType();
            patientStatus = new PatientStatus(type);
            if (pTag.contains("status")) {
                patientStatus.loadFromNBT(pTag.getCompound("status"));
            }
            if (level != null && level.isClientSide) {
                //explodeParticles();
                if (entity == null || differentType) {
                    entity = type.getMobFunction().apply(level);
                    entity.setYHeadRot(0);
                }
                if (entity instanceof SurgeryPatient sp) { // Will not be redundant once we expand to other patient types
                    sp.markAsPatient();
                    sp.setPatientStatus(patientStatus);
                }
                CompoundTag tag = pTag.getCompound("entity");
                entity.readAdditionalSaveData(tag);
            } else {
                entityData = pTag.getCompound("entity");
            }
            if (level != null && !level.isClientSide) {
                patientStatus.setLevelAndCoords((ServerLevel) level, getBlockPos());
            }
        } else {
            entity = null;
            entityData = null;
            patientStatus = null;
        }

    }

    protected CompoundTag getEntityData() {
        return entityData;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        savePatient(pTag);
    }

    private void savePatient(CompoundTag pTag) {
        if (entityData != null) {
            pTag.put("entity", entityData);
        }
        if (patientStatus != null) {
            CompoundTag statusTag = new CompoundTag();
            patientStatus.saveToNBT(statusTag);
            pTag.put("status", statusTag);
            pTag.putString("type", patientStatus.getPatientType().name());
        }
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        savePatient(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }


    public Mob getEntity() {
        return entity;
    }

    public void tickClient() {
        if (entity != null) {
            entity.tick();
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2, 2, 2));
    }



}
