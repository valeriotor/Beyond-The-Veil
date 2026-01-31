package com.valeriotor.beyondtheveil.capability;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamRegistry;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.rituals.bindings.Binding;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingData;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
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
    private final Map<String, CompoundTag> tags = new HashMap<>();
    private final List<Counter> counters = new ArrayList<>();
    private final Map<Memory, MemoryStatus> memories = new EnumMap<>(Memory.class);
    private final Map<String, Reminiscence> reminiscences = new HashMap<>();
    private final Map<String, Report> reports = new HashMap<>();
    private BindingData bindingData;
    private Report currentReport;
    private boolean editingReport;
    private String previousReportName;

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

    public Set<String> getAllBooleans() {
        return booleans;
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

    public void setTag(String key, CompoundTag tag) {
        tags.put(key, tag);
    }

    public void removeTag(String key) {
        tags.remove(key);
    }

    public CompoundTag getTag(String key) {
        return tags.getOrDefault(key, null);
    }

    public Set<String> getAllTagKeys() {
        return tags.keySet();
    }

    /**
     * Creates a new counter, or updates the timer of a previous one if one of the same type already existed
     *
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

    /**
     * @return finished CounterTypes
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

    public void addMemory(Memory memory) {
        memories.put(memory, new MemoryStatus(memory, new int[]{1, 0, 0, 0, 0, 0}));
    }

    public boolean hasMemory(Memory memory) {
        return memories.containsKey(memory) && memories.get(memory).values[0] > 0;
    }

    public MemoryStatus getStatusOrAddIfAbsent(Memory memory) {
        if (!memories.containsKey(memory)) {
            addMemory(memory);
        }
        return memories.get(memory);
    }

    public List<MemoryStatus> getStatuses() {
        return new ArrayList<>(memories.values());
    }

    public boolean addReminiscence(String key, Reminiscence reminiscence) {
        boolean returnValue = reminiscences.containsKey(key);
        reminiscences.put(key, reminiscence);
        return returnValue;
    }

    public Reminiscence removeReminiscence(String key) {
        return reminiscences.remove(key);
    }

    public void clearReminiscences() {
        reminiscences.clear();
    }

    public Map<String, Reminiscence> getReminiscences() {
        return reminiscences;
    }

    public Report addReport(Report report) {
        return reports.put(report.getName(), report);
    }

    public Report deleteReport(String name) {
        return reports.remove(name);
    }

    public Report getReport(String name) {
        return reports.get(name);
    }

    public Map<String, Report> getReports() {
        return reports;
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    public boolean isEditingReport() {
        return getBoolean(PlayerDataLib.editing_report.name());
    }

    public String getPreviousReportName() {
        return getString(PlayerDataLib.PREVIOUS_REPORT_NAME);
    }

    public void setCurrentReport(Report currentReport, boolean editing, String previousReportName) {
        this.currentReport = currentReport;
        setBoolean(PlayerDataLib.editing_report.name(), editing, false);
        if (previousReportName != null) {
            setString(PlayerDataLib.PREVIOUS_REPORT_NAME, previousReportName, false);
        } else {
            removeString(PlayerDataLib.PREVIOUS_REPORT_NAME);
        }
    }

    public void bind(Binding binding) {
        bindingData = new BindingData(binding);
    }

    public void setBindingData(BindingData bindingData) {
        this.bindingData = bindingData;
    }

    public BindingData getBindingData() {
        return bindingData;
    }

    public void saveToNBT(CompoundTag compoundTag) {
        CompoundTag booleans = new CompoundTag();
        CompoundTag ints = new CompoundTag();
        CompoundTag longs = new CompoundTag();
        CompoundTag strings = new CompoundTag();
        CompoundTag counters = new CompoundTag();
        CompoundTag tags = new CompoundTag();
        CompoundTag memories = saveMemories();
        CompoundTag reminiscences = new CompoundTag();
        CompoundTag reports = new CompoundTag();
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
        for (Entry<String, CompoundTag> e : this.tags.entrySet()) {
            tags.put(e.getKey(), e.getValue());
        }
        for (Counter c : this.counters) {
            counters.putInt(c.type.name(), c.counter);
        }
        for (Entry<String, Reminiscence> e : this.reminiscences.entrySet()) {
            reminiscences.put(e.getKey(), e.getValue().save());
        }
        for (Entry<String, Report> e : this.reports.entrySet()) {
            reports.put(e.getKey(), e.getValue().saveToNBT());
        }
        if (currentReport != null) {
            compoundTag.put("currentReport", currentReport.saveToNBT());
        }
        if (bindingData != null) {
            compoundTag.put("bindingData", bindingData.saveToNBT(new CompoundTag()));
        }
        compoundTag.put("booleans", booleans);
        compoundTag.put("ints", ints);
        compoundTag.put("longs", longs);
        compoundTag.put("strings", strings);
        compoundTag.put("tags", tags);
        compoundTag.put("counters", counters);
        compoundTag.put("memories", memories);
        compoundTag.put("reminiscences", reminiscences);
        compoundTag.put("reports", reports);
    }

    public CompoundTag saveMemories() {
        CompoundTag memories = new CompoundTag();
        for (Entry<Memory, MemoryStatus> entry : this.memories.entrySet()) {
            memories.put(entry.getKey().getDataName(), entry.getValue().saveToNBT());
        }
        return memories;
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
        if (compoundTag.contains("tags")) {
            CompoundTag tags = compoundTag.getCompound("tags");
            for (String key : tags.getAllKeys()) {
                this.tags.put(key, tags.getCompound(key));
            }
        }
        if (compoundTag.contains("counters")) {
            CompoundTag counters = compoundTag.getCompound("counters");
            for (String key : counters.getAllKeys()) {
                this.counters.add(new Counter(counters.getInt(key), CounterType.fromName(key)));
            }
        }
        if (compoundTag.contains("memories")) {
            CompoundTag memories = compoundTag.getCompound("memories");
            loadMemories(memories);
        }

        if (compoundTag.contains("reminiscences")) {
            CompoundTag reminiscences = compoundTag.getCompound("reminiscences");
            for (String key : reminiscences.getAllKeys()) {
                Reminiscence reminiscence = DreamRegistry.getReminiscence(key);
                if (reminiscence != null) {
                    reminiscence.load(reminiscences.getCompound(key));
                    this.reminiscences.put(key, reminiscence);
                }
            }
        }

        if (compoundTag.contains("bindingData")) {
            bindingData = new BindingData(compoundTag.getCompound("bindingData"));
        }

        CompoundTag reports = compoundTag.getCompound("reports");
        for (String key : reports.getAllKeys()) {
            Report report = Report.loadFromNBT(reports.getCompound(key));
            this.reports.put(report.getName(), report);
        }

        if (compoundTag.contains("currentReport")) {
            currentReport = Report.loadFromNBT(compoundTag.getCompound("currentReport"));
        }
    }

    public void loadMemories(CompoundTag memories) {
        for (String key : memories.getAllKeys()) {
            Memory memory = Memory.getMemoryFromDataName(key);
            if (memories.contains(key, 10)) {
                MemoryStatus status = MemoryStatus.fromNBT(memories.getCompound(key));
                if (memory != null) {
                    this.memories.put(memory, status);
                }
            }
        }
    }

    /**
     * Assumes new store is empty
     */
    public void copyToNewStore(PlayerData newStore) {
        newStore.booleans.addAll(booleans);
        newStore.ints.putAll(ints);
        newStore.longs.putAll(longs);
        newStore.strings.putAll(strings);
        newStore.counters.addAll(counters);
        newStore.tags.putAll(tags);
        newStore.memories.putAll(memories);
        newStore.reminiscences.putAll(reminiscences);
        newStore.reports.putAll(reports);

    }

    public static class MemoryStatus {

        private static MemoryStatus fromNBT(CompoundTag tag) {
            Memory memory1 = Memory.getMemoryFromDataName(tag.getString("memory"));
            int[] values1 = tag.getIntArray("values");
            MemoryStatus status = new MemoryStatus(memory1, values1);
            status.changed = tag.getBoolean("changed");
            return status;
        }

        private final Memory memory;
        private final int[] values;
        private boolean changed = true;

        private MemoryStatus(Memory memory) {
            this(memory, new int[]{0, 0, 0, 0, 0, 0});
        }

        private MemoryStatus(Memory memory, int[] values) {
            this.memory = memory;
            this.values = values;
            for (int i = 0; i < values.length; i++) {
                int max = memory.getMaxStatus(Memory.Target.values()[i / 2], i % 2 == 1);
                if (values[i] > max) {
                    values[i] = max;
                }
            }
        }

        public void increaseTo(int value, Memory.Target target, boolean hasVoid) {
            int i = target.ordinal() * 2 + (hasVoid ? 1 : 0);
            int max = memory.getMaxStatus(target, hasVoid);
            int prev = values[i];
            values[i] = Math.min(max, Math.max(values[i], value));
            if (prev != values[i]) {
                changed = true;
            }
            if (hasVoid && values[i - 1] < prev) {
                values[i - 1] = prev;
            }
        }

        public int[] getValues() {
            return values;
        }

        public void setChanged(boolean changed) {
            this.changed = changed;
        }

        public boolean isChanged() {
            return changed;
        }

        private CompoundTag saveToNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putString("memory", memory.getDataName());
            tag.putIntArray("values", values);
            tag.putBoolean("changed", changed);
            return tag;
        }
    }

    private static class FallBack extends PlayerData {
        // TODO find another way that doesn't require editing for every change to the main class
        // this is so stupid how could you wtf
        @Override
        public void setBoolean(String key, boolean value, boolean temporary) {
        }

        @Override
        public boolean getBoolean(String key) {
            return false;
        }

        @Override
        public boolean getBoolean(String key, boolean temporary) {
            return false;
        }

        @Override
        public void setInteger(String key, int value, boolean temporary) {
        }

        @Override
        public Integer incrementOrSetInteger(String key, int amount, int valueIfAbsent, boolean temporary) {
            return valueIfAbsent;
        }

        @Override
        public Integer getOrSetInteger(String key, int valueIfAbsent, boolean temporary) {
            return valueIfAbsent;
        }

        @Override
        public Integer getInteger(String key) {
            return null;
        }

        @Override
        public Integer getInteger(String key, boolean temporary) {
            return null;
        }

        @Override
        public void setLong(String key, long value, boolean temporary) {
        }

        @Override
        public Long getLong(String key) {
            return null;
        }

        @Override
        public Long getLong(String key, boolean temporary) {
            return null;
        }

        @Override
        public void setString(String key, String value, boolean temporary) {
        }

        @Override
        public String getString(String key) {
            return null;
        }

        @Override
        public String getString(String key, boolean temporary) {
            return null;
        }

        @Override
        public void saveToNBT(CompoundTag compoundTag) {
        }

        @Override
        public void loadFromNBT(CompoundTag compoundTag) {
        }

        @Override
        public void copyToNewStore(PlayerData newStore) {
        }


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
