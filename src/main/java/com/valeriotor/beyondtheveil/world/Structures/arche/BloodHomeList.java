package com.valeriotor.beyondtheveil.world.Structures.arche;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.beyondtheveil.world.biomes.BiomeRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class BloodHomeList extends WorldSavedData{
	
	private World world;
	private static final String DATA_NAME = "BLOODHOMEDATA";
	private Map<UUID, BlockPos> BHPos = new HashMap<UUID, BlockPos>();
	
	public static BloodHomeList get(World w) {
		MapStorage storage = w.getPerWorldStorage();
		BloodHomeList data = (BloodHomeList) storage.getOrLoadData(BloodHomeList.class, DATA_NAME);
		if(data == null) {
			data = new BloodHomeList(DATA_NAME);
			storage.setData(DATA_NAME, data);
		}
		data.world = w;
		return data;
	}
	
	private BloodHomeList(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(String s : nbt.getKeySet()) {
			BHPos.put(UUID.fromString(s), BlockPos.fromLong(nbt.getLong(s)));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(Entry<UUID, BlockPos> entry : BHPos.entrySet()) {
			compound.setLong(entry.getKey().toString(), entry.getValue().toLong());
		}
		return compound;
	}
	
	public BlockPos findPlayerHome(EntityPlayer p) {
		UUID id = p.getPersistentID();
		if(BHPos.containsKey(id)) {
			return BHPos.get(id);
		}
		final int x = ((id.hashCode() & 255) - 127)*1024;
		final int z = (((id.hashCode() >> 8) & 255) - 127) * 1024;
		MutableBlockPos pos = new MutableBlockPos(x, 129, z);
		BlockPos lastResort = null;
		final int MAX_X = 127 * 1024;
		final int MAX_Z = 127 * 1024;
		for(int i = 0; i < 400; i++) {
			if(world.getBiome(pos) == BiomeRegistry.arche_plains) {
				if(!BHPos.containsValue(pos)) {
					BHPos.put(id, pos);
					return pos;
				} else {
					lastResort = pos;
				}
			}
			pos.move(EnumFacing.SOUTH, 1024);
			if(pos.getX() > MAX_X) {
				pos.setPos(-MAX_X+1024, 129, pos.getZ()+1024);
				if(pos.getZ() > MAX_Z) 
					pos.move(EnumFacing.WEST, MAX_Z*2);
			} 
		}
		BHPos.put(id, pos);
		return pos;
	}

}
