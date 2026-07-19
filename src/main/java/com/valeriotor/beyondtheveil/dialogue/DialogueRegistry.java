package com.valeriotor.beyondtheveil.dialogue;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.research.ResearchRegistry;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DialogueRegistry {

    private static final Map<DialogueType, Map<String, DialogueTemplate>> dialogueTemplates = new EnumMap<>(DialogueType.class);

    public static void registerDialogues() {
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "initial");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "initial1");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "did_dream");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "did_dream2");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "thank_you");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "thank_you2");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "communed1");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "wantslug");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "afterslug");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "baptism");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "baptism2");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "baptism3");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "death");

        registerDialogue(DialogueType.SHOREMAN_BARTENDER, "initial");
        registerDialogue(DialogueType.SHOREMAN_BARTENDER, "communed1");
        registerDialogue(DialogueType.SHOREMAN_BARTENDER, "communed2");

        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "initial");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "initial1");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "communed1");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "communed2");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "communed3");

        registerDialogue(DialogueType.SHOREMAN_CLERK, "initial");
        registerDialogue(DialogueType.SHOREMAN_CLERK, "initial1");
        registerDialogue(DialogueType.SHOREMAN_CLERK, "communed1");
        registerDialogue(DialogueType.SHOREMAN_CLERK, "communed2");

        registerDialogue(DialogueType.SHOREMAN_DRUNK, "initial");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "initial1");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "drunk");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "drunk1");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "communed1");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "communed2");

        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "initial");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "no_closer");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "begone");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "communed1");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "communed2");

        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "initial");
        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "initial1");
        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "communed1");
        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "discuss");

        registerDialogue(DialogueType.BLACK_MIRROR, "initial");
        registerDialogue(DialogueType.BLACK_MIRROR, "initial2");
        registerDialogue(DialogueType.BLACK_MIRROR, "rationalize");
        registerDialogue(DialogueType.BLACK_MIRROR, "rationalize2");
        registerDialogue(DialogueType.BLACK_MIRROR, "rationalize3");
        registerDialogue(DialogueType.BLACK_MIRROR, "after_weep");
        registerDialogue(DialogueType.BLACK_MIRROR, "idle");
        registerDialogue(DialogueType.BLACK_MIRROR, "revelation");

        registerDialogue(DialogueType.BLOOD_CULTIST, "initial");
        registerDialogue(DialogueType.BLOOD_CULTIST, "immortal");
        registerDialogue(DialogueType.BLOOD_CULTIST, "immortal2");

        registerDialogue(DialogueType.DROWNED, "gnawing");
        registerDialogue(DialogueType.DROWNED, "ocean");
        registerDialogue(DialogueType.DROWNED, "you");
    }

    private static void registerDialogue(DialogueType type, String id) {
        try {
            String file = Resources.toString(BeyondTheVeil.class.getResource("/data/beyondtheveil/dialogue/%s/%s.json".formatted(type.name().toLowerCase(), id)), Charsets.UTF_8);
            DialogueTemplate template = BeyondTheVeil.GSON.fromJson(file, DialogueTemplate.class);
            Map<String, DialogueTemplate> dialogueTemplateMap = dialogueTemplates.computeIfAbsent(type, d -> new HashMap<>());
            dialogueTemplateMap.put(id, template);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DialogueTemplate getTemplate(DialogueType type, String id) {
        return dialogueTemplates.get(type).get(id);
    }

}
