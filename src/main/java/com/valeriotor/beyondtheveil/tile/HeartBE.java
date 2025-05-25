package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HeartBE extends BlockEntity {

    private int counter = 0;
    private BlockPos link;

    public HeartBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.HEART_BE.get(), pWorldPosition, pBlockState);
    }

    public void tickServer() {
        counter++;
        if ((counter & 7) == 0) {
            if (link != null && !(level.getBlockEntity(link) instanceof HeartBE) && !(level.getBlockEntity(link) instanceof BloodWellBE)) {
                setLink(null);
            }
            if (counter > Integer.MAX_VALUE / 2) counter = 0;
            // TODO should not get IPlayerGuardian entities
            List<Mob> undeads = this.level.getEntities(EntityTypeTest.forClass(Mob.class), AABB.ofSize(new Vec3(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()), 64, 64, 64), e -> e.getMobType() == MobType.UNDEAD);

            for (Mob undead : undeads) {
                undead.getCapability(ProcessionDataProvider.PROCESSION_DATA).ifPresent(c -> {
                    if (link != null && undead.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY(), worldPosition.getZ() + 0.5) <= 3) {
                        c.setDestination(link, worldPosition);
                    } else {
                        c.setDestination(worldPosition, worldPosition);
                    }
                });
            }
        }
        if (counter % 30 == 0) {
            if (level instanceof ServerLevel sl) {
                sl.playSound(null, worldPosition, BTVSounds.HEARTBEAT.get(), SoundSource.BLOCKS, 1, 1);
            }
        }

    }


    public void tickClient() {
        counter++;
        if (counter >= 30) {
            counter = 0;
        }
        Player player = DataUtilClient.getPlayer();
        ItemStack itemInMainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack itemInOffHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (itemInMainHand.getItem() == Registration.CORAL_STAFF.get() || itemInOffHand.getItem() == Registration.CORAL_STAFF.get()) {
            if (link != null) {
                int timer = player.tickCount & 63;
                double posX = worldPosition.getX() + 0.5 + timer * (link.getX() + 0.5 - worldPosition.getX() - 0.5) / 64D;
                double posY = worldPosition.getY() + 0.5 + timer * (link.getY() + 0.5 - worldPosition.getY() - 0.5) / 64D;
                double posZ = worldPosition.getZ() + 0.5 + timer * (link.getZ() + 0.5 - worldPosition.getZ() - 0.5) / 64D;
                level.addAlwaysVisibleParticle(DustParticleOptions.REDSTONE, posX, posY, posZ, 0, 0, 0);
            }
            ItemStack stack = itemInMainHand.getItem() == Registration.CORAL_STAFF.get() ? itemInMainHand : itemInOffHand;
            CompoundTag tag = stack.getOrCreateTag();
            BlockPos tagPos = BlockPos.of(tag.getLong("heart"));
            if (tag.contains("heart") && worldPosition.equals(tagPos)) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        for (int k = -1; k <= 1; k++) {
                            level.addAlwaysVisibleParticle(DustParticleOptions.REDSTONE, worldPosition.getX() + 0.5 + i * 0.1, worldPosition.getY() + j * 0.1, worldPosition.getZ() + 0.5 + k * 0.1, 0, 0, 0);
                        }
                    }
                }

            }
        }


    }

    public int getCounter() {
        return counter;
    }

    public void setLink(BlockPos link) {
        this.link = link;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag != null)
            super.load(pTag);
        loadLink(pTag);
    }

    private void loadLink(CompoundTag pTag) {
        if (pTag != null && pTag.contains("link")) {
            link = BlockPos.of(pTag.getLong("link"));
        } else {
            link = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveLink(tag);
    }

    private void saveLink(CompoundTag tag) {
        if (link != null) {
            tag.putLong("link", link.asLong());
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveLink(tag);
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
}


