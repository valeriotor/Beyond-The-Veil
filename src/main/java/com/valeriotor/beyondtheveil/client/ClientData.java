package com.valeriotor.beyondtheveil.client;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.gui.pool.BloodPoolGui;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingData;
import com.valeriotor.beyondtheveil.util.WaypointType;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientData {

    private static ClientData instance = new ClientData();

    public static ClientData getInstance() {
        return instance;
    }

    public static void newInstance() {
        ClientData old = instance;
        instance = new ClientData();
        // any data that should survive after recreating player
        instance.bloodPoolData = old.bloodPoolData;
    }

    //@SubscribeEvent
    //public static void logoutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
    //    instance = new ClientData();
    //}

    public final List<Waypoint> waypoints = new ArrayList<>();
    public ArcheCycleData archeCycleData = new ArcheCycleData();
    private BloodPoolData bloodPoolData = new BloodPoolData();
    private int contactTimer = 0;
    private int contactFogLevel = 0;
    private List<BlockPos> closeDeaths = new ArrayList<>();
    private int closestDeathTimer;
    private int blindnessTimer;
    private Map<UUID, List<Animation>> playerAnimations = new HashMap<>();

    public void addWaypoint(CompoundTag tag) {
        WaypointType type = WaypointType.valueOf(tag.getString("type"));
        for (Waypoint waypoint : waypoints) {
            if (waypoint.type == type) {
                return;
            }
        }
        waypoints.add(new Waypoint(type, BlockPos.of(tag.getLong("pos")), type.unlocalizedName));
    }

    public void addWaypoint(WaypointType type, BlockPos pos, String unlocalizedName) {
        waypoints.add(new Waypoint(type, pos, unlocalizedName));
    }

    public void removeWaypoint(CompoundTag tag) {
        WaypointType type = WaypointType.valueOf(tag.getString("type"));
        Iterator<Waypoint> waypointIterator = waypoints.listIterator();
        while (waypointIterator.hasNext()) {
            Waypoint wp = waypointIterator.next();
            if (wp.type == type) {
                waypointIterator.remove();
                break;
            }
        }
    }

    public HitResult getClientHitResult() {
        return Minecraft.getInstance().hitResult;
    }

    public void syncArcheData(CompoundTag tag) {
        archeCycleData = new ArcheCycleData(tag.getCompound("data"));
    }

    public void tick(TickEvent.ClientTickEvent event) {
        if(!Minecraft.getInstance().isPaused()) {
            if (contactTimer > 0) {
                if (contactFogLevel < 200) {
                    contactFogLevel++;
                }
                contactTimer--;
            } else if (contactFogLevel > 0) {
                contactFogLevel--;
            }
            if (closestDeathTimer > 0) {
                closestDeathTimer--;
            } else {
                LocalPlayer player = Minecraft.getInstance().player;
                ClientLevel l = Minecraft.getInstance().level;
                if (l != null && player != null && (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.SIGIL_PLAYER.get() || player.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.SIGIL_PLAYER.get())) {
                    for (BlockPos closeDeath : closeDeaths) {
                        RandomSource r = player.getRandom();
                        for (int i = 0; i < 20; i++) {
                            l.addParticle(BTVParticles.BLOODSPILL.get(), closeDeath.getX() + r.nextDouble(), closeDeath.getY() + i / 10D, closeDeath.getZ() + r.nextDouble(), r.nextDouble() - 0.5, r.nextDouble() - 0.5, r.nextDouble() - 0.5);
                        }
                    }
                }
            }
            if (blindnessTimer > 0) {
                blindnessTimer--;
            }
            if (event.phase == TickEvent.Phase.END) {
                for (Iterator<Map.Entry<UUID, List<Animation>>> iterator = playerAnimations.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<UUID, List<Animation>> e = iterator.next();
                    for (Iterator<Animation> iterator2 = e.getValue().iterator(); iterator2.hasNext(); ) {
                        Animation animation = iterator2.next();
                        animation.update();
                        if (animation.isDone()) {
                            iterator2.remove();
                        }
                    }
                    if (e.getValue().isEmpty()) {
                        iterator.remove();
                    }
                }
                // TEST LocalPlayer p = Minecraft.getInstance().player;
                // TEST if (p != null && (!playerAnimations.containsKey(p.getUUID()) || playerAnimations.get(p.getUUID()).isEmpty())) {
                // TEST     playerAnimations.computeIfAbsent(p.getUUID(), uuid -> new ArrayList<>()).add(new Animation(AnimationRegistry.player_default_test));
                // TEST }
            }
        }
    }

    public Animation getPlayerAnimation(UUID uuid, AnimatedModel<?> model) {
        for (Animation animation : playerAnimations.getOrDefault(uuid, new ArrayList<>())) {
            if (animation.matchesModel(model)) {
                return animation;
            }
        }
        return null;
    }

    public void startPlayerAnimation(UUID playerId, int animationId) {
        playerAnimations.computeIfAbsent(playerId, uuid -> new ArrayList<>()).add(new Animation(AnimationRegistry.animationFromId(animationId)));
    }

    public void renewContact() {
        contactTimer = 50;
    }

    public int getContactFogLevel() {
        return contactFogLevel;
    }

    public void syncPoolData(CompoundTag tag) {
        bloodPoolData = BloodPoolData.load(tag);
    }

    public BloodPoolData getBloodPoolData() {
        return bloodPoolData;
    }

    public void modifyPoolData(CompoundTag tag) {
        bloodPoolData.modifyPool(Minecraft.getInstance().level, tag);
        if (Minecraft.getInstance().screen instanceof BloodPoolGui gui) {
            gui.updateEntities();
        }
    }

    public void setClosestDeath(List<BlockPos> closeDeaths) {
        this.closeDeaths.clear();
        this.closeDeaths.addAll(closeDeaths);
        closestDeathTimer = 30;
    }

    public void blindCompletely() {
        blindnessTimer = 80;
    }

    public boolean isBlinded() {
        return blindnessTimer > 0;
    }

    public void syncBindingData(CompoundTag tag) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(c -> {
                if (tag.contains("data")) {
                    c.setBindingData(new BindingData(tag.getCompound("data")));
                } else {
                    c.setBindingData(null);
                }
            });
        }
    }

    public static class Waypoint {
        private WaypointType type;
        public final BlockPos pos;
        public final String localizedName;

        public Waypoint(WaypointType type, BlockPos pos, String unlocalizedName) {
            this.type = type;
            this.pos = pos;
            this.localizedName = I18n.get(unlocalizedName);
        }
    }
}

