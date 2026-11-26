package com.valeriotor.beyondtheveil.tile;

import com.mojang.authlib.GameProfile;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.UUID;

public class FleboBE extends BlockEntity {

    private ColorTriplet poolTriplet = new ColorTriplet(null, null, null);
    private UUID owner;
    private String username;

    public FleboBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.FLEBO_BE.get(), pPos, pBlockState);
    }

    public void setColor(DyeColor first, DyeColor second, DyeColor third) {
        poolTriplet = new ColorTriplet(first, second, third);
    }

    public ColorTriplet getColor() {
        return poolTriplet;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        poolTriplet.saveToTag(pTag);
        if (owner != null) {
            pTag.putUUID("owner", owner);
        }
        if (username != null) {
            pTag.putString("username", username);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        poolTriplet = ColorTriplet.fromTag(pTag);
        owner = pTag.contains("owner") ? pTag.getUUID("owner") : null;
        if (level instanceof ServerLevel sl && owner != null) {
            GameProfileCache cache = sl.getServer().getProfileCache();
            if (cache != null) {
                Optional<GameProfile> gameProfile = cache.get(owner);
                gameProfile.ifPresent(profile -> username = profile.getName());
            }
        }
        if (username == null) {
            username = pTag.contains("username", Tag.TAG_STRING) ? pTag.getString("username") : null;
        }
    }
}
