package com.valeriotor.BTV.items.baubles;

import java.util.List;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemWolfMedallion extends Item implements IBauble, IActiveBauble{
	
	public ItemWolfMedallion() {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		setRegistryName(References.MODID +":wolf_medallion");
		setUnlocalizedName("wolf_medallion");
	}
	
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("�5�o"+I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player.world.isRemote) return;
		if(player.ticksExisted % 600 == 0 && player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 0), 1, false) == 1) {
			int i = 0;
			boolean creepers = false;
			AxisAlignedBB bb = new AxisAlignedBB(player.getPosition().add(-25, -10, -25), player.getPosition().add(25, 12, 25));
			List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, bb);
			for(Entity e : entities) {
				if(e instanceof EntityLiving) {
					EntityLiving l = (EntityLiving) e;
					if(l instanceof EntityCreeper) {
						creepers = true;
						i++;
						continue;
					}
					for(EntityAITasks.EntityAITaskEntry t : l.targetTasks.taskEntries) {
						if(t.action instanceof EntityAITarget) {
							i++;
							break;
						}
					}
				}
			}
			if(i > 1) player.sendMessage(new TextComponentTranslation("artifact.medallion.humming", new Object[] {Integer.valueOf(i)}));
			else if(i == 1) player.sendMessage(new TextComponentTranslation("artifact.medallion.humming1"));
			if(creepers) player.sendMessage(new TextComponentTranslation("artifact.medallion.creepers"));
		}
	}


	@Override
	public boolean activate(EntityPlayer p) {
		AxisAlignedBB bb = new AxisAlignedBB(p.getPosition().add(-25, -10, -25), p.getPosition().add(25, 12, 25));
		List<Entity> entities = p.world.getEntitiesWithinAABBExcludingEntity(p, bb);
		entities.forEach(e -> {
			if(e instanceof EntityLivingBase) {
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 80, 1, false, true));
			}
		});
		return true;
	}


	@Override
	public int getCooldown() {
		return 30*20;
	}
	
	
}
