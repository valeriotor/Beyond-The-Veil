package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.world.effect.MobEffects;

import java.util.HashMap;
import java.util.Map;

public class ArsenalEffectRegistry {

    static final Map<String, ArsenalEffectType> REGISTRY = new HashMap<>();

    public static final ArsenalEffectType MOVEMENT_SPEED = register(new ArsenalEffectType.VanillaArsenalEffectType("movement_speed", MobEffects.MOVEMENT_SPEED));
    public static final ArsenalEffectType MOVEMENT_SLOWDOWN = register(new ArsenalEffectType.VanillaArsenalEffectType("movement_slowdown", MobEffects.MOVEMENT_SLOWDOWN));
    public static final ArsenalEffectType DIG_SPEED = register(new ArsenalEffectType.VanillaArsenalEffectType("dig_speed", MobEffects.DIG_SPEED));
    public static final ArsenalEffectType DIG_SLOWDOWN = register(new ArsenalEffectType.VanillaArsenalEffectType("dig_slowdown", MobEffects.DIG_SLOWDOWN));
    public static final ArsenalEffectType DAMAGE_BOOST = register(new ArsenalEffectType.VanillaArsenalEffectType("damage_boost", MobEffects.DAMAGE_BOOST));
    public static final ArsenalEffectType HEAL = register(new ArsenalEffectType.VanillaArsenalEffectType("heal", MobEffects.HEAL));
    public static final ArsenalEffectType HARM = register(new ArsenalEffectType.VanillaArsenalEffectType("harm", MobEffects.HARM));
    public static final ArsenalEffectType JUMP = register(new ArsenalEffectType.VanillaArsenalEffectType("jump", MobEffects.JUMP));
    public static final ArsenalEffectType CONFUSION = register(new ArsenalEffectType.VanillaArsenalEffectType("confusion", MobEffects.CONFUSION));
    public static final ArsenalEffectType REGENERATION = register(new ArsenalEffectType.VanillaArsenalEffectType("regeneration", MobEffects.REGENERATION));
    public static final ArsenalEffectType DAMAGE_RESISTANCE = register(new ArsenalEffectType.VanillaArsenalEffectType("damage_resistance", MobEffects.DAMAGE_RESISTANCE));
    public static final ArsenalEffectType FIRE_RESISTANCE = register(new ArsenalEffectType.VanillaArsenalEffectType("fire_resistance", MobEffects.FIRE_RESISTANCE));
    public static final ArsenalEffectType WATER_BREATHING = register(new ArsenalEffectType.VanillaArsenalEffectType("water_breathing", MobEffects.WATER_BREATHING));
    public static final ArsenalEffectType INVISIBILITY = register(new ArsenalEffectType.VanillaArsenalEffectType("invisibility", MobEffects.INVISIBILITY));
    public static final ArsenalEffectType BLINDNESS = register(new ArsenalEffectType.VanillaArsenalEffectType("blindness", MobEffects.BLINDNESS));
    public static final ArsenalEffectType NIGHT_VISION = register(new ArsenalEffectType.VanillaArsenalEffectType("night_vision", MobEffects.NIGHT_VISION));
    public static final ArsenalEffectType HUNGER = register(new ArsenalEffectType.VanillaArsenalEffectType("hunger", MobEffects.HUNGER));
    public static final ArsenalEffectType WEAKNESS = register(new ArsenalEffectType.VanillaArsenalEffectType("weakness", MobEffects.WEAKNESS));
    public static final ArsenalEffectType POISON = register(new ArsenalEffectType.VanillaArsenalEffectType("poison", MobEffects.POISON));
    public static final ArsenalEffectType WITHER = register(new ArsenalEffectType.VanillaArsenalEffectType("wither", MobEffects.WITHER));
    public static final ArsenalEffectType HEALTH_BOOST = register(new ArsenalEffectType.VanillaArsenalEffectType("health_boost", MobEffects.HEALTH_BOOST));
    public static final ArsenalEffectType ABSORPTION = register(new ArsenalEffectType.VanillaArsenalEffectType("absorption", MobEffects.ABSORPTION));
    public static final ArsenalEffectType SATURATION = register(new ArsenalEffectType.VanillaArsenalEffectType("saturation", MobEffects.SATURATION));
    public static final ArsenalEffectType GLOWING = register(new ArsenalEffectType.VanillaArsenalEffectType("glowing", MobEffects.GLOWING));
    public static final ArsenalEffectType LEVITATION = register(new ArsenalEffectType.VanillaArsenalEffectType("levitation", MobEffects.LEVITATION));
    public static final ArsenalEffectType LUCK = register(new ArsenalEffectType.VanillaArsenalEffectType("luck", MobEffects.LUCK));
    public static final ArsenalEffectType UNLUCK = register(new ArsenalEffectType.VanillaArsenalEffectType("unluck", MobEffects.UNLUCK));
    public static final ArsenalEffectType SLOW_FALLING = register(new ArsenalEffectType.VanillaArsenalEffectType("slow_falling", MobEffects.SLOW_FALLING));
    public static final ArsenalEffectType CONDUIT_POWER = register(new ArsenalEffectType.VanillaArsenalEffectType("conduit_power", MobEffects.CONDUIT_POWER));
    public static final ArsenalEffectType DOLPHINS_GRACE = register(new ArsenalEffectType.VanillaArsenalEffectType("dolphins_grace", MobEffects.DOLPHINS_GRACE));
    public static final ArsenalEffectType BAD_OMEN = register(new ArsenalEffectType.VanillaArsenalEffectType("bad_omen", MobEffects.BAD_OMEN));


    // vulnerability
    // disrobe
    // drop weapon
    // terror
    // folly
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
    // random movements (status effect moving the target left and right every second or so)
    // short immortality (but hp is set to half a heart at effect's end)
    // a

    private static ArsenalEffectType register(ArsenalEffectType type) {
        REGISTRY.put(type.getName(), type);
        return type;
    }


    // unrelated: blood spell to teleport yourself to a random nearby area (to get out of sticky situations) to get out of sticky situations



}
