package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.TeleportUtil;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockRegistry;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntity;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class BloodWellBE extends BlockEntity {

    private int counter;
    public static final int STARTUP_TIME = 55;
    private boolean didStartup = false;
    private UUID creator;
    private ColorTriplet triplet = new ColorTriplet(null, null, null);

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
            if (creator != null && level instanceof ServerLevel sl) {
                List<Mob> closeUndead = level.getEntities(EntityTypeTest.forClass(Mob.class), AABB.ofSize(new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5), 7, 1, 7), e -> e instanceof Zombie || e instanceof Skeleton);
                BloodPoolData bloodPoolData = BloodPoolData.getInstance(sl);
                for (Mob mob : closeUndead) {
                    BloodPoolEntityType type = mob instanceof Zombie ? BloodPoolEntityType.BLOOD_ZOMBIE : BloodPoolEntityType.BLOOD_SKELETON;
                    boolean success;
                    if (sl.getPlayerByUUID(creator) instanceof ServerPlayer p && !DataUtil.getBoolean(p, PlayerDataLib.obtained_undead.name())) {
                        success = true;
                        DataUtil.setBoolean(p, PlayerDataLib.obtained_undead.name(), true, false);
                    } else if (type == BloodPoolEntityType.BLOOD_ZOMBIE) {
                        success = level.getRandom().nextInt(3) == 0;
                    } else {
                        success = level.getRandom().nextInt(5) == 0;
                    }
                    if (success) {
                        bloodPoolData.addEntity(creator, triplet, new BloodPoolEntity(type, new CompoundTag()), sl);
                    }
                    mob.discard();
                }
            }
        }

        if ((counter & 3) == 0) {
            List<ItemEntity> closeItems = level.getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5), 7, 1, 7), e -> e.getItem().getItem() == Items.CLAY_BALL || e.getItem().getItem() instanceof DyeItem);
            for (ItemEntity closeItem : closeItems) {
                triplet = triplet.addToRight(closeItem.getItem().getItem());
                closeItem.discard();
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

    public void setCreator(UUID creator) {
        this.creator = creator;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("didStartup")) {
            didStartup = pTag.getBoolean("didStartup");
        }
        if (pTag.contains("creator")) {
            creator = pTag.getUUID("creator");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveClientData(pTag);
        if (creator != null) {
            pTag.putUUID("creator", creator);
        }
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
