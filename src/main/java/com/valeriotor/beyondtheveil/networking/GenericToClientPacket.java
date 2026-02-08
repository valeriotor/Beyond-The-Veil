package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.client.sounds.SurgerySoundInstance;
import com.valeriotor.beyondtheveil.client.util.CameraRotator;
import com.valeriotor.beyondtheveil.client.util.CrossSyncHolder;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingData;
import com.valeriotor.beyondtheveil.util.WaypointType;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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

    public static GenericToClientPacket syncMemories(CompoundTag tag) {
        return new GenericToClientPacket(MessageType.SYNC_MEMORIES, tag);
    }

    public static GenericToClientPacket startAnimation(AnimationTemplate animation, int entityId, int channel) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("id", entityId);
        tag.putInt("anim", animation.getId());
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

    public static GenericToClientPacket crossSync(Player sender, CrossSync data) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", sender.getUUID().toString());
        CompoundTag crossSyncTag = new CompoundTag();
        data.saveToNBT(crossSyncTag);
        tag.put("cross_sync", crossSyncTag);
        return new GenericToClientPacket(MessageType.CROSS_SYNC, tag);
    }

    public static GenericToClientPacket stopCrossSync(Player sender) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", sender.getUUID().toString());
        return new GenericToClientPacket(MessageType.STOP_CROSS_SYNC, tag);
    }

    public static GenericToClientPacket syncArcheData(ArcheCycleData data) {
        CompoundTag tag = new CompoundTag();
        tag.put("data", data.save(new CompoundTag()));
        return new GenericToClientPacket(MessageType.SYNC_ARCHE_DATA, tag);
    }

    public static GenericToClientPacket blackScreen(int duration, SoundEvent event) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("duration", duration);
        tag.putString("event", event.getLocation().toString());
        return new GenericToClientPacket(MessageType.BLACK_SCREEN, tag);
    }

    public static GenericToClientPacket syncLetterData(CompoundTag data) {
        CompoundTag tag = new CompoundTag();
        tag.put("data", data);
        return new GenericToClientPacket(MessageType.SYNC_LETTER_DATA, tag);
    }

    public static GenericToClientPacket hideOverlayMessage() {
        return new GenericToClientPacket(MessageType.HIDE_OVERLAY_MESSAGE, new CompoundTag());
    }

    public static GenericToClientPacket openGui(GuiHelper.GuiType type) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("id", type.ordinal());
        return new GenericToClientPacket(MessageType.OPEN_GUI, tag);
    }

    public static GenericToClientPacket renewContact() {
        return new GenericToClientPacket(MessageType.RENEW_CONTACT, new CompoundTag());
    }

    public static GenericToClientPacket shakeCamera() {
        return new GenericToClientPacket(MessageType.SHAKE_CAMERA, new CompoundTag());
    }

    public static GenericToClientPacket syncBloodPool(Player sender, BloodPoolData data) {
        CompoundTag compoundTag = new CompoundTag();
        data.forPlayer(sender.getUUID(), compoundTag);
        CompoundTag poolTag = new CompoundTag();
        poolTag.put("pool", compoundTag);
        return new GenericToClientPacket(MessageType.SYNC_BLOOD_POOL, poolTag);
    }

    public static GenericToClientPacket modifyBloodPool(CompoundTag modification) {
        return new GenericToClientPacket(MessageType.MODIFY_BLOOD_POOL, modification);
    }

    public static GenericToClientPacket addClosestDeath(List<BlockPos> deaths) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < deaths.size(); i++) {
            tag.putLong("death" + i, deaths.get(i).asLong());
        }
        return new GenericToClientPacket(MessageType.CLOSEST_DEATH, tag);
    }

    public static GenericToClientPacket addMemoryToast(Memory memory) {
        CompoundTag tag = new CompoundTag();
        tag.putString("memory", memory.getDataName());
        return new GenericToClientPacket(MessageType.ADD_MEMORY_TOAST, tag);
    }

    public static GenericToClientPacket blindCompletely() {
        CompoundTag tag = new CompoundTag();
        return new GenericToClientPacket(MessageType.BLIND_COMPLETELY, tag);
    }

    public static GenericToClientPacket startPlayerAnimation(ServerPlayer player, AnimationTemplate animation) {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("player", player.getUUID());
        tag.putInt("anim", animation.getId());
        return new GenericToClientPacket(MessageType.START_PLAYER_ANIMATION, tag);
    }

    public static GenericToClientPacket makeExplosionBlood(double x, double y, double z, int burstSize) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.putInt("burst", burstSize);
        return new GenericToClientPacket(MessageType.MAKE_EXPLOSION_BLOOD, tag);
    }

    public static GenericToClientPacket syncBindingData(BindingData data) {
        CompoundTag tag = new CompoundTag();
        if (data != null) {
            tag.put("data", data.saveToNBT(new CompoundTag()));
        }
        return new GenericToClientPacket(MessageType.SYNC_BINDING_DATA, tag);
    }

    //public static GenericToClientPacket coloredParticle(ParticleOptions particle, double x, double y, double z, int color, double xSpeed, double ySpeed, double zSpeed) {
    //    CompoundTag tag = new CompoundTag();
    //    tag.putInt("particle", BuiltInRegistries.PARTICLE_TYPE.getId(particle.getType()));
    //    tag.putDouble("x", x);
    //    tag.putDouble("y", y);
    //    tag.putDouble("z", z);
    //    tag.putInt("color", color);
    //    tag.putDouble("xSpeed", xSpeed);
    //    tag.putDouble("ySpeed", ySpeed);
    //    tag.putDouble("zSpeed", zSpeed);
    //    return new GenericToClientPacket(MessageType.COLORED_PARTICLE, tag);
    //}

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
                    case SYNC_MEMORIES -> ClientMethods.loadMemories(tag);
                    case START_ANIMATION -> ClientMethods.startEntityAnimation(tag);
                    case ROTATE_CAMERA -> RenderEvents.startCameraRotation(new CameraRotator(tag));
                    case MOVE -> ClientMethods.movePlayer(tag);
                    case START_SURGERY_SOUND -> ClientMethods.startSurgerySound(tag);
                    case STOP_SURGERY_SOUND -> SurgerySoundInstance.stopSound(tag);
                    case CROSS_SYNC -> CrossSyncHolder.setCrossSync(tag);
                    case STOP_CROSS_SYNC -> CrossSyncHolder.stopCrossSync(tag);
                    case SYNC_ARCHE_DATA -> ClientData.getInstance().syncArcheData(tag);
                    case BLACK_SCREEN -> ClientMethods.blackScreen(tag);
                    case SYNC_LETTER_DATA -> ClientMethods.loadLetterData(tag);
                    case HIDE_OVERLAY_MESSAGE -> ClientMethods.hideOverlayMessage();
                    case OPEN_GUI -> GuiHelper.openClientSideGui(tag);
                    case RENEW_CONTACT -> ClientData.getInstance().renewContact();
                    case SHAKE_CAMERA -> RenderEvents.shakeCamera(30);
                    case SYNC_BLOOD_POOL -> ClientData.getInstance().syncPoolData(tag.getCompound("pool"));
                    case MODIFY_BLOOD_POOL -> ClientData.getInstance().modifyPoolData(tag);
                    case CLOSEST_DEATH -> ClientData.getInstance().setClosestDeath(tag.getAllKeys().stream().map(tag::getLong).map(BlockPos::of).toList());
                    case ADD_MEMORY_TOAST -> ClientMethods.unlockMemoryToast(Memory.getMemoryFromDataName(tag.getString("memory")));
                    case BLIND_COMPLETELY -> ClientData.getInstance().blindCompletely();
                    case START_PLAYER_ANIMATION -> ClientData.getInstance().startPlayerAnimation(tag.getUUID("player"), tag.getInt("anim"));
                    case MAKE_EXPLOSION_BLOOD -> LivingAmmunitionEntity.makeExplosionBleed(ClientMethods.getLevel(), tag.getInt("burst"), tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
                    case SYNC_BINDING_DATA -> ClientData.getInstance().syncBindingData(tag);
                }
            });
        });
        return true;
    }


    private enum MessageType {
        WAYPOINT,
        WAYPOINT_REMOVE,
        SYNC_REMINISCENCES,
        SYNC_MEMORIES,
        START_ANIMATION,
        ROTATE_CAMERA,
        MOVE,
        START_SURGERY_SOUND,
        STOP_SURGERY_SOUND,
        CROSS_SYNC,
        STOP_CROSS_SYNC,
        SYNC_ARCHE_DATA,
        BLACK_SCREEN,
        SYNC_LETTER_DATA,
        HIDE_OVERLAY_MESSAGE,
        OPEN_GUI,
        RENEW_CONTACT,
        SHAKE_CAMERA,
        SYNC_BLOOD_POOL,
        MODIFY_BLOOD_POOL,
        CLOSEST_DEATH,
        ADD_MEMORY_TOAST,
        BLIND_COMPLETELY,
        START_PLAYER_ANIMATION,
        MAKE_EXPLOSION_BLOOD,
        SYNC_BINDING_DATA
    }

}
