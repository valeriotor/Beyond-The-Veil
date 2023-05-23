package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

import java.util.concurrent.Executor;

public class DreamWater extends Dream{

    public DreamWater() {
        super(Memory.WATER, 5);
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
        BlockPos blockpos = sl.findNearestMapFeature(ConfiguredStructureTags.ON_OCEAN_EXPLORER_MAPS, pos, 100, false);
        if (blockpos != null) {
            Messages.sendToPlayer(GenericToClientPacket.createWaypoint("oi", blockpos, 0), (ServerPlayer) p);
            return true;
        }
        return false;
    }
}
