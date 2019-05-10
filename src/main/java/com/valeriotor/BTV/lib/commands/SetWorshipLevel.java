package com.valeriotor.BTV.lib.commands;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataHandler;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDataToClient;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;

public class SetWorshipLevel extends CommandBase{

	@Override
	public String getName() {
		return "setworshiplevel";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "setLevelBTV";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 2) {
			sender.sendMessage(new TextComponentString("Not enough arguments"));
			return;
		}
		PlayerList p = server.getPlayerList();
		if(p.getPlayerByUsername(args[0]) == null) {
			sender.sendMessage(new TextComponentString(String.format("Player %s could not be found", args[0])));
			return;
		}
		for(char c : args[1].toCharArray()) {
			if(!Character.isDigit(c)) {
				sender.sendMessage(new TextComponentString(String.format("%s is not an integer!", args[1])));
				return;
			}
		}
		
		Deities.GREATDREAMER.cap(p.getPlayerByUsername(args[0])).setLevel(Integer.valueOf(args[1]));
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncDataToClient("level", Integer.valueOf(args[1])), p.getPlayerByUsername(args[0]));
	}
	
}
