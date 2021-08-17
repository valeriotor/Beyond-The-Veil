package com.valeriotor.beyondtheveil.capabilities;

import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MirrorHandler {

	public static final ResourceLocation MIRROR = new ResourceLocation(References.MODID, "mirror");
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if((event.getObject() instanceof EntityPlayer)) event.addCapability(MIRROR, new MirrorProvider());
	}
	
	public static class MirrorStorage implements IStorage<MirrorCapInstance>{

		@Override
		public NBTBase writeNBT(Capability<MirrorCapInstance> capability, MirrorCapInstance instance, EnumFacing side) {
			NBTTagCompound tag = new NBTTagCompound();
			instance.writeToNBT(tag);
			return tag;
		}

		@Override
		public void readNBT(Capability<MirrorCapInstance> capability, MirrorCapInstance instance, EnumFacing side,
				NBTBase nbt) {
			instance.readFromNBT((NBTTagCompound)nbt);
		}
		
	}
	
}
