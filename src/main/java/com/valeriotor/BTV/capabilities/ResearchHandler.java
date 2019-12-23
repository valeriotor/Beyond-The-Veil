package com.valeriotor.BTV.capabilities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.Research;
import com.valeriotor.BTV.research.ResearchRegistry;
import com.valeriotor.BTV.research.ResearchStatus;

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
public class ResearchHandler {
	
public static final ResourceLocation RESEARCH = new ResourceLocation(References.MODID, "research");
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if((event.getObject() instanceof EntityPlayer)) event.addCapability(RESEARCH, new ResearchProvider());
		
	}
	
	public static class ResearchStorage implements Capability.IStorage<IResearch>{

		@Override
		public NBTBase writeNBT(Capability<IResearch> capability, IResearch instance, EnumFacing side) {
			final NBTTagCompound tag = new NBTTagCompound();
			
			HashMap<String, ResearchStatus> researches = instance.getResearches();
			
			for(Entry<String, ResearchStatus> entry : researches.entrySet()) {
				tag.setTag(entry.getKey(), entry.getValue().writeToNBT(new NBTTagCompound()));				
			}
			
			return tag;
		}

		@Override
		public void readNBT(Capability<IResearch> capability, IResearch instance, EnumFacing side, NBTBase nbt) {
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			
			for(Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
				if(tag.hasKey(entry.getKey()))
					instance.addResearchStatus(new ResearchStatus(entry.getValue()).readFromNBT(tag.getCompoundTag(entry.getKey())));
				else
					instance.addResearchStatus(new ResearchStatus(entry.getValue()));
			}
		}
		
	}
	
	public static class ResearchData implements IResearch {
		private HashMap<String, ResearchStatus> researches = new HashMap<>();

		@Override
		public void addResearchStatus(ResearchStatus status) {
			researches.put(status.res.getKey(), status);
		}

		@Override
		public HashMap<String, ResearchStatus> getResearches() {
			return this.researches;
		}

		@Override
		public ResearchStatus getResearch(String key) {
			return this.researches.get(key);
		}

		@Override
		public void populate() {
			for(Entry<String, Research> entry : ResearchRegistry.researches.entrySet()) {
				if(!researches.containsKey(entry.getKey()))
					researches.put(entry.getKey(), new ResearchStatus(entry.getValue()));
			}			
		}
		
		
	}
	
	
	public static class ResearchCapFactory implements Callable<IResearch> {
		
		public ResearchCapFactory() {}
		
		@Override
		public IResearch call() throws Exception {
			return new ResearchData();
		}
		
	}
	
}
