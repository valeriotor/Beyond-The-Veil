package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.animations.Animation;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageEntityAnimation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Entities implementing ICustomAttacker must override swingArm() to send an animation, choosing an attack (possibly from an enum
 * 	with certain rand values).
 * 
 * @author valeriotor
 *
 */
public interface IAnimatedAttacker {
	
	public default void sendAnimation(int id) {
		if(this instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase) this;
			((WorldServer)e.world).getEntityTracker().sendToTracking(e, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageEntityAnimation(e, id)));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setAttackAnimation(int id);
	
	@SideOnly(Side.CLIENT)
	public Animation getAttackAnimation();
}
