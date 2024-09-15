package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.item.HeldVillagerItem;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class WateryCradleBE extends SurgicalBE {


    private Mob backupEntity;
    private int weeperCreatedTicks;

    public WateryCradleBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.WATERY_CRADLE_BE.get(), pWorldPosition, pBlockState, SurgicalLocation.SKULL);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2,1,2));
    }

    @Override
    protected void weeperCreated() {
        this.backupEntity = getEntity();
        weeperCreatedTicks = 180;
    }

    @Override
    public void tickClient() {
        super.tickClient();
        if (backupEntity != null) {
            backupEntity.tick();
        }
        if (weeperCreatedTicks > 0) {
            weeperCreatedTicks--;
            if (weeperCreatedTicks == 70) {
                backupEntity = null;
            }
        }
    }

    public Mob getBackupEntity() {
        return backupEntity;
    }

    public int getWeeperCreatedTicks() {
        return weeperCreatedTicks;
    }


}
