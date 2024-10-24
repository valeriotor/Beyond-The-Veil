package com.valeriotor.beyondtheveil.research;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.research.ResearchData;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.event.ResearchEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

import static com.valeriotor.beyondtheveil.capability.PlayerData.DUMMY;

public class ResearchStatus {

    public final Research res;
    private int stage = -1;
    private boolean learned;
    private boolean complete = false;
    private boolean updated = false;

    public ResearchStatus(Research res) {
        this.res = res;
        this.learned = !res.mustLearn();
    }

    public void learn() {
        this.learned = true;
    }

    public boolean canProgressStage(Player p) {
        return this.canProgressStage(p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY));
    }

    public boolean canProgressStage(PlayerData data) {
        return !complete && this.res.getStages()[stage].meetsRequirements(data);
    }

    public boolean progressStage(Player p) {
        PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY);
        Research.SubResearch[] stages = this.res.getStages();
        if (this.stage >= 0 && this.stage < stages.length) {
            if (stages[stage].meetsRequirements(data)) {
                this.progressStage_internal(p);
                return true;
            }
        } else if (this.stage == -1) {
            this.progressStage_internal(p);
            return true;
        }
        return false;
    }

    private void progressStage_internal(Player p) {
        int maxStage = res.getStages().length - 1;
        if (this.stage < maxStage) {
            this.stage++;
            if (this.stage == maxStage) {
                this.complete = true;
            }
            ResearchEvents.progressResearchEvent(p, this);
        }

    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean isHidden(Map<String, ResearchStatus> map, PlayerData data) {
        for (String key : this.res.getHiders()) {
            String[] split = key.split(";");
            String potentialResearchKey = split[0];
            if ((!map.containsKey(potentialResearchKey) || (!map.get(potentialResearchKey).complete && split.length == 1) || (split.length == 2 && map.get(potentialResearchKey).getStage() < Integer.parseInt(split[1]))) && !data.getBoolean(key)) return true;
        }
        return false;
    }

    public boolean isHidden(Player p) {
        return this.isHidden(p.getCapability(ResearchProvider.RESEARCH, null).map(ResearchData::getResearches).get(), p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY));
    }

    public boolean isVisible(Map<String, ResearchStatus> map, PlayerData data) {
        return learned && !isHidden(map, data);
    }

    public boolean isVisible(Player p) {
        return learned && !isHidden(p);
    }

    public boolean isKnown(Player p) {
        Map<String, ResearchStatus> map = p.getCapability(ResearchProvider.RESEARCH, null).map(ResearchData::getResearches).get();
        return complete || stage > -1 || (isVisible(map, p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY)) && parentsComplete(map));
    }

    public boolean isKnown(Map<String, ResearchStatus> map, PlayerData data) {
        return complete || stage > -1 || (isVisible(map, data) && parentsComplete((map)));
    }

    public boolean parentsComplete(Map<String, ResearchStatus> map) {
        for (String s : this.res.getParents()) {
            String[] split = s.split(";");
            if (split.length == 1) {
                if (!map.containsKey(s) || !map.get(s).complete) {
                    return false;
                }
            } else if (!map.containsKey(split[0]) || map.get(split[0]).getStage() < Integer.parseInt(split[1])) {
                return false;
            }
        }
        return true;
    }

    public boolean parentsComplete(Player p) {
        return this.parentsComplete(p.getCapability(ResearchProvider.RESEARCH, null).map(ResearchData::getResearches).get());
    }

    public boolean isLearnable(Player p) {
        return this.isLearnable(p.getCapability(ResearchProvider.RESEARCH, null).map(ResearchData::getResearches).get(), p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY));
    }

    public boolean isLearnable(Map<String, ResearchStatus> map, PlayerData data) {
        return !learned && !isHidden(map, data) && parentsComplete(map);
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
        nbt.putInt("stage", stage);
        nbt.putBoolean("learned", learned);
        nbt.putBoolean("complete", complete);
        nbt.putBoolean("updated", updated);
        return nbt;
    }

    public ResearchStatus readFromNBT(CompoundTag nbt) {
        if (nbt.contains("stage")) this.stage = nbt.getInt("stage");
        if (nbt.contains("learned")) this.learned = this.learned || nbt.getBoolean("learned");
        if (nbt.contains("complete")) this.complete = nbt.getBoolean("complete");
        if (nbt.contains("updated")) this.updated = nbt.getBoolean("updated");
        return this;
    }

    public int getStage() {
        return this.stage;
    }

    public Research.SubResearch getSubresearch() {
        return res.getStages()[getStage()];
    }
    public void unlearn() {
        this.stage = -1;
        this.complete = false;
        this.learned = !res.mustLearn();
    }

    public void complete(Player p) {
        this.complete(p.getCapability(ResearchProvider.RESEARCH, null).map(ResearchData::getResearches).get(), p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(DUMMY));
    }

    public boolean complete(Map<String, ResearchStatus> map, PlayerData data) { // TODO make this always capable of completing? Rename
        for (String key : this.res.getHiders()) {
            if (!map.containsKey(key))
                return false;
            if (!map.get(key).complete)
                if (!map.get(key).complete(map, data))
                    return false;
            if (!data.getBoolean(key)) {
                data.setBoolean(key, true, false);
            }
        }
        for (String key : this.res.getParentKeys()) {
            if (!map.containsKey(key))
                return false;
            if (!map.get(key).complete)
                if (!map.get(key).complete(map, data))
                    return false;
        }
        this.stage = res.getStages().length - 1;
        this.complete = true;
        this.learned = true;
        return true;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "ResearchStatus{" +
                "res=" + res +
                ", stage=" + stage +
                ", learned=" + learned +
                ", complete=" + complete +
                ", updated=" + updated +
                '}';
    }
}
