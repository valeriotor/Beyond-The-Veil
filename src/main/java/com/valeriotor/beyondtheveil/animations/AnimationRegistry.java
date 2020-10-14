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
	private static List<AnimationTemplate> anims = new ArrayList<>();
	
	public static void loadAnimations() {
		blood_skeleton_left_swing = new AnimationTemplate("blood_skeleton_left_swing");
		blood_skeleton_right_swing = new AnimationTemplate("blood_skeleton_right_swing");
		blood_skeleton_idle = new AnimationTemplate("blood_skeleton_idle");
		blood_skeleton_spook = new AnimationTemplate("blood_skeleton_spook");
		blood_zombie_idle = new AnimationTemplate("blood_zombie_idle");
		deep_one_roar = new AnimationTemplate("deep_one_roar");
		shoggoth_open_mouth = new AnimationTemplate("shoggoth_open_mouth");
		shoggoth_eye_tentacle = new AnimationTemplate("shoggoth_eye_tentacle");
		deep_one_left_swing = new AnimationTemplate("deep_one_left_swing");
		deep_one_right_swing = new AnimationTemplate("deep_one_right_swing");
		crazed_weeper_transform = new AnimationTemplate("crazed_weeper_transform");
		blood_zombie_left_swing = new AnimationTemplate("blood_zombie_left_swing");
		blood_zombie_right_swing = new AnimationTemplate("blood_zombie_right_swing");
		surgeon_surgery = new AnimationTemplate("surgeon_surgery");
		surgeon_attack = new AnimationTemplate("surgeon_attack");
		muray_bite = new AnimationTemplate("muray_bite");
		anims.clear();
		anims.add(deep_one_roar);
		anims.add(shoggoth_open_mouth);
		anims.add(shoggoth_eye_tentacle);
		anims.add(blood_zombie_idle);
		anims.add(blood_skeleton_idle);
		anims.add(blood_skeleton_spook);
		anims.add(deep_one_left_swing);
		anims.add(deep_one_right_swing);
		anims.add(blood_skeleton_left_swing);
		anims.add(blood_skeleton_right_swing);
		anims.add(crazed_weeper_transform);
		anims.add(blood_zombie_left_swing);
		anims.add(blood_zombie_right_swing);
		anims.add(surgeon_surgery);
		anims.add(surgeon_attack);
		anims.add(muray_bite);
	}
	
	public static int getIdFromAnimation(AnimationTemplate anim) {
		for(int i = 0; i < anims.size(); i++) {
			if(anims.get(i) == anim)
				return i;
		}
		return -1;
	}
	
	public static AnimationTemplate getAnimationFromId(int id) {
		if(id >= 0 && id < anims.size())
			return anims.get(id);
		return null;
	}
	
}
