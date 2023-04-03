package com.valeriotor.beyondtheveil.animation;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnimationRegistry {

    private static List<AnimationTemplate> animations = new ArrayList<>();
    public static AnimationTemplate blood_skeleton_swing;

    public static void loadAnimations(boolean client) {
        blood_skeleton_swing = registerAnimation("blood_skeleton_swing", client);
    }

    private static AnimationTemplate registerAnimation(String name, boolean client) {
        if(client) {
            AnimationTemplate anim = new AnimationTemplate(name, client);
            animations.add(anim);
            return anim;
        }
        AnimationTemplate anim = new AnimationTemplate(name, client);
        animations.add(anim);
        return anim;
    }

    public static AnimationTemplate animationFromId(int id) {
        return animations.get(id);
    }

    public static int idFromAnimation(AnimationTemplate animation) {
        for (int i = 0; i < animations.size(); i++) {
            if (animations.get(i).equals(animation)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Animation not present in registry");
    }


}
