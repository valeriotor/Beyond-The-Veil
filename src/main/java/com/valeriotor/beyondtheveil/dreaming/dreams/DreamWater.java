package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DreamWater extends Dream{

    public DreamWater() {
        super(Memory.WATER, 5, ReminiscenceWaypoint::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        return activatePos(p, l, p.getOnPos());
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        return false;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        ServerLevel sl = (ServerLevel) l;
        BlockPos blockpos = sl.findNearestMapStructure(StructureTags.ON_OCEAN_EXPLORER_MAPS, pos, 100, false);
        if (blockpos != null) {
            //DataUtil.createWaypoint(p, WaypointType.OCEAN_MONUMENT, 20*600, blockpos);
            Reminiscence r = new ReminiscenceWaypoint(blockpos, 0x7F16FF);
            DataUtil.addReminiscence(p, Memory.WATER, r);
            return true;
        }
        return false;
    }
}
