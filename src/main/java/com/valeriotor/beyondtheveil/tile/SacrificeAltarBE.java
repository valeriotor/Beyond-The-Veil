package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.multiblock.FullMultiBlock;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.rituals.RitualStatus;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SacrificeAltarBE extends BlockEntity {

    public static final double MAX_PLAYER_DISTANCE_SQR = 900;

    private PatientStatus patientStatus; // To simplify stuff we model this as a patient in chest position
    private Mob victim; // Exists only client side.
    private CompoundTag entityData; // Exists only server side
    private UUID playerInitiating;
    private List<BlockPos> basinsToBeUsed = new ArrayList<>();
    private RitualStatus ritualStatus;
    private int counter;

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
                        updateClient();
                        return true;
                    }
                }
            } else if (in.getItem() == Registration.SACRIFICIAL_KNIFE.get()) {
                executeChain(p);
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
                    patientStatus.setExposedLocation(SurgicalLocation.CHEST);
                    crossSync.setHeldPatient(null, p);
                    updateClient();
                }
            }
        }
        return false;

    }

    private void executeChain(Player player) {
        Optional<PlayerData> playerData = player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
        if (playerInitiating == null) {
            if (playerData.isPresent() && ritualStatus == null) {
                basinsToBeUsed.clear();
                playerInitiating = player.getUUID();
                updateClient();
                playerData.get().setLong(PlayerDataLib.SACRIFICE_ALTAR, getBlockPos().asLong(), false);
            }
        } else if (playerInitiating.equals(player.getUUID())) {
            if (playerData.isPresent() && ritualStatus == null) {
                ritualStatus = RitualStatus.startRitual(level, basinsToBeUsed);
                playerInitiating = null;
                basinsToBeUsed.clear();
                updateClient();
                playerData.get().setLong(PlayerDataLib.SACRIFICE_ALTAR, -1, false);
            }
        }
    }

    public void addBasin(BlockPos pos) {
        if (!level.isClientSide) {
            if (basinsToBeUsed.contains(pos)) {
                breakChain();
                updateClient();
            } else {
                basinsToBeUsed.add(pos);
                updateClient();
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag != null)
            super.load(tag);
        loadPatient(tag);
        if (tag.contains("playerInitiating")) {
            playerInitiating = UUID.fromString(tag.getString("playerInitiating"));
        }
        basinsToBeUsed.clear();
        CompoundTag basins = tag.getCompound("basins");
        basins.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).forEach(s -> basinsToBeUsed.add(BlockPos.of(basins.getLong(s))));

        if (tag.contains("ritualStatus")) {
            ritualStatus = new RitualStatus(tag.getCompound("ritualStatus"));
        }
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
                if (victim == null || differentType) {
                    victim = type.getMobFunction().apply(level);
                    victim.setYHeadRot(0);
                }
                if (victim instanceof SurgeryPatient sp) { // Will not be redundant once we expand to other patient types
                    sp.markAsPatient();
                    sp.setPatientStatus(patientStatus);
                }
                CompoundTag tag = pTag.getCompound("entity");
                victim.readAdditionalSaveData(tag);
            } else {
                entityData = pTag.getCompound("entity");
            }
            if (level != null && !level.isClientSide) {
                patientStatus.setLevelAndCoords((ServerLevel) level, getBlockPos());
            }
        } else {
            victim = null;
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

    public UUID getPlayerInitiating() {
        return playerInitiating;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        savePatient(pTag);
        if (playerInitiating != null) {
            pTag.putString("playerInitiating", playerInitiating.toString());
        }
        CompoundTag basins = new CompoundTag();
        pTag.put("basins", basins);
        for (int i = 0; i < basinsToBeUsed.size(); i++) {
            basins.putLong(String.valueOf(i), basinsToBeUsed.get(i).asLong());
        }
        if (ritualStatus != null) {
            CompoundTag statusTag = new CompoundTag();
            pTag.put("ritualStatus", statusTag);
            ritualStatus.saveToNBT(statusTag);
        }
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
        saveAdditional(tag);
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


    public Mob getVictim() {
        return victim;
    }

    public void tickClient() {
        if (victim != null) {
            victim.tick();
        }
        counter++;
        if (level != null) {
            Direction facing = getBlockState().getValue(FullMultiBlock.FACING);
            Vec3 prev = getBlockPos().getCenter();
            prev = prev.add(-facing.getStepX() * 0.5, 0.5, -facing.getStepZ() * 0.5);
            if (counter % 4 == 0) {
                int rescaledCounter = counter / 4;
                for (int i = 0; i < basinsToBeUsed.size(); i++) {
                    Vec3 curr = basinsToBeUsed.get(i).getCenter();
                    double dist = curr.distanceTo(prev);
                    double distX = curr.x() - prev.x();
                    double distY = curr.y() - prev.y();
                    double distZ = curr.z() - prev.z();
                    final double PER_BLOCK = 3;
                    for (int j = 0; j < (dist) * PER_BLOCK; j++) {
                        double currDist = j / PER_BLOCK + (1 / PER_BLOCK / 10 * (rescaledCounter % 10));
                        double percent = currDist / (dist);//
                        double sinOffset = Math.sin(percent * Math.PI) / 3 * Math.sin(rescaledCounter * 2 * Math.PI / (80D));
                        level.addAlwaysVisibleParticle(BTVParticles.BLOODSPILL.get(), prev.x() + distX * percent + Math.random() * 0.125 - 0.0625, prev.y() + distY * percent + 0.75 + Math.random() * 0.125 - 0.0625 + sinOffset, prev.z() + distZ * percent + Math.random() * 0.125 - 0.0625, 0, 0, 0);
                    }
                    prev = curr;
                }

            }
        }
    }

    public void tickServer() {
        // TODO every 10 ticks (on the 10th tick) if player not found within x blocks or if he doesn't wield knife then break chain (patient dies?)
        counter++;
        if (counter % 10 == 9) {
            if (playerInitiating != null && level != null) {
                Player player = level.getPlayerByUUID(playerInitiating);
                if (player != null) {
                    Long altarLong = DataUtil.getOrSetLong(player, PlayerDataLib.SACRIFICE_ALTAR, -1, false);
                    if (altarLong == -1 || !BlockPos.of(altarLong).equals(getBlockPos()) || getBlockPos().distSqr(player.blockPosition()) >= MAX_PLAYER_DISTANCE_SQR || player.getMainHandItem().getItem() != Registration.SACRIFICIAL_KNIFE.get())
                        breakChain();
                } else breakChain();
            }
        }
    }

    private void breakChain() {
        playerInitiating = null;
        basinsToBeUsed.clear();
        killVictim();
        updateClient();
    }

    private void killVictim() {
        // TODO
    }


    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2, 2, 2));
    }

    private void updateClient() {
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }
}
