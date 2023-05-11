package com.valeriotor.beyondtheveil.dreaming;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map.Entry;

public enum Memory {
    ANIMAL(Items.LEATHER, 0xFF401b00),
    BEHEADING(Items.WITHER_SKELETON_SKULL, 0xFF333333),
    CHANGE(Items.WHEAT_SEEDS, 0xFF4dff00, "metalDream"),
    CRYSTAL(Items.GLASS, 0xFFe6d8d8),
    DARKNESS(Items.COAL, 0xFF002233),
    DEATH(Items.ROTTEN_FLESH, 0xFF2a2a2c),
    ELDRITCH(Items.ENDER_EYE, 0xFF400021),
    //TODO HEARTBREAK(Items.getItemFromBlock(BlockRegistry.BlockHeart), 0xFFAA0000, "memPOWER"),
    HUMAN(Items.ARMOR_STAND, 0xFFFFFFFF, "metalDream"),
    INTROSPECTION(Items.PAPER, 0xFFFFFFFF),
    LEARNING(Items.GHAST_TEAR, 0xFF998b69),
    METAL(Items.IRON_INGOT, 0xFF8c8c8c),
    PLANT(Items.JUNGLE_SAPLING, 0xFF00FF00),
    POWER(Items.BLAZE_POWDER, 0xFFff9300, "metalDream"),
    REPAIR(Items.ANVIL, 0xFF99f19d),
    SENTIENCE(Items.BOOK, 0xFFd87474, "metalDream"),
    STILLNESS(Items.SOUL_SAND, 0xFF444444, "metalDream"),
    TOOL(Items.WOODEN_PICKAXE, 0xFF324eAA, "memHUMAN"),
    VOID(Items.OBSIDIAN, 0xFF36111F),
    WATER(Items.WATER_BUCKET, 0xFF1111FF);

    private final ItemStack item;
    private final int color;
    private final String[] reqs;

    Memory(Item item, int color, String... reqs) {
        this.item = new ItemStack(item, 1);
        this.color = color;
        this.reqs = reqs;
    }

    public String getDataName() {
        return "mem".concat(this.name());
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isUnlockable(Player p) {
        if (reqs == null || reqs.length == 0) return true;
        PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY);
        for (String s : reqs) {
            if (!data.getBoolean(s)) return false;
        }
        return true;
    }

    public boolean isUnlocked(Player p) {
        return DataUtil.getBoolean(p, getDataName());
    }

    public void unlock(Player p) {
        this.unlock(p, true);
    }

    public void unlock(Player p, boolean sendMessage) {
        if (!this.isUnlocked(p) && this.isUnlockable(p)) {
            String dataName = this.getDataName();
            DataUtil.setBooleanOnServerAndSync(p, dataName, true, false);
            String s = getFurtherData(this);
            if (s != null) {
                DataUtil.setBooleanOnServerAndSync(p, s, true, false);
            }
            if (sendMessage) {
                //TODO BTVPacketHandler.INSTANCE.sendTo(new MessageGenericToClient(GenericMessageKey.MEMORY_ENTRY, getDataName()), (ServerPlayer) p);
                p.sendMessage(new TranslatableComponent("memory.unlock.message", getTranslationComponent()), p.getUUID());
                for (Entry<String, ResearchStatus> entry : ResearchUtil.getResearches(p).entrySet()) {
                    for (Research.SubResearch addendum : entry.getValue().res.getAddenda()) {
                        for (String req : addendum.getRequirements()) {
                            if (req.equals(dataName)) {
                                p.sendMessage(new TranslatableComponent("memory.unlock.addenda", new TranslatableComponent(entry.getValue().res.getName())), p.getUUID());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public int getColor() {
        return this.color;
    }

    public ItemStack getItemCopy() {
        return this.item.copy();
    }

    public String getLocalizationKey() {
        return "memory.".concat(this.name().toLowerCase().concat(".name"));
    }

    public TranslatableComponent getTranslationComponent() {
        return new TranslatableComponent(getLocalizationKey());
    }

    public static Memory getMemoryFromDataName(String key) {
        for (Memory m : Memory.values()) {
            if (key.equals(m.getDataName()))
                return m;
        }
        return null;
    }

    public static String getFurtherData(Memory m) {
        return switch (m) {
            case ANIMAL -> null;
            case CHANGE -> "effectDream";
            case CRYSTAL -> null;
            case DARKNESS -> null;
            case DEATH -> null;
            case ELDRITCH -> null;
            case HUMAN -> null;
            case LEARNING -> null;
            case METAL -> null;
            case POWER -> "effectDream";
            case REPAIR -> null;
            case SENTIENCE -> null;
            case STILLNESS -> "effectDream";
            case TOOL -> null;
            case VOID -> null;
            default -> null;
        };
    }

}
