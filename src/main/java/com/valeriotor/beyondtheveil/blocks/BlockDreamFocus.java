package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.blocks.util.IDyableFocus;
import com.valeriotor.beyondtheveil.entities.EntityFletum;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageCameraRotatorClient;
import com.valeriotor.beyondtheveil.network.MessageSyncPlayerRender;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus.FocusType;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;
import com.valeriotor.beyondtheveil.util.SyncUtil;

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

public class BlockDreamFocus extends ModBlockFacing implements ITileEntityProvider, IDyableFocus{

	public BlockDreamFocus(String name) {
		super(Material.ROCK, name);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, final BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(hand == EnumHand.MAIN_HAND && !worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileDreamFocus) {
				boolean hasFletum = hasFletum(worldIn, pos);
				TileDreamFocus td = (TileDreamFocus)te;
				if(player.getHeldItemMainhand().isEmpty() && !hasFletum && td.setPlayer(player)) {
					BlockPos ppos = player.getPosition();
					td.clearList();
					SyncUtil.addStringDataOnServer(player, true, PlayerDataLib.DREAMFOCUS);
					((WorldServer)player.world).getEntityTracker().sendToTrackingAndSelf(player, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncPlayerRender(player.getPersistentID(), true, MessageSyncPlayerRender.Type.DREAMFOCUS)));
					BlockPos startPos = pos.offset(state.getValue(FACING));
					player.setPositionAndUpdate(startPos.getX()+0.5, startPos.getY()+0.5, startPos.getZ()+0.5);
					player.setRotationYawHead((((state.getValue(FACING).getOpposite().getHorizontalIndex()+1)&3)-1)*90);
					player.prevRotationYaw = (((state.getValue(FACING).getOpposite().getHorizontalIndex()+1)&3)-1)*90;
					player.rotationPitch = 0;
					if(!player.isSneaking())
						BTVPacketHandler.INSTANCE.sendTo(new MessageCameraRotatorClient((((state.getValue(FACING).getHorizontalIndex()+1)&3)-1)*90 - player.rotationYaw, -player.prevRotationPitch, 2), (EntityPlayerMP)player);
					//player.setPositionAndRotation(startPos.getX()+0.5, startPos.getY(), startPos.getZ()+0.5, (((state.getValue(FACING).getHorizontalIndex()+1)&3)-1)*90, 0);
					PlayerTimerBuilder ptb = new PlayerTimerBuilder(player)
												.addFinisher(EntityPlayer::isSneaking)
												.addContinuosAction(p -> BlockDreamFocus.continuosAction(p, td))
												.addFinalAction(p -> SyncUtil.removeStringDataOnServer(p, PlayerDataLib.DREAMFOCUS))
												.addFinalAction(p -> td.finish())
												.addFinalAction(p -> p.setPositionAndUpdate(ppos.getX(), ppos.getY(), ppos.getZ()))
												.addFinalAction(p -> ((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncPlayerRender(p.getPersistentID(), false, MessageSyncPlayerRender.Type.DREAMFOCUS))))
												.setTimer(300)
												.setName("dreamfocus");
					ServerTickEvents.addPlayerTimer(ptb.toPlayerTimer());
				} else if(player.getHeldItemMainhand().getItem() == ItemRegistry.held_fletum && !hasFletum) {
					player.getHeldItemMainhand().shrink(1);
					EntityFletum fletum = new EntityFletum(worldIn);
					fletum.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
					fletum.setMaster(player);
					worldIn.spawnEntity(fletum);
				} else this.setColor(player.getHeldItemMainhand(), worldIn, pos);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDreamFocus(FocusType.ITEM);
	}
	
	public static void continuosAction(EntityPlayer p, TileDreamFocus td) {
		td.addPoint(p.posX, p.posY, p.posZ);
	}
	
	public static boolean hasFletum(World w, BlockPos pos) {
		AxisAlignedBB bbox = new AxisAlignedBB(pos.getX()+0.3, pos.getY()+1, pos.getZ()+0.3, pos.getX()+0.7, pos.getY()+2, pos.getZ()+0.7);
		return !w.getEntitiesWithinAABB(EntityFletum.class, bbox).isEmpty();
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT);
	}

}
