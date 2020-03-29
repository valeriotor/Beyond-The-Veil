package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.events.special.CrawlerWorshipEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.items.baubles.IActiveBauble;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.potions.PotionRegistry;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship;

import baubles.api.BaublesApi;
import baubles.api.inv.BaublesInventoryWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageActivateBauble implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class ActivateBaubleMessageHandler implements IMessageHandler<MessageActivateBauble, IMessage>{

		@Override
		public IMessage onMessage(MessageActivateBauble message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
				if(data.getString(PlayerDataLib.TRANSFORMED)) {
					PlayerTimer pt = ServerTickEvents.getPlayerTimer("roar", p);
					if(pt == null) {
						p.world.getEntities(EntityLivingBase.class, e -> e.getDistance(p) < 25)
						 .forEach(e -> {
						 if(e != p && !BTVEntityRegistry.isFearlessEntity(e)) {
							 if(!(e instanceof EntityPlayer) || BaublesApi.isBaubleEquipped((EntityPlayer)e, ItemRegistry.bone_tiara) == -1)
								 e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 10));
							 e.addPotionEffect(new PotionEffect(PotionRegistry.terror, 120, 0));
						 }
						 if(e instanceof EntityPlayerMP) {
							 BTVPacketHandler.INSTANCE.sendTo(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.deepOneRoar), e.getPosition().toLong()), (EntityPlayerMP)e);
						 }
						 });
						BTVPacketHandler.INSTANCE.sendToAll(new MessagePlayerAnimation(p.getPersistentID(), AnimationRegistry.getIdFromAnimation(AnimationRegistry.deep_one_roar)));
						ServerTickEvents.addPlayerTimer(new PlayerTimer(p, null, 400).setName("roar"));
					} else {
						p.sendMessage(new TextComponentTranslation("roar.cooldown", pt.getTimer()/20));
					}
				}else {
					int selected = data.getOrSetInteger(PlayerDataLib.SELECTED_BAUBLE, -1, false);
					if(selected != -1) {
						ItemStack stack = BaublesApi.getBaublesHandler(p).getStackInSlot(selected);
						if(stack.getItem() instanceof IActiveBauble) {
							String key = String.format(PlayerDataLib.BAUBLE_COOLDOWN, selected);
							int cooldown = data.getOrSetInteger(key, 0, false);
							if(cooldown > 0) 
								p.sendMessage(new TextComponentTranslation("bauble.cooldown", cooldown/20));
							else if(((IActiveBauble)stack.getItem()).activate(p)) {
								int newCooldown = ((IActiveBauble)stack.getItem()).getCooldown();
								CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
								if(cw != null) newCooldown = cw.getBaubleCooldown(newCooldown);
								data.setInteger(key, newCooldown, false);
							}
						}
					}
				}
			});
			return null;
		}
		
	}
}
