package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.item.SurgeryIngredient;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishBarrelBE extends BlockEntity {

    public static final ModelProperty<ItemStack> STACK_PROPERTY = new ModelProperty<>();

    private final ItemStackHandler stackHandler;
    private final LazyOptional<IItemHandler> stackHolder;

    public FishBarrelBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.FISH_BARREL_BE.get(), pPos, pBlockState);
        stackHandler = new ItemStackHandler() {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.is(ItemTags.FISHES);
            }
        };
        stackHolder = LazyOptional.of(() -> stackHandler);
    }

    public boolean interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            ItemStack stack1 = stackHandler.extractItem(0, 64, false);
            player.setItemInHand(hand, stack1);
            update();
            return true;
        } else {
            ItemStack stack1 = stackHandler.insertItem(0, stack, false);
            if (stack.getCount() > stack1.getCount()) { // i.e. something was inserted
                player.setItemInHand(hand, stack1);
                update();
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return stackHolder.cast();
        }
        return super.getCapability(cap);
    }

    private void update() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag != null) {
            super.load(tag);
        }
        loadCommonData(tag);
    }

    private void loadCommonData(CompoundTag tag) {
        if (tag.contains("stack")) {
            stackHandler.deserializeNBT(tag.getCompound("stack"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveCommonData(tag);
    }

    private void saveCommonData(CompoundTag tag) {
        tag.put("stack", stackHandler.serializeNBT());
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
        if (pkt.getTag() != null) {
            load(pkt.getTag());
        }

        requestModelDataUpdate();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder()
                .with(STACK_PROPERTY, stackHandler.getStackInSlot(0).copy())
                .build();
    }
}
