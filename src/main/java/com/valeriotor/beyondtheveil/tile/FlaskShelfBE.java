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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FlaskShelfBE extends BlockEntity {

    public static final ModelProperty<List<Flask>> FLASKS_PROPERTY = new ModelProperty<>();
    private final List<Flask> flasks = new ArrayList<>();

    private int test = 0;

    public FlaskShelfBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FLASK_SHELF_BE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null) {
            super.load(pTag);
        }
        loadClientData(pTag);
    }

    private void loadClientData(CompoundTag pTag) {
        flasks.clear();
        CompoundTag flasks1 = pTag.getCompound("flasks");
        for (String key: flasks1.getAllKeys()) {
            flasks.add(new Flask(flasks1.getCompound(key)));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveClientData(pTag);
    }

    private void saveClientData(CompoundTag pTag) {
        CompoundTag flaskTag = new CompoundTag();
        pTag.put("flasks", flaskTag);
        int i = 0;
        for (Flask f : flasks) {
            flaskTag.put(String.valueOf(i), f.toNBT());
            i++;
        }
    }


    public void increaseTest() {
        test++;
    }
    public int testInt() {
        return test;
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadClientData(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(FLASKS_PROPERTY, flasks)
                .build();
    }


    private class Flask {
        // flask type
        // fill level
        private final double x, y, z;

        private Flask(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private Flask(CompoundTag tag) {
            this.x = tag.getDouble("x");
            this.y = tag.getDouble("y");
            this.z = tag.getDouble("z");
        }

        private CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putDouble("x", x);
            tag.putDouble("y", y);
            tag.putDouble("z", z);
            return tag;
        }
    }


}
