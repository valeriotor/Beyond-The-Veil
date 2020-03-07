package com.valeriotor.BTV.capabilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

import com.valeriotor.BTV.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerDataHandler {
	public static final ResourceLocation FLAG_CAP = new ResourceLocation(References.MODID, "flags");
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if((event.getObject() instanceof EntityPlayer)) event.addCapability(FLAG_CAP, new PlayerDataProvider());
		
	}
	
	
	public static class DataStorage implements Capability.IStorage<IPlayerData>{

		@Override
		public NBTTagCompound writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
			final NBTTagCompound tag = new NBTTagCompound();
			
			HashMap<String, Integer> ints = instance.getInts(false);
			HashMap<String, Long> longs = instance.getLongs();
			HashMap<String, String> keys = instance.getKeyedStrings();
			Set<String> strings = instance.getStrings(false);
			
			NBTTagCompound intTag = new NBTTagCompound();
			for(Entry<String, Integer> entry : ints.entrySet()) {
				intTag.setInteger(entry.getKey(), entry.getValue());
			}
			tag.setTag("ints", intTag);
			
			NBTTagCompound stringTag = new NBTTagCompound();
			for(String string : strings) {
				stringTag.setBoolean(string, true);
			}
			tag.setTag("strings", stringTag);
			
			NBTTagCompound longTag = new NBTTagCompound();
			for(Entry<String, Long> entry : longs.entrySet()) {
				longTag.setLong(entry.getKey(), entry.getValue());
			}
			tag.setTag("longs", longTag);
			
			NBTTagCompound keyTag = new NBTTagCompound();
			for(Entry<String, String> entry : keys.entrySet()) {
				keyTag.setString(entry.getKey(), entry.getValue());
			}
			tag.setTag("keys", keyTag);
			return tag;
			
		}

		@Override
		public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			
			if(tag.hasKey("strings")) {
				NBTTagCompound stringTag = (NBTTagCompound) tag.getTag("strings");
				for(String string : stringTag.getKeySet()) {
					instance.addString(string, false);
				}
			}	
			if(tag.hasKey("ints")) {
				NBTTagCompound intTag = (NBTTagCompound) tag.getTag("ints");
				for(String string : intTag.getKeySet()) {
					Integer a = intTag.getInteger(string);
					if(a != null)
						instance.setInteger(string, a, false);
				}
			}
			if(tag.hasKey("longs")) {
				NBTTagCompound longTag = (NBTTagCompound) tag.getTag("longs");
				for(String string: longTag.getKeySet()) {
					Long a = longTag.getLong(string);
					if(a != null)
						instance.setLong(string, a);
				}
			}
			if(tag.hasKey("keys")) {
				NBTTagCompound keyTag = (NBTTagCompound) tag.getTag("keys");
				for(String key : keyTag.getKeySet()) {
					String value = keyTag.getString(key);
					if(value != null)
						instance.addKeyedString(key, value);
				}
			}
			
		}
		
	}
	
	public static class Factory implements Callable<IPlayerData>{

		public Factory() {
			
		}
		
		@Override
		public IPlayerData call() throws Exception {
			return new PlayerDataHandler.PlayerData();
		}
	}
	
	public static class PlayerData implements IPlayerData{
		
		public Set<String> strings = new HashSet<>();
		public HashMap<String, Integer> ints = new HashMap<>();
		public Set<String> tempStrings = new HashSet<>();
		public HashMap<String, Integer> tempInts = new HashMap<>();
		public HashMap<String, Long> longs = new HashMap<>();
		public HashMap<String, String> keyedStrings = new HashMap<>();
		

		@Override
		public void addString(String string, boolean temporary) {
			if(!temporary) strings.add(string);
			else tempStrings.add(string);
		}

		@Override
		public void removeString(String string) {
			if(strings.contains(string))
				strings.remove(string);
			if(tempStrings.contains(string))
				tempStrings.remove(string);
		}
		
		@Override
		public void removeAllStrings() {
			tempStrings.clear();
			strings.clear();
		}

		@Override
		public void setInteger(String key, int value, boolean temporary) {
			if(!temporary) ints.put(key, Integer.valueOf(value));
			else tempInts.put(key, Integer.valueOf(value));
		}
		
		@Override
		public Integer incrementOrSetInteger(String key, int amount, int value, boolean temporary) {
			Integer currentValue = getInteger(key);
			if(currentValue == null) {
				setInteger(key, value, temporary);
				return value;
			}else {
				currentValue += amount;
				setInteger(key, currentValue, temporary);
				return currentValue;
			}
		}

		@Override
		public void removeInteger(String key) {
			if(ints.containsKey(key))
				ints.remove(key);
			if(tempInts.containsKey(key))
				tempInts.remove(key);
		}

		@Override
		public boolean getString(String string) {
			if(string == null) return true;
			if(strings.contains(string) || tempStrings.contains(string)) return true;
			return false;
		}

		@Override
		public Integer getInteger(String key) {
			if(ints.containsKey(key)) return ints.get(key);
			if(tempInts.containsKey(key)) return tempInts.get(key);
			
			return null;
		}
		
		@Override
		public Integer getOrSetInteger(String key, int value, boolean temporary) {
			Integer a = getInteger(key);
			if(a != null) return a;
			setInteger(key, value, temporary);
			return value;
		}

		@Override
		public HashMap<String, Integer> getInts(boolean temporary) {
			if(temporary) return tempInts;
			else return ints;
		}

		@Override
		public Set<String> getStrings(boolean temporary) {
			if(temporary) return tempStrings;
			else return strings;
		}

		@Override
		public void setLong(String key, long value) {
			longs.put(key, value);
		}

		@Override
		public Long removeLong(String key) {
			if(!longs.containsKey(key))
			return null;
			return longs.remove(key);
		}

		@Override
		public Long getLong(String key) {
			return longs.get(key);
		}

		@Override
		public Long getOrSetLong(String key, long value) {
			if(longs.containsKey(key))
				return longs.get(key);
			longs.put(key, value);
			return value;
		}

		@Override
		public HashMap<String, Long> getLongs() {
			return longs;
		}

		@Override
		public void addKeyedString(String key, String value) {
			keyedStrings.put(key, value);
		}

		@Override
		public String getKeyedString(String key) {
			return keyedStrings.get(key);
		}

		@Override
		public String getOrSetKeyedString(String key, String value) {
			if(keyedStrings.containsKey(key))
				return keyedStrings.get(key);
			keyedStrings.put(key, value);
			return value;
		}

		@Override
		public HashMap<String, String> getKeyedStrings() {
			return keyedStrings;
		}

		@Override
		public boolean hasKeyedString(String key) {
			return keyedStrings.containsKey(key);
		}

		@Override
		public void removeAllInts() {
			tempInts.clear();
			ints.clear();
		}
		
		
	}
}
