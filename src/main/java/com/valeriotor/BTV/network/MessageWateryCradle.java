package com.valeriotor.BTV.network;

import java.util.List;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.tileEntities.TileWateryCradle;
import com.valeriotor.BTV.tileEntities.TileWateryCradle.PatientStatus;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWateryCradle implements IMessage{
	
	public byte option = 0;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	
	public MessageWateryCradle() {}
	
	public MessageWateryCradle(byte option, int x, int y, int z) {
		this.option = option;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.option = buf.readByte();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(option);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
	
	public static class WateryCradleMessageHandler implements IMessageHandler<MessageWateryCradle, IMessage>{

		@Override
		public IMessage onMessage(MessageWateryCradle message, MessageContext ctx) {
			BlockPos pos = new BlockPos(message.x, message.y, message.z);
			EntityPlayer p = ctx.getServerHandler().player;
			World w = p.world;
			TileWateryCradle te = (TileWateryCradle) w.getTileEntity(pos);
			if(te != null) {
				PatientStatus status = te.getPatientStatus();
				SoundEvent sound = null;
				switch(message.option) {
				case 0: status = status.withSpineless(true);
						p.addItemStackToInventory(new ItemStack(ItemRegistry.spine));
						sound = BTVSounds.spineRip;
						break;
				case 1: status = status.withFilledBrain(true);
						break;
				case 3: status = status.withHeartless(true);
						p.addItemStackToInventory(new ItemStack(ItemRegistry.heart));
						sound = BTVSounds.heartRip;
						break;
				}
				
				w.playSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundCategory.PLAYERS, 1, 1, false);
				te.setPatient(status);
				List<EntityPlayerMP> players = w.getPlayers(EntityPlayerMP.class, player -> player.getDistanceSq(pos) < 100);
				if(sound != null)
					for(EntityPlayerMP player : players) 
						BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(sound), pos.toLong()), player);
			}
			return null;
		}
		
	}

}
	
