package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DreamVoid extends Dream {

    public DreamVoid() {
        super(Memory.VOID, 0, () -> new Reminiscence.TextReminiscence("reminiscence.void"));
    }

    @Override
    public boolean activate(Player p, Level l) {
        if (DreamHandler.hasVoid(p)) {
            return false;
        }
        DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.VOID, true, false);
        DataUtil.addReminiscence(p, Memory.VOID.getDataName(false), new Reminiscence.TextReminiscence("reminiscence.void"));
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        if (DreamHandler.hasVoid(target)) {
            return false;
        }
        DataUtil.setBooleanOnServerAndSync(target, PlayerDataLib.VOID, true, false);
        DataUtil.addReminiscence(target, Memory.VOID.getDataName(false), new Reminiscence.TextReminiscence("reminiscence.void"));
        DataUtil.syncReminiscences(target);
        return true;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        return activate(p, l);
    }
}
