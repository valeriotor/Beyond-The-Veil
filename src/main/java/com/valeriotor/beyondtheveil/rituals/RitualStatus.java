package com.valeriotor.beyondtheveil.rituals;

import com.valeriotor.beyondtheveil.entity.BloodZombieEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.tile.BloodBasinBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.stream.Collectors;

public class RitualStatus {

    public static final double STEP_SIZE = 0.025; // per tick
    private int numberOfModifiers;

    private int primaryInstability;
    private int secondaryInstability;
    private int currentHop;
    private double progressUntilNextHop; // In block distance
    private final RitualTemplate template;
    private final double[] distances;
    private final BlockPos startPos;
    private final List<BlockPos> altars;
    private boolean earlyStop;
    private final List<ItemStack> burnedModifiers = new ArrayList<>();
    private final List<ItemStack> burnedIngredients = new ArrayList<>();
    private int itemBurnCounter = -1;
    private final UUID initiator;
    private List<Item> startItems = new ArrayList<>();
    private int respitePeriod;
    private int counter;
    private boolean deleteVictim;

    public static RitualStatus startRitual(ServerLevel level, UUID initiator, BlockPos startPos, List<BlockPos> altars) {
        List<Item> items = new ArrayList<>();
        for (BlockPos altar : altars) {
            if (level.getBlockEntity(altar) instanceof BloodBasinBE bloodBasinBE) {
                ItemStack stackInSlot = bloodBasinBE.getStackHandler().getStackInSlot(0);
                if (!stackInSlot.isEmpty()) {
                    items.add(stackInSlot.getItem());
                }
            } else {
                return null;
            }
        }
        List<Item> nonModifierItems = new ArrayList<>();
        boolean flag = false;
        for (Item item : items) {
            if (!RitualModifierRegistry.isModifier(item)) {
                flag = true;
            }
            if (flag) {
                nonModifierItems.add(item);
            }
        }
        RitualTemplate template = RitualRegistry.findMatch(nonModifierItems);
        if (template == null) {
            return null;
        }
        return new RitualStatus(template, startPos, altars, initiator, items);
    }

    private RitualStatus(RitualTemplate template, BlockPos startPos, List<BlockPos> altars, UUID initiator, List<Item> startItems) {
        this.template = template;
        this.startPos = startPos;
        this.altars = new ArrayList<>(altars);
        this.initiator = initiator;
        this.startItems = startItems;
        numberOfModifiers = 0;
        for (Item startItem : startItems) {
            if (RitualModifierRegistry.isModifier(startItem)) {
                numberOfModifiers++;
            } else {
                break;
            }
        }

        distances = new double[altars.size() + 1];
        computeDistances(startPos, altars);

    }

    public RitualStatus(CompoundTag tag) {
        template = RitualRegistry.byName(tag.getString("template"));
        long[] altars = tag.getLongArray("altars");
        this.startPos = BlockPos.of(tag.getLong("startPos"));
        this.altars = new ArrayList<>();
        for (long altar : altars) {
            this.altars.add(BlockPos.of(altar));
        }
        this.earlyStop = tag.getBoolean("earlyStop");
        distances = new double[this.altars.size() + 1];
        computeDistances(startPos, this.altars);
        ListTag burnedModifiers = tag.getList("burnedModifiers", Tag.TAG_COMPOUND);
        for (int i = 0; i < burnedModifiers.size(); i++) {
            this.burnedModifiers.add(ItemStack.of(burnedModifiers.getCompound(i)));
        }
        ListTag burnedIngredients = tag.getList("burnedIngredients", Tag.TAG_COMPOUND);
        for (int i = 0; i < burnedIngredients.size(); i++) {
            this.burnedIngredients.add(ItemStack.of(burnedIngredients.getCompound(i)));
        }
        ListTag startItems = tag.getList("startItems", Tag.TAG_STRING);
        for (int i = 0; i < startItems.size(); i++) {
            this.startItems.add(BuiltInRegistries.ITEM.get(new ResourceLocation(startItems.getString(i))));
        }

        numberOfModifiers = tag.getInt("numberOfModifiers");
        currentHop = tag.getInt("currentHop");
        progressUntilNextHop = tag.getDouble("progressUntilNextHop");
        itemBurnCounter = tag.getInt("itemBurnCounter");
        initiator = UUID.fromString(tag.getString("initiator"));
    }

