package com.valeriotor.BTV.dreaming.dreams;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamSentience extends Dream{

	public DreamSentience(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		pos = w.findNearestStructure("Village", pos, false);
		if(pos != null) p.sendMessage(new TextComponentTranslation("dreams.villagesearch.success", new Object[] {pos.getX(), pos.getZ()}));
		else p.sendMessage(new TextComponentTranslation("dreams.villagesearch.fail"));
		
		return true;
	}

}
