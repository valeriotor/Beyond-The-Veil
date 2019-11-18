package com.valeriotor.BTV.network;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.util.CameraRotatorClient;
import com.valeriotor.BTV.util.CameraRotatorClient.RotatorFunction;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCameraRotatorClient implements IMessage{
	
	private float rotationYaw;
	private float rotationPitch;
	private int tickTime;
	private RotatorFunction funcYaw;
	private RotatorFunction funcPitch;
	
	public MessageCameraRotatorClient() {}
	public MessageCameraRotatorClient(float rotationYaw, float rotationPitch, int tickTime) {
		this(rotationYaw, rotationPitch, tickTime, RotatorFunction.LINEAR, RotatorFunction.LINEAR);
	}
	
	public MessageCameraRotatorClient(float rotationYaw, float rotationPitch, int tickTime, RotatorFunction funcYaw, RotatorFunction funcPitch) {
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.tickTime = tickTime;
		this.funcYaw = funcYaw;
		this.funcPitch = funcPitch;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.rotationYaw = buf.readFloat();
		this.rotationPitch = buf.readFloat();
		this.tickTime = buf.readInt();
		if(buf.isReadable()) {
			this.funcYaw = RotatorFunction.values()[buf.readInt()];
			this.funcPitch = RotatorFunction.values()[buf.readInt()];
		} else {
			this.funcYaw = this.funcPitch = RotatorFunction.LINEAR;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.rotationYaw);
		buf.writeFloat(this.rotationPitch);
		buf.writeInt(this.tickTime);
		if(funcYaw != RotatorFunction.LINEAR || funcPitch != RotatorFunction.LINEAR) {
			buf.writeInt(this.funcYaw.ordinal());
			buf.writeInt(this.funcPitch.ordinal());
		}
	}
	
	public static class CameraRotatorClientMessageHandler implements IMessageHandler<MessageCameraRotatorClient, IMessage>{

		@Override
		public IMessage onMessage(MessageCameraRotatorClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BeyondTheVeil.proxy.cEvents.cameraRotator = new CameraRotatorClient(message.rotationYaw, message.rotationPitch, message.tickTime, message.funcYaw, message.funcPitch);
			});
			return null;
		}
		
	}

}
