package com.valeriotor.beyondtheveil.rituals;

import com.valeriotor.beyondtheveil.tile.BloodBasinBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RitualStatus {

    public static final double STEP_SIZE = 0.025; // per tick

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
        RitualTemplate template = RitualRegistry.longestMatch(items);
        if (template == null) {
            return null;
        }
        return new RitualStatus(template, startPos, altars, initiator);
    }

    private RitualStatus(RitualTemplate template, BlockPos startPos, List<BlockPos> altars, UUID initiator) {
        this.template = template;
        this.startPos = startPos;
        this.altars = new ArrayList<>(altars);
        this.initiator = initiator;

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

    public List<BlockPos> getAltars() {
        return altars;
    }

    public boolean tick(Level level) {
        // SERVER ONLY
        // validity checks?

        if (currentHop >= distances.length) {
            return true;
        }
        if (currentHop >= altars.size() || level.getBlockEntity(altars.get(currentHop)) instanceof BloodBasinBE) {

            if (progressUntilNextHop <= distances[currentHop]) {
                progressUntilNextHop += STEP_SIZE;
            }
            if (progressUntilNextHop > distances[currentHop]) {
                // TODO wait for a bit, process instability
                boolean success = true;
                if (currentHop < distances.length - 1 && currentHop > numberOfModifiers() - 1) {
                    BloodBasinBE bloodBasin = (BloodBasinBE) level.getBlockEntity(altars.get(currentHop));
                    ItemStack heldItem = bloodBasin.getStackHandler().getStackInSlot(0);
                    if (heldItem.getItem() != template.getIngredients().get(currentHop - numberOfModifiers())) {
                        success = false;
                        primaryInstability += template.getPrimaryInstabilityRate();
                        secondaryInstability += template.getSecondaryInstabilityRate();
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
                if (success) {
                    currentHop++;
                    if (currentHop >= distances.length) {
                        return true;
                    } else {
                        BloodBasinBE bloodBasin = (BloodBasinBE) level.getBlockEntity(altars.get(currentHop - 1));
                        if (currentHop - 1 > numberOfModifiers() - 1) {
                            burnedIngredients.add(bloodBasin.removeItem());
                        } else {
                            burnedModifiers.add(bloodBasin.removeItem());
                        }
                    }
                    progressUntilNextHop = 0;
                }
            }
        } else {
            earlyStop = true;
        }
        return false;
    }

    private int numberOfModifiers() {
        return distances.length - 1 - template.getIngredients().size();
    }

    public boolean isEarlyStop() {
        return earlyStop;
    }

    public void terminationEffects(ServerLevel level, Vec3 altar) {
        Player player = level.getPlayerByUUID(initiator);
        List<ItemStack> outputStacks = template.getOutputs().apply(burnedIngredients, player);
        for (ItemStack outputStack : outputStacks) {
            ItemEntity item = new ItemEntity(level, altar.x, altar.y, altar.z, outputStack);
            level.addFreshEntity(item);
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

        tag.putInt("itemBurnCounter", itemBurnCounter);
        tag.putString("initiator", initiator.toString());

        return tag;
    }


}
