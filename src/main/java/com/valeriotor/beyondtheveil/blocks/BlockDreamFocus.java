package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageMovePlayer;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockDreamFocus extends ModBlock implements ITileEntityProvider{

	public BlockDreamFocus(String name) {
		super(Material.ROCK, name);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand == EnumHand.MAIN_HAND && !worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileDreamFocus) {
				BlockPos ppos = playerIn.getPosition();
				TileDreamFocus td = (TileDreamFocus)te;
				td.clearList();
				SyncUtil.addStringDataOnServer(playerIn, true, PlayerDataLib.DREAMFOCUS);
				playerIn.setPositionAndUpdate(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
				PlayerTimerBuilder ptb = new PlayerTimerBuilder(playerIn)
											.addContinuosAction(p -> BlockDreamFocus.continuosAction(p, td))
											.addFinalAction(p -> SyncUtil.removeStringDataOnServer(p, PlayerDataLib.DREAMFOCUS))
											.addFinalAction(p -> td.finish())
											.addFinalAction(p -> p.setPositionAndUpdate(ppos.getX(), ppos.getY(), ppos.getZ()))
											.setTimer(200)
											.setName("dreamfocus");
				ServerTickEvents.addPlayerTimer(ptb.toPlayerTimer());
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDreamFocus();
	}
	
	public static void continuosAction(EntityPlayer p, TileDreamFocus td) {
		td.addPoint(p.posX, p.posY, p.posZ);
	}

}
