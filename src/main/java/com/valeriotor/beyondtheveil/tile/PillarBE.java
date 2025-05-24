package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class PillarBE extends BlockEntity {

    private boolean offer;
    private BlockPos link;
    private UUID connection;
    private boolean fromItem;
    private int counter = 0;


    public PillarBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        offer = pType == BTVBlockEntities.OFFER_PILLAR_BE.get();
    }

    public boolean isOffer() {
        return offer;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (link != null) {
            pTag.putLong("link", link.asLong());
        }
        if (connection != null) {
            pTag.putString("connection", connection.toString());
        }
    }


    // BEHAVIOUR:
    // If it is placed in the world without having been previously linked to something, then nothing happens (and fromItem is also absent)
    // If it is first used on another (opposite) pillar, then it
    // 1. updates the existing pillar by:
    //  (a) setting its current link to null
    //  (b) setting its connection to the new connection
    // 2. updates the held pillar by:
    //  (a) adding fromItem = true
    //  (b) setting link to existing pillar's pos
    //  (c) setting its connection to the new connection
    // When a pillar item with this data is placed, in its first tick it:
    // 1. checks in saved data that the pillar to which it is linked still exists and has the same connection
    // 2. if so, it updates the other pillar's link to point to this one (both in saved data and, if available, the block entity itself)
    // 3. otherwise, it updates itself to point to nowhere and have no connection
    // 4. sets this.fromItem to false

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("link")) {
            link = BlockPos.of(pTag.getLong("link"));
        }
        if (pTag.contains("connection")) {
            connection = UUID.fromString(pTag.getString("connection"));
        }
        if (pTag.contains("fromItem")) {
            fromItem = true;
        }
    }

    private void loadFromLevel() {

    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!fromItem) { // when loading chunk this should happen after loading NBT? But then again it might be a useless check, since if made from an item then it's still not in the LifeEconomyData
            if (level instanceof ServerLevel sl) {
                LifeEconomyData.PillarData pillarData = LifeEconomyData.getInstance(sl).getPillarData(worldPosition);
                if (pillarData != null) {
                    link = pillarData.getLinkPos();
                    connection = pillarData.getConnection();
                }
            }
        }
    }

    public void setLink(BlockPos link) {
        this.link = link;
        counter = 0;
    }

    public BlockPos getLink() {
        return link;
    }

    public void setConnection(UUID connection) {
        this.connection = connection;
        counter = 0;
    }

/*    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof ServerLevel sl) {
            LifeEconomyData.getInstance(sl).addPillar(worldPosition, link, connection);
        }
    }*/

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    public void tickServer() {
        if (level instanceof ServerLevel sl) {
            if (fromItem) {
                LifeEconomyData.getInstance(sl).addPillar(worldPosition, link, connection);
                if (link != null) {
                    if (!LifeEconomyData.getInstance(sl).setPillarLink(sl, link, connection, worldPosition)) { // if the other pillar is no longer there or got a new connection
                        connection = null;
                        link = null;
                        setChanged();
                    }
                }
                fromItem = false;
            }
            counter++;
            if (link != null && (counter & 255) == 0) {
                if (!LifeEconomyData.getInstance(sl).checkPillarConnection(link, connection)) { // heartbeat message
                    connection = null;
                    link = null;
                    setChanged();
                }
            }
        }
    }


}
