package com.valeriotor.BTV.animations;

public class AnimationRegistry {
	
	public static AnimationTemplate deep_one_test;
	
	public static void loadAnimations() {
		deep_one_test = new AnimationTemplate("deep_one_test");
		
	}
	
	public static int getIdFromAnimation(AnimationTemplate anim) {
		if(anim == deep_one_test) return 0;
		return -1;
	}
	
	public static AnimationTemplate getAnimationFromId(int id) {
		switch(id) {
			case 0: return deep_one_test;
		}
		return null;
	}
	
}
