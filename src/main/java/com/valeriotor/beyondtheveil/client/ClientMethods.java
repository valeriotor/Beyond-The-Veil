package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.client.gui.SleepChamberGui;
import com.valeriotor.beyondtheveil.client.sounds.SurgerySoundInstance;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.lib.BTVSimpleGuis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;

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
            LocalPlayer player = Minecraft.getInstance().player;
            motionX = tag.getBoolean("absoluteX") ? motionX : player.getDeltaMovement().x + motionX;
            motionY = tag.getBoolean("absoluteY") ? motionY : player.getDeltaMovement().y + motionY;
            motionZ = tag.getBoolean("absoluteZ") ? motionZ : player.getDeltaMovement().z + motionZ;
            player.setDeltaMovement(new Vec3(motionX, motionY, motionZ));
        }
    }

    public static void startSurgerySound(CompoundTag tag) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) {
            SurgeryItem.SurgeryItemType type = SurgeryItem.SurgeryItemType.valueOf(tag.getString("type"));
            BlockPos pos = BlockPos.of(tag.getLong("pos"));
            if (type.getSound() != null) {
                mc.getSoundManager().play(new SurgerySoundInstance(type, pos));
            }
        }
    }

    public static void openSimpleGui(BTVSimpleGuis gui) {
        Screen s = switch (gui) {
            case SLEEP_CHAMBER -> new SleepChamberGui();
            default -> null;
        };
        if (s != null) {
            Minecraft.getInstance().setScreen(s);
        }
    }

    public static void blackScreen(CompoundTag tag) {
        int duration = tag.getInt("duration");
        String soundEvent = tag.getString("event");
        RenderEvents.setBlackScreenDuration(duration);
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundEvent));
        if (sound != null && Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.playSound(sound, 1, 1);
        }
    }

    public static void loadLetterData(CompoundTag tag) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
                c.loadFromNBT(tag.getCompound("data"));
            });
        }
    }

    private static int hideOverlayMessageTicks = 0;
    public static void hideOverlayMessage() {
        hideOverlayMessageTicks = 20;
        Minecraft.getInstance().gui.setOverlayMessage(Component.empty(), false);
    }

    public static void tick(TickEvent.ClientTickEvent event) {
        if (hideOverlayMessageTicks > 0) {
            Minecraft.getInstance().gui.setOverlayMessage(Component.empty(), false);
            hideOverlayMessageTicks--;
        }

    }

}
