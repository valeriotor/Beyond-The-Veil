package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;

import com.valeriotor.beyondtheveil.worship.DOSkill;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSawCleaverToServer implements IMessage{
	
	public MessageSawCleaverToServer() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class SawCleaverToServerMessageHandler implements IMessageHandler<MessageSawCleaverToServer, IMessage> {

		@Override
		public IMessage onMessage(MessageSawCleaverToServer message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				boolean flagCleaver = p.getHeldItemMainhand().getItem() == ItemRegistry.saw_cleaver;
				boolean flagDeepOne = false;
				if(!flagCleaver) {
					IPlayerData data = PlayerDataLib.getCap(p);
					flagDeepOne = (data.getString(PlayerDataLib.TRANSFORMED) && DOSkill.QUICKSTEP.isUnlocked(data));
				}
				if(flagCleaver || flagDeepOne) {
					PlayerTimer pt = ServerTickEvents.getPlayerTimer("CleaverDodge", p);
					if(pt == null && !p.isInWater() && !p.capabilities.isFlying) {
						IBlockState b = p.world.getBlockState(p.getPosition().add(0, -0.2, 0));
							if(b.isSideSolid(p.world, p.getPosition().add(0, -0.2, 0), EnumFacing.UP)) {
								BTVPacketHandler.INSTANCE.sendTo(new MessageSawCleaverToClient(), p);
								PlayerTimerBuilder ptb = new PlayerTimerBuilder(p)
															.setTimer(12)
															.setName("CleaverDodge");
								if(flagCleaver) ptb.addInterrupt(player -> player.getHeldItemMainhand().getItem() != ItemRegistry.saw_cleaver);
								ServerTickEvents.addPlayerTimer(ptb.toPlayerTimer());
								p.getFoodStats().addExhaustion(0.7F);
						}
						 
					}
				}
			});
			return null;
		}
		
		public static void movePlayer(EntityPlayer p, int direction, float multiplier) {
			float mX = (float) -Math.sin(p.rotationYawHead*2*Math.PI/360);
			float mZ = (float) Math.cos(p.rotationYawHead*2*Math.PI/360);
			float tmp = mX;
			switch(direction) {
			case 0: break;
			case 1:	
				mX = mZ;
				mZ = -tmp;
				break;
			case 2:
				mX = -mX;
				mZ = -mZ;
				break;
			case 3:
				mX = -mZ;
				mZ = tmp;
			}
			p.motionX += mX * 1.7 * multiplier;// *(p.isAirBorne ? 1 : 3);
			p.motionZ += mZ * 1.7 * multiplier;// *(p.isAirBorne ? 1 : 3);
		}	
		
	}

}
