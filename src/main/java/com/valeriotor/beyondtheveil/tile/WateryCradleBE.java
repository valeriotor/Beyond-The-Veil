package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.item.HeldVillagerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WateryCradleBE extends BlockEntity {

    private CrawlerEntity entity; // Exists only client side. TODO will probably have to convert this to an interface or abstract class extended by other entity types
    private CompoundTag entityData; // Exists only server side

    public WateryCradleBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.WATERY_CRADLE_BE.get(), pWorldPosition, pBlockState);
    }

    public boolean interact(Player p, ItemStack in, InteractionHand hand) {
        if (level == null) {
            return false;
        }
        if (level.isClientSide)
            return true;
        if (entityData != null) {
            if (in.isEmpty()) {
                ItemStack heldVillager = new ItemStack(Registration.HELD_VILLAGER.get());
                heldVillager.getOrCreateTag().put("data", entityData);
                p.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, heldVillager);
                entityData = null;
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return true;
            }
        } else {
            if (in.getItem() instanceof HeldVillagerItem) {
                entityData = in.getOrCreateTag().getCompound("data");
                in.shrink(1);
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return true;
            }
        }
        return false;

    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null)
            super.load(pTag);
        loadEntity(pTag);
    }

    private void loadEntity(CompoundTag pTag) {
        if (pTag != null && pTag.contains("entity")) {
            if (level != null && level.isClientSide) {
                entity = Registration.CRAWLER.get().create(level);
                CompoundTag tag = pTag.getCompound("entity");
                entity.readAdditionalSaveData(tag);
            } else {
                entityData = pTag.getCompound("entity");
            }
        } else {
            entity = null;
            entityData = null;
        }

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveEntity(pTag);
    }

    private void saveEntity(CompoundTag pTag) {
        if (entityData != null) {
            pTag.put("entity", entityData);
        }
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveEntity(tag);
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


    public CrawlerEntity getEntity() {
        return entity;
    }

    //public void tickClient() {
    //    if (itemEntity != null) {
    //        itemEntity.tick();
    //    }
    //}
}
