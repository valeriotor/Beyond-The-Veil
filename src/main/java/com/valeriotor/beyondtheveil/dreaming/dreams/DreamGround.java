package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamGround extends Dream{
	
	private final IBlockState state1;
	private final IBlockState state2;
	private final String name1;
	private final String name2;
	
	public DreamGround(String name, int priority, IBlockState state1, IBlockState state2) {
		super(name, priority);
		this.state1 = state1;
		this.state2 = state2;
		this.name1 = state1.getBlock().getUnlocalizedName().substring(5);
		this.name2 = state2.getBlock().getUnlocalizedName().substring(5);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		int radius = DreamHandler.hasDreamtOfVoid(p) ? 10 : 3;
		int startY = Math.min(pos.getY(), w.getHeight()/2);
		int[] tracker1 = new int[startY];
		int[] tracker2 = new int[startY];
		int sum1 = 0;
		int sum2 = 0;
		BlockPos coord1 = null;
		BlockPos coord2 = null;
		for(int y = startY-1; y>=0; y--) {
			for(int x = pos.getX()-radius; x<pos.getX()+radius; x++) {
				for(int z = pos.getZ()-radius; z<pos.getZ()+radius; z++) {
					IBlockState state = w.getBlockState(new BlockPos(x, y, z));
						// TODO: Get OreDictionary compatibility
						if(state.getBlock() == this.state1.getBlock()) {
							if(coord1 == null && DreamHandler.getDreamPowerLevel(p) >= 1) coord1 = new BlockPos(x, y, z);
							tracker1[y]++;
							sum1++;
						}else if(state.getBlock() == this.state2.getBlock()) {
							if(coord2 == null && DreamHandler.getDreamPowerLevel(p) >= 1) coord2 = new BlockPos(x, y, z);
							tracker2[y]++;
							sum2++;
						}
					
				}
			}
		}
		int max1 = 0;
		int highest1 = 0;
		for(int i = 0; i < tracker1.length; i++) {
			if(max1 < tracker1[i]) {
				max1 = tracker1[i];
				highest1 = i;
			}
		}
		
		int max2 = 0;
		int highest2 = 0;
		for(int i = 0; i < tracker2.length; i++) {
			if(max2 < tracker2[i]) {
				max2 = tracker2[i];
				highest2 = i;
			}
		}
		
		p.sendMessage(getGroundScanMessage(this.name1, sum1, highest1, coord1, radius*2+1));
		p.sendMessage(getGroundScanMessage(this.name2, sum2, highest2, coord2, radius*2+1));

		return true;
	}
	
	private int findMax(int[] array) {
		int max = 0;
		int highest = 0;
		for(int i = 0; i < array.length; i++) {
			if(max < array[i]) {
				max = array[i];
				highest = i;
			}
		}
		return highest;
	}
	
	private TextComponentTranslation getGroundScanMessage(String ore, int sum, int highest, BlockPos coord, int radius) {
		return new TextComponentTranslation(String.format("dreams.groundscan.%s", ore), new Object[] {Integer.valueOf(sum), radius, radius, getGroundScanConcentration(highest), getGroundScanBlockMessage(coord)});
	}
	
	private String getGroundScanConcentration(int highest) {
		if(highest > 0)
			return new TextComponentTranslation("dreams.groundscan.greatestconcentration", new Object[] {Integer.valueOf(highest)}).getUnformattedComponentText();
		return "";
	}
	
	private String getGroundScanBlockMessage(BlockPos coord) {
		if(coord != null)
			return new TextComponentTranslation("dreams.groundscan.block", new Object[] {Integer.valueOf(coord.getX()), Integer.valueOf(coord.getY()), Integer.valueOf(coord.getZ())}).getUnformattedComponentText();
		return "";
	}

}
