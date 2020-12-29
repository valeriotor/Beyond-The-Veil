package com.valeriotor.beyondtheveil.entities.AI.attacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AttackList {
    private final List<WeightedAttack> attacks = new ArrayList<>();
    private int totalWeight = 0;

    public void addAttack(TelegraphedAttackTemplate attack, int weight) {
        attacks.add(new WeightedAttack(attack, weight));
        totalWeight += weight;
    }

    public Optional<TelegraphedAttackTemplate> getRandomAttack(Random rand, double distance) {
        int n = rand.nextInt(totalWeight);
        for(WeightedAttack attack : attacks) {
            n -= attack.weight;
            if(n < 0) {
                if(attack.attack.getTriggerDistance() >= distance) {
                    return Optional.of(attack.attack);
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    private class WeightedAttack {
        private final TelegraphedAttackTemplate attack;
        private final int weight;

        private WeightedAttack(TelegraphedAttackTemplate attack, int weight) {
            this.attack = attack;
            this.weight = weight;
        }
    }

}
