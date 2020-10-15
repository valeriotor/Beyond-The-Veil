package com.valeriotor.beyondtheveil.blackmirror;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;

public class MirrorDialogueRegistry {
	
	private static Map<String, MirrorDialogueTemplate> dialogues = new HashMap<>();
	private static MirrorDialogueTemplate noDialogue;
	
	public static void registerMirrorDialogues() {
		registerMirrorDialogue("nodialogue");
		registerMirrorDialogue("start");
		registerMirrorDialogue("testdefaultunlock");
	}
	
	private static void registerMirrorDialogue(String name) {
		try {
			String file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/mirror/" + name + ".json"), Charsets.UTF_8);
			MirrorDialogueTemplate dialogue = BeyondTheVeil.gson.fromJson(file, MirrorDialogueTemplate.class);
			dialogues.put(dialogue.getID(), dialogue);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static MirrorDialogueTemplate getDialogueTemplate(String id) {
		return dialogues.get(id);
	}
	
	public static MirrorDialogueTemplate getNoDialogueTemplate() {
		return noDialogue;
	}
	
}
