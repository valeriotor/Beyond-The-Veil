package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;
import com.valeriotor.beyondtheveil.util.Teleport;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockArchePortal extends ModBlock{

	public BlockArchePortal(String name) {
		super(Material.PORTAL, name);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0F);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(entityIn instanceof EntityPlayer && !worldIn.isRemote && entityIn.dimension == DimensionRegistry.ARCHE.getId()) {
			EntityPlayer p = (EntityPlayer)entityIn;
			if(ServerTickEvents.getPlayerTimer("arche_portal", p) == null) {
				p.sendMessage(new TextComponentTranslation("enter.arche_portal.initiate"));
				PlayerTimer pt = new PlayerTimerBuilder(p)
									.setTimer(100)
									.setName("arche_portal")
									.addInterrupt(this::isNoLongerInPortal)
									.addFinalAction(this::teleportToOverworld)
									.toPlayerTimer();
				ServerTickEvents.addPlayerTimer(pt);
			}
		}
	}
	
	private boolean isNoLongerInPortal(EntityPlayer p) {
		Block b0 = p.world.getBlockState(p.getPosition()).getBlock();
		Block b1 = p.world.getBlockState(p.getPosition().down()).getBlock();
		if(b0 != this && b1 != this) {
			p.sendMessage(new TextComponentTranslation("enter.arche_portal.interrupt"));
			return true;
		}
		return false;
	}
	
	private void teleportToOverworld(EntityPlayer p) {
		int dim = 0;
		WorldServer w = p.world.getMinecraftServer().getWorld(dim);
		BlockPos bed = p.getBedLocation(dim);
		if(bed == null)
			bed = w.getSpawnPoint();
		BlockPos pos = p.getBedSpawnLocation(w, bed, true);
		if(pos == null) pos = bed;
		p.changeDimension(dim, new Teleport(w, pos.getX(), pos.getY(), pos.getZ()));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
