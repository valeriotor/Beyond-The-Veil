package com.valeriotor.BTV.items;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.lib.References;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
@Mod.EventBusSubscriber
public class ItemBlackjack extends Item{
	
	public ItemBlackjack() {
		this.setRegistryName(References.MODID + ":blackjack");
		this.setUnlocalizedName("blackjack");
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@SubscribeEvent
	public static void knockoutVillager(PlayerInteractEvent.EntityInteractSpecific event) {
		if(!(event.getTarget() instanceof EntityVillager)) return;
		EntityVillager vil = (EntityVillager) event.getTarget();
		if(vil.getProfession() == 5) return;
		EntityPlayer p = (EntityPlayer)event.getEntityPlayer();
		if(!(p.getHeldItemMainhand().getItem() instanceof ItemBlackjack)) return;
		if(/*Math.abs(vil.rotationYawHead - p.rotationYawHead) < 300 && */vil.getDistance(p) > 0.1) {
			BlockPos pos = vil.getPos();
			if(!event.getWorld().isRemote) {
				EntityCrawlingVillager worm = new EntityCrawlingVillager(event.getWorld(), true);
				worm.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), p.rotationYaw, 0);
				worm.setProfession(vil.getProfession());
				event.getWorld().removeEntity(vil);
				event.getWorld().spawnEntity(worm);
				worm.setRenderYawOffset(vil.rotationYaw);
			}
			event.getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1, 0.01F, false);
			event.setCancellationResult(EnumActionResult.SUCCESS); 
			// TODO: FIX DOUBLE SPAWN BUG
			// TODO: FIX ROTATION NOT BEING SET BUG
			// TODO: GET SOME REST
		}
		
	}
	
}
