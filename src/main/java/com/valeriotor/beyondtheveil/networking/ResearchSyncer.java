package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ResearchSyncer {

    public static OneResearchToServer oneResearchToServer(String key) {
        return new OneResearchToServer(key);
    }

    public static OneResearchToClient oneResearchToClient(ResearchStatus status) {
        return new OneResearchToClient(status);
    }

    public static MarkResearchesToClient markResearchesToClient(Collection<String> toUpdate) {
        return new MarkResearchesToClient(toUpdate);
    }

    public static AllResearchesToClient allResearchesToClient(CompoundTag researchData) {
        return new AllResearchesToClient(researchData);
    }

    public static ResearchSyncer fromServer(CompoundTag nbt) {
        String type = nbt.getString("syncType");
        if(OneResearchToClient.class.descriptorString().equals(type)) {
            return new OneResearchToClient(nbt);
        } else if (MarkResearchesToClient.class.descriptorString().equals(type)) {
            return new MarkResearchesToClient(nbt);
        } else if (AllResearchesToClient.class.descriptorString().equals(type)) {
            return new AllResearchesToClient(nbt);
        }
        return null;
    }

    public static ResearchSyncer fromClient(CompoundTag nbt) {
        String type = nbt.getString("syncType");
        if(OneResearchToServer.class.descriptorString().equals(nbt.getString("syncType"))) {
            return new OneResearchToServer(nbt);
        }
        return null;
    }

    public void writeToNBT(CompoundTag nbt) {
        nbt.putString("syncType", getClass().descriptorString());
        writeToNBT_internal(nbt);
    }

    protected abstract void writeToNBT_internal(CompoundTag nbt);

    public abstract void process(Player p);

    public static class OneResearchToServer extends ResearchSyncer {
        private boolean progress = false;
        private boolean updateMark = false;
        private boolean updateValue = false;
        private final String key;

        private OneResearchToServer(String key) {
            this.key = key;
        }

        public OneResearchToServer(CompoundTag nbt) {
            this.progress = nbt.getBoolean("progress");
            this.updateMark = nbt.getBoolean("updateMark");
            if(updateMark) this.updateValue = nbt.getBoolean("updateValue");
            this.key = nbt.getString("key");
        }

        public OneResearchToServer setProgress(boolean progress) {
            this.progress = progress;
            return this;
        }

        public OneResearchToServer setUpdateMark(boolean updateValue) {
            this.updateMark = true;
            this.updateValue = updateValue;
            return this;
        }

        @Override
        protected void writeToNBT_internal(CompoundTag nbt) {
            nbt.putString("key", key);
            nbt.putBoolean("progress", progress);
            nbt.putBoolean("updateMark", updateMark);
            if(updateMark) nbt.putBoolean("updateValue", updateValue);
        }

        @Override
        public void process(Player p) {
            if(progress) ResearchUtil.progressResearchServerAndSync(p, key);
            if(updateMark) ResearchUtil.setResearchUpdated(p, key, updateValue);
        }
    }

    public static class OneResearchToClient extends ResearchSyncer {
        private final String key;
        private final CompoundTag newStatus;

        private OneResearchToClient(ResearchStatus toUpdate) {
            this.key = toUpdate.res.getKey();
            newStatus = new CompoundTag();
            toUpdate.writeToNBT(newStatus);
        }

        private OneResearchToClient(CompoundTag nbt) {
            key = nbt.getString("key");
            newStatus = nbt.getCompound("status");
        }

        @Override
        protected void writeToNBT_internal(CompoundTag nbt) {
            nbt.putString("key", key);
            nbt.put("status", newStatus);
        }

        @Override
        public void process(Player p) {
            ResearchUtil.getResearch(p, key).readFromNBT(newStatus);
        }
    }

    public static class MarkResearchesToClient extends ResearchSyncer {

        private final Collection<String> updatedResearches;

        private MarkResearchesToClient(Collection<String> updatedResearches) {
            this.updatedResearches = updatedResearches;
        }

        private MarkResearchesToClient(CompoundTag tag) {
            this.updatedResearches = new ArrayList<>();
            CompoundTag list = tag.getCompound("list");
            this.updatedResearches.addAll(list.getAllKeys());
        }

        @Override
        protected void writeToNBT_internal(CompoundTag nbt) {
            CompoundTag list = new CompoundTag();
            for (String s : updatedResearches) {
                list.putBoolean(s, true);
            }
            nbt.put("list", list);
        }

        @Override
        public void process(Player p) {
            for (String s : updatedResearches) {
                ResearchUtil.setResearchUpdated(p, s, true);
            }
        }
    }

    public static class AllResearchesToClient extends ResearchSyncer {

        private CompoundTag researchData;

        private AllResearchesToClient(CompoundTag researchData) {
            this.researchData = researchData;
        }

        @Override
        protected void writeToNBT_internal(CompoundTag nbt) {
            nbt.put("data", researchData);
        }

        @Override
        public void process(Player p) {
            ResearchUtilClient.loadResearchDataNBT(researchData.getCompound("data"));
        }
    }


}
