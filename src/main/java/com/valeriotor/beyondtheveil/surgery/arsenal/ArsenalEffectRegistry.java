package com.valeriotor.beyondtheveil.surgery.arsenal;

import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffectType.ArsenalStatusEffectType.DurationArray;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

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
    public static final ArsenalEffectType DISROBE = register(new ArsenalEffectType.ArsenalStatusEffectType("disrobe", BTVEffects.DISROBE.get(), DurationArray.SMALL_DESCENDING));
    public static final ArsenalEffectType DROP_ITEM = register(new ArsenalEffectType.ArsenalStatusEffectType("drop_item", BTVEffects.DROP_ITEM.get(), DurationArray.SMALL_DESCENDING));
    public static final ArsenalEffectType FOLLY = register(new ArsenalEffectType.ArsenalStatusEffectType("folly", BTVEffects.FOLLY.get()));
    public static final ArsenalEffectType TERROR = register(new ArsenalEffectType.ArsenalStatusEffectType("terror", BTVEffects.TERROR.get()));
    public static final ArsenalEffectType FEARSOME = register(new ArsenalEffectType.ArsenalStatusEffectType("fearsome", BTVEffects.FEARSOME.get()));
    public static final ArsenalEffectType SINK = register(new ArsenalEffectType.ArsenalStatusEffectType("sink", BTVEffects.SINK.get()));

    public static final ArsenalEffectType HARM_UNDEAD = register(new ArsenalEffectType("harm_undead") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            if (target.getMobType() == MobType.UNDEAD) {
                target.hurt(target.damageSources().magic(), (float) (15 << amplifier));
            }
        }
    });

    public static final ArsenalEffectType HARM_ARTHROPODS = register(new ArsenalEffectType("harm_arthropods") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            if (target.getMobType() == MobType.ARTHROPOD) {
                target.hurt(target.damageSources().magic(), (float) (15 << amplifier));
            }
        }
    });

    public static final ArsenalEffectType ENWEB = register(new ArsenalEffectType("enweb") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            amplifier = 0;
            Level l = target.level();
            for (int x = -amplifier; x <= amplifier; x++) {
                for (int z = -amplifier; z <= amplifier; z++) {
                    for (int y = 0; y < 2 + amplifier - (Math.abs(x) + Math.abs(z)) / 2; y++) {
                        BlockPos pos = new BlockPos(target.blockPosition().offset(x, y, z));
                        BlockState blockState = l.getBlockState(pos);
                        if (blockState.canBeReplaced()) {
                            l.destroyBlock(pos, true, attacker);
                            l.setBlock(pos, Blocks.COBWEB.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    });

    public static final ArsenalEffectType DAMAGE_ARMOR = register(new ArsenalEffectType("damage_armor") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            double damagePercent = 0.15 * amplifier;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.isArmor()) {
                    ItemStack armorSlot = target.getItemBySlot(slot);
                    if (armorSlot.isDamageableItem()) {
                        armorSlot.hurtAndBreak((int) (armorSlot.getMaxDamage() * damagePercent), target, e -> e.broadcastBreakEvent(slot));
                    }
                }
            }
        }
    });

    public static final ArsenalEffectType KNOCK_UPWARDS = register(new ArsenalEffectType("knock_upwards") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            if (target instanceof ServerPlayer player) {
                Messages.sendToPlayer(GenericToClientPacket.movePlayer(0, amplifier, 0), player);
            } else {
                target.setDeltaMovement(0, amplifier, 0);
            }
        }
    });

    public static final ArsenalEffectType CREATE_SLIME = register(new ArsenalEffectType("create_slime") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            if (!(target instanceof Slime)) {
                Slime slime = new Slime(EntityType.SLIME, target.level());
                slime.setSize((amplifier + 2) * 2, true);
                slime.setPos(target.position());
                target.level().addFreshEntity(slime);
                slime.setTarget(target);
            }
        }
    });

    public static final ArsenalEffectType EVERYONE_TARGET = register(new ArsenalEffectType("everyone_target") {
        @Override
        public void doEffect(Mob attacker, LivingEntity target, int duration, int amplifier, boolean hideParticles) {
            double d = switch (amplifier) {
                case 0 -> 10;
                case 1 -> 20;
                case 2 -> 35;
                case 3 -> 50;
                default -> throw new IllegalStateException("Unexpected value: " + amplifier);
            };
            AABB aabb = AABB.ofSize(target.position(), d, d, d);
            for (Entity entity : target.level().getEntities(target, aabb, e -> !(e instanceof PlayerMinion))) {
                if (entity instanceof Mob mob) {
                    mob.setTarget(target);
                }
            }
        }
    });

    // encase in ice
    // make spectral (can't hit or be hit, render semi-invisible? Can we use mixins? Must change line 120-121 of LivingEntityRenderer)
    // switch place with master
    // reflect damage (if powered it reflects to the owner of the attacking entity, if any)
    // explode after a few seconds
    // mimic blocks?? (render 2 blocks or more in place of the entity, depending on what was under them at that point. Inspired by sandflatter)
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
