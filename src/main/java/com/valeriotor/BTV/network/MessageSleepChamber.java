package com.valeriotor.BTV.network;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.SleepChamber;
import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.FlagProvider;
import com.valeriotor.BTV.dreams.DreamHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

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
			BlockPos pos = new BlockPos((int)player.getPosition().getX(), (int)player.getPosition().getY(), (int)player.getPosition().getZ());
			IBlockState state = player.getServerWorld().getBlockState(pos);
			
			if(state.getBlock() instanceof SleepChamber) {
				int multiplier = 0;
				boolean advanced = false;
				if(state.getBlock() == BlockRegistry.SleepChamber) multiplier = 1;
				else if(state.getBlock() == BlockRegistry.SleepChamberAdvanced) {
					multiplier = 2;
					advanced = true;
				}
				IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(player);
				
				int times = player.getCapability(FlagProvider.FLAG_CAP, null).getTimesDreamt();
				int level = player.getCapability(DGProvider.LEVEL_CAP, null).getLevel()/2 + 1; // For when I start working on worship
				
				if(message.doesDream && times < level * multiplier) {
					if(advanced) DreamHandler.chooseDream(player, k, 2);
					else DreamHandler.chooseDream(player, k, 1);
				}
				
				player.sendMessage(new TextComponentString("You have dreamt " + times + " times today")); // DEBUG. REMOVE ON RELEASE (heh. "Release". haha)
				ejectPlayer(player.getEntityWorld(), pos, state, player);
			}
			return null;
		}
		
		public void ejectPlayer(World w, BlockPos pos, IBlockState state, EntityPlayerMP p) {
			BlockPos ejectPos;
			
			for(EnumFacing facing : EnumFacing.HORIZONTALS) {
				if(w.getBlockState(pos.offset(facing)).getBlock() == Blocks.AIR &&
				   w.getBlockState(pos.offset(facing).up()).getBlock() == Blocks.AIR) {
					ejectPos = pos.offset(facing);
					
					// The position needs to be slightly offset for 3 out of 4 EnumFacing values, lest minor visual bugs be shown
					double offsetX = facing == EnumFacing.EAST ? 0.6 : (facing == EnumFacing.WEST ? 0.4 : 0.5);
					p.setPositionAndUpdate(ejectPos.getX() + offsetX, ejectPos.getY(), ejectPos.getZ() + (facing == EnumFacing.SOUTH ? 0.6 : 0.5));
					return;
				}
			}
			
			for(EnumFacing facing : EnumFacing.HORIZONTALS) {
				if(w.getBlockState(pos.offset(facing).offset(facing.rotateYCCW())).getBlock() == Blocks.AIR &&
				   w.getBlockState(pos.offset(facing).offset(facing.rotateYCCW()).up()).getBlock() == Blocks.AIR) {
					ejectPos = pos.offset(facing).offset(facing.rotateYCCW());
					p.setPositionAndUpdate(ejectPos.getX()+0.5, ejectPos.getY(), ejectPos.getZ()+0.5);
					return;
				}
			}
			
			
		}
		
		
	}
	
}
