package com.valeriotor.beyondtheveil.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;

public class CameraRotator {
    private int counter = 0;

    private final float yaw;
    private final float startYaw;
    private final float pitch;
    private final float startPitch;
    private final int time;

    public CameraRotator(CompoundTag tag) {
        this(tag.getFloat("newYaw"), tag.getFloat("newPitch"), tag.getInt("timeInTicks"));
    }

    public CameraRotator(float yaw, float pitch, int time) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.time = time;
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            startYaw = p.getYHeadRot();
            startPitch = p.getXRot();
        } else {
            startYaw = 0;
            startPitch = 0;
        }
    }

    public boolean update() {
        counter++;
        return counter >= time;
    }

    public double computeYaw(double partialTicks) {
        return startYaw + (yaw - startYaw) * ((counter + partialTicks) / time);
    }


    public double computePitch(double partialTicks) {
        return startPitch + (pitch - startPitch) * ((counter + partialTicks) / time);
    }

}
