package com.valeriotor.BTV.dreaming.dreams;

import java.util.List;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class DreamHuman extends Dream{

	public DreamHuman(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		List<EntityPlayerMP> list = DreamHandler.copyPlayerList(w.getMinecraftServer().getPlayerList().getPlayers(), (EntityPlayerMP)p);
		if(!list.isEmpty()) {
			EntityPlayerMP target =	list.get(w.rand.nextInt(list.size()));
			DimensionType.getById(target.world.provider.getDimension()).getName();
			int attack = DreamHandler.getDreamAttack(p, target);
			if(attack == 0)
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.success", w.rand.nextBoolean() ? target.getPosition().getX() : target.getPosition().getZ()));
			else if(attack >= 1)
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.success2", new Object[] {target.getPosition().getX(), target.getPosition().getZ()}));
			if(attack >= 2) 
				p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", target.getName()));
			return true;
		}else {
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
			return false;
		}
	}

}
