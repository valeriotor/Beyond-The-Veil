package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BloodBasinBE extends BlockEntity {

    private ItemEntity itemEntity;

    private ItemStackHandler stackHandler = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            if (level != null && level.isClientSide) {
                BlockPos pos = getBlockPos();
                itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stacks.get(0));
            }
        }
    };

    private final LazyOptional<IItemHandler> stackHolder = LazyOptional.of(() -> stackHandler);


    public BloodBasinBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.BLOOD_BASIN_BE.get(), pPos, pBlockState);
    }

    public void interact(Player player, InteractionHand hand, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() != Registration.SACRIFICIAL_KNIFE.get()) {
                ItemStack left = stackHandler.insertItem(0, stack, false);
                player.setItemInHand(hand, left);
                updateClient();
            } else {
                Long altarLong = DataUtil.getOrSetLong(player, PlayerDataLib.SACRIFICE_ALTAR, -1, false);
                if (altarLong != -1 && level.getBlockEntity(BlockPos.of(altarLong)) instanceof SacrificeAltarBE sacrificeAltar) {
                    sacrificeAltar.addBasin(getBlockPos());
                }
            }
        } else {
            ItemHandlerHelper.giveItemToPlayer(player, stackHandler.extractItem(0, 64, false));
            updateClient();
        }
    }

    private void updateClient() {
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    public ItemStackHandler getStackHandler() {
        return stackHandler;
    }

    public void removeItem() {
        stackHandler.setStackInSlot(0, ItemStack.EMPTY);
        updateClient();
    }

    @Override
    public void load(CompoundTag pTag) {
        if(pTag != null)
            super.load(pTag);
        loadItem(pTag);
    }

    private void loadItem(CompoundTag pTag) {
        if (pTag.contains("item")) {
            stackHandler.deserializeNBT(pTag.getCompound("item"));
        }
        createEntity(pTag);

    }

    private void createEntity(CompoundTag pTag) {
        if (level != null && level.isClientSide) {
            ItemStack stackInSlot = stackHandler.getStackInSlot(0);
            if (stackInSlot.isEmpty()) {
                itemEntity = null;
            } else if(level != null && level.isClientSide) {
                itemEntity = new ItemEntity(this.level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stackInSlot);
            }

        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveItem(pTag);
    }

    private void saveItem(CompoundTag pTag) {
        pTag.put("item", stackHandler.serializeNBT());
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag.contains("item")) {
            stackHandler.deserializeNBT(tag.getCompound("item"));
        }
        createEntity(tag);
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

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return stackHolder.cast();
        return super.getCapability(capability, facing);
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void tickClient() {
        if (itemEntity != null) {
            itemEntity.tick();
        }
    }

}
