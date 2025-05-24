package com.valeriotor.beyondtheveil.util.timers;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;

public class ContactTimer extends PlayerTimer {
    private int time;
    private boolean done;
    private static final Map<Integer, DeepOneEntity.ContactType> SPAWN_TIMERS = Map.of(100, DeepOneEntity.ContactType.MOVE1, 170, DeepOneEntity.ContactType.MOVE2, 210, DeepOneEntity.ContactType.MOVE3);

    public ContactTimer() {
        super(Integer.MAX_VALUE, "contact", null, new HashMap<>());
        time = 0;
    }

    @Override
    public boolean update(Player player) {
        time++;
        if(player.getVehicle() instanceof CanoeEntity canoe) {
            DeepOneEntity.ContactType contactType = SPAWN_TIMERS.get(time);
            if (contactType != null) {
                DeepOneEntity deepOne = new DeepOneEntity(BTVEntities.DEEP_ONE.get(), player.level());
                deepOne.setContact(contactType, player);
                double radius = 55D / contactType.getFactor() / 2;
                double x = player.getX() + Math.sin(contactType.getStartOffset() * Math.PI / 50 + canoe.getYRot()) * radius;
                double z = player.getZ() + -Math.cos(contactType.getStartOffset() * Math.PI / 50 + canoe.getYRot()) * radius;
                deepOne.setPos(x, player.getY() - 2.2, z);
                deepOne.setExtraCounterOffset((int) (canoe.getYRot() * 50 / Math.PI));
                player.level().addFreshEntity(deepOne);
                ((ServerLevel) player.level()).sendParticles(ParticleTypes.DRIPPING_WATER, x, player.getY() - 1, z, 25, 0, 0.1, 0, 1);
                ((ServerLevel) player.level()).sendParticles(ParticleTypes.UNDERWATER, x, player.getY() - 1, z, 25, 0, 0.1, 0, 1);
                player.level().playSound(null, new BlockPos((int) x, (int) (player.getY() - 1), (int) z), SoundEvents.HOSTILE_SPLASH, SoundSource.NEUTRAL, 1, 1);
            }
            if (time % 20 == 0) {
                Messages.sendToPlayer(GenericToClientPacket.renewContact(), (ServerPlayer) player);
            }
            if (Set.of(78, 168, 237).contains(time)) {
                player.level().playSound(null, new BlockPos((int) (player.getX() + player.getRandom().nextInt(24) - 12), (int) (player.getY() - 1), (int) (player.getZ() + player.getRandom().nextInt(24) - 12)), SoundEvents.PLAYER_SPLASH, SoundSource.NEUTRAL, 1, 1);
            }
            if (time == 370) {
                DeepOneEntity deepOne = new DeepOneEntity(BTVEntities.DEEP_ONE.get(), player.level());
                double x = player.getX();// + Math.sin(contactType.getStartOffset() * Math.PI / 50) * radius;
                double z = player.getZ();// + -Math.cos(contactType.getStartOffset() * Math.PI / 50) * radius;
                deepOne.setPos(x, player.getY() - 1.2, z);
                player.level().addFreshEntity(deepOne);
                canoe.startRiding(deepOne);
                deepOne.setContact(DeepOneEntity.ContactType.TRADE, player);
            }
            if (time == 379) {
                player.level().playSound(null, player.getOnPos(), SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, SoundSource.NEUTRAL, 1, 1);
                Messages.sendToPlayer(GenericToClientPacket.shakeCamera(), (ServerPlayer) player);
            }
            if (time > 379) {
                List<ItemEntity> tears = player.level().getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(player.position(), 3, 3, 3), e -> e.getItem().getItem() == Items.GHAST_TEAR);
                for (ItemEntity tear : tears) {
                    done = true;
                    tear.discard();
                    player.level().playSound(null, player.getOnPos(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL);
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Registration.SHELL.get()));
                    DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.HAD_CONTACT, true, false);
                    break;
                }
            }
        } else {
            done = true;
        }
        return super.update(player);
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
