package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import com.valeriotor.beyondtheveil.world.arche.WorldProviderArche;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class DeepCityList extends WorldSavedData{

	private World world;
	private static final String DATA_NAME = "DEEPCITYDATA";
	private final List<DeepCity> cityList = new ArrayList<>();
	private final Map<Long, DeepCity> cityChunks = new HashMap<>();
	private int emptyCachesCounter = 0;
	
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
	
	@SubscribeEvent
	public static void worldTickEvent(WorldTickEvent e) {
		World w = e.world;
		if(!w.isRemote && w.provider instanceof WorldProviderArche) {
			get(w).emptyCaches();
		}
	}
	
	private void emptyCaches() {
		emptyCachesCounter++;
		if(emptyCachesCounter > 12000) {
			emptyCachesCounter = 0;
			for(DeepCity city : cityList) city.emptyCache();
		}
	}

	public DeepCityList(String name) {
		super(name);
	}
	
	private void setWorlds() {
		for(DeepCity city : cityList)
			city.setWorld(world);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) { // TODO FIXA
		for(String s : nbt.getKeySet()) {
			NBTTagCompound cityNBT = nbt.getCompoundTag(s);
			DeepCity city = new DeepCity();
			city.readFromNBT(cityNBT);
			for(Long chunkLong : city.getChunkCoords()) {
				cityChunks.put(chunkLong, city);
			}
			cityList.add(city);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int index = 0;
		for(DeepCity city : cityList) {
			NBTTagCompound cityNBT = new NBTTagCompound();
			city.writeToNBT(cityNBT);
			compound.setTag("city" + (index++), cityNBT);
		}
		return compound;
	}
	
	public void addCity(int chunkX, int chunkZ, DeepCity city) {
		for(Long chunkLong : city.getChunkCoords()) {
			cityChunks.put(chunkLong, city);
		}
		cityList.add(city);
		markDirty();
	}
	
	public DeepCity getCity(int chunkX, int chunkZ) {
		return cityChunks.get(ChunkPos.asLong(chunkX, chunkZ));
	}

	public boolean isChunkUsed(int chunkX, int chunkZ) {
		return isChunkUsed(ChunkPos.asLong(chunkX, chunkZ));
	}

	public boolean isChunkUsed(Long l) {
		return cityChunks.containsKey(l);
	}
	
	public boolean isFarEnough(int x, int z, int minAxisDistance) {
		for(DeepCity c : cityList) {
			BlockPos pos = c.getCenter();
			long distX = Math.abs(x-pos.getX());
			long distZ = Math.abs(z-pos.getZ());
			if(distX < minAxisDistance && distZ < minAxisDistance)
				return false;
		}
		return true;
	}
}
