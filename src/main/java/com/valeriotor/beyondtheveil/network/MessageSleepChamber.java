package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.blocks.BlockSleepChamber;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.events.MemoryUnlocks;
import com.valeriotor.beyondtheveil.gui.Guis;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
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
					if(message.doesDream)
						MemoryUnlocks.sleepChamberEvent(player);
					int multiplier = 0;
					boolean advanced = false;
					if(state.getBlock() == BlockRegistry.SleepChamber) multiplier = 1;
					else if(state.getBlock() == BlockRegistry.SleepChamberAdvanced) {
						multiplier = 2;
						advanced = true;
					}
					IPlayerData data = player.getCapability(PlayerDataProvider.PLAYERDATA, null);
					long currentTime = player.world.getWorldTime(), currentWorldTime = player.world.getTotalWorldTime();
					long lastDream = data.getOrSetLong(PlayerDataLib.LASTDREAMTINDAY, currentTime);
					long lastDreamWorld = data.getOrSetLong(PlayerDataLib.LASTDREAMTINWORLD, currentWorldTime);
					if(currentTime < lastDream || lastDreamWorld - currentWorldTime >= 24000L) {
						data.setLong(PlayerDataLib.LASTDREAMTINDAY, currentTime);
						data.setLong(PlayerDataLib.LASTDREAMTINWORLD, currentWorldTime);
						data.setInteger(PlayerDataLib.TIMESDREAMT, 0, false);
					}
					int times = data.getOrSetInteger(PlayerDataLib.TIMESDREAMT, 0, false);
					int level = DGWorshipHelper.getDreamPower(player)/2 + 1;
					if(message.doesDream && times < multiplier*level) {
						boolean increaseTimesDreamt = false;
						if(advanced) increaseTimesDreamt = DreamHandler.chooseDream(player, 2, false);
						else increaseTimesDreamt = DreamHandler.chooseDream(player, 1, false);
						if(increaseTimesDreamt) {
							times = SyncUtil.incrementIntDataOnServer(player, false, PlayerDataLib.TIMESDREAMT, 1, 1);
							SPacketTitle spackettitle1 = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentTranslation("dreams.timesdreamt", times, multiplier*level));
		                    player.connection.sendPacket(spackettitle1);
						}
					} else {
						BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiEmpty), player);
					}
				}
			});
			return null;
		}		
		
	}
	
}
