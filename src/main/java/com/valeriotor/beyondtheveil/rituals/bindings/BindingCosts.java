package com.valeriotor.beyondtheveil.rituals.bindings;

import java.util.function.Function;

public class BindingCosts {

    public static final int MAX_ENERGY = 10000;
    public static final int ZOMBIE_FILL_AMOUNT = 6000;
    public static final int OVERWORLD_BREAK = 2; // per block
    public static final int OVERWORLD_HEAL = 5;
    public static final int OVERWORLD_REPAIR = 40;
    public static final int OVERWORLD_BUILD = 1;
    public static final Function<Float, Integer> NETHER_ATTACK = requiredPlayerHealth -> (int) (requiredPlayerHealth * 40);
    public static final int NETHER_HEAL = 20;
    public static final int NETHER_FEED = 5;
    public static final int NETHER_CREATE_FIRE = 1;
    public static final int NETHER_TARGET = 10;
    public static final int END_FLY = 5;
    public static final int END_ATTACK = 5;
    public static final int END_VERTICAL_TP = 30;
    public static final int END_SURVIVE = 2000;
    public static final int ARCHE_ATTACK = 100;
    public static final int ARCHE_MOVE = 1;
    public static final int ARCHE_NODE = 200;
    public static final Function<Float, Integer> ARCHE_HEAL = amount -> (int) (amount * 2);


}
