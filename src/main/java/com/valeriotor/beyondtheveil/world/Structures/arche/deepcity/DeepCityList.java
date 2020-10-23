package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class DeepCityList extends WorldSavedData{

	private World world;
	private static final String DATA_NAME = "DEEPCITYDATA";
	private Map<Long, DeepCity> cities = new HashMap<>();
	private List<DeepCity> cityList = new ArrayList<>();
	
	public static DeepCityList get(World w) {
		MapStorage storage = w.getPerWorldStorage();
		DeepCityList data = (DeepCityList) storage.getOrLoadData(DeepCityList.class, DATA_NAME);
		if(data == null) {
			data = new DeepCityList(DATA_NAME);
			storage.setData(DATA_NAME, data);
		}
		data.world = w;
		data.setWorlds();
		return data;
	}
	
	public DeepCityList(String name) {
		super(name);
	}
	
	private void setWorlds() {
		for(DeepCity city : cityList)
			city.setWorld(world);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(String s : nbt.getKeySet()) {
			NBTTagCompound entry = nbt.getCompoundTag(s);
			DeepCity city = new DeepCity();
			city.readFromNBT(entry.getCompoundTag("city"));
			Long l = entry.getLong("long");
			cities.put(l, city);
			cityList.add(city);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int index = 0;
		for(Entry<Long, DeepCity> entry : cities.entrySet()) {
			NBTTagCompound entryNBT = new NBTTagCompound();
			entryNBT.setLong("long", entry.getKey());
			NBTTagCompound city = new NBTTagCompound();
			entry.getValue().writeToNBT(city);
			entryNBT.setTag("city", city);
			compound.setTag("city" + (index++), entryNBT);
		}
		return compound;
	}
	
	public void addCity(int chunkX, int chunkZ, DeepCity city) {
		cities.put(ChunkPos.asLong(chunkX, chunkZ), city);
		cityList.add(city);
		markDirty();
	}
	
	public DeepCity getCity(int chunkX, int chunkZ) {
		for(DeepCity c : cityList) {
			if(c.intersects(chunkX, chunkZ)) {
				markDirty();
				return c;
			}
		}
		return null;
	}

}
