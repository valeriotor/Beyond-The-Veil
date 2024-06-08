package com.valeriotor.beyondtheveil.surgery.arsenal;

import com.valeriotor.beyondtheveil.lib.BTVEffects;
import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;
import java.util.Map;

public class ArsenalEffectRegistry {

    static final Map<String, ArsenalEffectType> REGISTRY = new HashMap<>();

    public static final ArsenalEffectType MOVEMENT_SPEED = register(new ArsenalEffectType.ArsenalStatusEffectType("movement_speed", MobEffects.MOVEMENT_SPEED));
    public static final ArsenalEffectType MOVEMENT_SLOWDOWN = register(new ArsenalEffectType.ArsenalStatusEffectType("movement_slowdown", MobEffects.MOVEMENT_SLOWDOWN));
    public static final ArsenalEffectType DIG_SPEED = register(new ArsenalEffectType.ArsenalStatusEffectType("dig_speed", MobEffects.DIG_SPEED));
    public static final ArsenalEffectType DIG_SLOWDOWN = register(new ArsenalEffectType.ArsenalStatusEffectType("dig_slowdown", MobEffects.DIG_SLOWDOWN));
    public static final ArsenalEffectType DAMAGE_BOOST = register(new ArsenalEffectType.ArsenalStatusEffectType("damage_boost", MobEffects.DAMAGE_BOOST));
    public static final ArsenalEffectType HEAL = register(new ArsenalEffectType.ArsenalStatusEffectType("heal", MobEffects.HEAL));
    public static final ArsenalEffectType HARM = register(new ArsenalEffectType.ArsenalStatusEffectType("harm", MobEffects.HARM));
    public static final ArsenalEffectType JUMP = register(new ArsenalEffectType.ArsenalStatusEffectType("jump", MobEffects.JUMP));
    public static final ArsenalEffectType CONFUSION = register(new ArsenalEffectType.ArsenalStatusEffectType("confusion", MobEffects.CONFUSION));
    public static final ArsenalEffectType REGENERATION = register(new ArsenalEffectType.ArsenalStatusEffectType("regeneration", MobEffects.REGENERATION));
    public static final ArsenalEffectType DAMAGE_RESISTANCE = register(new ArsenalEffectType.ArsenalStatusEffectType("damage_resistance", MobEffects.DAMAGE_RESISTANCE));
    public static final ArsenalEffectType FIRE_RESISTANCE = register(new ArsenalEffectType.ArsenalStatusEffectType("fire_resistance", MobEffects.FIRE_RESISTANCE));
    public static final ArsenalEffectType WATER_BREATHING = register(new ArsenalEffectType.ArsenalStatusEffectType("water_breathing", MobEffects.WATER_BREATHING));
    public static final ArsenalEffectType INVISIBILITY = register(new ArsenalEffectType.ArsenalStatusEffectType("invisibility", MobEffects.INVISIBILITY));
    public static final ArsenalEffectType BLINDNESS = register(new ArsenalEffectType.ArsenalStatusEffectType("blindness", MobEffects.BLINDNESS));
    public static final ArsenalEffectType NIGHT_VISION = register(new ArsenalEffectType.ArsenalStatusEffectType("night_vision", MobEffects.NIGHT_VISION));
    public static final ArsenalEffectType HUNGER = register(new ArsenalEffectType.ArsenalStatusEffectType("hunger", MobEffects.HUNGER));
    public static final ArsenalEffectType WEAKNESS = register(new ArsenalEffectType.ArsenalStatusEffectType("weakness", MobEffects.WEAKNESS));
    public static final ArsenalEffectType POISON = register(new ArsenalEffectType.ArsenalStatusEffectType("poison", MobEffects.POISON));
    public static final ArsenalEffectType WITHER = register(new ArsenalEffectType.ArsenalStatusEffectType("wither", MobEffects.WITHER));
    public static final ArsenalEffectType HEALTH_BOOST = register(new ArsenalEffectType.ArsenalStatusEffectType("health_boost", MobEffects.HEALTH_BOOST));
    public static final ArsenalEffectType ABSORPTION = register(new ArsenalEffectType.ArsenalStatusEffectType("absorption", MobEffects.ABSORPTION));
    public static final ArsenalEffectType SATURATION = register(new ArsenalEffectType.ArsenalStatusEffectType("saturation", MobEffects.SATURATION));
    public static final ArsenalEffectType GLOWING = register(new ArsenalEffectType.ArsenalStatusEffectType("glowing", MobEffects.GLOWING));
    public static final ArsenalEffectType LEVITATION = register(new ArsenalEffectType.ArsenalStatusEffectType("levitation", MobEffects.LEVITATION));
    public static final ArsenalEffectType LUCK = register(new ArsenalEffectType.ArsenalStatusEffectType("luck", MobEffects.LUCK));
    public static final ArsenalEffectType UNLUCK = register(new ArsenalEffectType.ArsenalStatusEffectType("unluck", MobEffects.UNLUCK));
    public static final ArsenalEffectType SLOW_FALLING = register(new ArsenalEffectType.ArsenalStatusEffectType("slow_falling", MobEffects.SLOW_FALLING));
    public static final ArsenalEffectType CONDUIT_POWER = register(new ArsenalEffectType.ArsenalStatusEffectType("conduit_power", MobEffects.CONDUIT_POWER));
    public static final ArsenalEffectType DOLPHINS_GRACE = register(new ArsenalEffectType.ArsenalStatusEffectType("dolphins_grace", MobEffects.DOLPHINS_GRACE));
    public static final ArsenalEffectType BAD_OMEN = register(new ArsenalEffectType.ArsenalStatusEffectType("bad_omen", MobEffects.BAD_OMEN));
    public static final ArsenalEffectType VULNERABILITY = register(new ArsenalEffectType.ArsenalStatusEffectType("vulnerability", BTVEffects.VULNERABILITY.get()));


    // vulnerability (acid)
    // disrobe (higher duration modifier makes actual duration shorter rather than longer) (higher amplifier may make the armor fall on the ground or remove multiple pieces)
    // drop weapon (ditto)
    // folly
    // terror
    // fearsome
    // harm undead
    // harm arthropods
    // enweb
    // damage armor
    // create slime
    // encase in ice
    // make spectral (can't hit or be hit, render semi-invisible? Can we use mixins? Must change line 120-121 of LivingEntityRenderer)
    // teleport up
    // switch place with master
    // quick sink, extra fall damage, prevents flight
    // reflect damage (if powered it reflects to the owner of the attacking entity, if any)
    // explode after a few seconds
    // mimic blocks?? (render 2 blocks or more in place of the entity, depending on what was under them at that point. Inspired by sandflatter)
    // make everyone target you
    // make everyone who's targeting you target someone else
    // extra vulnerable to knockback
    // random movements (status effect moving the target left and right every second or so (less when powered))
    // short immortality (but hp is set to half a heart at effect's end)
    // a

    private static ArsenalEffectType register(ArsenalEffectType type) {
        REGISTRY.put(type.getName(), type);
        return type;
    }


    // unrelated: blood spell to teleport yourself to a random nearby area (to get out of sticky situations) to get out of sticky situations



}
