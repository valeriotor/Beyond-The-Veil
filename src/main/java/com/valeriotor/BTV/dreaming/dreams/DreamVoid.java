package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class DreamVoid extends AbstractDream{

	public DreamVoid(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
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
