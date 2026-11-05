package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.MemorySieveBlock;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.Nullable;

public class MemorySieveBE extends BlockEntity {

    private ItemStack heldItem = ItemStack.EMPTY;
    private ItemEntity itemEntity;
    private int tearCounter = 0;
    private int itemCounter = 0;

    public MemorySieveBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.MEMORY_SIEVE_BE.get(), pWorldPosition, pBlockState);
    }

    public boolean getOrAddItem(Player p, ItemStack in, InteractionHand pHand) {
        if (level.isClientSide)
            return true;

        if (!heldItem.isEmpty()) {
            if (in.getItem() != Registration.MEMORY_PHIAL.get()) {
                heldItem = itemCounter < 3 ? ItemStack.EMPTY : ItemHandlerHelper.insertItemStacked(new PlayerMainInvWrapper(p.getInventory()), heldItem, false);
                itemCounter = 0;
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return true;
            } else {
                for (Memory m : Memory.values()) {
                    ItemStack memStack = m.getItem();
                    if (heldItem.getItem() == memStack.getItem() && m.isUnlocked(p)) {
                        //if(!memStack.getHasSubtypes() || heldItem.getMetadata() == memStack.getMetadata()) {
                        ItemStack newStack = new ItemStack(Registration.MEMORY_PHIAL.get());
                        newStack.getOrCreateTag().putString("memory", m.getDataName());
                        in.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(p, newStack);
                        DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.MADE_MEMORY.apply(m), true, false);
                        BlockState blockState = getBlockState();
                        if (tearCounter > 0) {
                            itemCounter = Math.max(itemCounter - 1, 0);
                            tearCounter--;
                            if (tearCounter <= 0) {
                                itemCounter = 0;
                                blockState = Registration.MEMORY_SIEVE.get().defaultBlockState().setValue(MemorySieveBlock.TEARS, false);
                                level.setBlock(worldPosition, blockState, 3);
                            }
                        } else {
                            itemCounter = 0;
                        }
                        if (itemCounter == 0) {
                            this.heldItem = ItemStack.EMPTY;
                            itemEntity = null;
                        }
                        setChanged();
                        level.sendBlockUpdated(worldPosition, blockState, blockState, 2);
                        //if (m == Memory.LEARNING)
                        //    SyncUtil.addStringDataOnServer(p, false, "firstMemory");

                        return true;
                        //}
                    }
                }
            }
        } else if(!in.isEmpty()){
            if (in.getItem() == Registration.TEARS_FLUID_BUCKET.get()) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.sieve_tears.name(), true);
                p.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                tearCounter = 21;
                level.setBlock(worldPosition, Registration.MEMORY_SIEVE.get().defaultBlockState().setValue(MemorySieveBlock.TEARS, true), 3);
                level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS);
                return true;
            }
            heldItem = in.copy();
            in.shrink(1);
            heldItem.setCount(1);
            itemCounter = 3;
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
        tearCounter = pTag.getInt("tearCounter");
    }

    private void loadItem(CompoundTag pTag) {
        heldItem = pTag != null && pTag.contains("item") ? ItemStack.of(pTag.getCompound("item")) : ItemStack.EMPTY;
        createEntity();
        if (pTag != null) {
            itemCounter = pTag.getInt("itemCounter");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveItem(pTag);
        pTag.putInt("tearCounter", tearCounter);
    }

    private void saveItem(CompoundTag pTag) {
        if (!heldItem.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            heldItem.save(tag);
            pTag.put("item", tag);
        }
        pTag.putInt("itemCounter", itemCounter);
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

    public int getItemCounter() {
        return itemCounter;
    }
}
