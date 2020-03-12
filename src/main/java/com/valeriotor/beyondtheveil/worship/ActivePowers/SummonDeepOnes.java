package com.valeriotor.beyondtheveil.worship.ActivePowers;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Deities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
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
			for(int j = 1; j < 14; j+=4) {
				BlockPos pos = p.getPosition().add(j*Math.sin(angle), 0, j*Math.cos(angle));
				pos = checkSpace(p.world, pos);
				if(pos != null) {
					EntityDeepOne deepOne = new EntityDeepOne(p.world, 2000);
					deepOne.setMaster(p);
					deepOne.setPosition(pos.getX(), pos.getY()+1.5, pos.getZ());
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
		int amount = 0;
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
		if(cw != null) amount += cw.getDeepOneBonus();
		return amount + 1; 
		// maybe + cap.getString("transformed") ? 1 : 0;
	}
	
	private static BlockPos checkSpace(World w, BlockPos pos) {
		for(int i = 0; i > -10; i--) {
			IBlockState state = w.getBlockState(pos.down());
			if(state.getBlock() == Blocks.AIR) pos = pos.down();
			else break;
		}
		for(int i = 0; i < 5; i++) {
			IBlockState state = w.getBlockState(pos);
			if(state.getBlock().causesSuffocation(state) && pos.getY() < w.getHeight()) pos = pos.up();
			else break;
		}
		for(int x = -2; x <= 2; x++) {
			for(int y = 0; y <= 3; y++) {
				for(int z = -2; z <= 2; z++) {
					IBlockState s = w.getBlockState(pos.add(x, y, z));
					if(s.getBlock().causesSuffocation(s)) {
						return null;
					}
				}
			}
		}
		return pos;
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
		return DGWorshipHelper.canSummon(p);
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/summon_deep_ones.png");
	
	@Override
	public ResourceLocation getGuiTexture() {
		return TEXTURE;
	}
	
}
