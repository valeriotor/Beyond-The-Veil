package com.valeriotor.beyondtheveil.entities.AI.attacks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class AttackList {
    protected final List<WeightedAttack> attacks = new ArrayList<>();
    protected int totalWeight = 0;

    public void addAttack(TelegraphedAttackTemplate attack, int weight) {
        attacks.add(new EagerWeightedAttack(attack, weight));
        totalWeight += weight;
    }

    public void addAttack(Supplier<TelegraphedAttackTemplate> attackTemplateSupplier, int weight) {
        attacks.add(new LazyWeightedAttack(attackTemplateSupplier, weight));
        totalWeight += weight;
    }

    public void setNoAttackWeight(int weight) {
        removeNoAttackWeight();
        totalWeight += weight;
    }

    public void removeNoAttackWeight() {
        totalWeight = 0;
        for(WeightedAttack attack : attacks)
            totalWeight += attack.getWeight();
    }

    public Optional<TelegraphedAttackTemplate> getRandomAttack(Random rand, double distance, EntityLiving attacker, EntityLivingBase target) {
        int totalWeight = this.totalWeight;
        List<WeightedAttack> attacksWithinDistance = new ArrayList<>();
        for(WeightedAttack attack: attacks) {
            if(attack.getAttack().getTriggerDistance() >= distance && attack.getAttack().canUseAttack(attacker, target)) {
                attacksWithinDistance.add(attack);
            } else {
                totalWeight -= attack.getWeight();
            }
        }
        if(totalWeight <= 0)
            return Optional.empty();
        int n = rand.nextInt(totalWeight);
        for(WeightedAttack attack : attacksWithinDistance) {
            n -= attack.getWeight();
            if(n < 0) {
                return Optional.of(attack.getAttack());
            }
        }
        return Optional.empty();
    }

    private abstract class WeightedAttack {
        private final int weight;
        public WeightedAttack(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        abstract public TelegraphedAttackTemplate getAttack();
    }

    private class EagerWeightedAttack extends WeightedAttack{
        private final TelegraphedAttackTemplate attack;

        public EagerWeightedAttack(TelegraphedAttackTemplate attack, int weight) {
            super(weight);
            this.attack = attack;
        }

        public TelegraphedAttackTemplate getAttack() {
            return attack;
        }
    }

    private class LazyWeightedAttack extends WeightedAttack {
        private final Supplier<TelegraphedAttackTemplate> supplier;
        public LazyWeightedAttack(Supplier<TelegraphedAttackTemplate> supplier, int weight) {
            super(weight);
            this.supplier = supplier;
        }

        @Override
        public TelegraphedAttackTemplate getAttack() {
            return supplier.get();
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
        public void addAttack(Supplier<TelegraphedAttackTemplate> attackTemplateSupplier, int weight) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNoAttackWeight(int weight) {
            throw new UnsupportedOperationException();
        }
    }

}