    private void computeDistances(BlockPos startPos, List<BlockPos> altars) {
        BlockPos current = startPos;
        altars = new ArrayList<>(altars);
        altars.add(startPos);
        for (int i = 0; i < altars.size(); i++) {
            BlockPos next = altars.get(i);
            distances[i] = Math.sqrt(next.distSqr(current));
            current = next;
        }
    }

    public UUID getInitiator() {
        return initiator;
    }

    public int getCurrentHop() {
        return currentHop;
    }

    public double getProgressUntilNextHop() {
        return progressUntilNextHop;
    }

    public double[] getDistances() {
        return distances;
    }

    public List<BlockPos> getAltars() {
        return altars;
    }

    public boolean tick(ServerLevel level) {
        // SERVER ONLY
        // validity checks?

        if (currentHop >= distances.length) {
            return true;
        }
        primaryInstability += template.getPrimaryInstabilityRate();
        secondaryInstability = Math.max(secondaryInstability - 1, 0);
        if (currentHop >= altars.size() || level.getBlockEntity(altars.get(currentHop)) instanceof BloodBasinBE) {
            counter++;

            if (progressUntilNextHop <= distances[currentHop]) {
                progressUntilNextHop += STEP_SIZE;
            }
            if (progressUntilNextHop > distances[currentHop]) {
                // TODO wait for a bit, process instability
                boolean success = true;
                if (currentHop < distances.length - 1 && currentHop > numberOfModifiers - 1) {
                    if (level.getBlockEntity(altars.get(currentHop)) instanceof BloodBasinBE bloodBasin) {
                        ItemStack heldItem = bloodBasin.getStackHandler().getStackInSlot(0);
                        if (heldItem.getItem() != startItems.get(currentHop - numberOfModifiers)) { // change to template.match(burnedItems + leftItems)? but we just want that one item... so no. Just tell the player that is has to be either the same item or an identical one
                            success = false;
                            secondaryInstability += template.getSecondaryInstabilityRate();
                            doInstabilityEffects(true, level);
                        }
                        if (success) {
                            if (itemBurnCounter == -1) {
                                itemBurnCounter = 100; // TODO make this template dependent?
                            }
                            itemBurnCounter--;
                            if (itemBurnCounter != -1) {
                                success = false;
                                bloodBasin.createParticles(true);
                            } else {
                                bloodBasin.createParticles(false);
                            }
                        }
                    }
                }
                if (success) {
                    currentHop++;
                    if (currentHop >= distances.length) {
                        return true;
                    } else {
                        if (level.getBlockEntity(altars.get(currentHop - 1)) instanceof BloodBasinBE bloodBasin) {
                            if (currentHop - 1 > numberOfModifiers - 1) {
                                burnedIngredients.add(bloodBasin.removeItem());
                            } else {
                                burnedModifiers.add(bloodBasin.removeItem());
                            }
                        }
                    }
                    progressUntilNextHop = 0;
                }
            }
            if (counter % 8 == 0) {
                doInstabilityEffects(false, level);
            }
            for (int i = currentHop; i < altars.size(); i++) {
                BlockPos blockPos = altars.get(i);
                if (level.getBlockEntity(blockPos) instanceof BloodBasinBE bloodBasin) {
                    ItemStack heldItem = bloodBasin.getStackHandler().getStackInSlot(0);
                    if (heldItem.getItem() != startItems.get(i - numberOfModifiers)) {
                        Vec3 center = blockPos.getCenter();
                        level.sendParticles(ParticleTypes.LARGE_SMOKE, center.x, center.y + 1, center.z, 10, 0, 0.2, 0, 0.2);
                    }
                }
            }
        } else {
            earlyStop = true;
        }
        return false;
    }

