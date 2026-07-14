package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.client.gui.LetterBoxGui;
import com.valeriotor.beyondtheveil.client.gui.SleepChamberGui;
import com.valeriotor.beyondtheveil.client.sounds.SurgerySoundInstance;
import com.valeriotor.beyondtheveil.client.toasts.MemoryToast;
import com.valeriotor.beyondtheveil.client.util.CrossSyncHolder;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.AnimatedTalkable;
import com.valeriotor.beyondtheveil.event.LivingTickEvents;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.lib.BTVSimpleGuis;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ClientMethods {

    private static SoundInstance bloodRitualSoundInstance;
    private static int bloodRitualSoundTicks;
    private static int hideOverlayMessageTicks = 0;

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

    public static void dialogueAnimation(CompoundTag tag) {
        int entityId = tag.getInt("id");
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            if (level.getEntity(entityId) instanceof AnimatedTalkable talkable) {
                talkable.toggleDialogueAnimation(tag.getBoolean("start"));
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
                if (Minecraft.getInstance().screen instanceof LetterBoxGui gui) {
                    gui.serverSync();
                }
            });
        }
    }

    public static void hideOverlayMessage() {
        hideOverlayMessageTicks = 20;
        Minecraft.getInstance().gui.setOverlayMessage(Component.empty(), false);
    }

    public static void playRitualSound(BlockPos pos) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.distanceToSqr(pos.getCenter()) < 900) {
                if (bloodRitualSoundInstance == null) {
                    bloodRitualSoundInstance = new SimpleSoundInstance(BTVSounds.BLOOD_RITUAL.get().getLocation(), SoundSource.BLOCKS, 1, 1, Minecraft.getInstance().player.getRandom(), true, 0, SoundInstance.Attenuation.LINEAR, pos.getX(), pos.getY(), pos.getZ(), false);
                    Minecraft.getInstance().getSoundManager().play(bloodRitualSoundInstance);
                }
                bloodRitualSoundTicks = 50;
            }
        }
    }

    public static void tick(TickEvent.ClientTickEvent event) {
        if (hideOverlayMessageTicks > 0) {
            Minecraft.getInstance().gui.setOverlayMessage(Component.empty(), false);
            hideOverlayMessageTicks--;
        }
        if (bloodRitualSoundTicks > 0 && !Minecraft.getInstance().isPaused()) {
            bloodRitualSoundTicks--;
            if (bloodRitualSoundTicks == 0) {
                stopRitual();
            }
        }

    }

    private static void stopRitual() {
        if (bloodRitualSoundInstance != null) {
            Minecraft.getInstance().getSoundManager().stop(bloodRitualSoundInstance);
        }
        bloodRitualSoundInstance = null;
        bloodRitualSoundTicks = 0;
    }

    // Just hardcoding exceptions (items with multiple recipes). Should not be an issue
    public static List<Optional<? extends Recipe<?>>> recipesForModItem(String key) {
        List<Optional<? extends Recipe<?>>> recipes = new ArrayList<>();
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(key));
        if (item != null) {
            if (item == Registration.ANTIDOTE_CAPSULE.get()) {
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key + "2")));
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key + "1")));
            } else if (item == Registration.FLASK_LARGE_ITEM.get()) {
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(References.MODID, "flask_large")));
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(References.MODID, "flask_medium")));
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(References.MODID, "flask_small")));
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(References.MODID, "flask_item")));
            } else {
                recipes.add(Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key)));
            }
        }
        return recipes;
    }

    public static String necronomiconDescriptionId() {
        LocalPlayer p = Minecraft.getInstance().player;
        if (DataUtil.getBoolean(p, PlayerDataLib.renamed_necronomicon.name())) {
            return "item.beyondtheveil.necronomicon2";
        }
        return "item.beyondtheveil.necronomicon";
    }

    public static void doArcheEffects(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() != Minecraft.getInstance().player) {
            return;
        }
        ArcheCycleData data = ClientData.getInstance().archeCycleData;
        long ticks = data.ticksInCycle();
        if (ticks > 20 * 20) {
            LivingTickEvents.doArcheMovement(data.getCurrentIntensity(), Minecraft.getInstance().player);
        }
        if (ticks == 0) {
            //Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(BTVSounds.CURRENTS.get(), 1, 1));
        }
    }

    public static void unlockMemoryToast(Memory memory) {
        Minecraft.getInstance().getToasts().addToast(new MemoryToast(memory));
    }

    public static void loadMemories(CompoundTag tag) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(c -> {
                c.loadMemories(tag);
            });
        }
    }

    public static Level getLevel() {
        return Minecraft.getInstance().level;
    }

    public static void cancelJump(Player player) {
        LocalPlayer mainPlayer = Minecraft.getInstance().player;
        if (mainPlayer == player) {
            CrossSync crossSync = CrossSyncHolder.getCrossSync(player);
            if (crossSync != null && crossSync.isCrawling()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0.3, 1));
            }

        }
    }

    public static void setCrawlingPlayerSize(BiConsumer<EntityDimensions, Float> updater, Player p) {
        CrossSync crossSync = CrossSyncHolder.getCrossSync(p);
        if (crossSync != null && (crossSync.isCrawling() || crossSync.isDreamFocus())) {
            updater.accept(EntityDimensions.fixed(0.2F, 0.2F), 0.3F);
        }
    }

    public static void colorParticle(ParticleOptions particle, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, int rgb) {
        colorParticle(particle, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, rgb >> 16, ((rgb >> 8) & 255), rgb & 255);
    }

    public static void colorParticle(ParticleOptions particle, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, int r, int g, int b) {
        Particle p = Minecraft.getInstance().particleEngine.createParticle(particle, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        if (p != null) {
            p.setColor(r / 255F, g / 255F, b / 255F);
        }
    }

    //public static void addColoredParticle(CompoundTag tag) {
    //    ParticleType<?> particletype = BuiltInRegistries.PARTICLE_TYPE.byId(tag.getInt("particle"));
    //    double x = tag.getDouble("x");
    //    double y = tag.getDouble("y");
    //    double z = tag.getDouble("z");
    //    int color = tag.getInt("color");
    //    double xSpeed = tag.getDouble("xSpeed");
    //    double ySpeed = tag.getDouble("ySpeed");
    //    double zSpeed = tag.getDouble("zSpeed");
    //    Minecraft.getInstance().particleEngine.createParticle(particletype, x, y, z, xSpeed, ySpeed, zSpeed);
    //    Minecraft.getInstance().levelRenderer.addParticle();
//
    //}
}
