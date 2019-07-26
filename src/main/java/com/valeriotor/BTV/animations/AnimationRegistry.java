package com.valeriotor.BTV.animations;

public class AnimationRegistry {
	
	public static AnimationTemplate deep_one_test;
	
	public static void loadAnimations() {
		deep_one_test = new AnimationTemplate("deep_one_test");
		
		//DEBUG
		deep_one_test.debug();
	}
	
}
