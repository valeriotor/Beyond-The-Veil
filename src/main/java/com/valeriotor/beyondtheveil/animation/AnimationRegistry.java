package com.valeriotor.beyondtheveil.animation;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationRegistry {

    private static List<AnimationTemplate> animations = new ArrayList<>();
    private static Map<AnimationTemplate, Integer> animationToId = new HashMap<>();
    public static AnimationTemplate blood_skeleton_swing;
    public static AnimationTemplate crawler_pain_low_1;
    public static AnimationTemplate crawler_back_pain_high_1;
    public static AnimationTemplate ammunition_explode;
    public static AnimationTemplate ammunition_explode_body;
    public static AnimationTemplate weeper_explode;

    public static void loadAnimations(boolean client) {
        animations.clear();
        animationToId.clear();
        int i = 0;
        blood_skeleton_swing = registerAnimation("blood_skeleton_swing", client, i++);
        crawler_pain_low_1 = registerAnimation("crawler_pain_low_1", client, i++);
        crawler_back_pain_high_1 = registerAnimation("crawler_back_pain_high_1", client, i++);
        ammunition_explode = registerAnimation("ammunition_explode", client, i++);
        ammunition_explode_body = registerAnimation("ammunition_explode_body", client, i++);
        weeper_explode = registerAnimation("weeper_explode", client, i++);
    }

    private static AnimationTemplate registerAnimation(String name, boolean client, int id) {
        if(client) {
            AnimationTemplate anim = new AnimationTemplate(name, client);
            animations.add(anim);
            animationToId.put(anim, id);
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
        return animationToId.get(animation);
    }


}
