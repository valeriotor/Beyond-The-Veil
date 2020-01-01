package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.util.WorldHelper;
import com.valeriotor.BTV.world.BiomeRegistry;
import com.valeriotor.BTV.world.HamletList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamDarkness extends Dream{

	public DreamDarkness(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		boolean flag = false;
		BlockPos pos = WorldHelper.findClosestBiomeOfType(BiomeRegistry.innsmouth, p.getPosition(), w, 6000);
		if(pos != null) {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.innsmouth", new Object[] {pos.getX(), pos.getZ()}));			
			flag = true;
		} else {
			p.sendMessage(new TextComponentTranslation("dreams.biomesearch.fail"));
		}
		
		if(DreamHandler.hasDreamtOfVoid(p)) {
			BlockPos hamletPos = HamletList.get(w).getClosestHamlet(p.getPosition());
			if(hamletPos != null) {
				p.sendMessage(new TextComponentTranslation("dreams.biomesearch.hamlet", new Object[] {Integer.valueOf(hamletPos.getX()), Integer.valueOf(hamletPos.getZ())}));
				flag = true;
			}
		}
		
		return flag;
	}

}
