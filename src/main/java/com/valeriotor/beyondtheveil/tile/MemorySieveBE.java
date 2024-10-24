package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.Nullable;

public class MemorySieveBE extends BlockEntity {

    private ItemStack heldItem = ItemStack.EMPTY;
    private ItemEntity itemEntity;

    public MemorySieveBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.MEMORY_SIEVE_BE.get(), pWorldPosition, pBlockState);
    }

    public boolean getOrAddItem(Player p, ItemStack in) {
        if (level.isClientSide)
            return true;

        if (!heldItem.isEmpty()) {
            if (in.getItem() != Registration.MEMORY_PHIAL.get()) {
                ItemStack newStack = ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(p.getInventory()), heldItem, false);
                // TODO
                //if(stack.getItem() instanceof IArtifactItem) {
                //    ((IArtifactItem)stack.getItem()).unlockData(p);
                //}
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);

                boolean success = newStack.getCount() < heldItem.getCount();
                heldItem = newStack;
                return success;
            } else {
                for (Memory m : Memory.values()) {
                    ItemStack memStack = m.getItem();
                    if (heldItem.getItem() == memStack.getItem() /*&& m.isUnlocked(p)*/) {
                        //if(!memStack.getHasSubtypes() || heldItem.getMetadata() == memStack.getMetadata()) {
                        ItemStack newStack = new ItemStack(Registration.MEMORY_PHIAL.get());
                        newStack.getOrCreateTag().putString("memory", m.getDataName());
                        in.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(p, newStack);
                        DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.MADE_MEMORY.apply(m), true, false);
                        this.heldItem = ItemStack.EMPTY;
                        itemEntity = null;
                        setChanged();
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                        //if (m == Memory.LEARNING)
                        //    SyncUtil.addStringDataOnServer(p, false, "firstMemory");

                        return true;
                        //}
                    }
                }
            }
        } else if(!in.isEmpty()){
            heldItem = in.copy();
            in.shrink(1);
            heldItem.setCount(1);
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            return true;
        }
        return false;
    }
    private void createEntity() {
        if (heldItem.isEmpty()) itemEntity = null;
        else if(level != null && level.isClientSide)
            itemEntity = new ItemEntity(this.level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), heldItem);
    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null)
            super.load(pTag);
        loadItem(pTag);
    }

    private void loadItem(CompoundTag pTag) {
        heldItem = pTag != null && pTag.contains("item") ? ItemStack.of(pTag.getCompound("item")) : ItemStack.EMPTY;
        createEntity();

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveItem(pTag);
    }

    private void saveItem(CompoundTag pTag) {
        if (!heldItem.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            heldItem.save(tag);
            pTag.put("item", tag);
        }
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveItem(tag);
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


    public ItemEntity getEntity() {
        return itemEntity;
    }

    public void tickClient() {
        if (itemEntity != null) {
            itemEntity.tick();
        }
    }
}
