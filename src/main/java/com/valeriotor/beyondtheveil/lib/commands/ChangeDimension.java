package com.valeriotor.beyondtheveil.lib.commands;

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
	
	public static class Teleport extends Teleporter {
		
		private final WorldServer world;
		private final double x;
		private final double y;
		private final double z;
		
		public Teleport(WorldServer worldIn, double posX, double posY, double posZ) {
			super(worldIn);
			this.world = worldIn;
			this.x = posX;
			this.y = posY;
			this.z = posZ;
		}
		
		@Override
		public void placeInPortal(Entity entityIn, float rotationYaw) {
			entityIn.setPosition(x, y, z);
			entityIn.motionX = 0;
			entityIn.motionY = 0;
			entityIn.motionZ = 0;
		}
		
	}

}
