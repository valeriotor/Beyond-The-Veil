package com.valeriotor.beyondtheveil.lib.commands;

import com.valeriotor.beyondtheveil.util.Teleport;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class ChangeDimension extends CommandBase{

	@Override
	public String getName() {
		return "btvtpdim";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "btvtpdim";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1 || !(sender instanceof EntityPlayer)) {
			System.out.println("ERROR IN TPDIM COMMAND");
			return;
		}
		int i = Integer.parseInt(args[0]);
		EntityPlayer p = (EntityPlayer)sender;
		p.changeDimension(i, new Teleport(server.getWorld(i), p.posX, p.posY, p.posZ));
	}
}
