package com.valeriotor.BTV.items.baubles;

import java.util.List;

import com.valeriotor.BTV.items.ModItem;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

public class ItemRevelationRing extends ModItem implements IBauble, IActiveBauble{

	public ItemRevelationRing(String name) {
		super(name);
	}

	@Override
	public boolean activate(EntityPlayer p) {
		List<EntityLivingBase> ents = p.world.getEntities(EntityLivingBase.class, (e -> e.getActivePotionEffect(MobEffects.INVISIBILITY) != null && e.getDistance(p) < 30));
		boolean atLeastOne = !ents.isEmpty();
		for(EntityLivingBase e : ents) {
			e.removePotionEffect(MobEffects.INVISIBILITY);
			e.setInvisible(false);
		}
		return atLeastOne;
	}

	@Override
	public int getCooldown() {
		return 5 * 20;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}
	

}
