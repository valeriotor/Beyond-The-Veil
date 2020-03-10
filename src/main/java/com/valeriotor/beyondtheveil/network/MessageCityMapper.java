package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.tileEntities.TileCityMapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemHandlerHelper;

public class MessageCityMapper implements IMessage{
	
	public static final byte RESET_MAP = 0;
	public static final byte RESET_PLAYER = 1;
	public static final byte UPDATE_BUILDINGS = 2;
	public static final byte CREATE_MAP = 3;
	
	public byte mode;
	public BlockPos pos;
	public NBTTagCompound nbt = new NBTTagCompound();
	
	public MessageCityMapper() {}
	public MessageCityMapper(byte mode, BlockPos pos) {
		this.mode = mode;
		this.pos = pos;
	}
	public MessageCityMapper(byte mode, BlockPos pos, NBTTagCompound nbt) {
		this.mode = mode;
		this.pos = pos;
		this.nbt = nbt;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.mode = buf.readByte();
		this.pos = BlockPos.fromLong(buf.readLong());
		if(this.mode == 2)
			this.nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(mode);
		buf.writeLong(this.pos.toLong());
		if(this.mode == 2)
			ByteBufUtils.writeTag(buf, nbt);
	}
	
	public static class CityMapperMessageHandler implements IMessageHandler<MessageCityMapper, IMessage>{

		@Override
		public IMessage onMessage(MessageCityMapper message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.world.getTileEntity(message.pos);
			if(!(te instanceof TileCityMapper)) return null;
			TileCityMapper tc = (TileCityMapper) te;
			if(message.mode == 0) {
				tc.viewingPlayer = null;
				tc.timer = -100;
			}else if(message.mode == 1) {
				tc.viewingPlayer = null;
			}else if(message.mode == 2) {
				tc.readBuildingsFromNBT(message.nbt);
			}else if(message.mode == 3) {
				if(tc.buildings.size() > 0) {
					ItemStack stack = tc.create();
					ItemHandlerHelper.giveItemToPlayer(ctx.getServerHandler().player, stack);
				}
			}
			tc.sendSmallUpdates();
			return null;
		}
		
	}

}
