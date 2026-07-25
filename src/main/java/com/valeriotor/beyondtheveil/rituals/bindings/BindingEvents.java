package com.valeriotor.beyondtheveil.rituals.bindings;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.VanillaUtils;
import com.valeriotor.beyondtheveil.util.timers.PlaceBlocksTimer;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
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
                    if (BaseFireBlock.canBePlacedAt(level, posInFront, pContext.getHorizontalDirection()) && DataUtil.decreaseBindingEnergy(sp, BindingCosts.NETHER_CREATE_FIRE)) {
                        level.playSound(null, posInFront, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                        BlockState blockstate1 = BaseFireBlock.getState(level, posInFront);
                        level.setBlock(posInFront, blockstate1, 11);
                        level.gameEvent(sp, GameEvent.BLOCK_PLACE, clickedPos);
                    }
                } else if (data.getBinding() == Binding.ARCHE) {
                    if (level instanceof ServerLevel sl && DataUtil.decreaseBindingEnergy(sp, BindingCosts.ARCHE_NODE)) {
                        PlayerSavedData.getInstance(sl).addArcheBindingNode(sp, posInFront);
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
            } else if (data.getBinding() == Binding.ARCHE) {
                return true;
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
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null) {
            if (data.getBinding() == Binding.END && sp.tickCount % 3 == 0 && sp.isFallFlying() && DataUtil.decreaseBindingEnergy(sp, BindingCosts.END_FLY)) {
                Messages.sendToPlayer(GenericToClientPacket.movePlayer(0, 0.5, 0, false, false, false), sp);
            } else if (data.getBinding() == Binding.ARCHE && sp.tickCount % 3 == 0 && DataUtil.decreaseBindingEnergy(sp, BindingCosts.ARCHE_MOVE)) {
                List<Entity> entities = sp.level().getEntities(sp, AABB.ofSize(sp.position(), 30, 20, 30), e -> {
                    if (!(e instanceof LivingEntity)) return false;
                    if (!(e instanceof PlayerMinion minion)) return true;
                    return !Objects.equals(minion.getMasterID(), sp.getUUID());
                });
                if (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_FIST.get()) {
                    for (Entity entity : entities) {
                        moveEntityWithCenter(entity, sp.position(), sp.isShiftKeyDown(), false, true);
                    }
                } else {
                    if (sp.isShiftKeyDown()) {
                        for (Entity entity : entities) {
                            moveEntityWithCenter(entity, sp.position(), true, true, true);
                        }

                    } else {
                        double x = -Math.sin(sp.getYRot() * Math.PI / 180);
                        double z = Math.cos(sp.getYRot() * Math.PI / 180);
                        for (Entity entity : entities) {
                            moveEntity(entity, x, z, 1, true);
                        }
                    }
                }
            }
        }
    }

    public static void moveEntityWithCenter(Entity entity, Vec3 center, boolean away, boolean perpendicular, boolean playSound) {
        double x = entity.getX() - center.x();
        double z = entity.getZ() - center.z();
        double sqrt = Math.sqrt(x * x + z * z);
        x /= sqrt;
        z /= sqrt;
        if (!away) {
            x = -x;
            z = -z;
        }
        if (perpendicular) {
            double tmp = x;
            x = -z;
            z = tmp;
        }
        moveEntity(entity, x, z, !away || perpendicular ? 0.5F : 1, playSound);
    }

    private static void moveEntity(Entity entity, double x, double z, float pitch, boolean playSound) {
        if (entity instanceof ServerPlayer sp) {
            Messages.sendToPlayer(GenericToClientPacket.movePlayer(x, 0, z, false, false, false), sp);
        } else if (entity instanceof LivingEntity l) {
            l.setDeltaMovement(l.getDeltaMovement().add(x, 0, z));
        }
        if (entity.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.DRIPPING_WATER, entity.getX(), entity.getY(), entity.getZ(), 20, 1, 1, 1, 1);
            sl.sendParticles(ParticleTypes.BUBBLE, entity.getX(), entity.getY(), entity.getZ(), 10, 1, 1, 1, 1);
            if (playSound) {
                sl.playSound(null, entity.blockPosition(), SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 1, pitch);
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
                if (data.isBlockBreakingMode() && sp.tickCount % 3 == 0 && data.getEnergy() >= BindingCosts.OVERWORLD_BREAK) {
                    if (sp.isShiftKeyDown()) {
                        for (int x = -1; x <= 1; x++) {
                            for (int z = -1; z <= 1; z++) {
                                BlockPos pos = sp.blockPosition().offset(x, -1, z);
                                BlockState state = l.getBlockState(pos);
                                if (state.getBlock().defaultDestroyTime() < 40 && state.getBlock().defaultDestroyTime() >= 0) {
                                    if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_BREAK))
                                        l.destroyBlock(pos, true, sp);
                                    else return;
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
                                    BlockPos pos = new BlockPos(sp.getBlockX() - 1, sp.getBlockY() + y, sp.getBlockZ() + i);
                                    if (l.destroyBlock(pos, true, sp)) {
                                        DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_BREAK, false);
                                        if(data.getEnergy() < BindingCosts.OVERWORLD_BREAK) {
                                            DataUtil.syncBindingData(sp);
                                            return;
                                        }
                                    }
                                } else if (highX) {
                                    BlockPos pos = new BlockPos(sp.getBlockX() + 1, sp.getBlockY() + y, sp.getBlockZ() + i);
                                    if (l.destroyBlock(pos, true, sp)) {
                                        DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_BREAK, false);
                                        if(data.getEnergy() < BindingCosts.OVERWORLD_BREAK) {
                                            DataUtil.syncBindingData(sp);
                                            return;
                                        }
                                    }
                                }

                                if (lowZ) {
                                    BlockPos pos = new BlockPos(sp.getBlockX() + i, sp.getBlockY() + y, sp.getBlockZ() - 1);
                                    if (l.destroyBlock(pos, true, sp)) {
                                        DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_BREAK, false);
                                        if(data.getEnergy() < BindingCosts.OVERWORLD_BREAK) {
                                            DataUtil.syncBindingData(sp);
                                            return;
                                        }
                                    }
                                } else if (highZ) {
                                    BlockPos pos = new BlockPos(sp.getBlockX() + i, sp.getBlockY() + y, sp.getBlockZ() + 1);
                                    if (l.destroyBlock(pos, true, sp)) {
                                        DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_BREAK, false);
                                        if(data.getEnergy() < BindingCosts.OVERWORLD_BREAK) {
                                            DataUtil.syncBindingData(sp);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        DataUtil.syncBindingData(sp);
                    }

                }
            } else if (data.getBinding() == Binding.END) {
                PlayerTimerData playerTimerData = PlayerTimerData.for_(sp);
                if (sp.isShiftKeyDown() && data.getEnergy() >= BindingCosts.END_VERTICAL_TP && l.getBlockState(sp.blockPosition().below()).entityCanStandOn(l, sp.blockPosition().below(), sp) && !playerTimerData.hasTimer("recharge_end_binding_jump") && (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_FIST.get() || sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.BLOOD_FIST.get())) {
                    for (int y = sp.getBlockY() - 2; y > l.getMinBuildHeight(); y--) {
                        BlockPos pos = sp.blockPosition().atY(y);
                        BlockPos pos1 = pos.below();
                        BlockPos pos2 = pos1.below();
                        if (l.getBlockState(pos2).entityCanStandOn(l, pos2, sp) && !l.getBlockState(pos1).isSuffocating(l, pos1) && !l.getBlockState(pos).isSuffocating(l, pos)) {
                            if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.END_VERTICAL_TP)) {
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
        //sp.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().setBindingData(new BindingData(Binding.ARCHE));
        BindingData data = DataUtil.getBindingData(sp);
        //data.setSelectedType(BindingData.ArcheDamageType.values()[(data.getSelectedType() == null ? 0 : ((data.getSelectedType().ordinal() + 1) % BindingData.ArcheDamageType.values().length))]);
        if (data != null && data.getBinding() == Binding.END && data.getEnergy() >= BindingCosts.END_VERTICAL_TP && (sp.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_FIST.get() || sp.getItemInHand(InteractionHand.OFF_HAND).getItem() == Registration.BLOOD_FIST.get())) {
            Level l = sp.level();
            for (int y = sp.getBlockY() + 2; y < l.getMaxBuildHeight(); y++) {
                BlockPos pos = sp.blockPosition().atY(y);
                BlockPos pos1 = pos.above();
                BlockPos pos2 = pos1.above();
                if (l.getBlockState(pos).entityCanStandOn(l, pos, sp) && !l.getBlockState(pos1).isSuffocating(l, pos1) && !l.getBlockState(pos2).isSuffocating(l, pos2)) {
                    if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.END_VERTICAL_TP)) {
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
                    if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.OVERWORLD_REPAIR)) {
                        ItemStack stack = sp.getItemInHand(InteractionHand.MAIN_HAND);
                        double percentage = (double) stack.getDamageValue() / stack.getMaxDamage();
                        percentage -= 0.005;
                        stack.setDamageValue((int) Math.max(0, percentage * stack.getMaxDamage() - 2));
                    }
                } else if (data.getBinding() == Binding.NETHER) {
                    if (attacked.getMaxHealth() <= 80 && data.isInstantKill()) {
                        float requiredPlayerHealth = attacked.getMaxHealth() / 60.0F * 10 + 7;
                        if (sp.getHealth() > requiredPlayerHealth + 1 && DataUtil.decreaseBindingEnergy(sp, BindingCosts.NETHER_ATTACK.apply(requiredPlayerHealth))) {
                            sp.setHealth(sp.getHealth() - requiredPlayerHealth);
                            attacked.kill();
                            PlayerTimerData.for_(sp).addTimer(new PlayerTimer(4, "no_nether_binding_instakill", null, new HashMap<>()));
                        }
                    }
                } else if (data.getBinding() == Binding.END) {
                    if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.END_ATTACK)) {
                        double angle = attacked.getYRot() * Math.PI / 180;
                        angle += Math.PI;
                        double x = -Math.sin(angle) * 2.4;
                        double z = Math.cos(angle) * 2.4;
                        sp.teleportTo(attacked.getX() + x, attacked.getY(), attacked.getZ() + z);
                        Messages.sendToPlayer(GenericToClientPacket.rotateCamera((float) ((angle + Math.PI) * 180 / Math.PI), 0, 0), sp);
                    }
                } else if (data.getBinding() == Binding.ARCHE) {
                    if (Math.random() < 0.2) {
                        ItemStack mainHandItem = attacked.getMainHandItem();
                        if (!mainHandItem.isEmpty() && DataUtil.decreaseBindingEnergy(sp, BindingCosts.ARCHE_ATTACK)) {
                            VanillaUtils.dropItem(attacked);
                        }
                    }
                }
            }
            if (data.getBinding() == Binding.NETHER && !attacked.isDeadOrDying()) {
                if (Math.random() < 0.4) {
                    List<Entity> entities = attacked.level().getEntities(((Entity) null), AABB.ofSize(attacked.position(), 50, 50, 50), e -> (e instanceof Mob));
                    for (Entity entity : entities) {
                        if (entity instanceof Mob mob && Objects.equals(mob.getTarget(), sp)) {
                            if (Math.random() < 0.5 && DataUtil.decreaseBindingEnergy(sp, BindingCosts.NETHER_TARGET)) {
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
            nearbyPlayers.forEach(p -> {
                if (DataUtil.decreaseBindingEnergy(p, BindingCosts.OVERWORLD_HEAL)) {
                    p.heal(2);
                }
            });
        }
    }

    public static void playerDeathEvent(LivingDeathEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null && data.getBinding() == Binding.END && DataUtil.decreaseBindingEnergy(sp, BindingCosts.END_SURVIVE)) {
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
                if (data != null && data.getBinding() == Binding.NETHER && !PlayerTimerData.for_(sp).hasTimer("no_nether_binding_instakill") && DataUtil.decreaseBindingEnergy(sp, BindingCosts.NETHER_HEAL)) {
                    return true;
                }
            }
            return false;
        }), event.getEntity(), aabb);
        nearbyPlayers.forEach(p -> p.heal(2));

    }

    public static void minionDamageEvent(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof PlayerMinion minion) {
            ServerPlayer sp = minion.getMaster();
            if (sp != null) {
                BindingData data = DataUtil.getBindingData(sp);
                if (data != null && data.getBinding() == Binding.NETHER) {
                    LivingEntity attacked = event.getEntity();
                    attacked.setHealth(attacked.getHealth() - event.getAmount() * 0.3F);
                    event.setAmount(event.getAmount() * 1.5F);
                    sp.heal(1);
                }

            }
        }
    }

    public static void playerDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            BindingData data = DataUtil.getBindingData(sp);
            if (data != null) {
                if (data.getBinding() == Binding.NETHER && event.getSource().is(DamageTypeTags.IS_FIRE) && DataUtil.decreaseBindingEnergy(sp, BindingCosts.NETHER_FEED)) {
                    sp.getFoodData().setFoodLevel((int) Math.min(20, sp.getFoodData().getFoodLevel() + event.getAmount() * 3));
                }
            }
        }
    }

    public static void playerHurtEvent(LivingHurtEvent event, ServerPlayer sp) {
        BindingData data = DataUtil.getBindingData(sp);
        if (data != null) {
            if (data.getBinding() == Binding.ARCHE) {
                int resistance = data.checkArcheResistance(event.getSource());
                if (resistance > 0) {
                    if (DataUtil.decreaseBindingEnergy(sp, BindingCosts.ARCHE_HEAL.apply(event.getAmount()))) {
                        event.setCanceled(true);
                        sp.heal(event.getAmount());
                    }
                } else if (resistance < 0) {
                    event.setAmount(event.getAmount() * 2);
                }
            }
        }
    }
}
