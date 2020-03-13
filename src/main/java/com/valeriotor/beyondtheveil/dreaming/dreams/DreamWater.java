package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamWater extends Dream{

	public DreamWater(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		pos = w.findNearestStructure("Monument", pos, false);
		if(pos != null) {
			p.sendMessage(new TextComponentTranslation("dreams.monumentsearch.success", new Object[] {pos.getX(), pos.getZ()}));
			return true;
		}
		else p.sendMessage(new TextComponentTranslation("dreams.monumentsearch.fail"));
		
		return false;
	}

}
