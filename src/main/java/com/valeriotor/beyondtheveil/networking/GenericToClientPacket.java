package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.client.sounds.SurgerySoundInstance;
import com.valeriotor.beyondtheveil.client.util.CameraRotator;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.util.WaypointType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GenericToClientPacket {

    public static GenericToClientPacket createWaypoint(WaypointType type, BlockPos pos, int color) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        tag.putLong("pos", pos.asLong());
        tag.putInt("color", color);
        return new GenericToClientPacket(MessageType.WAYPOINT, tag);
    }

    public static GenericToClientPacket removeWaypoint(WaypointType type) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        return new GenericToClientPacket(MessageType.WAYPOINT_REMOVE, tag);
    }

    public static GenericToClientPacket syncReminiscences(CompoundTag tag) {
        return new GenericToClientPacket(MessageType.SYNC_REMINISCENCES, tag);
    }

    public static GenericToClientPacket startAnimation(AnimationTemplate animation, int entityId, int channel) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("id", entityId);
        tag.putInt("anim", AnimationRegistry.idFromAnimation(animation));
        tag.putInt("channel", channel);
        return new GenericToClientPacket(MessageType.START_ANIMATION, tag);
    }

    public static GenericToClientPacket rotateCamera(float newYaw, float newPitch, int timeInTicks) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("newYaw", newYaw);
        tag.putFloat("newPitch", newPitch);
        tag.putInt("timeInTicks", timeInTicks);
        return new GenericToClientPacket(MessageType.ROTATE_CAMERA, tag);
    }

    public static GenericToClientPacket movePlayer(double motionX, double motionY, double motionZ) {
        return movePlayer(motionX, motionY, motionZ, true, true, true);
    }

    public static GenericToClientPacket movePlayer(double motionX, double motionY, double motionZ, boolean absoluteX, boolean absoluteY, boolean absoluteZ) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("motionX", motionX);
        tag.putDouble("motionY", motionY);
        tag.putDouble("motionZ", motionZ);
        tag.putBoolean("absoluteX", absoluteX);
        tag.putBoolean("absoluteY", absoluteY);
        tag.putBoolean("absoluteZ", absoluteZ);
        return new GenericToClientPacket(MessageType.MOVE, tag);
    }

    public static GenericToClientPacket startSurgerySound(SurgeryItem.SurgeryItemType type, BlockPos bePos) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        tag.putLong("pos", bePos.asLong());
        return new GenericToClientPacket(MessageType.START_SURGERY_SOUND, tag);
    }

    public static GenericToClientPacket stopSurgerySound(BlockPos bePos) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("pos", bePos.asLong());
        return new GenericToClientPacket(MessageType.STOP_SURGERY_SOUND, tag);
    }


        private final MessageType type;
    private final CompoundTag tag;

    public GenericToClientPacket(MessageType type, CompoundTag tag) {
        this.type = type;
        this.tag = tag;
    }

    public GenericToClientPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(MessageType.class);
        this.tag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeNbt(tag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                switch (type) {
                    case WAYPOINT -> ClientData.getInstance().addWaypoint(tag);
                    case WAYPOINT_REMOVE -> ClientData.getInstance().removeWaypoint(tag);
                    case SYNC_REMINISCENCES -> DataUtilClient.loadReminiscences(tag);
                    case START_ANIMATION -> ClientMethods.startEntityAnimation(tag);
                    case ROTATE_CAMERA -> RenderEvents.startCameraRotation(new CameraRotator(tag));
                    case MOVE -> ClientMethods.movePlayer(tag);
                    case START_SURGERY_SOUND -> ClientMethods.startSurgerySound(tag);
                    case STOP_SURGERY_SOUND -> SurgerySoundInstance.stopSound(tag);
                }
            });
        });
        return true;
    }


    private enum MessageType {
        WAYPOINT,
        WAYPOINT_REMOVE,
        SYNC_REMINISCENCES,
        START_ANIMATION,
        ROTATE_CAMERA,
        MOVE,
        START_SURGERY_SOUND,
        STOP_SURGERY_SOUND
    }

}
