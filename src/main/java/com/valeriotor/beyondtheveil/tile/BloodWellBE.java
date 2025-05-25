package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.util.TeleportUtil;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodWellBE extends BlockEntity {

    private int counter;
    public static final int STARTUP_TIME = 55;
    private boolean didStartup = false;

    public BloodWellBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.BLOOD_WELL_BE.get(), pWorldPosition, pBlockState);
    }

    public void tickServer() {
        counter++;
        if ((counter & 15) == 0 && level != null) {
            if (!MultiblockRegistry.BLOOD_WELL_COMPLETE.checksOutBottomCenter(level, worldPosition.below())) {
                level.setBlock(worldPosition, Registration.BLOOD_BRICK.get().defaultBlockState(), 3);
                return;
            }
            List<Mob> undeads = level.getEntities(EntityTypeTest.forClass(Mob.class), AABB.ofSize(new Vec3(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()), 64, 64, 64), e -> e.getMobType() == MobType.UNDEAD);

            for (Mob undead : undeads) {
                undead.getCapability(ProcessionDataProvider.PROCESSION_DATA).ifPresent(c -> {
                    c.setDestination(worldPosition, worldPosition, true);
                });
            }
            List<ServerPlayer> players = level.getEntities(EntityTypeTest.forClass(ServerPlayer.class), AABB.ofSize(new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5), 7, 1, 7), p -> true);
            for (ServerPlayer player : players) {
                Item main = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                Item off = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                if ((main == Items.ROTTEN_FLESH && off == Items.COAL) || (off == Items.ROTTEN_FLESH && main == Items.COAL)) {
                    TeleportUtil.teleportToArche(player, player.level());
                }
            }
        }
        if (counter == STARTUP_TIME) {
            didStartup = true;
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
        if (counter % 20 == 0) {
            if (level instanceof ServerLevel sl) {
                sl.playSound(null, worldPosition, BTVSounds.HEARTBEAT.get(), SoundSource.BLOCKS, 1, 1);
            }
        }
    }

    public void tickClient() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public boolean didStartup() {
        return didStartup;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("didStartup")) {
            didStartup = pTag.getBoolean("didStartup");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveClientData(pTag);
    }

    private void saveClientData(CompoundTag tag) {
        tag.putBoolean("didStartup", didStartup);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveClientData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-3, 0, -3), getBlockPos().offset(4, 1, 4));
    }
}
