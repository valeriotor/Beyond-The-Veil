package com.valeriotor.BTV.network;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.BlockSleepChamber;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.worship.Deities;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSleepChamber implements IMessage {

	boolean doesDream = false;
	public MessageSleepChamber() {}
	
	public MessageSleepChamber(boolean dreams) {
		this.doesDream = dreams;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.doesDream = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.doesDream);
	}
	
	public static class SleepChamberMessageHandler implements IMessageHandler<MessageSleepChamber, IMessage>{

		@Override
		public IMessage onMessage(MessageSleepChamber message, MessageContext ctx) {
			
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				BlockPos pos = new BlockPos((int)player.getPosition().getX(), (int)player.getPosition().getY(), (int)player.getPosition().getZ());
				IBlockState state = player.getServerWorld().getBlockState(pos);
				
				if(state.getBlock() instanceof BlockSleepChamber) {
					int multiplier = 0;
					boolean advanced = false;
					if(state.getBlock() == BlockRegistry.SleepChamber) multiplier = 1;
					else if(state.getBlock() == BlockRegistry.SleepChamberAdvanced) {
						multiplier = 2;
						advanced = true;
					}
					
					int times = player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.TIMESDREAMT, 0, false);
					int level = Deities.GREATDREAMER.cap(player).getLevel()/2 + 1; // For when I start working on worship
					if(message.doesDream && times < multiplier) {
						if(advanced) DreamHandler.chooseDream(player, 2);
						else DreamHandler.chooseDream(player, 1);
					} else {
						BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiEmpty), player);
					}
				}
			});
			return null;
		}		
		
	}
	
}
