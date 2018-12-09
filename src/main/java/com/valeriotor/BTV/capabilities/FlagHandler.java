package com.valeriotor.BTV.capabilities;

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
public class FlagHandler {
	public static final ResourceLocation FLAG_CAP = new ResourceLocation(References.MODID, "flags");
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if((event.getObject() instanceof EntityPlayer)) event.addCapability(FLAG_CAP, new FlagProvider());
		
	}
	
	public static class FlagStorage implements Capability.IStorage<IFlags>{

		@Override
		public NBTTagCompound writeNBT(Capability<IFlags> capability, IFlags instance, EnumFacing side) {
			final NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("timesDreamt", instance.getTimesDreamt());
			return tag;
			
		}

		@Override
		public void readNBT(Capability<IFlags> capability, IFlags instance, EnumFacing side, NBTBase nbt) {
			final NBTTagCompound tag = (NBTTagCompound) nbt;
			instance.setTimesDreamt(tag.getInteger("timesDreamt"));
			
		}
		
	}
	
	public static class Factory implements Callable<IFlags>{

		public Factory() {
			
		}
		
		@Override
		public IFlags call() throws Exception {
			return new FlagHandler.Flags();
		}
	}
	
	public static class Flags implements IFlags{
		
		private int timesDreamt = 0;
		
		@Override
		public void setTimesDreamt(int times) {
			this.timesDreamt = times;
			
		}

		@Override
		public int getTimesDreamt() {
			return this.timesDreamt;
		}
		
		
		
	}
}
