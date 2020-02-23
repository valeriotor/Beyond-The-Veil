package com.valeriotor.BTV.dreaming.dreams;

import java.util.List;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class DreamHuman extends Dream{

	public DreamHuman(String name, int priority) {
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
		int attack = DreamHandler.getDreamAttack(p, target);
		if(targeted) attack++;
		if(attack == 0)
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.success", w.rand.nextBoolean() ? target.getPosition().getX() : target.getPosition().getZ()));
		else if(attack == 1)
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.success2", new Object[] {target.getPosition().getX(), target.getPosition().getZ()}));
		else
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.success3", new Object[] {target.getPosition().getX(), target.getPosition().getY(), target.getPosition().getZ()}));
		if(attack >= 2 || targeted) 
			p.sendMessage(new TextComponentTranslation("dreams.playersearch.name", target.getName()));
		return true;
	
	}
	

}
