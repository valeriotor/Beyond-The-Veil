package com.valeriotor.BTV.items.baubles;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.items.ModItem;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageCovenantData;
import com.valeriotor.BTV.util.MathHelper;
import com.valeriotor.BTV.util.PlayerTimer;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemBloodCovenant extends ModItem implements IBauble, IActiveBauble{

	public ItemBloodCovenant(String name) {
		super(name);
	}

	@Override
	public boolean activate(EntityPlayer p) {
		if(p.world.isRemote) return false;
		List<EntityPlayer> playerss = p.world.getPlayers(EntityPlayer.class, (player) -> {
			IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
			return !player.equals(p)
				&&	(handler.getStackInSlot(1).getItem() instanceof ItemBloodCovenant) || (handler.getStackInSlot(2).getItem() instanceof ItemBloodCovenant)
				&& player.isInWater();
		});
		if(ServerTickEvents.containsCovenantTimer(p)) {
			BlockPos nearestPos = MathHelper.minimumLookAngle(p.getPosition(), p.getLookVec(), playerss.stream().map(EntityPlayer::getPosition).collect(Collectors.toList()), Math.PI/16);
			if(nearestPos == null) {
				makeTimer(p, playerss);
				return false;
			}
			ServerTickEvents.removeCovenantTimer(p);
			p.setPositionAndUpdate(nearestPos.getX(), nearestPos.getY(), nearestPos.getZ());
			return true;
		} else {
			makeTimer(p, playerss);
			return false;
		}
	}
	
	private void makeTimer(EntityPlayer p, List<EntityPlayer> playerss) {
		HashMap<String, BlockPos> map = new HashMap();
		for(EntityPlayer player : playerss) {
			map.put(player.getDisplayNameString(), player.getPosition());
		}
		ServerTickEvents.addCovenantTimer(new PlayerTimer(p));
		BTVPacketHandler.INSTANCE.sendTo(new MessageCovenantData(false, map), (EntityPlayerMP)p);
	}

	@Override
	public int getCooldown() {
		return 50*20;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

}
