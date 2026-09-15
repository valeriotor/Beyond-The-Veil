package com.valeriotor.beyondtheveil.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.PlayerTransformation;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class AttributeSets {

    public static final float MAX_DEEP_ONE_DAMAGE = 5.0F;
    public static final float MAX_DEEP_ONE_DAMAGE_IN_WATER = 3.0F;


    public static void addBaptismAttributes(Player p) {
        if (!p.level().isClientSide) {
            if (DataUtil.getBoolean(p, PlayerDataLib.baptized.name())) {
                Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier("baptism_swim_speed", 2.2, AttributeModifier.Operation.MULTIPLY_BASE));
                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("baptism_attack", 1.1, AttributeModifier.Operation.MULTIPLY_BASE));
                p.getAttributes().addTransientAttributeModifiers(map);
            }
        }
    }

    public static void addCrawlingAttributes(Player p) {
        if (!p.level().isClientSide) {
            p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(c -> {
                if (c.getCrossSync().isCrawling()) {
                    Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                    map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("crawling_speed", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    p.getAttributes().addTransientAttributeModifiers(map);
                }
            });
        }
    }


    private static final UUID DEEP_ONE_SPEED = UUID.fromString("378fd9fb-1464-431d-bf19-bc6853d68d1b");
    private static final UUID DEEP_ONE_ATTACK = UUID.fromString("32031573-1d62-4a46-b0be-04e3e690f457");
    private static final UUID DEEP_ONE_KNOCKBACK = UUID.fromString("dd56492a-9b9d-4b6b-ae5d-6f812956b49b");
    private static final UUID DEEP_ONE_HEALTH = UUID.fromString("8c498269-ccf0-4e93-9fb8-c4a5eda417f7");
    private static final UUID DEEP_ONE_STEP = UUID.fromString("a50208d8-3cc7-4411-8c99-7b6ca0059435");
    private static final UUID DEEP_ONE_SWIM = UUID.fromString("a5d25a41-80b5-4cd7-b9f8-a188244b1f78");
    private static final UUID DEEP_ONE_ENTITY_REACH = UUID.fromString("9a2d2f77-19f3-4420-9fcf-706b0a6bd0da");
    private static final UUID DEEP_ONE_BLOCK_REACH = UUID.fromString("d7cda8a9-156d-402d-9fb6-8125508a9959");

    public static void applyDeepOneAttributes(Player p, boolean add) {
        if (!p.level().isClientSide) {
            p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(c -> {
                Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(DEEP_ONE_SPEED, "deep_one_speed", 2, AttributeModifier.Operation.MULTIPLY_BASE));
                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DEEP_ONE_ATTACK, "deep_one_attack", 18, AttributeModifier.Operation.ADDITION));
                map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(DEEP_ONE_KNOCKBACK, "deep_one_knockback", 0.5, AttributeModifier.Operation.ADDITION));
                map.put(Attributes.MAX_HEALTH, new AttributeModifier(DEEP_ONE_HEALTH, "deep_one_health", 40, AttributeModifier.Operation.ADDITION));
                map.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(DEEP_ONE_STEP, "deep_one_step", 0.5, AttributeModifier.Operation.ADDITION));
                map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(DEEP_ONE_SWIM, "deep_one_swim", 7.5, AttributeModifier.Operation.MULTIPLY_BASE));
                map.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(DEEP_ONE_ENTITY_REACH, "deep_one_entity_reach", 1, AttributeModifier.Operation.ADDITION));
                map.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(DEEP_ONE_BLOCK_REACH, "deep_one_block_reach", 1, AttributeModifier.Operation.ADDITION));
                if (add) {
                    p.getAttributes().addTransientAttributeModifiers(map);
                } else {
                    p.getAttributes().removeAttributeModifiers(map);
                }
            });
        }
    }
}
