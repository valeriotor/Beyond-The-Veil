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

    private static Map<DialogueType, Map<String, DialogueTemplate>> dialogueTemplates = new EnumMap<>(DialogueType.class);

    public static void registerDialogues() {
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "initial");
        registerDialogue(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "initial1");

        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "initial");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "no_closer");
        registerDialogue(DialogueType.SHOREMAN_FISHERMAN, "begone");
        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "initial");
        registerDialogue(DialogueType.SHOREMAN_SCHOLAR, "initial1");
        registerDialogue(DialogueType.SHOREMAN_CLERK, "initial");
        registerDialogue(DialogueType.SHOREMAN_CLERK, "initial1");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "initial");
        registerDialogue(DialogueType.SHOREMAN_CARPENTER, "initial1");
        registerDialogue(DialogueType.SHOREMAN_BARTENDER, "initial");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "initial");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "initial1");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "drunk");
        registerDialogue(DialogueType.SHOREMAN_DRUNK, "drunk1");
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
