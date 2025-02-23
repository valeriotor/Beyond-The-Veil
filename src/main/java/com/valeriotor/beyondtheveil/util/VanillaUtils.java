package com.valeriotor.beyondtheveil.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class VanillaUtils {
    public static void awardAdvancement(ServerPlayer p, ResourceLocation id) {
        if (p.getServer() != null) {
            Advancement advancement = p.getServer().getAdvancements().getAdvancement(id);
            if (advancement != null) {
                AdvancementProgress progress = p.getAdvancements().getOrStartProgress(advancement);
                if (!progress.isDone()) {
                    for (String remainingCriterion : progress.getRemainingCriteria()) {
                        p.getAdvancements().award(advancement, remainingCriterion);
                    }
                }
            }
        }
    }
}
