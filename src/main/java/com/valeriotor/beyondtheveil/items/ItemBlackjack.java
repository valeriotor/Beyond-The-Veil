package com.valeriotor.beyondtheveil.items;

import com.valeriotor.beyondtheveil.entities.EntityCrawlingVillager;
import com.valeriotor.beyondtheveil.entities.EntityWeeper;
import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
		if(event.getHand() == EnumHand.OFF_HAND) return;
		if(!(p.getHeldItemMainhand().getItem() instanceof ItemBlackjack)) return;
		ItemStack stack = p.getHeldItemMainhand();
		Entity target = event.getTarget();
		if(!(target instanceof EntityLiving)) return;
		if(processInteract(stack, target, p.getDistance(target), true)) {
			event.setCancellationResult(EnumActionResult.SUCCESS); 
		}
	}
	
	public static boolean processInteract(ItemStack stack, Entity target, double distance, boolean damageItem) {
		if(!(target instanceof EntityVillager)) {
			if(target instanceof EntityLiving && !target.world.isRemote && !(target instanceof EntityWeeper)) { //I'm letting the EntityWeeper class handle BJ
				if(target instanceof EntityDreamVillager) {
					((EntityDreamVillager)target).knockout();
				} else ((EntityLiving)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 4));
				if(damageItem) stack.damageItem(3, (EntityLivingBase) target);
			}
			return false;
		}
		EntityVillager vil = (EntityVillager) target;
		if(vil.isChild()) return false;
		if(/*Math.abs(vil.rotationYawHead - p.rotationYawHead) < 300 && */distance > 0.1) {
			BlockPos pos = vil.getPos();
			if(!target.world.isRemote) {
				EntityCrawlingVillager worm = new EntityCrawlingVillager(target.world, true);
				worm.setPositionAndRotation(pos.getX()+0.5, vil.posY, pos.getZ()+0.5, vil.rotationYaw, 0);
				worm.setProfession(vil.getProfession());
				target.world.removeEntity(vil);
				target.world.spawnEntity(worm);
				worm.setRenderYawOffset(vil.rotationYaw);
				if(damageItem) stack.damageItem(1, vil);
			}
			target.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1, 0.01F, false);
			return true;
		}
		return false;
	}
	
	
}
