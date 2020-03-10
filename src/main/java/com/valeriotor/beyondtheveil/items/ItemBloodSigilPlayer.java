package com.valeriotor.beyondtheveil.items;

import java.util.List;

import com.valeriotor.beyondtheveil.multiblock.MultiblockRegistry;
import com.valeriotor.beyondtheveil.util.ItemHelper;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBloodSigilPlayer extends ModItem{

	public ItemBloodSigilPlayer(String name) {
		super(name);
		this.setMaxStackSize(1);
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		if(!nbt.hasKey("playername")) {
			tooltip.add(I18n.format("tooltip.blood_sigil_player.noplayer"));
		} else {
			String name = nbt.getString("playername");
			tooltip.add(I18n.format("tooltip.blood_sigil_player.player", name));
		}
	}

}
