package com.valeriotor.BTV.items;

import java.util.List;

import com.valeriotor.BTV.entities.EntityWeeper;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.potions.PotionRegistry;
import com.valeriotor.BTV.research.ResearchUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlute extends Item{
	public ItemFlute() {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		this.setRegistryName(References.MODID + ":flute");
		this.setUnlocalizedName("flute");
		this.setMaxDamage(250);
		
	}
	
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 64;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack.isItemDamaged()) {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(stack.getItemDamage() > 0 && entityIn instanceof EntityPlayer) stack.damageItem(-1, (EntityPlayer)entityIn);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase player) {
		if(stack.isItemDamaged()) return super.onItemUseFinish(stack, worldIn, player);
		stack.damageItem(240, player);
		BlockPos pos = player.getPosition();
		worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), BTVSounds.flute, SoundCategory.PLAYERS, 1, 1, false);
		if(player.world.isRemote) return super.onItemUseFinish(stack, worldIn, player);
		AxisAlignedBB bb = new AxisAlignedBB(player.getPosition().add(-10, -6, -10), player.getPosition().add(10, 6, 10));
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, bb);
		entities.forEach(e -> {
			if(e instanceof EntityWeeper) {
				((EntityWeeper)e).goCrazed();
			} else if(e instanceof EntityLivingBase && e.isNonBoss()) {
				((EntityLivingBase) e).addPotionEffect(new PotionEffect(PotionRegistry.folly, 250));
				((EntityLivingBase) e).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
			}
		});
		if(player instanceof EntityPlayer && !ResearchUtil.isResearchComplete(((EntityPlayer)player), "FIRSTCONTACT")) {
			player.addPotionEffect(new PotionEffect(PotionRegistry.folly, 300));
			if(!ResearchUtil.isResearchComplete(((EntityPlayer)player), "FIRSTDREAMS") ) {
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120));
				
			}
		}
		return super.onItemUseFinish(stack, worldIn, player);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("�5�o"+I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
}
