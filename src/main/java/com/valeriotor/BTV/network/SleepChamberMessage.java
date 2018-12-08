package com.valeriotor.BTV.network;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.SleepChamber;
import com.valeriotor.BTV.dreams.DreamHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public class SleepChamberMessage implements IMessage {

	
	public SleepChamberMessage() {}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class SleepChamberMessageHandler implements IMessageHandler<SleepChamberMessage, IMessage>{

		@Override
		public IMessage onMessage(SleepChamberMessage message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			BlockPos pos = new BlockPos((int)player.getPosition().getX(), (int)player.getPosition().getY(), (int)player.getPosition().getZ());
			IBlockState state = player.getServerWorld().getBlockState(pos);
			if(state.getBlock() == BlockRegistry.SleepChamber) {
				IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(player);
				
				// TODO: Check whether sleep attempt was successful, only activate dreams if it was
				// TODO: Check whether the player has slept already during the current day
				DreamHandler.chooseDream(player, k);
				ejectPlayer(player.getEntityWorld(), pos, state, player);
			}
			return null;
		}
		
		public void ejectPlayer(World w, BlockPos pos, IBlockState state, EntityPlayer p) {
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
