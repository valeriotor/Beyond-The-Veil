package com.valeriotor.BTV.items;

import static com.valeriotor.BTV.lib.PlayerDataLib.RITUALQUEST;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.worship.DGWorshipHelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

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
            p.getFoodStats().addStats(getFoodByLevel(p), 1.0F);
            worldIn.playSound((EntityPlayer)null, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            getEffectsByLevel(p).forEach(e -> p.addPotionEffect(e));
            
            p.addStat(StatList.getObjectUseStats(this));

            if (p instanceof EntityPlayerMP)
            {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)p, stack);
            }
            int currentSlugs = data.getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
            data.setInteger(PlayerDataLib.SLUGS, currentSlugs+1, false);
            stack.shrink(1);
        	if(!data.getString(RITUALQUEST) && DGWorshipHelper.researches.get(RITUALQUEST).canBeUnlocked(p) && !worldIn.isRemote) {
        		DrowningRitualEvents.checkRitual(p);
        	}else if(p.isInWater() && p.posY < 40 && p.world.getBiome(p.getPosition()) == Biomes.DEEP_OCEAN && worldIn.isRemote) {
        		p.openGui(BeyondTheVeil.instance, GuiContainerHandler.DAGON, worldIn, (int) p.posX, (int) p.posY, (int) p.posZ);
        	}
        }

        return stack;
	}
	
	private static int getFoodByLevel(EntityPlayer p) {
		if(!DGWorshipHelper.researches.get(PlayerDataLib.SLUGS).isUnlocked(p)) {
			return 1;
		} else if(!DGWorshipHelper.researches.get(PlayerDataLib.FISHQUEST).isUnlocked(p)){
			return 2;
		} else if(!DGWorshipHelper.researches.get(PlayerDataLib.RITUALQUEST).isUnlocked(p)){
			return 3;
		}
		return 4;
	}
	
	private static List<PotionEffect> getEffectsByLevel(EntityPlayer p){
		List<PotionEffect> effects = Lists.newArrayList();
		if(!DGWorshipHelper.researches.get(PlayerDataLib.SLUGS).isUnlocked(p)) {
			effects.add(new PotionEffect(MobEffects.POISON, 200, 2));
			effects.add(new PotionEffect(MobEffects.NAUSEA, 160));
		} else if(!DGWorshipHelper.researches.get(PlayerDataLib.FISHQUEST).isUnlocked(p)){
			effects.add(new PotionEffect(MobEffects.NAUSEA, 160));
		} else if(!DGWorshipHelper.researches.get(PlayerDataLib.RITUALQUEST).isUnlocked(p)){
			
		}
		return effects;
	}
	
	
	
	
	

}
