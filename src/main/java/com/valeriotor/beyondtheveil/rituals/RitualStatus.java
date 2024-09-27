package com.valeriotor.beyondtheveil.rituals;

import com.valeriotor.beyondtheveil.tile.BloodBasinBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
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

    public static RitualStatus startRitual(ServerLevel level, BlockPos startPos, List<BlockPos> altars) {
        List<Item> items = new ArrayList<>();
        for (BlockPos altar : altars) {
            if (level.getBlockEntity(altar) instanceof BloodBasinBE bloodBasinBE) {
                ItemStack stackInSlot = bloodBasinBE.getStackHandler().getStackInSlot(0);
                if(!stackInSlot.isEmpty()) {
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
        return new RitualStatus(template, startPos, altars);
    }

    private RitualStatus(RitualTemplate template, BlockPos startPos, List<BlockPos> altars) {
        this.template = template;
        this.startPos = startPos;
        this.altars = new ArrayList<>(altars);

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


        currentHop = tag.getInt("currentHop");
        progressUntilNextHop = tag.getDouble("progressUntilNextHop");
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


            progressUntilNextHop += STEP_SIZE;
            if (progressUntilNextHop > distances[currentHop]) {
                // TODO wait for a bit, process instability
                currentHop++;
                if (currentHop >= distances.length) {
                    return true;
                } else {
                    BloodBasinBE bloodBasin = (BloodBasinBE) level.getBlockEntity(altars.get(currentHop - 1));
                    bloodBasin.removeItem();
                }
                progressUntilNextHop = 0;
            }
        } else {
            earlyStop = true;
        }
        return false;
    }

    public boolean isEarlyStop() {
        return earlyStop;
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("template", template.getName());
        tag.putLong("startPos", startPos.asLong());
        tag.putLongArray("altars", this.altars.stream().map(BlockPos::asLong).collect(Collectors.toList()));
        tag.putInt("currentHop", currentHop);
        tag.putDouble("progressUntilNextHop", progressUntilNextHop);
        tag.putBoolean("earlyStop", earlyStop);
        return tag;
    }


}
