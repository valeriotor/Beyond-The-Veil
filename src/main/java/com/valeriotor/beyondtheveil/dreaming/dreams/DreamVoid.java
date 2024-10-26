package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DreamVoid extends Dream {

    public DreamVoid() {
        super(Memory.VOID, 0, Reminiscence.EmptyReminiscenceVoid::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        if (DreamHandler.hasVoid(p)) {
            return false;
        }
        DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.VOID, true, false);
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        return activate(caster, l);
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        return activate(p, l);
    }
}
