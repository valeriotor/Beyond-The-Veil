package com.valeriotor.beyondtheveil.items;

import java.util.UUID;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.blocks.BlockStatue;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.sacrifice.SacrificeHelper;
import com.valeriotor.beyondtheveil.tileEntities.TileStatue;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemSacrificialKnife extends ModItem{

	public ItemSacrificialKnife(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer p, EnumHand handIn) {
		if(!w.isRemote) p.attackEntityFrom(DamageSource.OUT_OF_WORLD, 14);
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);		
		if(cw != null) {
			cw.getSelfHarmBonuses(p);
		}
		return super.onItemRightClick(w, p, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer p, World w, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block b = w.getBlockState(pos).getBlock();
		if(!w.isRemote) p.attackEntityFrom(DamageSource.OUT_OF_WORLD, 14);
		if(b == BlockRegistry.BlockSacrificeAltar) {
			if(!w.isRemote) {
				CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
				if(cw != null && cw.sacrificeAltar() && SacrificeHelper.checkStructure(p, pos)) {
					ItemStack stack;
					if(hand == EnumHand.MAIN_HAND)
						stack = p.getHeldItemOffhand();
					else	
						stack = p.getHeldItemMainhand();
					SacrificeHelper.doEffectKnife(p, stack, pos);
				}
			}
			return EnumActionResult.SUCCESS;
		} else if(b instanceof BlockStatue /*&& knows statue research*/) {
			if(!w.isRemote) {
				TileEntity te = w.getTileEntity(pos);
				if(te instanceof TileStatue) {
					TileStatue ts = (TileStatue) te;
					UUID u = ts.getMaster();
					if(u == null) {
						ts.setMaster(p);
						p.sendMessage(new TextComponentTranslation("interact.statue.bound"));
						SyncUtil.addStringDataOnServer(p, false, "linkedstatue");
					} else if(u.equals(p.getPersistentID())) {
						p.sendMessage(new TextComponentTranslation("interact.statue.alreadyme"));
					} else {
						p.sendMessage(new TextComponentTranslation("interact.statue.alreadyelse"));
					}
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(p, w, pos, hand, facing, hitX, hitY, hitZ);
	}

}
