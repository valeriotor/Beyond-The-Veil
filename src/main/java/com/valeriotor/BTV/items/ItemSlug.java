package com.valeriotor.BTV.items;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.world.World;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

import static com.valeriotor.BTV.lib.PlayerDataLib.RITUALQUEST;

public class ItemSlug extends ItemFood{
	
	public ItemSlug() {
		super(4, false);
		this.setRegistryName(References.MODID + ":slug");
		this.setUnlocalizedName("slug");
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer p = (EntityPlayer)entityLiving;
            IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
    		int lvl = Deities.GREATDREAMER.cap(p).getLevel();
            p.getFoodStats().addStats(getFoodByLevel(p, lvl), 1.0F);
            worldIn.playSound((EntityPlayer)null, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            getEffectsByLevel(lvl).forEach(e -> p.addPotionEffect(e));
            
            p.addStat(StatList.getObjectUseStats(this));

            if (p instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)p, stack);
            }
            int currentSlugs = data.getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
            data.setInteger(PlayerDataLib.SLUGS, currentSlugs+1, false);
            stack.shrink(1);
            if(!worldIn.isRemote && !data.getString(RITUALQUEST) && DGWorshipHelper.researches.get(RITUALQUEST).canBeUnlocked(ThaumcraftCapabilities.getKnowledge(p))) 
            	DrowningRitualEvents.checkRitual(p);
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
