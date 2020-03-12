package com.valeriotor.beyondtheveil.world;

import java.util.ArrayList;
import java.util.List;

import com.valeriotor.beyondtheveil.blocks.BlockStatue;
import com.valeriotor.beyondtheveil.tileEntities.TileStatue;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class StatueChunkLoader implements OrderedLoadingCallback{

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		for(Ticket t : tickets) {
			int x = t.getModData().getInteger("statueX");
			int y = t.getModData().getInteger("statueY");
			int z = t.getModData().getInteger("statueZ");
			BlockPos pos = new BlockPos(x,y,z);
			TileStatue s = (TileStatue)world.getTileEntity(pos);
			s.forceChunkLoading(t);
			ChunkPos chunk = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
			ForgeChunkManager.forceChunk(t,chunk);
		}
	}

	@Override
	public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
		List<Ticket> validTickets = new ArrayList<>();
		for(Ticket t : tickets) {
			int x = t.getModData().getInteger("statueX");
			int y = t.getModData().getInteger("statueY");
			int z = t.getModData().getInteger("statueZ");
			if(world.getBlockState(new BlockPos(x,y,z)).getBlock() instanceof BlockStatue)
				validTickets.add(t);
		}
		return validTickets;
	}

}
