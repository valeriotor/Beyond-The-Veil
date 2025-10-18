package com.valeriotor.beyondtheveil.dreaming;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum Memory {
    ANIMAL(Registration.HEART_ITEM.get(), 0xFF401b00, new int[]{1, 0, 1, 0, 1, 0}),
    BEHEADING(Items.WITHER_SKELETON_SKULL, 0xFF333333, new int[]{1, 0, 0, 0, 1, 0}),
    CHANGE(Items.HEART_OF_THE_SEA, 0xFF4dff00, new int[]{1, 1, 1, 0, 1, 0}),
    CRYSTAL(Items.GLASS, 0xFFe6d8d8, new int[]{1, 1, 1, 0, 1, 0}),
    DARKNESS(Items.COAL, 0xFF002233, new int[]{1, 0, 1, 0, 1, 1}),
    DEATH(Items.ROTTEN_FLESH, 0xFF2a2a2c, new int[]{1, 1, 1, 0, 1, 1}),
    //ELDRITCH(Items.ENDER_EYE, 0xFF400021),
    //TODO HEARTBREAK(Items.getItemFromBlock(BlockRegistry.BlockHeart), 0xFFAA0000, "memPOWER"),
    //HUMAN(Items.ARMOR_STAND, 0xFFFFFFFF),
    //INTROSPECTION(Items.PAPER, 0xFFFFFFFF),
    NULL(Items.AIR, 0xFF998b69, new int[]{0, 0, 0, 0, 0, 0}),
    METAL(Items.IRON_INGOT, 0xFF8c8c8c, new int[]{1, 1, 1, 0, 1, 0}),
    //PLANT(Items.JUNGLE_SAPLING, 0xFF00FF00),
    POWER(Items.BLAZE_POWDER, 0xFFff9300, new int[]{1, 1, 1, 1, 1, 1}),
    REPAIR(Items.ANVIL, 0xFF99f19d, new int[]{1, 1, 1, 1, 1, 1}),
    SENTIENCE(Items.BOOK, 0xFFd87474, new int[]{1, 1, 1, 0, 1, 0}),
    STILLNESS(Items.SOUL_SAND, 0xFF444444, new int[]{1, 1, 1, 1, 1, 1}),
    //TOOL(Items.WOODEN_PICKAXE, 0xFF324eAA),
    VOID(Items.OBSIDIAN, 0xFF36111F, new int[]{1, 1, 0, 0, 1, 0}),
    WATER(Items.WATER_BUCKET, 0xFF1111FF, new int[]{1, 1, 1, 0, 1, 0});

    private final ItemStack item;
    private final int color;
    private final int[] maxStatus;


    Memory(Item item, int color, int[] maxStatus) {
        this.item = new ItemStack(item, 1);
        this.color = color;
        this.maxStatus = maxStatus;
    }

    public String getDataName(boolean hasVoid) {
        return (hasVoid ? "void_" : "") + getDataName();
    }

    public String getDataName() {
        return name().toLowerCase();
    }

    public String getDreamName() {
        return name().toLowerCase().concat("Dream");
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isUnlocked(Player p) {
        return DataUtil.hasMemory(p, this);
    }

    public void unlock(ServerPlayer p) {
        this.unlock(p, true);
    }

    public void unlock(ServerPlayer p, boolean sendMessage) {
        if (!this.isUnlocked(p)) {
            Messages.sendToPlayer(GenericToClientPacket.addMemoryToast(this), p);
            String dataName = this.getDataName();
            DataUtil.unlockMemoryOnServerAndSync(p, this);
            String s = getFurtherData(this);
            if (s != null) {
                DataUtil.setBooleanOnServerAndSync(p, s, true, false);
            }
            //if (sendMessage) {
            //    //TODO BTVPacketHandler.INSTANCE.sendTo(new MessageGenericToClient(GenericMessageKey.MEMORY_ENTRY, getDataName()), (ServerPlayer) p);
            //    p.sendSystemMessage(Component.translatable("memory.unlock.message", getTranslationComponent()));
            //    for (Entry<String, ResearchStatus> entry : ResearchUtil.getResearches(p).entrySet()) {
            //        for (Research.SubResearch addendum : entry.getValue().res.getAddenda()) {
            //            for (String req : addendum.getRequirements()) {
            //                if (req.equals(dataName)) {
            //                    p.sendSystemMessage(Component.translatable("memory.unlock.addenda", Component.translatable(entry.getValue().res.getName())));
            //                    return;
            //                }
            //            }
            //        }
            //    }
            //}
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

    public MutableComponent getTranslationComponent() {
        return Component.translatable(getLocalizationKey());
    }

    public MutableComponent getTranslationComponentWithPrefix() {
        return Component.translatable("memory.prefix", getTranslationComponent().getString());
    }

    public static Memory getMemoryFromDataName(String key) {
        for (Memory m : Memory.values()) {
            if (key.equals(m.getDataName()))
                return m;
        }
        return null;
    }

    public int getMaxStatus(Target target, boolean hasVoid) {
        return maxStatus[target.ordinal() * 2 + (hasVoid ? 1 : 0)];
    }

    public static String getFurtherData(Memory m) {
        return switch (m) {
            case ANIMAL -> null;
            case CHANGE -> "effectDream";
            case CRYSTAL -> null;
            case DARKNESS -> null;
            case DEATH -> null;
            //case ELDRITCH -> null;
            //case HUMAN -> null;
            case NULL -> null;
            case METAL -> null;
            case POWER -> "effectDream";
            case REPAIR -> null;
            case SENTIENCE -> null;
            case STILLNESS -> "effectDream";
            //case TOOL -> null;
            case VOID -> null;
            default -> null;
        };
    }

    public enum Target {
        BASE, PATH, PLAYER
    }

}
