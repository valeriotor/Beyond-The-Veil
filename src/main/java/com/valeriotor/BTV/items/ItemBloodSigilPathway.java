package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.multiblock.MultiblockRegistry;
import com.valeriotor.BTV.util.ItemHelper;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBloodSigilPathway extends ModItem{

	public ItemBloodSigilPathway(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 64;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer p, World w, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = w.getBlockState(pos);
		if(state.getBlock() == Blocks.QUARTZ_BLOCK && state.getValue(BlockQuartz.VARIANT) == BlockQuartz.EnumType.CHISELED) {
			if(!w.isRemote) {
				boolean shrine = MultiblockRegistry.multiblocks.get("dream_shrine").checksOutBottomCenter(w, pos.down());
				if(shrine)
					p.sendMessage(new TextComponentTranslation("multiblock.dream_shrine.checksout"));
				else 
					p.sendMessage(new TextComponentTranslation("multiblock.dream_shrine.noshrine"));
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(p, w, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(ItemHelper.checkTagCompound(stack).hasKey("path"))
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("path")) {
			nbt.setLong("path", entityLiving.getPosition().toLong());
			if(entityLiving instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer)entityLiving;
				if(!worldIn.isRemote) {
					p.sendMessage(new TextComponentTranslation("use.blood_sigil.path"));
					if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("boundpathway")) {
						SyncUtil.addStringDataOnServer(p, false, "boundpathway");
						SyncUtil.addStringDataOnServer(p, false, "boundpathway2");
					}
				} else
					p.world.playSound(p, p.getPosition(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 1, 1);
			}
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("path")) {
			tooltip.add(I18n.format("tooltip.blood_sigil_pathway.nopath"));
		} else {
			BlockPos pos = BlockPos.fromLong(nbt.getLong("path"));
			tooltip.add(I18n.format("tooltip.blood_sigil_pathway.path", pos.getX(), pos.getY(), pos.getZ()));
		}
	}

}
