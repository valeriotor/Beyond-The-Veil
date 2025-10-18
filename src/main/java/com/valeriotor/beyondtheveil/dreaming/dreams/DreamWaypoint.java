package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.function.TriFunction;

public class DreamWaypoint extends Dream {

    private final TriFunction<ServerLevel, BlockPos, Player, BlockPos> function;
    private final int color;

    public DreamWaypoint(Memory memory, TriFunction<ServerLevel, BlockPos, Player, BlockPos> function, int color) {
        this(memory, false, function, color);
    }

    public DreamWaypoint(Memory memory, boolean isVoid, TriFunction<ServerLevel, BlockPos, Player, BlockPos> function, int color) {
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
        boolean hasVoid = false;
        boolean flag = false;
        if (memory == Memory.DARKNESS) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 40, 1));
            hasVoid = DreamHandler.consumeVoid(caster);
            if (hasVoid) {
                target.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 40, 1));
            }
            Reminiscence r = new Reminiscence.TextReminiscence("reminiscence.blinded");
            DataUtil.addReminiscence(caster, "darkness_player", r);
            flag = true;
        } else if (memory == Memory.WATER) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = -1; y <= 3; y++) {
                        if (x != 0 || z != 0 || y == -1 || y == 3) {
                            BlockPos offset = target.blockPosition().offset(x, y, z);
                            if (l.getBlockState(offset).getBlock() == Blocks.AIR) {
                                l.setBlock(offset, Blocks.PACKED_ICE.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
            Reminiscence r = new Reminiscence.TextReminiscence("reminiscence.ice");
            DataUtil.addReminiscence(caster, "water_player", r);
            flag = true;
        }
        if (!flag) {
            flag = activatePos(caster, l, target.blockPosition());
        }
        return flag;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        if (isVoid) {
            DreamHandler.consumeVoid(p);
        }
        ServerLevel sl = (ServerLevel) l;
        BlockPos blockpos = function.apply(sl, pos, p);
        if (blockpos != null) {
            //DataUtil.createWaypoint(p, WaypointType.OCEAN_MONUMENT, 20*600, blockpos);
            Reminiscence r = new ReminiscenceWaypoint(blockpos, color, sl.dimension());
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
