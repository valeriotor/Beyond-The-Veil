package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DreamFocusBE extends BlockEntity {

    private UUID usingPlayer = null;
    private List<Vec3> points = new ArrayList<>();
    private DyeColor color = DyeColor.BLUE;
    private boolean showPath = false;

    public DreamFocusBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.DREAM_FOCUS_BE.get(), pPos, pBlockState);
    }

    public void setDyeColor(DyeColor color) {
        this.color = color;
        updateClient();
    }

    public void toggleShowPath() {
        showPath = !showPath;
        updateClient();
    }

    public boolean setPlayer(Player p) {
        if (usingPlayer != null) {
            return false;
        }
        usingPlayer = p.getUUID();
        return true;
    }

    public void clearList() {
        points.clear();
    }

    public void addPoint(Player player, Vec3 point) {
        if (player.getUUID().equals(usingPlayer)) {
            points.add(point);
        }
    }

    public void finish() {
        usingPlayer = null;
        updateClient();
    }

    public void tickServer() {

    }

    public void tickClient() {
        for (int i = 0; i < points.size(); i++) {
            if (i % 2 == 0) {
                Vec3 vec3 = points.get(i);
                if (level != null) {
                    int c = color.getTextColor();
                    ClientMethods.colorParticle(DustParticleOptions.REDSTONE, vec3.x, vec3.y, vec3.z, 0,0,0, c >> 16, ((c >> 8) & 255), c & 255);
                }
            }
        }
    }

    private void updateClient() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadCommonData(tag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveCommonData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt != null) {
            load(pkt.getTag());
        }
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveCommonData(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadCommonData(pTag);
    }

    private void saveCommonData(CompoundTag tag) {
        ListTag pointsTag = new ListTag();
        tag.put("points", pointsTag);
        int i = 0;
        for (Vec3 point : points) {
            CompoundTag pointTag = new CompoundTag();
            pointTag.putDouble("x", point.x);
            pointTag.putDouble("y", point.y);
            pointTag.putDouble("z", point.z);
            pointsTag.addTag(i++, pointTag);
        }
        tag.putInt("dye", color.getId());
        tag.putBoolean("showPath", showPath);
    }

    private void loadCommonData(CompoundTag tag) {
        if (tag.contains("points")) {
            points.clear();
            ListTag pointsTag = tag.getList("points", Tag.TAG_COMPOUND);
            for (int i = 0; i < pointsTag.size(); i++) {
                CompoundTag pointTag = pointsTag.getCompound(i);
                points.add(new Vec3(pointTag.getDouble("x"), pointTag.getDouble("y"), pointTag.getDouble("z")));
            }
        }
        if (tag.contains("dye")) {
            color = DyeColor.byId(tag.getInt("dye"));
        }
        showPath = tag.getBoolean("showPath");
    }
}
