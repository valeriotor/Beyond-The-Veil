package com.valeriotor.BTV.lib.commands;

import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageReloadResources;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ReloadResources extends CommandBase{

	@Override
	public String getName() {
		return "btvreload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "btvreload";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.getCommandSenderEntity() instanceof EntityPlayerMP)
		BTVPacketHandler.INSTANCE.sendTo(new MessageReloadResources(), (EntityPlayerMP) sender.getCommandSenderEntity());
		
	}

}
