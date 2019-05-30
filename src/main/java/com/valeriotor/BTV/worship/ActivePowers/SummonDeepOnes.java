package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SummonDeepOnes implements IActivePower{
	
	private SummonDeepOnes() {}
	private static final SummonDeepOnes INSTANCE = new SummonDeepOnes();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(p.world.isRemote) return false;
		//if(Deities.GREATDREAMER.cap(p).getLevel() < 4) return false;
		int amount = getAmount(p);
		double avgAngle = 2*Math.PI / amount;
		boolean spawnedAtLeastOne = false;
		double angle = 0;
		for(int i = 0; i < amount; i++) {
			for(int j = 8; j < 20; j+=4) {
				BlockPos pos = p.getPosition().add(j*Math.sin(angle), 0, j*Math.cos(angle));
				if(enoughSpace(p.world, pos)) {
					EntityDeepOne deepOne = new EntityDeepOne(p.world);
					deepOne.setIgnoredPlayer(p);
					deepOne.setPosition(pos.getX(), pos.getY(), pos.getZ());
					p.world.spawnEntity(deepOne);
					spawnedAtLeastOne = true;
					break;
				}
			}
			angle += avgAngle;
		}
		return spawnedAtLeastOne;
	}

	@Override
	public int getCooldownTicks() {
		return 3000;
	}
	
	private static int getAmount(EntityPlayer p) {
		return (Deities.GREATDREAMER.cap(p).getLevel() - 2) / 2; 
		// maybe + cap.getString("transformed") ? 1 : 0;
	}
	
	private static boolean enoughSpace(World w, BlockPos pos) {
		for(int i = 0; i > -5; i--) {
			IBlockState state = w.getBlockState(pos.down());
			if(!state.isTopSolid() || state.getBlock() == Blocks.WATER) pos = pos.down();
		}
		for(int i = 0; i < 5; i++) {
			IBlockState state = w.getBlockState(pos);
			if(state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER) pos = pos.up();
		}
		IBlockState state = w.getBlockState(pos);
		IBlockState stateDown = w.getBlockState(pos.down());
		if((state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER && state.causesSuffocation()) 
			/*|| (stateDown.getBlock() == Blocks.WATER && !stateDown.isTopSolid())*/) return false;
		for(int x = -1; x <= 2; x++) {
			for(int y = 0; y <= 3; y++) {
				for(int z = -1; z <= 2; z++) {
					IBlockState s = w.getBlockState(pos.add(x, y, z));
					if(state.causesSuffocation()) return false;
				}
			}
		}
		return true;
	}

	@Override
	public Deities getDeity() {
		return Deities.GREATDREAMER;
	}

	@Override
	public int getIndex() {
		return 0;
	}

	@Override
	public boolean hasRequirement(EntityPlayer p) {
		return true; // TODO: Change this to check for research
	}
	
}
