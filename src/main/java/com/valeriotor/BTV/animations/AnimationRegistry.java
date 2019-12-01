package com.valeriotor.BTV.animations;

public class AnimationRegistry {
	
	public static AnimationTemplate deep_one_roar;
	public static AnimationTemplate shoggoth_open_mouth;
	public static AnimationTemplate shoggoth_eye_tentacle;
	public static AnimationTemplate blood_zombie_idle;
	
	public static void loadAnimations() {
		blood_zombie_idle = new AnimationTemplate("blood_zombie_idle");
		deep_one_roar = new AnimationTemplate("deep_one_roar");
		shoggoth_open_mouth = new AnimationTemplate("shoggoth_open_mouth");
		shoggoth_eye_tentacle = new AnimationTemplate("shoggoth_eye_tentacle");
	}
	
	public static int getIdFromAnimation(AnimationTemplate anim) {
		if(anim == deep_one_roar) return 0;
		if(anim == shoggoth_open_mouth) return 1;
		if(anim == shoggoth_eye_tentacle) return 2;
		if(anim == blood_zombie_idle) return 3;
		return -1;
	}
	
	public static AnimationTemplate getAnimationFromId(int id) {
		switch(id) {
			case 0: return deep_one_roar;
			case 1: return shoggoth_open_mouth;
			case 2: return shoggoth_eye_tentacle;
			case 3: return blood_zombie_idle;
		}
		return null;
	}
	
}
