package com.valeriotor.beyondtheveil.animations;

import java.util.ArrayList;
import java.util.List;

public class AnimationRegistry {
	
	public static AnimationTemplate deep_one_roar;
	public static AnimationTemplate shoggoth_open_mouth;
	public static AnimationTemplate shoggoth_eye_tentacle;
	public static AnimationTemplate blood_zombie_idle;
	public static AnimationTemplate blood_skeleton_idle;
	public static AnimationTemplate blood_skeleton_spook;
	public static AnimationTemplate blood_skeleton_left_swing;
	public static AnimationTemplate blood_skeleton_right_swing;
	public static AnimationTemplate blood_zombie_left_swing;
	public static AnimationTemplate blood_zombie_right_swing;
	public static AnimationTemplate deep_one_right_swing;
	public static AnimationTemplate deep_one_left_swing;
	public static AnimationTemplate crazed_weeper_transform;
	public static AnimationTemplate surgeon_surgery;
	public static AnimationTemplate surgeon_attack;
	public static AnimationTemplate muray_bite;
	public static AnimationTemplate deep_one_brute_left_swing;
	public static AnimationTemplate deep_one_brute_right_swing;
	public static AnimationTemplate deep_one_brute_right_followup_swing;
	public static AnimationTemplate deep_one_brute_left_followup_swing;
	public static AnimationTemplate deep_one_brute_smash;
	public static AnimationTemplate deep_one_brute_roar_followup;
	public static AnimationTemplate deep_one_myrmidon_spear_impale;
	public static AnimationTemplate deep_one_myrmidon_sword_swing;
	public static AnimationTemplate deep_one_myrmidon_spear_impale_followup_spear_impale;
	public static AnimationTemplate deep_one_myrmidon_sword_swing_followup_sword_impale;
	public static AnimationTemplate deep_one_myrmidon_spear_impale_followup_spear_right_swing;
	public static AnimationTemplate deep_one_myrmidon_sword_impale_followup_spear_impale;
	public static AnimationTemplate deep_one_myrmidon_spear_impale_followup_sword_impale;
	public static AnimationTemplate deep_one_myrmidon_sword_impale_followup_sword_back_swing;
	public static AnimationTemplate deep_one_player_climb;
	public static AnimationTemplate cephalopodian_crunch;
	private static List<AnimationTemplate> anims = new ArrayList<>();
	
	public static void loadAnimations(boolean client) {
		anims.clear();
		blood_skeleton_left_swing = createAnimationTemplate("blood_skeleton_left_swing", client);
		blood_skeleton_right_swing = createAnimationTemplate("blood_skeleton_right_swing", client);
		blood_skeleton_idle = createAnimationTemplate("blood_skeleton_idle", client);
		blood_skeleton_spook = createAnimationTemplate("blood_skeleton_spook", client);
		blood_zombie_idle = createAnimationTemplate("blood_zombie_idle", client);
		deep_one_roar = createAnimationTemplate("deep_one_roar", client);
		shoggoth_open_mouth = createAnimationTemplate("shoggoth_open_mouth", client);
		shoggoth_eye_tentacle = createAnimationTemplate("shoggoth_eye_tentacle", client);
		deep_one_left_swing = createAnimationTemplate("deep_one_left_swing", client);
		deep_one_right_swing = createAnimationTemplate("deep_one_right_swing", client);
		crazed_weeper_transform = createAnimationTemplate("crazed_weeper_transform", client);
		blood_zombie_left_swing = createAnimationTemplate("blood_zombie_left_swing", client);
		blood_zombie_right_swing = createAnimationTemplate("blood_zombie_right_swing", client);
		surgeon_surgery = createAnimationTemplate("surgeon_surgery", client);
		surgeon_attack = createAnimationTemplate("surgeon_attack", client);
		muray_bite = createAnimationTemplate("muray_bite", client);
		deep_one_brute_left_swing = createAnimationTemplate("deep_one_brute/left_swing", client);
		deep_one_brute_right_swing = createAnimationTemplate("deep_one_brute/right_swing", client);
		deep_one_brute_right_followup_swing = createAnimationTemplate("deep_one_brute/right_followup_swing", client);
		deep_one_brute_left_followup_swing = createAnimationTemplate("deep_one_brute/left_followup_swing", client);
		deep_one_brute_smash = createAnimationTemplate("deep_one_brute/smash", client);
		deep_one_brute_roar_followup = createAnimationTemplate("deep_one_brute/roar_followup", client);
		deep_one_myrmidon_spear_impale = createAnimationTemplate("deep_one_myrmidon/spear_impale", client);
		deep_one_myrmidon_sword_swing = createAnimationTemplate("deep_one_myrmidon/sword_swing", client);
		deep_one_myrmidon_spear_impale_followup_spear_impale = createAnimationTemplate("deep_one_myrmidon/spear_impale_followup_spear_impale", client);
		deep_one_myrmidon_sword_swing_followup_sword_impale = createAnimationTemplate("deep_one_myrmidon/sword_swing_followup_sword_impale", client);
		deep_one_myrmidon_spear_impale_followup_spear_right_swing = createAnimationTemplate("deep_one_myrmidon/spear_impale_followup_spear_right_swing", client);
		deep_one_myrmidon_sword_impale_followup_spear_impale = createAnimationTemplate("deep_one_myrmidon/sword_impale_followup_spear_impale", client);
		deep_one_myrmidon_spear_impale_followup_sword_impale = createAnimationTemplate("deep_one_myrmidon/spear_impale_followup_sword_impale", client);
		deep_one_myrmidon_sword_impale_followup_sword_back_swing = createAnimationTemplate("deep_one_myrmidon/sword_impale_followup_sword_back_swing", client);
		deep_one_player_climb = createAnimationTemplate("deep_one_player_climb", client);
		cephalopodian_crunch = createAnimationTemplate("cephalopodian_crunch", client);
	}
	
	public static int getIdFromAnimation(AnimationTemplate anim) {
		for(int i = 0; i < anims.size(); i++) {
			if(anims.get(i) == anim) {
				System.out.println(i);
				return i;
			}
		}
		return -1;
	}
	
	public static AnimationTemplate getAnimationFromId(int id) {
		if(id >= 0 && id < anims.size())
			return anims.get(id);
		return null;
	}

	private static AnimationTemplate createAnimationTemplate(String name, boolean client) {
		if(client) return AnimationTemplate.createTemplate(name, anims);
		else return AnimationTemplate.createDummyTemplate(anims);
	}
	
}
