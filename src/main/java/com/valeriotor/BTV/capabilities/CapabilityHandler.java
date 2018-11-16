package com.valeriotor.BTV.capabilities;

import java.util.concurrent.Callable;

import com.valeriotor.BTV.lib.References;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class CapabilityHandler {
	public static final ResourceLocation LEVEL_CAP = new ResourceLocation(References.MODID, "level");
	
	
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if((event.getObject() instanceof EntityPlayer)) event.addCapability(LEVEL_CAP, new DGProvider());
		
	}
	
	public static class DGStorage implements Capability.IStorage<IWorship>{

		@Override
		public NBTTagCompound writeNBT(Capability<IWorship> capability, IWorship instance, EnumFacing side) {
			final NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("level", instance.getLevel());
			return tag;
			
		}

		@Override
		public void readNBT(Capability<IWorship> capability, IWorship instance, EnumFacing side, NBTBase nbt) {
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.setLevel(tag.getInteger("level"));
			
		}
		
		
		

	}
	
	public static class Factory implements Callable<IWorship>{

		public Factory() {
			
		}
		
		@Override
		public IWorship call() throws Exception {
			return new CapabilityHandler.DreamingGod();
		}
	}
	
	public static class DreamingGod implements IWorship{
		private int level = 0;

		@Override
		public void setLevel(int lv) {
			this.level = lv;
			
		}

		@Override
		public int getLevel() {
			return this.level;
		}

		@Override
		public void addLevel() {
			this.level++;
			
		}

		@Override
		public void removeLevel() {
			this.level--;
			
		}

		
		
		
	}
	
	
}


