package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class SurgeryIngredient extends Item {

    private static final Set<SurgeryIngredient> SURGERY_INGREDIENTS = new HashSet<>();

    public SurgeryIngredient() {
        super(new Item.Properties().stacksTo(4));
        SURGERY_INGREDIENTS.add(this);
    }

    public boolean isKnown(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return false;
        }
        Advancement advancement = server.getAdvancements().getAdvancement(new ResourceLocation(References.MODID, "ingredients/" + toString().split(":")[1]));
        if (advancement == null) {
            return false;
        }
        return player.getAdvancements().getOrStartProgress(advancement).isDone();
    }

    public Advancement isKnown(AdvancementList advancements) {
        return advancements.get(new ResourceLocation(References.MODID, "ingredients/" + toString().split(":")[1]));
    }


    public static Set<SurgeryIngredient> getSurgeryIngredients() {
        return SURGERY_INGREDIENTS;
    }
}
