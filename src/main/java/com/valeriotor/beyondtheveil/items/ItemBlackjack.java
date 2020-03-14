package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.entities.EntityWeeper;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
@Mod.EventBusSubscriber
public class ItemBlackjack extends Item{
	
	public ItemBlackjack() {
		this.setRegistryName(References.MODID + ":blackjack");
		this.setUnlocalizedName("blackjack");
		this.setCreativeTab(References.BTV_TAB);
		this.setMaxDamage(150);
		this.setMaxStackSize(1);
	}
	
	@SubscribeEvent
	public static void knockoutVillager(PlayerInteractEvent.EntityInteractSpecific event) {
		EntityPlayer p = (EntityPlayer)event.getEntityPlayer();
		if(!(p.getHeldItemMainhand().getItem() instanceof ItemBlackjack)) return;
		if(!(event.getTarget() instanceof EntityVillager)) {
			if(event.getTarget() instanceof EntityLiving && !p.world.isRemote && !(event.getTarget() instanceof EntityWeeper)) { //I'm letting the EntityWeeper class handle BJ
				((EntityLiving)event.getTarget()).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 4));
				p.getHeldItemMainhand().damageItem(3, p);
			}
			return;
		}
		EntityVillager vil = (EntityVillager) event.getTarget();
		if(vil.getProfession() == 5) return;
		if(/*Math.abs(vil.rotationYawHead - p.rotationYawHead) < 300 && */vil.getDistance(p) > 0.1) {
			BlockPos pos = vil.getPos();
			if(!event.getWorld().isRemote) {
				EntityCrawlingVillager worm = new EntityCrawlingVillager(event.getWorld(), true);
				worm.setPositionAndRotation(pos.getX()+0.5, pos.getY()+0.2, pos.getZ()+0.5, p.rotationYaw, 0);
				worm.setProfession(vil.getProfession());
				event.getWorld().removeEntity(vil);
				event.getWorld().spawnEntity(worm);
				worm.setRenderYawOffset(vil.rotationYaw);
				p.getHeldItemMainhand().damageItem(1, p);
			}
			event.getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1, 0.01F, false);
			event.setCancellationResult(EnumActionResult.SUCCESS); 
			// TODO: FIX DOUBLE SPAWN BUG
			// TODO: FIX ROTATION NOT BEING SET BUG
			// TODO: GET SOME REST
		}
		
	}
	
}
