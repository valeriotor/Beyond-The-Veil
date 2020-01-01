package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class DreamVoid extends Dream{

	public DreamVoid(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID)) {
			SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.VOID);
			return true;
		} else
			return false;
	}

}