    private void doInstabilityEffects(boolean onlySecondary, ServerLevel level) {
        if (respitePeriod > 0) {
            respitePeriod--;
        } else {
            if(!onlySecondary) {
                WeightedEntry.Wrapper<PrimaryInstabilityEffect>[] primaryEffects = Arrays.stream(PrimaryInstabilityEffect.values()).filter(effect -> primaryInstability > effect.minimum).map(effect -> WeightedEntry.wrap(effect, effect.weight)).toArray(WeightedEntry.Wrapper[]::new);
                Optional<WeightedEntry.Wrapper<PrimaryInstabilityEffect>> primaryEffect = WeightedRandomList.create(primaryEffects).getRandom(level.getRandom());
                primaryEffect.ifPresent(e -> this.doPrimaryInstabilityEffect(e.getData(), level));
            }
            WeightedEntry.Wrapper<SecondaryInstabilityEffect>[] secondaryEffects = Arrays.stream(SecondaryInstabilityEffect.values()).filter(effect -> secondaryInstability > effect.minimum).map(effect -> WeightedEntry.wrap(effect, effect.weight)).toArray(WeightedEntry.Wrapper[]::new);
            Optional<WeightedEntry.Wrapper<SecondaryInstabilityEffect>> secondaryEffect = WeightedRandomList.create(secondaryEffects).getRandom(level.getRandom());
            secondaryEffect.ifPresent(e -> this.doSecondaryInstabilityEffect(e.getData(), level));

        }
    }

    private void doSecondaryInstabilityEffect(SecondaryInstabilityEffect effect, ServerLevel level) {
        switch (effect) {

            case ZOMBIE -> {
                Mob zombie;
                int i = level.getRandom().nextInt(10);
                if (i < 5) {
                    zombie = new Zombie(level);
                } else if (i < 9) {
                    zombie = new Husk(EntityType.HUSK, level);
                } else {
                    zombie = new Drowned(EntityType.DROWNED, level);
                }
                makeMob(zombie, level);
            }
            case SKELETON -> {
                Mob skellie;
                int i = level.getRandom().nextInt(10);
                if (i < 7) {
                    skellie = new Skeleton(EntityType.SKELETON, level);
                } else {
                    skellie = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
                }
                makeMob(skellie, level);
            }
            case BLOOD_ZOMBIE -> {
                earlyStop = true;
                deleteVictim = true;
                Mob zombie = new BloodZombieEntity(BTVEntities.BLOOD_ZOMBIE.get(), level);
                zombie.setPos(startPos.getCenter().add(0, 1, 0));
                level.addFreshEntity(zombie);
            }
            case EXPLOSION -> {
                BlockPos blockPos = randomUpcomingAltarPos(level.getRandom());
                if (blockPos != null) {
                    Vec3 center = blockPos.getCenter();
                    level.explode(null, center.x, center.y, center.z, 3, Level.ExplosionInteraction.NONE);
                }
            }
            case LIGHTNING -> {
                BlockPos blockPos = randomUpcomingAltarPos(level.getRandom());
                if (blockPos != null) {
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                    if (bolt != null) {
                        bolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                        level.addFreshEntity(bolt);
                    }
                }
            }
            case NONE -> {
            }
        }

        if (secondaryInstability > 1000) {
            secondaryInstability -= effect.reduction;
        }
        respitePeriod += 15;
        if (effect != SecondaryInstabilityEffect.NONE) {
            primaryInstability += 100;
        }
    }

    private void makeMob(Mob mob, ServerLevel level) {
        BlockPos pos = randomUpcomingAltarPos(level.getRandom());
        if (pos != null) {
            mob.setPos(pos.getCenter().add(0, 1, 0));
            level.addFreshEntity(mob);
        }
    }

