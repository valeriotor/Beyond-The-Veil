package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class ClientMethods {

    public static void startEntityAnimation(CompoundTag tag) {
        AnimationTemplate template = AnimationRegistry.animationFromId(tag.getInt("anim"));
        int entityId = tag.getInt("id");
        int channel = tag.getInt("channel");
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            if (level.getEntity(entityId) instanceof AnimatedEntity animatedEntity) {
                animatedEntity.startAnimation(template, channel);
            }
        }
    }

    public static void movePlayer(CompoundTag tag) {
        if (Minecraft.getInstance().player != null) {
            double motionX = tag.getDouble("motionX");
            double motionY = tag.getDouble("motionY");
            double motionZ = tag.getDouble("motionZ");
            Minecraft.getInstance().player.setDeltaMovement(new Vec3(motionX, motionY, motionZ));
        }
    }

}
