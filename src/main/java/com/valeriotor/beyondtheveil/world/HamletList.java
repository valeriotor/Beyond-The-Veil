package com.valeriotor.beyondtheveil.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class HamletList extends WorldSavedData{
	
	private World world;
	private static final String DATA_NAME = "HAMLETDATA";
	private Map<UUID, BlockPos> HPos = new HashMap<UUID, BlockPos>();
	
	public HamletList(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(String s : nbt.getKeySet()) {
			HPos.put(UUID.fromString(s), BlockPos.fromLong(nbt.getCompoundTag(s).getLong("Pos")));
		}
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		for(Entry<UUID, BlockPos> e : HPos.entrySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setLong("Pos", e.getValue().toLong());
			nbt.setTag(e.getKey().toString(), tag);
		}
		return nbt;
	}
	
	public void registerHamlet(UUID u, BlockPos pos) {
		HPos.put(u, pos);
		markDirty();
	}
	
	public void unregisterHamlet(UUID u) {
		HPos.remove(u);
		markDirty();
	}
	
	public static HamletList get(World w) {
		MapStorage storage = w.getPerWorldStorage();
		HamletList data = (HamletList) storage.getOrLoadData(HamletList.class, DATA_NAME);
		if(data == null) {
			data = new HamletList(DATA_NAME);
			storage.setData(DATA_NAME, data);
		}
		data.world = w;
		return data;
	}
	
	public boolean isTooClose(BlockPos pos1) {
		for(BlockPos pos2 : HPos.values()) {
			if(pos2.distanceSq(pos1) < 65536) {
				return true;
			}
			
		}
		return false;
	}
	
	public BlockPos getClosestHamlet(BlockPos pos1) {
		BlockPos shortest = null;
		double distance = 0;
		for(BlockPos pos2 : HPos.values()) {
			if(pos1.equals(pos2)) return pos2;
			double newDistance = pos2.distanceSq(pos1);
			if(newDistance < distance || distance == 0) {
				distance = newDistance;
				shortest = pos2;
			}
		}
		
		return shortest;
	}
	
}
