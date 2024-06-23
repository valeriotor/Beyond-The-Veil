package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.util.CounterType;
import net.minecraft.nbt.CompoundTag;

import java.util.*;
import java.util.Map.Entry;

public class PlayerData {

    public static final PlayerData DUMMY = new FallBack();

    private final Set<String> booleans = new HashSet<>();
    private final Set<String> tempBooleans = new HashSet<>();
    private final Map<String, Integer> ints = new HashMap<>();
    private final Map<String, Integer> tempInts = new HashMap<>();
    private final Map<String, Long> longs = new HashMap<>();
    private final Map<String, Long> tempLongs = new HashMap<>();
    private final Map<String, String> strings = new HashMap<>();
    private final Map<String, String> tempStrings = new HashMap<>();
    private final List<Counter> counters = new ArrayList<>();
    private final EnumMap<Memory, Reminiscence> reminiscences = new EnumMap<>(Memory.class);

    public void setBoolean(String key, boolean value, boolean temporary) {
        if (value) {
            if (temporary) {
                tempBooleans.add(key);
            } else {
                booleans.add(key);
            }
        } else {
            tempBooleans.remove(key);
            booleans.remove(key);
        }
    }

    public boolean getBoolean(String key) {
        return booleans.contains(key) || tempBooleans.contains(key);
    }

    public boolean getBoolean(String key, boolean temporary) {
        if (!temporary) {
            return booleans.contains(key);
        } else {
            return tempBooleans.contains(key);
        }
    }

    public void setInteger(String key, int value, boolean temporary) {
        if (!temporary) {
            ints.put(key, value);
        } else {
            tempInts.put(key, value);
        }
    }

    public Integer incrementOrSetInteger(String key, int amount, int valueIfAbsent, boolean temporary) {
        Integer currentValue = getInteger(key);
        if (currentValue == null) {
            setInteger(key, valueIfAbsent, temporary);
            return valueIfAbsent;
        } else {
            currentValue += amount;
            setInteger(key, currentValue, temporary);
            return currentValue;
        }
    }

    public Integer getOrSetInteger(String key, int valueIfAbsent, boolean temporary) {
        Integer a = getInteger(key);
        if (a != null) return a;
        setInteger(key, valueIfAbsent, temporary);
        return valueIfAbsent;
    }

    public Long getOrSetLong(String key, long valueIfAbsent, boolean temporary) {
        Long a = getLong(key);
        if (a != null) return a;
        setLong(key, valueIfAbsent, temporary);
        return valueIfAbsent;
    }

    public Integer getInteger(String key) {
        if (ints.containsKey(key)) return ints.get(key);
        else if (tempInts.containsKey(key)) return tempInts.get(key);
        return null;
    }

    public Integer getInteger(String key, boolean temporary) {
        if (!temporary) {
            if (ints.containsKey(key)) return ints.get(key);
            return null;
        } else {
            if (tempInts.containsKey(key)) return tempInts.get(key);
            return null;
        }
    }

    public void setLong(String key, long value, boolean temporary) {
        if (!temporary) {
            longs.put(key, value);
        } else {
            tempLongs.put(key, value);
        }
    }

    public Long getLong(String key) {
        if (longs.containsKey(key)) return longs.get(key);
        else if (tempLongs.containsKey(key)) return tempLongs.get(key);
        return null;
    }

    public Long getLong(String key, boolean temporary) {
        if (!temporary) {
            if (longs.containsKey(key)) return longs.get(key);
            return null;
        } else {
            if (tempLongs.containsKey(key)) return tempLongs.get(key);
            return null;
        }
    }

    public void setString(String key, String value, boolean temporary) {
        if (!temporary) {
            strings.put(key, value);
        } else {
            tempStrings.put(key, value);
        }
    }

    public String getString(String key) {
        if (strings.containsKey(key)) return strings.get(key);
        else if (tempStrings.containsKey(key)) return tempStrings.get(key);
        return null;
    }

    public String getString(String key, boolean temporary) {
        if (!temporary) {
            if (strings.containsKey(key)) return strings.get(key);
            return null;
        } else {
            if (tempStrings.containsKey(key)) return tempStrings.get(key);
            return null;
        }
    }

    public boolean removeString(String key) {
        boolean success = false;
        if (strings.containsKey(key)) {
            strings.remove(key);
            success = true;
        }
        if (tempStrings.containsKey(key)) {
            tempStrings.remove(key);
            success = true;
        }
        return success;
    }

    /** Creates a new counter, or updates the timer of a previous one if one of the same type already existed
     * @return timer of the previously existing counter of the same type, 0 if absent
     */
    public int createCounter(CounterType type, int timer) {
        for (Counter c : counters) {
            if (c.type == type) {
                int prev = c.counter;
                c.counter = timer;
                return prev;
            }
        }
        counters.add(new Counter(timer, type));
        return 0;
    }

    /** @return finished CounterTypes
     */
    public List<CounterType> tickCounters() {
        List<CounterType> finished = new ArrayList<>();
        Iterator<Counter> counterIterator = counters.listIterator();
        while (counterIterator.hasNext()) {
            Counter c = counterIterator.next();
            c.counter--;
            if (c.counter <= 0) {
                counterIterator.remove();
                finished.add(c.type);
            }
        }
        return finished;
    }

