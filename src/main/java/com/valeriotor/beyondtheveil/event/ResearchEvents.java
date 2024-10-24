package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import net.minecraft.world.entity.player.Player;

public class ResearchEvents {

    public static void progressResearchEvent(Player p, ResearchStatus status) {
        if (!p.level().isClientSide) {
            status.getSubresearch().getMemories().forEach(m -> m.unlock(p));
        }
    }

}
