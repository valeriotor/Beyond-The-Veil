package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.PlayerTransformation;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffect;
import com.valeriotor.beyondtheveil.surgery.arsenal.Burst;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;

public class TransformationUtil {

    private static final int TRANSFORM_COOLDOWN = 200;

    public static void startExplodingPlayer(ServerPlayer player) {
        player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(c -> {
            CrossSync crossSync = c.getCrossSync();
            if (crossSync.getTransformation().isCanExplode() && !PlayerTimerData.for_(player).hasTimer("explosion")) {
                PlayerTimer timer = new PlayerTimer.Builder("explosion", 30).addFinalActions((player1, playerTimer) -> {
                    player1.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(conv -> {
                        TriggerData data = conv.getTriggerData();
                        Burst burst = data.getBurst();
                        List<ArsenalEffect> effects = data.getEffects();
                        if (burst != null) {
                            List<LivingEntity> hitEntities = burst.getHitEntities(player1);
                            for (LivingEntity hitEntity : hitEntities) {
                                if (hitEntity != player1) {
                                    for (ArsenalEffect arsenalEffect : effects) {
                                        arsenalEffect.process(player1, hitEntity);
                                    }
                                }
                            }
                        }
                        player1.kill();
                        Messages.sendToTrackingAndSelf(GenericToClientPacket.makeExplosionBlood(player1.getX(), player.getY(), player.getZ(), burst == null ? 2 : burst.getExtension()), player1);
                    });
                }).toTimer();
                PlayerTimerData.for_(player).addTimer(timer);
                if (crossSync.getTransformation() == PlayerTransformation.ABOMINATION_0) {
                    Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(player, AnimationRegistry.ammunition_explode), player);
                    Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(player, AnimationRegistry.ammunition_explode_body), player);
                } else if (crossSync.getTransformation() == PlayerTransformation.ABOMINATION_1) {
                    Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(player, AnimationRegistry.abomination1_explode), player);
                } else if (crossSync.getTransformation() == PlayerTransformation.ABOMINATION_2) {
                    Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(player, AnimationRegistry.abomination2_explode), player);
                }
            }
        });

    }

    public static void transform(ServerPlayer player) {
        if (DataUtil.getBoolean(player, PlayerDataLib.metamorphosis.name())) {
            player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(c -> {
                CrossSync crossSync = c.getCrossSync();
                PlayerTimerData playerTimerData = PlayerTimerData.for_(player);
                if (!playerTimerData.hasTimer("transform_cooldown")) {
                    if (crossSync.getTransformation() == null || crossSync.getTransformation() == PlayerTransformation.SCALED) {
                        crossSync.setTransformation(PlayerTransformation.DEEP_ONE, player);
                        player.level().playSound(null, player.blockPosition(), BTVSounds.DEEP_ONE_TRANSFORM.get(), SoundSource.PLAYERS);
                        playerTimerData.addTimer(new PlayerTimer(TRANSFORM_COOLDOWN, "transform_cooldown", PersistentPlayerTimer.TRANSFORM_COOLDOWN, new HashMap<>()));
                    } else if (crossSync.getTransformation() == PlayerTransformation.DEEP_ONE) {
                        crossSync.setTransformation(null, player);
                        playerTimerData.addTimer(new PlayerTimer(TRANSFORM_COOLDOWN, "transform_cooldown", PersistentPlayerTimer.TRANSFORM_COOLDOWN, new HashMap<>()));
                    }
                } else {
                    player.sendSystemMessage(Component.translatable("message.transformation.cooldown", playerTimerData.getTimer("transform_cooldown").getRemainingTime() / 20 + 1));
                }
            });
        }
    }

}
