package com.valeriotor.beyondtheveil.rituals.bindings;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.timers.PlaceBlocksTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BindingEvents {

    private static final int END_DEATH_RECHARGE_TIMER = 10 * 60 * 20; // 10 mins

    public static InteractionResult useFistOnBlock(UseOnContext pContext) {
        if (pContext.getPlayer() instanceof ServerPlayer sp) {
            Level level = sp.level();
            BindingData data = DataUtil.getBindingData(sp);
            BlockPos clickedPos = pContext.getClickedPos();
            BlockPos posInFront = clickedPos.relative(pContext.getClickedFace());
            BlockState stateInFront = pContext.getLevel().getBlockState(posInFront);
            if (data != null) {
                if (data.getBinding() == Binding.OVERWORLD) {
                    if (stateInFront.canBeReplaced()) {
                        if (data.getOverworldPos1() == null) {
                            data.setOverworldPos1(posInFront);
                        } else if (data.getOverworldPos2() == null) {
                            data.setOverworldPos2(posInFront);
                        } else {
                            data.setOverworldPos1(null);
                            data.setOverworldPos2(null);
                        }
                        DataUtil.syncBindingData(sp);
                    }
                } else if (data.getBinding() == Binding.NETHER) {
                    if (BaseFireBlock.canBePlacedAt(level, posInFront, pContext.getHorizontalDirection())) {
                        level.playSound(null, posInFront, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                        BlockState blockstate1 = BaseFireBlock.getState(level, posInFront);
                        level.setBlock(posInFront, blockstate1, 11);
                        level.gameEvent(sp, GameEvent.BLOCK_PLACE, clickedPos);
                    }
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    public static boolean useFistInAir(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        BindingData data = DataUtil.getBindingData(pPlayer);

        if (data != null) {
            if (data.getBinding() == Binding.END) {
                if (pPlayer.isFallFlying()) {
                    return true;
                }
            }
            if (!pLevel.isClientSide) {
                if (data.getBinding() == Binding.OVERWORLD) {
                    data.setBlockBreakingMode(!data.isBlockBreakingMode());
                }
            }
        }


        return false;
    }

    public static void usingFist(Level pLevel, ServerPlayer sp) {
        if (sp.isFallFlying()) {
            BindingData data = DataUtil.getBindingData(sp);
            if (data != null && data.getBinding() == Binding.END && sp.tickCount % 3 == 0) {
                Messages.sendToPlayer(GenericToClientPacket.movePlayer(0, 0.5, 0, false, false, false), sp);
            }
        }
    }

    public static void placeBlock(BlockEvent.EntityPlaceEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null) {
            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState placedBlock = event.getPlacedBlock();
            if (data.getBinding() == Binding.OVERWORLD) {
                BlockPos overworldPos1 = data.getOverworldPos1();
                BlockPos overworldPos2 = data.getOverworldPos2();
                if (overworldPos1 != null && overworldPos2 != null) {
                    BlockPos pos1 = null, pos2 = null;
                    if (pos.equals(overworldPos1)) {
                        pos1 = overworldPos1;
                        pos2 = overworldPos2;
                    } else if (pos.equals(overworldPos2)) {
                        pos1 = overworldPos2;
                        pos2 = overworldPos1;
                    }
                    if (pos1 != null && !pos1.equals(pos2)) {
                        PlayerTimerData.for_(sp).addTimer(new PlaceBlocksTimer(pos1, pos2, placedBlock.getBlock()));
                    }
                }
            }
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null) {
            Level l = sp.level();
            if (data.getBinding() == Binding.OVERWORLD) {
                if (data.isBlockBreakingMode() && sp.tickCount % 3 == 0) {
                    if (sp.isShiftKeyDown()) {
                        for (int x = -1; x <= 1; x++) {
                            for (int z = -1; z <= 1; z++) {
                                BlockPos pos = sp.blockPosition().offset(x, -1, z);
                                BlockState state = l.getBlockState(pos);
                                if (state.getBlock().defaultDestroyTime() < 40 && state.getBlock().defaultDestroyTime() >= 0) {
                                    l.destroyBlock(pos, true, sp);
                                }
                            }
                        }
                    } else {
                        boolean lowX = sp.getX() - Math.floor(sp.getX()) < 0.5;
                        boolean lowZ = sp.getZ() - Math.floor(sp.getZ()) < 0.5;
                        boolean highX = sp.getX() - Math.floor(sp.getX()) > 0.5;
                        boolean highZ = sp.getZ() - Math.floor(sp.getZ()) > 0.5;

                        for (int y = 0; y < 3; y++) {
                            for (int i = -1; i <= 1; i++) {
                                if (lowX) {
                                    l.destroyBlock(new BlockPos(sp.getBlockX() - 1, sp.getBlockY() + y, sp.getBlockZ() + i), true, sp);
                                } else if (highX) {
                                    l.destroyBlock(new BlockPos(sp.getBlockX() + 1, sp.getBlockY() + y, sp.getBlockZ() + i), true, sp);
                                }
                                if (lowZ) {
                                    l.destroyBlock(new BlockPos(sp.getBlockX() + i, sp.getBlockY() + y, sp.getBlockZ() - 1), true, sp);
                                } else if (highZ) {
                                    l.destroyBlock(new BlockPos(sp.getBlockX() + i, sp.getBlockY() + y, sp.getBlockZ() + 1), true, sp);
                                }
                            }
                        }
                    }

                }
            } else if (data.getBinding() == Binding.END) {
                PlayerTimerData playerTimerData = PlayerTimerData.for_(sp);
                if (sp.isShiftKeyDown() && l.getBlockState(sp.blockPosition().below()).entityCanStandOn(l, sp.blockPosition().below(), sp) && !playerTimerData.hasTimer("recharge_end_binding_jump") && (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_FIST.get() || sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.BLOOD_FIST.get())) {
                    for (int y = sp.getBlockY() - 2; y > l.getMinBuildHeight(); y--) {
                        BlockPos pos = sp.blockPosition().atY(y);
                        BlockPos pos1 = pos.below();
                        BlockPos pos2 = pos1.below();
                        if (l.getBlockState(pos2).entityCanStandOn(l, pos2, sp) && !l.getBlockState(pos1).isSuffocating(l, pos1) && !l.getBlockState(pos).isSuffocating(l, pos)) {
                            if (data.drainEnergy(25)) {
                                sp.teleportTo(sp.getX(), pos1.getY(), sp.getZ());
                                playerTimerData.addTimer(new PlayerTimer(3, "recharge_end_binding_jump", null, new HashMap<>()));
                                sp.level().playSound(null, sp.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1, 1);
                                sp.serverLevel().broadcastEntityEvent(sp, (byte) 46);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void jump(LivingEvent.LivingJumpEvent event, ServerPlayer sp) {
        //sp.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().setBindingData(new BindingData(Binding.NETHER));
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null && data.getBinding() == Binding.END && (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_FIST.get() || sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.BLOOD_FIST.get())) {
            Level l = sp.level();
            for (int y = sp.getBlockY() + 2; y < l.getMaxBuildHeight(); y++) {
                BlockPos pos = sp.blockPosition().atY(y);
                BlockPos pos1 = pos.above();
                BlockPos pos2 = pos1.above();
                if (l.getBlockState(pos).entityCanStandOn(l, pos, sp) && !l.getBlockState(pos1).isSuffocating(l, pos1) && !l.getBlockState(pos2).isSuffocating(l, pos2)) {
                    if (data.drainEnergy(25)) {
                        sp.teleportTo(sp.getX(), pos1.getY(), sp.getZ());
                        sp.setDeltaMovement(0, 0, 0);
                        sp.level().playSound(null, sp.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1, 1);
                        sp.serverLevel().broadcastEntityEvent(sp, (byte) 46);
                    }
                    break;
                }
            }
        }
    }

    public static void playerAttackEvent(LivingAttackEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        LivingEntity attacked = event.getEntity();
        if (data != null) {
            if (sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.BLOOD_FIST.get()) {
                if (data.getBinding() == Binding.OVERWORLD) {
                    ItemStack stack = sp.getItemInHand(InteractionHand.MAIN_HAND);
                    double percentage = (double) stack.getDamageValue() / stack.getMaxDamage();
                    percentage -= 0.005;
                    stack.setDamageValue((int) Math.max(0, percentage * stack.getMaxDamage() - 2));
                } else if (data.getBinding() == Binding.NETHER) {
                    if (attacked.getMaxHealth() <= 80 && data.isInstantKill()) {
                        float requiredPlayerHealth = attacked.getMaxHealth() / 60.0F * 10 + 7;
                        if (sp.getHealth() > requiredPlayerHealth + 1 && data.drainEnergy((int) (requiredPlayerHealth * 2) + 2)) {
                            sp.setHealth(sp.getHealth() - requiredPlayerHealth);
                            attacked.kill();
                            PlayerTimerData.for_(sp).addTimer(new PlayerTimer(4, "no_nether_binding_instakill", null, new HashMap<>()));
                        }
                    }
                } else if (data.getBinding() == Binding.END) {
                    double angle = attacked.getYRot() * Math.PI / 180;
                    angle += Math.PI;
                    double x = -Math.sin(angle) * 2.4;
                    double z = Math.cos(angle) * 2.4;
                    sp.teleportTo(attacked.getX() + x, attacked.getY(), attacked.getZ() + z);
                    Messages.sendToPlayer(GenericToClientPacket.rotateCamera((float) ((angle + Math.PI) * 180 / Math.PI), 0, 0), sp);
                }
            }
            if (data.getBinding() == Binding.NETHER && !attacked.isDeadOrDying()) {
                if (Math.random() < 0.4) {
                    List<Entity> entities = attacked.level().getEntities(((Entity) null), AABB.ofSize(attacked.position(), 50, 50, 50), e -> (e instanceof Mob));
                    for (Entity entity : entities) {
                        if (entity instanceof Mob mob && Objects.equals(mob.getTarget(), sp)) {
                            if (Math.random() < 0.5) {
                                mob.setTarget(attacked);
                            }
                        }
                    }
                }
            }
        }

    }

    public static void joinLevelEvent(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity l && l.getMobType() != MobType.UNDEAD) {
            AABB aabb = event.getEntity().getBoundingBox().inflate(40);
            List<Player> nearbyPlayers = event.getLevel().getNearbyPlayers(TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(e -> {
                if (e instanceof ServerPlayer sp) {
                    BindingData data = DataUtil.getBindingData(sp);
                    if (data != null && data.getBinding() == Binding.OVERWORLD) {
                        return true;
                    }
                }
                return false;
            }), l, aabb);
            nearbyPlayers.forEach(p -> p.heal(2));
        }
    }

    public static void playerDeathEvent(LivingDeathEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null && data.getBinding() == Binding.END && data.drainEnergy(0)) {
            PlayerTimerData playerTimerData = PlayerTimerData.for_(sp);
            if (!playerTimerData.hasTimer("recharge_end_binding")) {
                playerTimerData.addTimer(new PlayerTimer(END_DEATH_RECHARGE_TIMER, "recharge_end_binding", PersistentPlayerTimer.RECHARGE_END_BINDING, new HashMap<>()));
                event.setCanceled(true);
                sp.setHealth(sp.getMaxHealth());
                double randomAngle = Math.random() * 2 * Math.PI;
                double x = sp.getX() + Math.sin(randomAngle) * 5000;
                double z = sp.getZ() + Math.cos(randomAngle) * 5000;
                sp.teleportTo(x, Math.min(110, sp.level().getMaxBuildHeight() - 30), z);
                sp.level().playSound(null, sp.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS);
            }
        }
    }

    public static void livingDeathEvent(LivingDeathEvent event) {
        AABB aabb = event.getEntity().getBoundingBox().inflate(40);
        List<Player> nearbyPlayers = event.getEntity().level().getNearbyPlayers(TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(e -> {
            if (e instanceof ServerPlayer sp) {
                BindingData data = DataUtil.getBindingData(sp);
                if (data != null && data.getBinding() == Binding.NETHER && !PlayerTimerData.for_(sp).hasTimer("no_nether_binding_instakill")) {
                    return true;
                }
            }
            return false;
        }), event.getEntity(), aabb);
        nearbyPlayers.forEach(p -> p.heal(2));

    }

    public static void minionDamageEvent(LivingDamageEvent event) {
        if (event.getSource() instanceof PlayerMinion minion) {
            ServerPlayer sp = minion.getMaster();
            BindingData data = DataUtil.getBindingData(sp);
            if (data != null && data.getBinding() == Binding.NETHER) {
                LivingEntity attacked = event.getEntity();
                attacked.setHealth(attacked.getHealth() - event.getAmount() * 0.3F);
                event.setAmount(event.getAmount() * 1.5F);
                sp.heal(1);
            }
        }
    }

    public static void playerDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && event.getSource().is(DamageTypeTags.IS_FIRE)) {
            BindingData data = DataUtil.getBindingData(sp);
            if (data != null && data.getBinding() == Binding.NETHER) {
                sp.getFoodData().setFoodLevel((int) Math.min(20, sp.getFoodData().getFoodLevel() + event.getAmount()));
            }
        }
    }
}
