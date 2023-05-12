package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FumeSpreaderBE extends BlockEntity {

    private Memory storedMemory;
    public FumeSpreaderBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FUME_SPREADER_BE.get(), pWorldPosition, pBlockState);
    }

    public Memory getStoredMemory() {
        return storedMemory;
    }

    public void setStoredMemory(Memory storedMemory) {
        this.storedMemory = storedMemory;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null)
            super.load(pTag);
        storedMemory = pTag != null && pTag.contains("memory") ? Memory.getMemoryFromDataName(pTag.getString("memory")) : null;
    }
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (storedMemory != null) {
            pTag.putString("memory", storedMemory.getDataName());
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



}
