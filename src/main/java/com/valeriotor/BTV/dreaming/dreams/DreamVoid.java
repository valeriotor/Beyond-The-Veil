package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamVoid extends Dream{

	public DreamVoid(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.VOID)) {
			SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.VOID);
			return true;
		} else
			return false;
	}

}
