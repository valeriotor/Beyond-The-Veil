package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DreamSound extends Dream {

    private final String soundKey;

    public DreamSound(Memory memory, SoundEvent event, boolean isVoid) {
        super(memory, 1, () -> new Reminiscence.SoundReminiscence(event.getLocation().getPath()), isVoid);
        soundKey = event.getLocation().getPath();
    }

    @Override
    public boolean activate(Player p, Level l) {
        String dataName = memory.getDataName(isVoid);
        DataUtil.addReminiscence(p, dataName, new Reminiscence.SoundReminiscence(soundKey));
        if (isVoid) {
            DreamHandler.consumeVoid(p);
        }
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
