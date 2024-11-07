package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.BiFunction;

public class DreamWaypoint extends Dream{

    private final BiFunction<ServerLevel, BlockPos, BlockPos> function;
    private final int color;

    public DreamWaypoint(Memory memory, BiFunction<ServerLevel, BlockPos, BlockPos> function, int color) {
        this(memory, false, function, color);
    }

    public DreamWaypoint(Memory memory, boolean isVoid, BiFunction<ServerLevel, BlockPos, BlockPos> function, int color) {
        super(memory, 5, ReminiscenceWaypoint::new, isVoid);
        this.function = function;
        this.color = color;
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
        if (isVoid) {
            DreamHandler.consumeVoid(p);
        }
        ServerLevel sl = (ServerLevel) l;
        BlockPos blockpos = function.apply(sl, pos);
        if (blockpos != null) {
            //DataUtil.createWaypoint(p, WaypointType.OCEAN_MONUMENT, 20*600, blockpos);
            Reminiscence r = new ReminiscenceWaypoint(blockpos, color);
            DataUtil.addReminiscence(p, memory.getDataName(isVoid), r);
            return true;
        }
        /*Runnable runnable = () -> {
            BlockableEventLoop<?> executor = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);

            BlockPos blockpos = function.apply(sl, pos);
            if (blockpos != null) {
                if (!executor.isSameThread()) {
                    //DataUtil.createWaypoint(p, WaypointType.OCEAN_MONUMENT, 20*600, blockpos);
                    Reminiscence r = new ReminiscenceWaypoint(blockpos, color);
                    DataUtil.addReminiscence(p, memory.getDataName(isVoid), r);
                    DataUtil.syncReminiscences(p);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();*/
        return false;
    }
}
