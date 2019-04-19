package com.valeriotor.BTV.items;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSlug extends ItemFood{
	
	public ItemSlug() {
		super(4, false);
		this.setRegistryName(References.MODID + ":slug");
		this.setUnlocalizedName("slug");
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
    		int lvl = entityplayer.getCapability(DGProvider.LEVEL_CAP, null).getLevel();
            entityplayer.getFoodStats().addStats(getFoodByLevel(entityplayer, lvl), 1.0F);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            getEffectsByLevel(lvl).forEach(e -> entityplayer.addPotionEffect(e));
            
            entityplayer.addStat(StatList.getObjectUseStats(this));

            if (entityplayer instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
            }
            int currentSlugs = entityplayer.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
            entityplayer.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SLUGS, currentSlugs+1, false);
            stack.shrink(1);
        }

        return stack;
	}
	
	private static int getFoodByLevel(EntityPlayer p, int lvl) {
		switch(lvl) {
			case 0: return 0;
			case 1:
			case 2:	
				return 2;
			case 3:
			case 4:
				return 4;
			default:
				return 6;
		}
	}
	
	private static List<PotionEffect> getEffectsByLevel(int lvl){
		List<PotionEffect> effects = Lists.newArrayList();
		switch(lvl) {
			case 0:
				effects.add(new PotionEffect(MobEffects.POISON, 200, 2));
				effects.add(new PotionEffect(MobEffects.NAUSEA, 160));
				break;
			case 1:
				effects.add(new PotionEffect(MobEffects.NAUSEA, 80));
				break;
			case 4:
			case 5:
				effects.add(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, -4 + lvl));
		}
		return effects;
	}
	
	
	
	
	

}
