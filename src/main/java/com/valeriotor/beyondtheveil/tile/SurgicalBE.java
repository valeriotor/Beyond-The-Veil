package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.item.HeldVillagerItem;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public abstract class SurgicalBE extends BlockEntity {

    private final SurgicalLocation defaultLocation;
    private PatientStatus patientStatus;
    private Mob entity; // Exists only client side. TODO will probably have to convert this to an interface or abstract class extended by other entity types
    private CompoundTag entityData; // Exists only server side


    public SurgicalBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, SurgicalLocation defaultLocation) {
        super(pType, pPos, pBlockState);
        this.defaultLocation = defaultLocation;
    }

    public boolean interact(Player p, ItemStack in, InteractionHand hand) {
        if (level == null) {
            return false;
        }
        if (level.isClientSide)
            return true;
        if (entityData != null) {
            if (in.isEmpty() && !p.isShiftKeyDown()) {
                //ItemStack heldVillager = new ItemStack(Registration.HELD_VILLAGER.get());
                //heldVillager.getOrCreateTag().put("data", entityData);
                //heldVillager.getOrCreateTag().put("status", patientStatus.saveToNBT(new CompoundTag()));
                //p.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, heldVillager);
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
                } else {
                    // TODO player becomes patient
                }

            } else {
                return handleSurgery(p, in);
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
                    patientStatus.setExposedLocation(defaultLocation);
                    crossSync.setHeldPatient(null, p);
                    setChanged();
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                }

            }
            //if (in.getItem() instanceof HeldVillagerItem) {
            //    CompoundTag itemTag = in.getOrCreateTag();
            //    if (itemTag.contains("data")) {
            //        entityData = itemTag.getCompound("data");
            //        patientStatus = new PatientStatus();
            //        patientStatus.setLevelAndCoords((ServerLevel) level, getBlockPos());
            //        if (itemTag.contains("status")) {
            //            patientStatus.loadFromNBT(itemTag.getCompound("status"));
            //        }
            //        patientStatus.setExposedLocation(defaultLocation);
            //        in.shrink(1);
            //        setChanged();
            //        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            //        return true;
            //    }
            //}
        }
        return false;

    }

    private boolean handleSurgery(Player p, ItemStack in) {
        if (patientStatus == null) {
            return false;
        }
        Item i = in.getItem();
        boolean usingItem = false; // Whether to update client
        if (i == Registration.SYRINGE.get()) {
            IFluidHandlerItem syringe = in.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
            FluidStack drained = syringe.drain(1, IFluidHandler.FluidAction.EXECUTE);
            if (!drained.isEmpty()) {
                usingItem = patientStatus.inject(p, drained, this);
            }
        } else if (i == Registration.SCALPEL.get()) {
            usingItem = patientStatus.performIncision(p, this);
        } else if (i == Registration.FORCEPS.get()) {
            CompoundTag forcepTag = in.getOrCreateTag();
            // replace with full compound tag of item?
            Item contained = forcepTag.contains("contained") ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(forcepTag.getString("contained"))) : null;
            if (contained != null) {
                usingItem = patientStatus.insert(p, contained, this);
            }
        } else if (i == Registration.TONGS.get()) {
            usingItem = patientStatus.extract(p, this);
        } else if (i == Registration.SEWING_NEEDLE.get()) {
            usingItem = patientStatus.sewIncision();
        }
        return usingItem;
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
            patientStatus = new PatientStatus(type);
            if (pTag.contains("status")) {
                patientStatus.loadFromNBT(pTag.getCompound("status"));
            }
            if (level != null && level.isClientSide) {
                if (entity == null) { // TODO check if this is correct. Also use entity type
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

    public void tickServer() {
        if (patientStatus != null) {
            patientStatus.tick(false);
        }
        if (patientStatus != null && patientStatus.isDirty()) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            patientStatus.setDirty(false);
        }
    }

}
