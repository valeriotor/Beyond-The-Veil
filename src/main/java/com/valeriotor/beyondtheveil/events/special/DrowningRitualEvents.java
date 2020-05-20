package com.valeriotor.beyondtheveil.events.special;

import java.util.HashMap;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.worship.DrowningRitual;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DrowningRitualEvents {
	
	public static final HashMap<UUID, DrowningRitual> rituals = new HashMap<>();
	
	public static void checkRitual(EntityPlayer p) {
		if(rituals.containsKey(p.getPersistentID())) return;
		World w = p.world;
		BlockPos pos = p.getPosition();
		for(int y = 0; y < 5; y++) {
			if(!w.getBlockState(pos.offset(EnumFacing.UP, y)).equals(Blocks.WATER.getDefaultState())) return;
			for(EnumFacing facing : EnumFacing.HORIZONTALS) {
				if(!w.getBlockState(pos.offset(facing).offset(EnumFacing.UP, y)).isFullBlock()) return;
			}
		}
		rituals.put(p.getPersistentID(), new DrowningRitual(p));
		p.world.setBlockState(p.getPosition().offset(EnumFacing.UP, 4), Blocks.PACKED_ICE.getDefaultState());
	}
	
	public static void preventDamage(LivingHurtEvent event) {
		if(rituals.containsKey(((EntityPlayer)event.getEntityLiving()).getPersistentID())) event.setCanceled(true);
	}
	
	public static void update() {
		for(Entry<UUID, DrowningRitual> entry : rituals.entrySet()) {
			entry.getValue().update();
		}
	}
	
}
