package com.valeriotor.BTV.items.baubles;

import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.items.ModItem;
import com.valeriotor.BTV.util.PlayerTimer;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAzacnoCharm extends ModItem implements IBauble, IActiveBauble{

	public ItemAzacnoCharm(String name) {
		super(name);
	}

	@Override
	public boolean activate(EntityPlayer p) {
		if(ServerTickEvents.getPlayerTimer("azacno", p) != null)
			return false;
		ServerTickEvents.addPlayerTimer(new PlayerTimer(p).setName("azacno"));
		return true;
	}

	@Override
	public int getCooldown() {
		return 500;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.CHARM;
	}

}
