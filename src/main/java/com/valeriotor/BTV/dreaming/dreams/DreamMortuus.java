package com.valeriotor.BTV.dreaming.dreams;

import java.util.List;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.lib.PlayerDataLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;

public class DreamMortuus extends AbstractDream{

	public DreamMortuus(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w, IPlayerKnowledge k) {
		if(!DreamHandler.youDontHaveLevel(p, 2)) return false;
		List<EntityPlayerMP> list = DreamHandler.copyPlayerList(w.getMinecraftServer().getPlayerList().getPlayers(), (EntityPlayerMP)p);
		int lvl = DreamHandler.getDreamingGodLevel(p);
		if(list.isEmpty()) p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
		for(int i = 0; i < lvl-1 && !list.isEmpty(); i++) {
			int index = w.rand.nextInt(list.size());
			EntityPlayer target = list.get(index);
			if(DreamHandler.getDreamAttack(p, target) >= 0) {
				IPlayerData cap = target.getCapability(PlayerDataProvider.PLAYERDATA, null);
				Integer x = cap.getInteger(PlayerDataLib.DEATH_X);
				Integer y = cap.getInteger(PlayerDataLib.DEATH_Y);
				Integer z = cap.getInteger(PlayerDataLib.DEATH_Z);
				if(x != null && y != null && z != null) {
					p.sendMessage(new TextComponentTranslation("dreams.deathsearch.found", new Object[] {target.getName(), x, y, z}));
				} else {
					p.sendMessage(new TextComponentTranslation("dreams.deathsearch.notrecently", new Object[] {target.getName()}));
				}
			} else {
				p.sendMessage(new TextComponentTranslation("dreams.deathsearch.toostrong", new Object[] {target.getName()}));
			}
			list.remove(index);
		}
		return true;
	}

}
