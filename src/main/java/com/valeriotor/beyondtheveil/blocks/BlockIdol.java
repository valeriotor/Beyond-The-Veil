package com.valeriotor.beyondtheveil.blocks;



import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlaySound;
import com.valeriotor.beyondtheveil.potions.PotionRegistry;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DelayedMessage;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Deities;
import com.valeriotor.beyondtheveil.worship.Worship;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockIdol extends ModBlock{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN && p_apply_1_ != EnumFacing.UP;
        }
    });
	
	public BlockIdol() {
		super(Material.ROCK, BlockNames.IDOL);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0F);
		
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		switch(meta) {
			case 0:
				state = state.withProperty(FACING, EnumFacing.SOUTH);
				break;
			case 1:
				state = state.withProperty(FACING, EnumFacing.WEST);
				break;
			case 2:
				state = state.withProperty(FACING, EnumFacing.NORTH);
				break;
			case 3:
				state = state.withProperty(FACING, EnumFacing.EAST);
				break;	
			
		}
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getHorizontalIndex();
		
		return meta;
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
	
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	private static final AxisAlignedBB BBOX_BASE = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.8125D,0.25D,0.8125D);
	private static final AxisAlignedBB BBOX_NORTH = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.8125D,0.875D,0.6875D);
	private static final AxisAlignedBB BBOX_WEST = new AxisAlignedBB(0.1875D,0.0D,0.1875D,0.6875D,0.875D,0.8125D);
	private static final AxisAlignedBB BBOX_SOUTH = new AxisAlignedBB(0.1875D,0.0D,0.3125D,0.8125D,0.875D,0.8125D);
	private static final AxisAlignedBB BBOX_EAST = new AxisAlignedBB(0.3125D,0.0D,0.1875D,0.8125D,0.875D,0.8125D);
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
		case NORTH: return BBOX_NORTH;
		case WEST: return BBOX_WEST;
		case SOUTH: return BBOX_SOUTH;
		case EAST: return BBOX_EAST;
		default: return BBOX_NORTH;
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		
		if(!ResearchUtil.isResearchVisible(playerIn, "IDOL")) {
			if(!worldIn.isRemote) playerIn.sendMessage(new TextComponentTranslation("interact.idol.notyet"));
			return true;
		}
		if(!worldIn.isRemote) {
			if(ResearchUtil.getResearchStage(playerIn, "IDOL") == 0) {
				SyncUtil.addStringDataOnServer(playerIn, false, "IdolInteract");
				playerIn.sendMessage(new TextComponentTranslation("interact.idol.communion"));
			}
			if(Worship.getSelectedDeity(playerIn) != Deities.GREATDREAMER) Worship.setSelectedDeity(playerIn, Deities.GREATDREAMER);
			DGWorshipHelper.levelUp(playerIn);
		} else {
			BeyondTheVeil.proxy.cEvents.muteSounds(100);
		}
		return true;
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer p) {
		if(!worldIn.isRemote) {
			IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			if(data.getString(PlayerDataLib.IDOLFOLLY)) {
				p.addPotionEffect(new PotionEffect(PotionRegistry.folly, 4*20, 3));
			} else {
				int a = data.getOrSetInteger(PlayerDataLib.IDOLBREAK, 0, true);
				if(a < 9) {
					p.sendMessage(new TextComponentTranslation(String.format("hit.idol.schiz%d", a)));
					data.setInteger(PlayerDataLib.IDOLBREAK, a+1, true);
				} else if(a == 9){
					for(int i = 0; i < 4; i++) 
						p.sendMessage(new TextComponentTranslation("hit.idol.schiz8"));
					data.setInteger(PlayerDataLib.IDOLBREAK, a+1, true);
				} else {
					p.addPotionEffect(new PotionEffect(PotionRegistry.folly, 4*20));
					SPacketTitle spackettitle1 = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentTranslation("hit.idol.schiz8"));
                    ((EntityPlayerMP)p).connection.sendPacket(spackettitle1);
                    BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.shoggoth_screech), p.getPosition().toLong()), (EntityPlayerMP)p);
                    for(int i = 0; i < 12; i++)  {
                    	PlayerTimer pt = new PlayerTimer(p, player -> BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.dagonThump), p.getPosition().toLong()), (EntityPlayerMP)player), i*5);
                    	ServerTickEvents.addPlayerTimer(pt);
                    }
                    data.addString(PlayerDataLib.IDOLFOLLY, false);
                    ServerTickEvents.addMessage(new DelayedMessage(85, new TextComponentTranslation("hit.idol.schiz9"), p));
                    ServerTickEvents.addMessage(new DelayedMessage(115, new TextComponentTranslation("hit.idol.schiz10"), p));
				}
			}
		}
	}
	
	
}
