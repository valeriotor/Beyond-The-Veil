package com.valeriotor.beyondtheveil.client.gui.research.journal;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public enum JournalCategory {


    OVERVIEW, TOOLS, INGREDIENTS, JOURNAL, ABOMINATIONS(p -> DataUtil.getBoolean(p, PlayerDataLib.has_abominations.name()));

    private final Predicate<Player> isUnlocked;

    JournalCategory() {
        this(p -> true);
    }
    JournalCategory(Predicate<Player> isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public boolean isUnlocked(Player player) {
        return isUnlocked.test(player);
    }

}
