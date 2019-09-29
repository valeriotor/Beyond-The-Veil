package com.valeriotor.BTV.events.special;

import java.util.HashMap;
import java.util.Map.Entry;

import com.valeriotor.BTV.worship.DrowningRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DrowningRitualEvents {
	
	public static final HashMap<EntityPlayer, DrowningRitual> rituals = new HashMap<>();
	
	public static void checkRitual(EntityPlayer p) {
		if(rituals.containsKey(p)) return;
		World w = p.world;
		BlockPos pos = p.getPosition();
		for(int y = 0; y < 5; y++) {
			if(w.getBlockState(pos.offset(EnumFacing.UP, y)).getBlock() != Blocks.WATER) return;
			for(EnumFacing facing : EnumFacing.HORIZONTALS) {
				if(!w.getBlockState(pos.offset(facing).offset(EnumFacing.UP, y)).isFullBlock()) return;
			}
		}
		rituals.put(p, new DrowningRitual(p));
		p.world.setBlockState(p.getPosition().offset(EnumFacing.UP, 4), Blocks.PACKED_ICE.getDefaultState());
	}
	
	public static void update() {
		for(Entry<EntityPlayer, DrowningRitual> entry : rituals.entrySet()) {
			entry.getValue().update();
		}
	}
	
}
