package com.valeriotor.beyondtheveil.bossfights;

import com.valeriotor.beyondtheveil.entities.bosses.EntityArenaBoss;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneBrute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ArenaFightHandler {
    public static final int DEEP_ONE_BRUTE = 0;
    public static final int DEEP_ONE_MYRMIDON = 1;
    public static final int SCION_OF_DAGON = 2;

    private static long innerCounter = 0;
    private static final Map<UUID, ArenaFight> fights = new HashMap<>();

    public static void serverTick() {
        innerCounter++;
        if((innerCounter & 15) == 0) {
            Iterator<Map.Entry<UUID, ArenaFight>> iterator = fights.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<UUID, ArenaFight> entry = iterator.next();
                ArenaFight fight = entry.getValue();
                if(fight.hasPlayerLeftArena()) {
                    iterator.remove();
                } else {
                    fight.updateFight();
                }
            }
        }
    }

    public static boolean isPlayerInFight(UUID playerID) {
        return fights.containsKey(playerID);
    }

    public static boolean isArenaOccupied(BlockPos pos) {
        for(Map.Entry<UUID, ArenaFight> entry : fights.entrySet()) {
            if(entry.getValue().equalsStarterPos(pos))
                return true;
        }
        return false;
    }

    public static void startFight(EntityPlayer player, BlockPos arenaStarterPos, int bossID) {
        fights.put(player.getPersistentID(), new ArenaFight(player, arenaStarterPos));
        EntityArenaBoss boss = null;
        switch (bossID) {
            case DEEP_ONE_BRUTE: boss = new EntityDeepOneBrute(player.world, player); break;
            case DEEP_ONE_MYRMIDON:
            case SCION_OF_DAGON:
        }
        if(boss != null) {
            boss.setPosition(arenaStarterPos.getX()+20, arenaStarterPos.getY()+5, arenaStarterPos.getZ()-20);
            player.world.spawnEntity(boss);
        }
    }

    public static void removeArenaFight(UUID playerID) {
        fights.remove(playerID);
    }

    public static void endFight(UUID playerID) {
        ArenaFight fight = fights.remove(playerID);
        if(fight != null)
            fight.endFight();
    }

}
