package com.valeriotor.beyondtheveil.client.research;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.research.ResearchData;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.ResearchSyncer;
import com.valeriotor.beyondtheveil.networking.SyncResearchToServerPacket;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

public class ResearchUtilClient {

    public static void processResearchSyncer(ResearchSyncer syncer) {
        syncer.process(Minecraft.getInstance().player);
    }

    public static void loadResearchDataNBT(CompoundTag researchData) {
        if (Minecraft.getInstance().player != null) {
            Optional<ResearchData> resolve = Minecraft.getInstance().player.getCapability(ResearchProvider.RESEARCH).resolve();
            resolve.ifPresent(researchData1 -> researchData1.loadFromNBT(researchData));
        }
    }

    public static void progressResearchClientAndSync(String key) {
        if (Minecraft.getInstance().player != null) {
            ResearchUtil.progressResearch(Minecraft.getInstance().player, key);
            Messages.sendToServer(new SyncResearchToServerPacket(ResearchSyncer.oneResearchToServer(key).setProgress(true)));
        }
    }

    public static void openUpdatedResearchClientAndSync(String key) {
        if (Minecraft.getInstance().player != null) {
            ResearchUtil.setResearchUpdated(Minecraft.getInstance().player, key, false);
            Messages.sendToServer(new SyncResearchToServerPacket(ResearchSyncer.oneResearchToServer(key).setUpdateMark(false)));
        }
    }

}
