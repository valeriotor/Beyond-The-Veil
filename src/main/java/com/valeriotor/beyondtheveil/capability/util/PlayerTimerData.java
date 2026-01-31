package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PlayerTimerData {

    public static PlayerTimerData for_(Player player) {
        return player.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).orElse(DUMMY);
    }
    private final List<PlayerTimer> playerTimers = new ArrayList<>();
    private static final PlayerTimerData DUMMY = new Dummy();

    public PlayerTimer getTimer(String name) {
        for (PlayerTimer playerTimer : playerTimers) {
            if (Objects.equals(playerTimer.getId(), name)) {
                return playerTimer;
            }
        }
        return null;
    }

    public boolean hasTimer(String name) {
        for (PlayerTimer playerTimer : playerTimers) {
            if (Objects.equals(playerTimer.getId(), name)) {
                return true;
            }
        }
        return false;
    }

    public boolean addTimer(PlayerTimer playerTimer) {
        if (playerTimers.contains(playerTimer)) {
            return false;
        }
        playerTimers.add(playerTimer);
        return true;
    }

    public boolean removeTimer(String id) {
        return playerTimers.removeIf(t -> Objects.equals(t.getId(), id));
    }

    public void tick(Player player) {
        Iterator<PlayerTimer> iterator = playerTimers.iterator();
        while (iterator.hasNext()) {
            PlayerTimer playerTimer = iterator.next();
            if (playerTimer.update(player)) {
                iterator.remove();
            }
        }
    }


    public void saveToNBT(CompoundTag compoundTag) {
        for (int i = 0; i < playerTimers.size(); i++) {
            CompoundTag tag = playerTimers.get(i).writeToNBT();
            if (tag != null) {
                compoundTag.put(String.valueOf(i), tag); // irrelevant if tag keys are non-contiguous
            }
        }
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        for (String key : compoundTag.getAllKeys()) {
            playerTimers.add(PlayerTimer.fromNBT(compoundTag.getCompound(key)));
        }
    }

    public void copyToNewStore(PlayerTimerData newStore) {
        newStore.playerTimers.clear();
        newStore.playerTimers.addAll(playerTimers.stream().filter(PlayerTimer::copyOnPlayerClone).toList());
    }

    private static class Dummy extends PlayerTimerData {
        @Override public PlayerTimer getTimer(String name) {return null;}

        @Override public boolean hasTimer(String name) {return false;}

        @Override public boolean addTimer(PlayerTimer playerTimer) {return false;}

        @Override public void tick(Player player) {}

        @Override public void saveToNBT(CompoundTag compoundTag) {}

        @Override public void loadFromNBT(CompoundTag compoundTag) {}
    }

}
