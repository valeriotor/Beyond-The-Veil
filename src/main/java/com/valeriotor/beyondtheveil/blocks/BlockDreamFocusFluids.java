package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.entities.EntityFletum;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageCameraRotatorClient;
import com.valeriotor.beyondtheveil.network.MessageSyncPlayerRender;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus.FocusType;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockDreamFocusFluids extends ModBlock implements ITileEntityProvider{

	public BlockDreamFocusFluids(String name) {
		super(Material.ROCK, name);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT);
	}
	
	public static int hasFleti(World w, BlockPos pos) {
		int a = 0;
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos pos2 = pos.offset(facing);
			AxisAlignedBB bbox = new AxisAlignedBB(pos2.getX()+0.3, pos2.getY()+1, pos2.getZ()+0.3, pos2.getX()+0.7, pos2.getY()+2, pos2.getZ()+0.7);
			if(!w.getEntitiesWithinAABB(EntityFletum.class, bbox).isEmpty()) a++;
		}
		return a;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDreamFocus(FocusType.FLUID);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand == EnumHand.MAIN_HAND && !worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileDreamFocus) {
				int fleti = hasFleti(worldIn, pos);
				TileDreamFocus td = (TileDreamFocus)te;
				if(player.getHeldItemMainhand().isEmpty() && fleti == 0 && td.setPlayer(player)) {
					BlockPos ppos = player.getPosition();
					td.clearList();
					SyncUtil.addStringDataOnServer(player, true, PlayerDataLib.DREAMFOCUS);
					((WorldServer)player.world).getEntityTracker().sendToTrackingAndSelf(player, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncPlayerRender(player.getPersistentID(), true, MessageSyncPlayerRender.Type.DREAMFOCUS)));
					BlockPos startPos = pos.offset(EnumFacing.DOWN);
					if(worldIn.getBlockState(startPos).isFullCube()) return false;
					player.setPositionAndUpdate(startPos.getX()+0.5, startPos.getY()+0.5, startPos.getZ()+0.5);
					PlayerTimerBuilder ptb = new PlayerTimerBuilder(player)
												.addFinisher(EntityPlayer::isSneaking)
												.addContinuosAction(p -> BlockDreamFocus.continuosAction(p, td))
												.addFinalAction(p -> SyncUtil.removeStringDataOnServer(p, PlayerDataLib.DREAMFOCUS))
												.addFinalAction(p -> td.finish())
												.addFinalAction(p -> p.setPositionAndUpdate(ppos.getX(), ppos.getY(), ppos.getZ()))
												.addFinalAction(p -> ((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncPlayerRender(p.getPersistentID(), false, MessageSyncPlayerRender.Type.DREAMFOCUS))))
												.setTimer(200)
												.setName("dreamfocus");
					ServerTickEvents.addPlayerTimer(ptb.toPlayerTimer());
				}
			}
		}
		return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

}
