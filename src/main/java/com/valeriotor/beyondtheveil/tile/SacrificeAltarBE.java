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
import com.valeriotor.beyondtheveil.surgery.PatientCondition;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import org.jetbrains.annotations.NotNull;
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
            } else if (in.getItem() == Registration.SACRIFICIAL_KNIFE.get() && !patientStatus.isDead()) {
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
                ritualStatus = RitualStatus.startRitual((ServerLevel) level, playerInitiating, getBlockPos(), basinsToBeUsed);
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
        } else {
            playerInitiating = null;
        }
        basinsToBeUsed.clear();
        CompoundTag basins = tag.getCompound("basins");
        basins.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).forEach(s -> basinsToBeUsed.add(BlockPos.of(basins.getLong(s))));

        if (tag.contains("ritualStatus")) {
            ritualStatus = new RitualStatus(tag.getCompound("ritualStatus"));
        } else {
            ritualStatus = null;
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
            Vec3 prev = getCenterPos();
            if (ritualStatus != null || playerInitiating != null) {
                for (int i = 0; i < 3; i++) {
                    level.addAlwaysVisibleParticle(BTVParticles.BLOODSPILL.get(), prev.x(), prev.y() - 0.2, prev.z(), (Math.random() - 0.5) * 1.25, 0.1, (Math.random() - 0.5) * 1.25);
                }
            }
            if (counter % 4 == 0) {
                int rescaledCounter = counter / 4;
                boolean endsAtAltar = ritualStatus != null;
                makeParticles(prev, rescaledCounter, endsAtAltar);

            }
        }
    }

    @NotNull
    private Vec3 getCenterPos() {
        Direction facing = getBlockState().getValue(FullMultiBlock.FACING);
        Vec3 prev = getBlockPos().getCenter();
        prev = prev.add(-facing.getStepX() * 0.6 -facing.getStepZ() * 0.2, 0.5, -facing.getStepZ() * 0.6 + facing.getStepX() * 0.2);
        return prev;
    }

    private void makeParticles(Vec3 prev, int rescaledCounter, boolean endsAtAltar) {
        if (level == null) {
            return;
        }
        Vec3 altar = prev;
        Vec3 player = null;
        if (playerInitiating != null && level.getPlayerByUUID(playerInitiating) != null) {
            player = level.getPlayerByUUID(playerInitiating).position();
        }
        List<BlockPos> toUse = endsAtAltar ? ritualStatus.getAltars() : basinsToBeUsed;
        for (int i = 0; i < toUse.size() + 1; i++) {
            Vec3 curr = i == toUse.size() ? (endsAtAltar ? altar : player) : toUse.get(i).getCenter();
            if (curr == null) {
                return;
            }
            double dist = curr.distanceTo(prev);
            double distX = curr.x() - prev.x();
            double distY = curr.y() - prev.y();
            double distZ = curr.z() - prev.z();
            final double PER_BLOCK = 3;
            for (int j = 0; j < (dist) * PER_BLOCK; j++) {
                double currDist = j / PER_BLOCK + (1 / PER_BLOCK / 10 * (rescaledCounter % 10));
                double percent = currDist / (dist);//
                double sinOffset = Math.sin(percent * Math.PI) / 6 * Math.sin(rescaledCounter * 2 * Math.PI / (80D));
                level.addAlwaysVisibleParticle(BTVParticles.BLOODSPILL.get(), prev.x() + distX * percent + Math.random() * 0.125 - 0.0625, prev.y() + distY * percent + 0.75 + Math.random() * 0.125 - 0.0625 + sinOffset, prev.z() + distZ * percent + Math.random() * 0.125 - 0.0625, 0, 0, 0);
            }
            prev = curr;
        }
    }

    public void tickServer() {
        // TODO every 10 ticks (on the 10th tick) if player not found within x blocks or if he doesn't wield knife then break chain (patient dies?)
        if (this.level instanceof ServerLevel sl) {
            if (patientStatus != null) {
                patientStatus.setLevelAndCoords(sl, getBlockPos());
                patientStatus.tick(false);
                if (patientStatus.isDirty()) {
                    updateClient();
                }
            }
            counter++;
            if (counter % 10 == 9) {
                if (playerInitiating != null) {
                    Player player = sl.getPlayerByUUID(playerInitiating);
                    if (player != null) {
                        Long altarLong = DataUtil.getOrSetLong(player, PlayerDataLib.SACRIFICE_ALTAR, -1, false);
                        if (altarLong == -1 || !BlockPos.of(altarLong).equals(getBlockPos()) || getBlockPos().distSqr(player.blockPosition()) >= MAX_PLAYER_DISTANCE_SQR || player.getMainHandItem().getItem() != Registration.SACRIFICIAL_KNIFE.get())
                            breakChain();
                        else {
                            for (BlockPos blockPos : basinsToBeUsed) {
                                if (!(sl.getBlockEntity(blockPos) instanceof BloodBasinBE)) {
                                    breakChain();
                                    break;
                                }
                            }
                        }
                    } else breakChain();
                }
            }
            if (ritualStatus != null) {
                Vec3 centerPos = getCenterPos();
                if (counter % 4 == 0) {
                    renderOrb();
                }
                setChanged();
                if (ritualStatus.tick(sl)) {
                    ritualStatus.terminationEffects(sl, centerPos);
                    killVictim();
                    ritualStatus = null;
                    updateClient();
                }
            }

        }
    }

    private void renderOrb() {
        Vec3 centerPos = getCenterPos();
        ServerLevel sl = (ServerLevel) level;
        int currentHop = ritualStatus.getCurrentHop();
        double progressUntilNextHop = ritualStatus.getProgressUntilNextHop();
        Vec3 start = currentHop == 0 ? centerPos : ritualStatus.getAltars().get(currentHop - 1).getCenter();
        Vec3 end = currentHop == ritualStatus.getAltars().size() ? centerPos : ritualStatus.getAltars().get(currentHop).getCenter();
        double distanceRatio = progressUntilNextHop / ritualStatus.getDistances()[currentHop];
        Vec3 orbPos = new Vec3(start.x + (end.x - start.x) * distanceRatio, start.y + (end.y - start.y) * distanceRatio, start.z + (end.z - start.z) * distanceRatio);
        sl.sendParticles(BTVParticles.BLOODSPILL.get(), orbPos.x, orbPos.y + 0.75, orbPos.z, 50, (Math.random() - 0.5) * 0.25, (Math.random() - 0.5) * 0.25, (Math.random() - 0.5) * 0.25, 0);
        sl.sendParticles(ParticleTypes.SMOKE, orbPos.x, orbPos.y + 0.75, orbPos.z, 25, (Math.random() - 0.5) * 0.25, (Math.random() - 0.5) * 0.25, (Math.random() - 0.5) * 0.25, 0);

    }

    private void breakChain() {
        playerInitiating = null;
        basinsToBeUsed.clear();
        killVictim();
        updateClient();
    }

    private void killVictim() {
        if (patientStatus != null && patientStatus.getCondition() != PatientCondition.DEAD && level instanceof ServerLevel sl) {
            patientStatus.setCondition(PatientCondition.DEAD);
            Vec3 centerPos = getCenterPos();
            sl.sendParticles(BTVParticles.BLOODSPILL.get(), centerPos.x, centerPos.y - 0.2, centerPos.z, 100, (Math.random() - 0.5) * 1.25, 0.1, (Math.random() - 0.5) * 1.25, 1);
        }
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
