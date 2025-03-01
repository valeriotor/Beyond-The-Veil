package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;

public class ConvalescentBreedAnimalsGoal<T extends Mob & SurgeryPatient> extends Goal {

    private final T entity;
    private Animal target1;
    private Animal target2;
    private boolean reachedTarget1;
    private boolean reachedTarget2;
    private int counter = 0;

    public ConvalescentBreedAnimalsGoal(T entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        LazyOptional<ConvalescentData> cap = entity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
        if (cap.isPresent()) {
            ConvalescentData data = cap.resolve().get();
            if (data.getFlags().getOrDefault("parental_hormones", 0) > 0 && data.getChestPos() != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        counter++;
        if ((counter & 15) == 0 && !entity.level().isClientSide) {
            entity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                ItemStack heldStack = c.getHeldStack();
                // if it already has targets. We don't care about serialization here
                int moveRange = 24;
                BlockPos chestPos = c.getChestPos();
                if ((target1 != null && target1.isAlive() && target1.distanceToSqr(chestPos.getCenter()) < moveRange * moveRange) && (target2 != null && target2.isAlive() && target2.distanceToSqr(chestPos.getCenter()) < moveRange * moveRange) && (!reachedTarget1 || !reachedTarget2) && !heldStack.isEmpty()) {
                    if (!reachedTarget1) {
                        entity.getNavigation().moveTo(target1, 1);
                        if (entity.distanceToSqr(target1) < 9) {
                            target1.setInLove(null);
                            heldStack.setCount(heldStack.getCount() - 1);
                            reachedTarget1 = true;
                        }
                    } else {
                        entity.getNavigation().moveTo(target2, 1);
                        if (entity.distanceToSqr(target2) < 9) {
                            target2.setInLove(null);
                            heldStack.setCount(heldStack.getCount() - 1);
                            reachedTarget2 = true;
                        }
                    }
                } else {
                    BlockEntity be = entity.level().getBlockEntity(chestPos);
                    if ((target1 == null || !target1.isAlive() || target1.distanceToSqr(chestPos.getCenter()) >= moveRange * moveRange || target2 == null || !target2.isAlive()) || target2.distanceToSqr(chestPos.getCenter()) >= moveRange * moveRange|| (reachedTarget1 && reachedTarget2)) {
                        entity.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);
                        if (entity.distanceToSqr(chestPos.getCenter()) < 9 && !heldStack.isEmpty() && be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                            c.setHeldStack(ItemHandlerHelper.insertItemStacked(be.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get(), heldStack, false));
                        }
                    }
                    List<Animal> animals = entity.level().getEntities(EntityTypeTest.forClass(Animal.class), AABB.ofSize(entity.position(), moveRange, 5, moveRange), a -> true);
                    if (animals.size() < 25) {
                        animals.removeIf(a -> a.getAge() != 0 || a.isBaby() || a.isInLove());
                        Map<EntityType<?>, List<Animal>> types = new HashMap<>();
                        List<Tuple<Animal, Animal>> candidates = new ArrayList<>();
                        for (Animal animal : animals) {
                            if (!heldStack.isEmpty() && !animal.isFood(heldStack)) {
                                continue;
                            }
                            double breedRange = 12;
                            List<? extends Animal> entities = entity.level().getEntities(EntityTypeTest.forClass(animal.getClass()), AABB.ofSize(entity.position(), breedRange, 5, breedRange), a -> a != animal && !a.isBaby() && !a.isInLove() && a.getAge() == 0 && a.distanceToSqr(entity) < moveRange * moveRange);
                            entities.sort(Comparator.comparing(a -> a.distanceToSqr(animal)));
                            if (!entities.isEmpty()) {
                                candidates.add(new Tuple<>(animal, entities.get(0)));
                            }
                            //types.computeIfAbsent(animal.getType(), a -> new ArrayList<>()).add(animal);
                        }
                        if (!candidates.isEmpty()) {
                            Tuple<Animal, Animal> animalAnimalTuple = candidates.get(entity.getRandom().nextInt(candidates.size()));
                            if (!heldStack.isEmpty()) {
                                reachedTarget1 = reachedTarget2 = false;
                                target1 = animalAnimalTuple.getA();
                                target2 = animalAnimalTuple.getB();
                            } else if (entity.distanceToSqr(chestPos.getCenter()) < 9) {

                                if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                                    IItemHandler handler = be.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get();
                                    for (int i = 0; i < handler.getSlots(); i++) {
                                        if (animalAnimalTuple.getA().isFood(handler.getStackInSlot(i)) && handler.getStackInSlot(i).getCount() > 1) {
                                            c.setHeldStack(handler.extractItem(i, 2, false));
                                            reachedTarget1 = reachedTarget2 = false;
                                            target1 = animalAnimalTuple.getA();
                                            target2 = animalAnimalTuple.getB();
                                        }
                                    }
                                }
                            }

                        }
                    }
                    //List<List<Animal>> animalGroups = types.values().stream().filter(animalList -> animalList.size() >= 2).toList();
                    //for (List<Animal> animalGroup : animalGroups) {
                    //    animalGroup.sort(Comparator.comparing(a -> a.distanceToSqr(entity)));
                    //}
                }

            });

        }
    }
}
