package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.player.EntityPlayer;

public class TransformDeepOne implements IActivePower{
	
	private TransformDeepOne() {}
	private static final TransformDeepOne INSTANCE = new TransformDeepOne();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(!DGWorshipHelper.canTransform(p)) return false;
		boolean transformed = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED);
		if(transformed) p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString(PlayerDataLib.TRANSFORMED);
		else p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(PlayerDataLib.TRANSFORMED, false);
		for(EntityPlayer player : p.getServer().getPlayerList().getPlayers()) {
			SyncUtil.syncTransformData(player);
		}
		return true;
	}

	@Override
	public int getCooldownTicks() {
		return 300;
	}

	@Override
	public Deities getDeity() {
		return Deities.GREATDREAMER;
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public boolean hasRequirement(EntityPlayer p) {
		return DGWorshipHelper.canTransform(p);
	}
	

}
