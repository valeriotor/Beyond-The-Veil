package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.events.special.AzacnoParasiteEvents;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.events.special.DrowningRitualEvents;
import com.valeriotor.beyondtheveil.items.ItemBloodSigilPlayer;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.ItemHelper;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerDamageEvents {
	
	@SubscribeEvent
	public static void hurtEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(event.getEntityLiving().world.isRemote) return;
			damageParasite(event);
			GreatDreamerBuffs.applyDefenseModifier(event);
			DrowningRitualEvents.preventDamage(event);
			dagonProtect(event);
		}
	}	

	@SubscribeEvent
	public static void damageEvent(LivingDamageEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(event.getEntityLiving().world.isRemote) return;
			resetFlute(event);
			GreatDreamerBuffs.applyDamageCap(event);
			enrageMinions(event);
			applyBleedingBelt(event);
			applyBloodCrown(event);
			bindBloodSigil(event);
		}
	}
	
	@SubscribeEvent
	public static void livingAttack(LivingAttackEvent event) {
		if(event.getEntityLiving() instanceof EntityPlayer) {
			GreatDreamerBuffs.denyFall(event);
		}
	}
	
	private static void resetFlute(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer)event.getEntityLiving();
		ItemStack stack = null;
		if(p.getHeldItemMainhand().getItem() == ItemRegistry.flute) {
			stack = p.getHeldItemMainhand();
		}else if(p.getHeldItemOffhand().getItem() == ItemRegistry.flute){
			stack = p.getHeldItemOffhand();
		}
		if(stack != null) {
			if(p.getItemInUseCount() > 0)
			stack.setItemDamage(150);
		}
	}
	
	private static void applyBleedingBelt(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer)event.getEntityLiving();
		if(BaublesApi.getBaublesHandler(p).getStackInSlot(3).getItem() == ItemRegistry.bleeding_belt &&
			p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 3), 1, false) == 1	) {
			float damage = event.getAmount();
			int food = p.getFoodStats().getFoodLevel();
			if(damage < food) {
				p.getFoodStats().setFoodLevel((int)(food - damage));
				event.setAmount(0);
			}else {
				p.getFoodStats().setFoodLevel(0);
				event.setAmount(damage - food);
			}
		}
	}
	
	private static void dagonProtect(LivingHurtEvent event) {
		if(!(event.getSource().getTrueSource() instanceof EntityLiving)) return;
		EntityPlayer p = (EntityPlayer) event.getEntityLiving();
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(ResearchUtil.getResearchStage(p, "ALLIANCE") == 0 && !data.getString(PlayerDataLib.WAIT)) {
			EntityDeepOne guardian = new EntityDeepOne(p.world, 500);
			guardian.setPosition(p.posX, p.posY, p.posZ);
			guardian.setAttackTarget((EntityLivingBase)event.getSource().getTrueSource());
			guardian.setMaster(p);
			p.world.spawnEntity(guardian);
			SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.WAIT);
		}
	}
	
	private static void damageParasite(LivingHurtEvent event) {
		if(event.getSource().isFireDamage()) {
			EntityPlayer p = (EntityPlayer) event.getEntityLiving();
			if(AzacnoParasiteEvents.parasites.containsKey(p.getPersistentID())) {
				AzacnoParasiteEvents.parasites.get(p.getPersistentID()).damageParasite((int)event.getAmount());
			}
		}		
	}
	
	private static void applyBloodCrown(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer) event.getEntityLiving();
		if(event.getAmount() > p.getHealth()) {
			if(BaublesApi.getBaublesHandler(p).getStackInSlot(4).getItem() == ItemRegistry.blood_crown &&
					p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 4), 1, false) == 1	) {
				if(ServerTickEvents.getPlayerTimer("bcrown1", p) == null && ServerTickEvents.getPlayerTimer("bcrown2", p) == null) {
					p.sendMessage(new TextComponentTranslation("bauble.blood_crown.activated"));
					p.world.playSound(null, p.posX, p.posY, p.posZ, BTVSounds.heartRip, SoundCategory.PLAYERS, 1, 1);
					p.heal(5.0F);
					CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
					boolean cwImp = cw != null && cw.improvesCrownOfThorns();
					final float a = event.getAmount() / (cwImp ? 3 : 1);
					PlayerTimer nested = new PlayerTimerBuilder(p).setName("bcrown2")
										.addInterrupt(player -> player.isDead)
										.setTimer(cwImp ? 5 : 100)
										.toPlayerTimer();
					PlayerTimer pt = new PlayerTimerBuilder(p).setTimer(300)
										.addFinalAction(player -> player.attackEntityFrom(event.getSource(), a))
										.addFinalAction(player -> ServerTickEvents.addBufferedTimer(nested))
										.addInterrupt(player -> player.isDead)
										.setName("bcrown1")
										.toPlayerTimer();
					event.setAmount(0);			
					ServerTickEvents.addPlayerTimer(pt);
				}
			}
		}
	}
	
	private static void enrageMinions(LivingDamageEvent event) {
		EntityPlayer p = (EntityPlayer) event.getEntityLiving();
		CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
		if(cw != null && event.getAmount() > 0) {
			cw.empowerMinions(p);
		}
		
	}
	
	private static void bindBloodSigil(LivingDamageEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer target = (EntityPlayer) event.getEntityLiving();
			float hp = target.getHealth();
			if(hp > 10 && hp - event.getAmount() < 10) {
				EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
				for(EnumHand hand : EnumHand.values()) {
					ItemStack stack = attacker.getHeldItem(hand);
					if(stack.getItem() instanceof ItemBloodSigilPlayer) {
						NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
						if(!nbt.hasKey("player")) {
							nbt.setString("player", target.getPersistentID().toString());
							nbt.setString("playername", target.getName());
							target.world.playSound(null, attacker.getPosition(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 1, 1);
						}
					}
				}
			}
		}
	}
	
}
