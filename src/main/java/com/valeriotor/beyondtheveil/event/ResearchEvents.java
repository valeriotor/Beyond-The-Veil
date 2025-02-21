package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.DialogueDataProvider;
import com.valeriotor.beyondtheveil.dialogue.Dialogue;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import net.minecraft.world.entity.player.Player;

public class ResearchEvents {

    public static void progressResearchEvent(Player p, ResearchStatus status) {
        if (!p.level().isClientSide) {
            status.getSubresearch().getMemories().forEach(m -> m.unlock(p));
            unlockDialogues(p, status);
        }
    }

    private static void unlockDialogues(Player p, ResearchStatus status) {
        p.getCapability(DialogueDataProvider.DIALOGUE_DATA).ifPresent(d -> {
            if ("BLACK_MIRROR".equals(status.res.getKey()) && status.getStage() == 0) {
                addDialogue(d, DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "did_dream");
            } else if ("BLACK_MIRROR".equals(status.res.getKey()) && status.getStage() == 1) {
                addDialogue(d, DialogueType.BLACK_MIRROR, "initial2");
            } else if ("SURGERY".equals(status.res.getKey()) && status.getStage() == 0) {
                addDialogue(d, DialogueType.BLACK_MIRROR, "rationalize");
            }
        });
    }

    private static void addDialogue(DialogueData d, DialogueType type, String id) {
        d.setDialogue(type, DialogueRegistry.getTemplate(type, id));
    }

}
