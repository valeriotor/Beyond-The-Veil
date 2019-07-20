package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;

public class DreamVacuos extends AbstractDream{

	public DreamVacuos(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w, IPlayerKnowledge k) {
		if(k.isResearchKnown("SLEEPCHAMBER") && k.isResearchKnown("HUMANDREAMS")) {
			if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("vacuos")) {
				p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("vacuos", true);
				BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("vacuos"), (EntityPlayerMP)p);
				return true;
			} else
				return false;
		}
		return false;
	}

}
