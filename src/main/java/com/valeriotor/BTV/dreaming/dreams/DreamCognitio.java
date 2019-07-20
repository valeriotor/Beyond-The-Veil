package com.valeriotor.BTV.dreaming.dreams;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.IPlayerKnowledge;

public class DreamCognitio extends AbstractDream{

	public DreamCognitio(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w, IPlayerKnowledge k) {
		BlockPos pos = w.findNearestStructure("Village", p.getPosition(), false);
		if(pos != null) p.sendMessage(new TextComponentTranslation("dreams.villagesearch.success", new Object[] {pos.getX(), pos.getZ()}));
		else p.sendMessage(new TextComponentTranslation("dreams.villagesearch.fail"));
		
		return true;
	}

}
