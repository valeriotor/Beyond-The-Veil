package com.valeriotor.BTV.dreaming.dreams;

import java.util.List;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamTool extends Dream{

	public DreamTool(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		List<EntityPlayerMP> list = DreamHandler.copyPlayerList(w.getMinecraftServer().getPlayerList().getPlayers(), (EntityPlayerMP)p);
		if(!list.isEmpty()) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			ItemStack stack = target.getHeldItemMainhand();
			if(stack != null && !stack.getDisplayName().equals("Air")) {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.success", stack.getDisplayName()));
			}else {
				p.sendMessage(new TextComponentTranslation("dreams.playeritem.mildsuccess"));
			}
			int attack = DreamHandler.getDreamAttack(p, target);
			if(attack >= 1) {
				String name = target.getName();
				if(attack == 1)
					p.sendMessage(new TextComponentTranslation("dreams.playeritem.firstletter", name.substring(0, 1)));
				else
					p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", name));		
			}
			return true;
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
			return false;
		}
	}

}
