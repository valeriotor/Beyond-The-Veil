package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.DialogueDataProvider;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.datagen.BTVAdvancements;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeRegistry;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.VanillaUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Set;

public class ResearchEvents {

    private static final Map<String, Integer> SHOREMEN_CUSTOMS_RESEARCHES = Map.of("FARMING_TECHNIQUES", 1, "DELICACIES", 1, "CARPENTRY", 1, "GRASPING_WATER", 1, "DREAM_BOTTLE", 1, "LOCAL_MEDICINE", 1);
    public static void progressResearchEvent(Player p, ResearchStatus status) {
        if (!p.level().isClientSide) {
            status.getSubresearch().getMemories().forEach(m -> m.unlock(p));
            unlockData((ServerPlayer) p, status);
        }
    }

    private static void unlockData(ServerPlayer p, ResearchStatus status) {
        String key = status.res.getKey();
        int stage = status.getStage();
        p.getCapability(DialogueDataProvider.DIALOGUE_DATA).ifPresent(d -> {
            if ("BLACK_MIRROR".equals(key) && stage == 0) {
                addDialogue(d, DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "did_dream");
            } else if ("BLACK_MIRROR".equals(key) && stage == 1) {
                addDialogue(d, DialogueType.BLACK_MIRROR, "initial2");
            } else if ("SURGERY".equals(key) && stage == 0) {
                addDialogue(d, DialogueType.BLACK_MIRROR, "rationalize");
            } else if ("SURGERY".equals(key) && stage == 1) {
                addDialogue(d, DialogueType.BLACK_MIRROR, "idle");
            } else if ("COMMUNION".equals(key) && stage == 1) {
                addCommunionDialogues(d);
            } else if ("CUSTOMS".equals(key) && stage == 0) {
                addDialogue(d, DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "wantslugs");
                DataUtil.addExchange(p, "keeper_ask_slugs");
            } else if (SHOREMEN_CUSTOMS_RESEARCHES.containsKey(key) && stage == SHOREMEN_CUSTOMS_RESEARCHES.get(key)) {
                boolean embraced = DataUtil.getBoolean(p, PlayerDataLib.EMBRACED_CUSTOMS);
                if (!embraced || true) {
                    int total = 0;
                    for (Map.Entry<String, Integer> entry : SHOREMEN_CUSTOMS_RESEARCHES.entrySet()) {
                        int researchStage = ResearchUtil.getResearchStage(p, entry.getKey());
                        if (researchStage >= entry.getValue()) {
                            total++;
                            if (total >= 3) {
                                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.EMBRACED_CUSTOMS, true, false);
                                addDialogue(d, DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "baptism");
                                DataUtil.addExchange(p, "keeper_baptism");
                                break;
                            }
                        }
                    }
                }
            }
        });
        if ("BONE_TIARA".equals(key) && stage == 0) {
            VanillaUtils.awardAdvancement(p, new ResourceLocation(References.MODID, "ingredients/emerald_gem"));
        } else if ("WATERY_CRADLE".equals(key) && stage == 1) {
            DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DISCOVERED_FLUID.apply(BTVFluids.FLUID_OBEDIENCE_HORMONES.getA().get()), true, false);
            DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DISCOVERED_FLUID.apply(BTVFluids.FLUID_PARENTAL_HORMONES.getA().get()), true, false);
        }
    }

    private static void addCommunionDialogues(DialogueData d) {
        addDialogue(d, DialogueType.SHOREMAN_BARTENDER, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_CARPENTER, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_CLERK, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_DRUNK, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_FISHERMAN, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_SCHOLAR, "communed1");
        addDialogue(d, DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "communed1");
    }

    private static void addDialogue(DialogueData d, DialogueType type, String id) {
        d.setDialogue(type, DialogueRegistry.getTemplate(type, id));
    }

    public static void gearBenchCraftEvent(Player p, ItemStack stack) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(c -> {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (key != null) {
                String flag = "crafted_" + key.getPath();
                if (!c.getBoolean(flag)) {
                    DataUtil.setBooleanOnServerAndSync(p, flag, true, false);
                }
            }
        });
    }

    public static void sendLetterEvents(Player player, Exchange exchange) {
        String templateName = exchange.getTemplate().getName();
        if (templateName.equals("keeper_ask_slugs") && exchange.size() == 1) {
            addDialogue(DialogueData.for_(player), DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "afterslug");
        }
    }

}
