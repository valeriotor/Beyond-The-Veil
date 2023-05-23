package com.valeriotor.beyondtheveil.dreaming;

import com.valeriotor.beyondtheveil.block.FumeSpreaderBlock;
import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.tile.FumeSpreaderBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class DreamHandler {

    public static void dream(Player p) {
        List<FumeSpreaderBE> spreaders = findFumeSpreader(p, p.level, p.getOnPos(), 1);
        //spreaders.sort(Comparator.comparingInt(be -> Dream.REGISTRY.get(be.getStoredMemory()).getPriority()));
        List<FumeSpreaderBE> successes = new ArrayList<>();
        for (FumeSpreaderBE be : spreaders) {
            if (Dream.REGISTRY.get(be.getStoredMemory()).activate(p, p.level)) {
                successes.add(be);
            }
        }

        for (FumeSpreaderBE spreader : spreaders) {
            //TODO long dream
            DataUtil.setBooleanOnServerAndSync(p, spreader.getStoredMemory().name() + "Dream", true, false);
            spreader.setStoredMemory(null);
            BlockPos pos = spreader.getBlockPos();
            p.level.setBlock(pos, p.level.getBlockState(pos).setValue(FumeSpreaderBlock.FULL, false), 3);
        }
    }
    private static final int[][] MULTIPLIERS = {{1,1},{-1,-1},{1,-1},{-1,1}};
    private static List<FumeSpreaderBE> findFumeSpreader(Player p, Level level, BlockPos playerPos, int max) {
        List<FumeSpreaderBE> spreaders = new ArrayList<>();
        if(max == 0) return spreaders;
        checkColumnForSpreader(p,0, 0, level, playerPos, spreaders, max);
        for (int i = 1; i < 4 && spreaders.size() < max; i++) {
            for (int j = 0; j <= i && spreaders.size() < max; j++) {
                int multipliersToCheck = j == 0 ? 2 : 4;
                for (int k = 0; k < multipliersToCheck && spreaders.size() < max; k++) {
                    int[] multiplier = MULTIPLIERS[k];
                    checkColumnForSpreader(p,i * multiplier[0], j * multiplier[1], level, playerPos, spreaders, max);
                    if (i != j) {
                        checkColumnForSpreader(p,j * multiplier[0], i * multiplier[1], level, playerPos, spreaders, max);
                    }
                }
            }
        }
        return spreaders;
    }

    private static void checkColumnForSpreader(Player p, int xOffset, int zOffset, Level level, BlockPos startPos, List<FumeSpreaderBE> spreaders, int max) {
        if(spreaders.size() >= max) return;
        for (int yOffset = -1; yOffset < 3; yOffset++) {
            BlockEntity entity = level.getBlockEntity(startPos.offset(xOffset, yOffset, zOffset));
            if (entity instanceof FumeSpreaderBE fumeSpreaderBE) {
                Memory storedMemory = fumeSpreaderBE.getStoredMemory();
                if (storedMemory != null && storedMemory.isUnlocked(p)) {
                    spreaders.add(fumeSpreaderBE);
                    if (spreaders.size() >= max) {
                        return;
                    }
                }
            }
        }
    }

}