    public boolean hasCounterType(CounterType type) {
        for (Counter c : counters) {
            if (c.type == type) {
                return true;
            }
        }
        return false;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public boolean addReminiscence(Memory memory, Reminiscence reminiscence) {
        boolean returnValue = reminiscences.containsKey(memory);
        reminiscences.put(memory, reminiscence);
        return returnValue;
    }

    public void clearReminiscences() {
        reminiscences.clear();
    }

    public EnumMap<Memory, Reminiscence> getReminiscences() {
        return reminiscences;
    }

    public void saveToNBT(CompoundTag compoundTag) {
        CompoundTag booleans = new CompoundTag();
        CompoundTag ints = new CompoundTag();
        CompoundTag longs = new CompoundTag();
        CompoundTag strings = new CompoundTag();
        CompoundTag counters = new CompoundTag();
        CompoundTag reminiscences = new CompoundTag();
        for (String s : this.booleans) {
            booleans.putBoolean(s, true);
        }
        for (Entry<String, Integer> e : this.ints.entrySet()) {
            ints.putInt(e.getKey(), e.getValue());
        }
        for (Entry<String, Long> e : this.longs.entrySet()) {
            longs.putLong(e.getKey(), e.getValue());
        }
        for (Entry<String, String> e : this.strings.entrySet()) {
            strings.putString(e.getKey(), e.getValue());
        }
        for (Counter c : this.counters) {
            counters.putInt(c.type.name(), c.counter);
        }
        for (Entry<Memory, Reminiscence> e : this.reminiscences.entrySet()) {
            reminiscences.put(e.getKey().getDataName(), e.getValue().save());
        }
        compoundTag.put("booleans", booleans);
        compoundTag.put("ints", ints);
        compoundTag.put("longs", longs);
        compoundTag.put("strings", strings);
        compoundTag.put("counters", counters);
        compoundTag.put("reminiscences", reminiscences);
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        if (compoundTag.contains("booleans")) {
            CompoundTag booleans = compoundTag.getCompound("booleans");
            this.booleans.addAll(booleans.getAllKeys());
        }
        if (compoundTag.contains("ints")) {
            CompoundTag ints = compoundTag.getCompound("ints");
            for (String key : ints.getAllKeys()) {
                this.ints.put(key, ints.getInt(key));
            }
        }
        if (compoundTag.contains("longs")) {
            CompoundTag longs = compoundTag.getCompound("longs");
            for (String key : longs.getAllKeys()) {
                this.longs.put(key, longs.getLong(key));
            }
        }
        if (compoundTag.contains("strings")) {
            CompoundTag strings = compoundTag.getCompound("strings");
            for (String key : strings.getAllKeys()) {
                this.strings.put(key, strings.getString(key));
            }
        }
        if (compoundTag.contains("counters")) {
            CompoundTag counters = compoundTag.getCompound("counters");
            for (String key : counters.getAllKeys()) {
                this.counters.add(new Counter(counters.getInt(key), CounterType.fromName(key)));
            }
        }
        if (compoundTag.contains("reminiscences")) {
            CompoundTag reminiscences = compoundTag.getCompound("reminiscences");
            for (String key : reminiscences.getAllKeys()) {
                Memory memory = Memory.getMemoryFromDataName(key);
                Reminiscence reminiscence = Dream.REMINISCENCE_REGISTRY.get(memory).get();
                reminiscence.load(reminiscences.getCompound(key));
                this.reminiscences.put(memory, reminiscence);
            }
        }
    }

    /** Assumes new store is empty
     */
    public void copyToNewStore(PlayerData newStore) {
        newStore.booleans.addAll(booleans);
        newStore.ints.putAll(ints);
        newStore.longs.putAll(longs);
        newStore.strings.putAll(strings);
        newStore.counters.addAll(counters);
    }

    private static class FallBack extends PlayerData {
        // TODO find another way that doesn't require editing for every change to the main class
        @Override public void setBoolean(String key, boolean value, boolean temporary) {}
        @Override public boolean getBoolean(String key) {return false;}
        @Override public boolean getBoolean(String key, boolean temporary) {return false;}
        @Override public void setInteger(String key, int value, boolean temporary) {}
        @Override public Integer incrementOrSetInteger(String key, int amount, int valueIfAbsent, boolean temporary) {return valueIfAbsent;}
        @Override public Integer getOrSetInteger(String key, int valueIfAbsent, boolean temporary) {return valueIfAbsent;}
        @Override public Integer getInteger(String key) {return null;}
        @Override public Integer getInteger(String key, boolean temporary) {return null;}
        @Override public void setLong(String key, long value, boolean temporary) {}
        @Override public Long getLong(String key) {return null;}
        @Override public Long getLong(String key, boolean temporary) {return null;}
        @Override public void setString(String key, String value, boolean temporary) {}
        @Override public String getString(String key) {return null;}
        @Override public String getString(String key, boolean temporary) {return null;}
        @Override public void saveToNBT(CompoundTag compoundTag) {}
        @Override public void loadFromNBT(CompoundTag compoundTag) {}
        @Override public void copyToNewStore(PlayerData newStore) {}
    }

    public static class Counter {
        int counter;
        CounterType type;

        public Counter(int counter, CounterType type) {
            this.counter = counter;
            this.type = type;
        }

        public CounterType getType() {
            return type;
        }
    }


}
