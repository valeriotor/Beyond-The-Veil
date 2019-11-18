package com.valeriotor.BTV.util;

import java.util.function.DoubleUnaryOperator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class CameraRotatorClient {
	private int counter = 0;
	private final int time;
	private final float rotationYaw;
	private final float rotationPitch;
	private final float startYaw;
	private final float startPitch;
	private final RotatorFunction funcYaw;
	private final RotatorFunction funcPitch;
	
	public CameraRotatorClient(float rotationYaw, float rotationPitch, int time) {
		this(rotationYaw, rotationPitch, time, RotatorFunction.LINEAR, RotatorFunction.LINEAR);
	}
	
	public CameraRotatorClient(float rotationYaw, float rotationPitch, int time, RotatorFunction funcYaw, RotatorFunction funcPitch) {
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.time = time;
		EntityPlayer p = Minecraft.getMinecraft().player;
		this.startYaw = p.rotationYaw;
		this.startPitch = p.rotationPitch;
		this.funcYaw = funcYaw;
		this.funcPitch = funcPitch;
	}
	
	public boolean update() {
		this.counter++;
		return this.counter >= time;
	}
	
	public float getYaw(float partialTicks) {
		return (float) (this.startYaw + this.rotationYaw * this.funcYaw.op.applyAsDouble((counter + partialTicks)/time));
	}
	
	public float getPitch(float partialTicks) {
		return (float) (this.startPitch + this.rotationPitch * this.funcPitch.op.applyAsDouble((counter + partialTicks)/time));
	}
	
	
	public static enum RotatorFunction {
		LINEAR(a -> a),
		QUADRATIC(a -> a*a);
		
		DoubleUnaryOperator op;
		
		private RotatorFunction(DoubleUnaryOperator op) {
			this.op = op;
		}
		
	}
	

}
