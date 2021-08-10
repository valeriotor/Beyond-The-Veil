package com.valeriotor.beyondtheveil.tileEntities;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.inventory.ContainerDeepChest;
import com.valeriotor.beyondtheveil.world.Structures.loot.LootTables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TileDeepChest extends TileEntity {

    private ItemStackHandler handler = new ItemStackHandler(36);
    private ResourceLocation lootTable;
    private long lootTableSeed;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler) : super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeUpdateTag(compound);
        return super.writeToNBT(compound);
    }

    private void writeUpdateTag(NBTTagCompound compound) {
        compound.setTag("handler", handler.serializeNBT());
        if (lootTable != null) {
            compound.setString("loot", lootTable.getResourcePath());
            compound.setLong("lootseed", lootTableSeed);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readUpdateTag(compound);
        super.readFromNBT(compound);
    }

    private void readUpdateTag(NBTTagCompound compound) {
        handler.deserializeNBT(compound.getCompoundTag("handler"));
        if (compound.hasKey("loot")) {
            lootTable = LootTables.lootTables.get(compound.getString("loot"));
            lootTableSeed = compound.getLong("lootseed");
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        writeUpdateTag(compound);
        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readUpdateTag(pkt.getNbtCompound());
    }

    public void setLootTable(ResourceLocation p_189404_1_, long p_189404_2_)
    {
        this.lootTable = p_189404_1_;
        this.lootTableSeed = p_189404_2_;
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        this.fillWithLoot(playerIn);
        return new ContainerDeepChest(playerInventory, this);
    }

    public void fillWithLoot(@Nullable EntityPlayer player) {
        if (this.lootTable != null) {
            LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(this.lootTable);
            this.lootTable = null;
            Random random;

            if (this.lootTableSeed == 0L) {
                random = new Random();
            } else {
                random = new Random(this.lootTableSeed);
            }

            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.world);

            if (player != null) {
                lootcontext$builder.withLuck(player.getLuck()).withPlayer(player); // Forge: add player to LootContext
            }

            fillLootInventory(loottable, random, lootcontext$builder.build());
        }
    }

    private void fillLootInventory(LootTable lootTable, Random rand, LootContext context) {
        List<ItemStack> list = lootTable.generateLootForPools(rand, context);
        List<Integer> list1 = getEmptySlotsRandomized(handler, rand);
        shuffleItems(list, list1.size(), rand);

        for (ItemStack itemstack : list) {

            if (itemstack.isEmpty()) {
                handler.setStackInSlot(list1.remove(list1.size() - 1), ItemStack.EMPTY);
            } else {
                handler.setStackInSlot(list1.remove(list1.size() - 1), itemstack);
            }
        }
    }

    private List<Integer> getEmptySlotsRandomized(IItemHandler handler, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for (int i = 0; i < handler.getSlots(); ++i) {
            if (handler.getStackInSlot(i).isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    private void shuffleItems(List<ItemStack> stacks, int p_186463_2_, Random rand) {
        List<ItemStack> list = Lists.newArrayList();
        Iterator<ItemStack> iterator = stacks.iterator();

        while (iterator.hasNext()) {
            ItemStack itemstack = iterator.next();

            if (itemstack.isEmpty()) {
                iterator.remove();
            } else if (itemstack.getCount() > 1) {
                list.add(itemstack);
                iterator.remove();
            }
        }

        p_186463_2_ = p_186463_2_ - stacks.size();

        while (p_186463_2_ > 0 && !list.isEmpty()) {
            ItemStack itemstack2 = list.remove(MathHelper.getInt(rand, 0, list.size() - 1));
            int i = MathHelper.getInt(rand, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.splitStack(i);

            if (itemstack2.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack2);
            } else {
                stacks.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack1);
            } else {
                stacks.add(itemstack1);
            }
        }

        stacks.addAll(list);
        Collections.shuffle(stacks, rand);
    }
}
