package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.util.List;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamTool extends Dream{

	public DreamTool(String name, int priority) {
		super(name, priority);
	}
	
	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		return this.activatePlayer(p, null, w);
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer p, EntityPlayer target, World w) {
		boolean targeted = true;
		if(target == null) {
			targeted = false;
			List<EntityPlayerMP> list = w.getPlayers(EntityPlayerMP.class, player -> !player.equals(p));
			if(list.isEmpty()) {
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
				return false;
			}
			target = list.get(w.rand.nextInt(list.size()));
		}
		ItemStack stack = target.getHeldItemMainhand();
		if(stack != null && !stack.getDisplayName().equals("Air")) {
			p.sendMessage(new TextComponentTranslation("dreams.playeritem.success", stack.getDisplayName()));
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.playeritem.mildsuccess"));
		}
		int attack = DreamHandler.getDreamAttack(p, target);
		if(attack >= 1 || targeted) {
			String name = target.getName();
			if(attack == 1 && !targeted)
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.firstletter", name.substring(0, 1)));
			else
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", name));		
		}
		return true;
		
	}

}