    private void doPrimaryInstabilityEffect(PrimaryInstabilityEffect effect, ServerLevel level) {
        switch (effect) {

            case PUSH_ITEM -> {
                if (currentHop < altars.size()) {
                    BlockPos blockPos = altars.get(level.getRandom().nextInt(currentHop, altars.size()));
                    if (level.getBlockEntity(blockPos) instanceof BloodBasinBE be) {
                        Vec3 center = blockPos.getCenter();
                        level.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 5, 0, 0, 0, 0);
                        level.playSound(null, blockPos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS);
                        ItemStack stack = be.removeItem();
                        ItemEntity itemEntity = new ItemEntity(level, center.x, center.y, center.z, stack);
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
            case BURN_ITEM -> {
                if (currentHop < altars.size()) {
                    BlockPos blockPos = altars.get(level.getRandom().nextInt(currentHop, altars.size()));
                    if (level.getBlockEntity(blockPos) instanceof BloodBasinBE be) {
                        Vec3 center = blockPos.getCenter();
                        level.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 5, 0, 0, 0, 0);
                        level.playSound(null, blockPos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS);
                        be.removeItem();
                        if (level.getBlockState(blockPos.above()).getBlock() == Blocks.AIR) {
                            level.setBlock(blockPos.above(), Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                }
            }
            case NONE -> {
            }
        }
        if (primaryInstability > 1000) {
            primaryInstability -= effect.reduction;
        }
    }

    private BlockPos randomUpcomingAltarPos(RandomSource randomSource) {
        if (currentHop < altars.size()) {
            return altars.get(randomSource.nextInt(currentHop, altars.size()));
        }
        return null;

    }


    public boolean isEarlyStop() {
        return earlyStop;
    }

    public boolean isDeleteVictim() {
        return deleteVictim;
    }

    public void terminationEffects(ServerLevel level, Vec3 altar) {
        List<ItemStack> outputStacks = template.getOutputs().apply(burnedIngredients, initiator);
        for (ItemStack outputStack : outputStacks) {
            ItemEntity item = new ItemEntity(level, altar.x, altar.y, altar.z, outputStack);
            level.addFreshEntity(item);
        }
        template.getOtherEffects().apply(initiator, level, altar);
        Player player = level.getPlayerByUUID(initiator);
        if (player != null) {
            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.performed_ritual.name(), true, false);
        }
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("template", template.getName());
        tag.putLong("startPos", startPos.asLong());
        tag.putLongArray("altars", this.altars.stream().map(BlockPos::asLong).collect(Collectors.toList()));
        tag.putInt("currentHop", currentHop);
        tag.putDouble("progressUntilNextHop", progressUntilNextHop);
        tag.putBoolean("earlyStop", earlyStop);

        ListTag burnedModifiers = new ListTag();
        for (int i = 0; i < this.burnedModifiers.size(); i++) {
            ItemStack burnedModifier = this.burnedModifiers.get(i);
            burnedModifiers.addTag(i, burnedModifier.serializeNBT());
        }
        tag.put("burnedModifiers", burnedModifiers);

        ListTag burnedIngredients = new ListTag();
        for (int i = 0; i < this.burnedIngredients.size(); i++) {
            ItemStack burnedIngredient = this.burnedIngredients.get(i);
            burnedIngredients.addTag(i, burnedIngredient.serializeNBT());
        }
        tag.put("burnedIngredients", burnedIngredients);

        ListTag startItems = new ListTag();
        for (int i = 0; i < this.startItems.size(); i++) {
            Item startItem = this.startItems.get(i);
            startItems.add(i, StringTag.valueOf(BuiltInRegistries.ITEM.getKey(startItem).toString()));
        }
        tag.put("startItems", startItems);

        tag.putInt("numberOfModifiers", numberOfModifiers);
        tag.putInt("itemBurnCounter", itemBurnCounter);
        tag.putString("initiator", initiator.toString());

        return tag;
    }

    private enum PrimaryInstabilityEffect {
        PUSH_ITEM(1000, 6), // sfx and small explosion particles
        BURN_ITEM(2000, 15), // fire
        NONE(0, 1000, 600);

        private final int minimum;
        private final int weight;
        private final int reduction;

        PrimaryInstabilityEffect(int minimum, int weight) {
            this(minimum, weight, minimum / 2);
        }

        PrimaryInstabilityEffect(int minimum, int weight, int reduction) {
            this.minimum = minimum;
            this.weight = weight;
            this.reduction = reduction;
        }
    }

    private enum SecondaryInstabilityEffect {
        ZOMBIE(1000, 2),
        SKELETON(1200, 2),
        BLOOD_ZOMBIE(4500, 15),
        EXPLOSION(2000, 4),
        LIGHTNING(1500, 2),
        NONE(1200, 200, 100);

        private final int minimum;
        private final int weight;
        private final int reduction;

        SecondaryInstabilityEffect(int minimum, int weight) {
            this(minimum, weight, minimum / 2);
        }

        SecondaryInstabilityEffect(int minimum, int weight, int reduction) {
            this.minimum = minimum;
            this.weight = weight;
            this.reduction = reduction;
        }
    }


}
