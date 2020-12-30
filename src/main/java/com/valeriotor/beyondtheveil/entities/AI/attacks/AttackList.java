package com.valeriotor.beyondtheveil.entities.AI.attacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AttackList {
    protected final List<WeightedAttack> attacks = new ArrayList<>();
    protected int totalWeight = 0;

    public void addAttack(TelegraphedAttackTemplate attack, int weight) {
        attacks.add(new WeightedAttack(attack, weight));
        totalWeight += weight;
    }

    public void setNoAttackWeight(int weight) {
        removeNoAttackWeight();
        totalWeight += weight;
    }

    public void removeNoAttackWeight() {
        totalWeight = 0;
        for(WeightedAttack attack : attacks)
            totalWeight += attack.weight;
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

    public static final AttackList EMPTY = new ImmutableAttackList();
    public static AttackList immutableAttackListOf(AttackList source) {
        return new ImmutableAttackList(source);
    }

    private static class ImmutableAttackList extends AttackList {

        private ImmutableAttackList(AttackList source) {
            attacks.addAll(source.attacks);
            totalWeight = source.totalWeight;
        }

        private ImmutableAttackList() {
            totalWeight = 1;
        }

        @Override
        public void addAttack(TelegraphedAttackTemplate attack, int weight) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNoAttackWeight(int weight) {
            throw new UnsupportedOperationException();
        }
    }

}
