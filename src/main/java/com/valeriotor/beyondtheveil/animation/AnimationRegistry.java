package com.valeriotor.beyondtheveil.animation;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationRegistry {

    private static List<AnimationTemplate> animations = new ArrayList<>();
    private static Map<AnimationTemplate, Integer> animationToId = new HashMap<>();
    public static AnimationTemplate ammunition_explode;
    public static AnimationTemplate ammunition_explode_body;
    public static AnimationTemplate blood_cultist_backstab;
    public static AnimationTemplate blood_cultist_bow;
    public static AnimationTemplate blood_skeleton_swing;
    public static AnimationTemplate bonecage_crunch;
    public static AnimationTemplate cephalopodian_crunch;
    public static AnimationTemplate cephalopodian_tentacles;
    public static AnimationTemplate crawler_back_death;
    public static AnimationTemplate crawler_back_pain_high_1;
    public static AnimationTemplate crawler_back_pain_low;
    public static AnimationTemplate crawler_back_pain_medium;
    public static AnimationTemplate crawler_chest_death;
    public static AnimationTemplate crawler_chest_pain_high;
    public static AnimationTemplate crawler_chest_pain_low;
    public static AnimationTemplate crawler_chest_pain_medium;
    public static AnimationTemplate crawler_ritual;
    public static AnimationTemplate crawler_skull_death;
    public static AnimationTemplate crawler_skull_pain_high;
    public static AnimationTemplate crawler_skull_pain_low;
    public static AnimationTemplate crawler_skull_pain_medium;
    public static AnimationTemplate deep_one_left_attack;
    public static AnimationTemplate deep_one_open_arm_attack;
    public static AnimationTemplate deep_one_right_attack;
    public static AnimationTemplate deep_one_trade;
    public static AnimationTemplate deep_one_trade2;
    public static AnimationTemplate deep_one_trade3;
    public static AnimationTemplate deep_one_trade4;
    public static AnimationTemplate sandflatter_ambush;
    public static AnimationTemplate sandflatter_claw;
    public static AnimationTemplate surgeon_operate_stop;
    public static AnimationTemplate surgeon_operate_start;
    public static AnimationTemplate surgeon_standup;
    public static AnimationTemplate weeper_explode;
    public static AnimationTemplate weeper_get_up;
    public static AnimationTemplate weeper_get_up_spineless;

    public static void loadAnimations(boolean client) {
        animations.clear();
        animationToId.clear();
        int i = 0;
        ammunition_explode = registerAnimation("ammunition_explode", client, i++);
        ammunition_explode_body = registerAnimation("ammunition_explode_body", client, i++);
        blood_cultist_backstab = registerAnimation("blood_cultist_backstab", client, i++);
        blood_cultist_bow = registerAnimation("blood_cultist_bow", client, i++);
        blood_skeleton_swing = registerAnimation("blood_skeleton_swing", client, i++);
        bonecage_crunch = registerAnimation("bonecage_crunch", client, i++);
        cephalopodian_crunch = registerAnimation("cephalopodian_crunch", client, i++);
        cephalopodian_tentacles = registerAnimation("cephalopodian_tentacles", client, i++);
        crawler_back_death = registerAnimation("crawler_back_death", client, i++);
        crawler_back_pain_high_1 = registerAnimation("crawler_back_pain_high_1", client, i++);
        crawler_back_pain_low = registerAnimation("crawler_back_pain_low", client, i++);
        crawler_back_pain_medium = registerAnimation("crawler_back_pain_medium", client, i++);
        crawler_chest_death = registerAnimation("crawler_chest_death", client, i++);
        crawler_chest_pain_high = registerAnimation("crawler_chest_pain_high", client, i++);
        crawler_chest_pain_low = registerAnimation("crawler_chest_pain_low", client, i++);
        crawler_chest_pain_medium = registerAnimation("crawler_chest_pain_medium", client, i++);
        crawler_ritual = registerAnimation("crawler_ritual", client, i++);
        crawler_skull_death = registerAnimation("crawler_skull_death", client, i++);
        crawler_skull_pain_high = registerAnimation("crawler_skull_pain_high", client, i++);
        crawler_skull_pain_low = registerAnimation("crawler_skull_pain_low", client, i++);
        crawler_skull_pain_medium = registerAnimation("crawler_skull_pain_medium", client, i++);
        deep_one_left_attack = registerAnimation("deep_one_left_attack", client, i++);
        deep_one_open_arm_attack = registerAnimation("deep_one_open_arm_attack", client, i++);
        deep_one_right_attack = registerAnimation("deep_one_right_attack", client, i++);
        deep_one_trade = registerAnimation("deep_one_trade", client, i++);
        deep_one_trade2 = registerAnimation("deep_one_trade2", client, i++);
        deep_one_trade3 = registerAnimation("deep_one_trade3", client, i++);
        deep_one_trade4 = registerAnimation("deep_one_trade4", client, i++);
        sandflatter_ambush = registerAnimation("sandflatter_ambush", client, i++);
        sandflatter_claw = registerAnimation("sandflatter_claw", client, i++);
        surgeon_operate_start = registerAnimation("surgeon_operate_start", client, i++);
        surgeon_operate_stop = registerAnimation("surgeon_operate_stop", client, i++);
        surgeon_standup = registerAnimation("surgeon_standup", client, i++);
        weeper_explode = registerAnimation("weeper_explode", client, i++);
        weeper_get_up = registerAnimation("weeper_get_up", client, i++);
        weeper_get_up_spineless = registerAnimation("weeper_get_up_spineless", client, i++);
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
