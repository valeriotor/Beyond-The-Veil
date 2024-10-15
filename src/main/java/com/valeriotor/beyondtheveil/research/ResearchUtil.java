package com.valeriotor.beyondtheveil.research;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.research.ResearchData;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.ResearchSyncer;
import com.valeriotor.beyondtheveil.networking.SyncResearchToClientPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;

import java.util.*;
import java.util.Map.Entry;

public class ResearchUtil {

    public static void progressResearchServerAndSync(Player p, String key) {
        progressResearch(p, key);
        ResearchStatus newStatus = getResearch(p, key);
        Messages.sendToPlayer(new SyncResearchToClientPacket(ResearchSyncer.oneResearchToClient(newStatus)), (ServerPlayer) p);
    }

    public static void progressResearch(Player p, String key) {
        p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> researchData.getResearch(key).progressStage(p));
    }

    public static void learnResearchServerAndSync(Player p, String key) {
        learnResearch(p, key);
        ResearchStatus newStatus = getResearch(p, key);
        Messages.sendToPlayer(new SyncResearchToClientPacket(ResearchSyncer.oneResearchToClient(newStatus)), (ServerPlayer) p);
    }

    public static void learnResearch(Player p, String key) {
        p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> researchData.getResearch(key).learn());
    }

    /**
     * Server-side only
     */
    public static boolean learn(Player p) {
        if (p.level().isClientSide) return false;
        Optional<ResearchData> capability = p.getCapability(ResearchProvider.RESEARCH, null).resolve();
        if (capability.isPresent()) {
            Map<String, ResearchStatus> stati = capability.get().getResearches();
            PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY);
            for (Entry<String, ResearchStatus> entry : stati.entrySet()) {
                if (entry.getValue().isLearnable(stati, data)) {
                    ResearchUtil.learnResearchServerAndSync(p, entry.getKey());
                    if (entry.getKey().equals("CRYSTALDREAMS"))
                        Memory.METAL.unlock(p, false);
                    return true;
                }
            }
        }
        return false;
    }

    public static void setResearchUpdated(Player p, String key, boolean updated) {
        p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> researchData.getResearch(key).setUpdated(updated));
    }

    public static ResearchStatus getResearch(Player p, String key) {
        if (!(p instanceof FakePlayer)) {
            Optional<ResearchData> capability = p.getCapability(ResearchProvider.RESEARCH).resolve();
            if (capability.isPresent()) {
                return capability.get().getResearch(key);
            }
            return DUMMY_STATUS;
        }
        return DUMMY_STATUS;
    }

    public static Map<String, ResearchStatus> getResearches(Player p) {
        if (!(p instanceof FakePlayer)) {
            Optional<ResearchData> capability = p.getCapability(ResearchProvider.RESEARCH).resolve();
            if (capability.isPresent()) {
                return capability.get().getResearches();
            }
        }
        return new HashMap<>();
    }


    public static int getResearchStage(Player p, String key) {
        ResearchStatus r = getResearch(p, key);
        if (r != null) return r.getStage();
        return -2;
    }

    public static Map<String, Research> getKnownRecipes(Player p) {
        Map<String, ResearchStatus> researches = getResearches(p);
        Map<String, Research> knownRecipes = new HashMap<>();
        for (ResearchStatus value : researches.values()) {
            if (value.isVisible(p) && value.getStage() >= 0) {
                for (int i = 0; i < value.res.getStages().length && i < value.getStage(); i++) {
                    for (String recipe : value.res.getRecipes()) {
                        if (!knownRecipes.containsKey(recipe)) {
                            knownRecipes.put(recipe, value.res);
                        }
                    }
                }
            }
        }
        return knownRecipes;
    }

    public static boolean isResearchVisible(Player p, String key) {
        return getResearch(p, key).isVisible(p);
    }

    public static boolean isResearchVisible(Map<String, ResearchStatus> map, PlayerData data, String key) {
        return map.get(key).isVisible(map, data);
    }

    public static boolean isResearchComplete(Player p, String key) {
        return getResearch(p, key).isComplete();
    }

    public static boolean isResearchKnown(Player p, String key) {
        return getResearch(p, key).isKnown(p);
    }

    public static boolean isResearchOpened(Player p, String key) {
        return getResearchStage(p, key) >= 0;
    }

	/*public static boolean knowsResearch(Player p, String key) {
		return false;
	}*/

    public static void completeResearch(Player p, String key) {
        if (p.level().isClientSide) return;
        p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> {
            Map<String, ResearchStatus> map = researchData.getResearches();
            if (map.containsKey(key)) {
                researchData.getResearch(key).complete(map, p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY));
                ResearchStatus newStatus = getResearch(p, key);
                Messages.sendToPlayer(new SyncResearchToClientPacket(ResearchSyncer.oneResearchToClient(newStatus)), (ServerPlayer) p);
            } else {
                p.sendSystemMessage(Component.translatable("Research key not found"));
            }
        });

    }

    //public static void resetResearch(Player p) {
    //    p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> {
    //        Map<String, ResearchStatus> map = researchData.getResearches();
    //        for (Entry<String, ResearchStatus> entry : map.entrySet()) {
    //            entry.getValue().unlearn();
    //            BTVPacketHandler.INSTANCE.sendTo(new MessageSyncResearchToClient(new ResearchSyncer(entry.getKey()).setUnlearn(true)), (ServerPlayer) p);
    //            SyncUtil.removeStringDataOnServer(p, entry.getKey());
    //        }
    //    });
    //}

    public static void printResearch(Player p) {
        p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> {
            Map<String, ResearchStatus> map = researchData.getResearches();
            PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY);
            for (Entry<String, ResearchStatus> entry : map.entrySet()) {
                if (entry.getValue().isVisible(map, data)) {
                    p.sendSystemMessage(Component.translatable(entry.getKey() + " Stage: " + entry.getValue().getStage()));
                }
            }
        });

    }

    public static void markResearchAsUpdated(Player p, String newDataString) {
        if (!p.level().isClientSide) {
            p.getCapability(ResearchProvider.RESEARCH, null).ifPresent(researchData -> {
                Map<String, ResearchStatus> map = researchData.getResearches();
                PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY);
                Set<String> updatedResearches = new HashSet<>();
                for (Entry<String, ResearchStatus> entry : map.entrySet()) {
                    ResearchStatus research = entry.getValue();
                    if (research.isKnown(map, data) && research.getStage() > -1) {
                        boolean flag = false;
                        if (research.canProgressStage(data)) {
                            for (String requirement : research.res.getStages()[research.getStage()].getRequirements()) {
                                if (requirement.equals(newDataString)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            for (Research.SubResearch addenda : research.res.getAddenda()) {
                                if (addenda.meetsRequirements(data)) {
                                    for (String s : addenda.getRequirements()) {
                                        if (s.equals(newDataString)) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (flag) {
                            research.setUpdated(true);
                            updatedResearches.add(research.res.getKey());
                        }
                    }
                }
                Messages.sendToPlayer(new SyncResearchToClientPacket(ResearchSyncer.markResearchesToClient(updatedResearches)), (ServerPlayer) p);
            });

        }
    }

    private static final ResearchStatus DUMMY_STATUS = new ResearchStatus(ResearchRegistry.researches.get("CRYSTALDREAMS")) {
        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public int getStage() {
            return -2;
        }


        @Override
        public boolean isKnown(Player p) {
            return false;
        }


        @Override
        public boolean isVisible(Player p) {
            return false;
        }

        @Override
        public CompoundTag writeToNBT(CompoundTag nbt) {
            return nbt;
        }
    };

}
