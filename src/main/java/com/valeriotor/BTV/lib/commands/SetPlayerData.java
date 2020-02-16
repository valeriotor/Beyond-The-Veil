package com.valeriotor.BTV.lib.commands;

import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.DGWorshipHelper;

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
		PlayerList list = server.getPlayerList();
		EntityPlayer p = list.getPlayerByUsername(args[0]);
		if(p == null) {
			sender.sendMessage(new TextComponentString(String.format("Player %s could not be found", args[0])));
			return;
		}
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(args[1].toLowerCase().equals("add") || args[1].toLowerCase().equals("set")) remove = false;
		else if(args[1].toLowerCase().equals("remove")) remove = true;
		else if(args[1].toLowerCase().equals("data") && args[2].toLowerCase().equals("print")) {
			this.printData(p, data);
			return;
		}
		else if(args[1].toLowerCase().equals("research")) {
			researchHandler(args, p);
			return;
		} else {
			sender.sendMessage(new TextComponentString("Check command syntax"));
			return;
		}
		
		if(args.length > 3)
		for(char c : args[3].toCharArray()) {
			if(!Character.isDigit(c)) {
				sender.sendMessage(new TextComponentString(String.format("%s is not an integer!", args[3])));
				return;
			}
		}
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
	
	private void researchHandler(String[] args, EntityPlayer p) {
		if(args[2].toLowerCase().equals("reset")) {
			ResearchUtil.resetResearch(p);
		} else if(args[2].toLowerCase().equals("print")) {
			ResearchUtil.printResearch(p);
		} else {
			String key = args[2];
			ResearchUtil.completeResearch(p, key);
		}
		DGWorshipHelper.calculateModifier(p);
	}
	
	private void printData(EntityPlayer p, IPlayerData data) {
		for(String s : data.getStrings(false))
			p.sendMessage(new TextComponentString(s + " (Non temporary)"));
		for(String s : data.getStrings(true))
			p.sendMessage(new TextComponentString(s + " (Temporary)"));
		for(Entry<String, Integer> entry : data.getInts(false).entrySet()) 
			p.sendMessage(new TextComponentString(entry.getKey() + ": " + entry.getValue().toString() + " (Non temporary)"));
		for(Entry<String, Integer> entry : data.getInts(true).entrySet()) 
			p.sendMessage(new TextComponentString(entry.getKey() + ": " + entry.getValue().toString() + " (Temporary)"));
	}

}
