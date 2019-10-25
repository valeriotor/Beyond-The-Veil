package com.valeriotor.BTV.lib.commands;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;

public class SetPlayerData extends CommandBase{

	@Override
	public String getName() {
		return "setplayerdata";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "setPlayerData";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 3) {
			sender.sendMessage(new TextComponentString("Not enough arguments"));
			return;
		}
		boolean remove = false;
		if(args[1].toLowerCase().equals("add") || args[1].toLowerCase().equals("set")) remove = false;
		else if(args[1].toLowerCase().equals("remove")) remove = true;
		else {
			sender.sendMessage(new TextComponentString("Check command syntax"));
			return;
		}
		PlayerList list = server.getPlayerList();
		EntityPlayer p = list.getPlayerByUsername(args[0]);
		if(p == null) {
			sender.sendMessage(new TextComponentString(String.format("Player %s could not be found", args[0])));
			return;
		}
		if(args.length > 3)
		for(char c : args[3].toCharArray()) {
			if(!Character.isDigit(c)) {
				sender.sendMessage(new TextComponentString(String.format("%s is not an integer!", args[3])));
				return;
			}
		}
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(args.length == 3) {
			if(!remove) {
				if(data.getString(args[2])) sender.sendMessage(new TextComponentString("The player already has that string"));
				else data.addString(args[2], false);
			} else {
				if(!data.getString(args[2])) sender.sendMessage(new TextComponentString("The player doesn't have that string"));
				else data.removeString(args[2]);
			}
		}else if(args.length > 3) {
			if(!remove)
				data.setInteger(args[2], Integer.valueOf(args[3]), false);
			else data.removeInteger(args[2]);
		}
			
		SyncUtil.syncCapabilityData(p);
		
	}

}
