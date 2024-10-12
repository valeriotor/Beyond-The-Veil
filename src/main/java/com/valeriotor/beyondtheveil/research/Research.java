package com.valeriotor.beyondtheveil.research;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Research {

    private final String key;
    private final String name;
    private final String[] icons;
    private final ItemStack[] iconsTex;
    private final int[] location;
    private final String[] parents;
    private final String[] hiders;
    private final boolean learn;
    private final SubResearch[] stages;
    private final SubResearch[] addenda;


    public Research(ResearchRegistry.ResearchTemp res) {
        this.key = res.key;
        this.name = res.name;
        this.icons = res.icons;
        this.location = res.location;
        this.parents = res.parents == null ? new String[0] : res.parents;
        this.hiders = res.hiders == null ? new String[0] : res.hiders;
        this.learn = res.learn;
        this.stages = res.stages == null ? new SubResearch[0] : res.stages;
        this.addenda = res.addenda == null ? new SubResearch[0] : res.addenda;
        List<ItemStack> stacks = new ArrayList<>();
        for(String icon : icons) {
            String[] split = icon.split(";");
            int amount = split.length > 1 ? Integer.parseInt(split[1]) : 1;
            int meta = split.length > 2 ? Integer.parseInt(split[2]) : 0;
            CompoundTag tag = new CompoundTag();
            tag.putInt("Damage", meta);
            ForgeRegistries.ITEMS.getHolder(new ResourceLocation(split[0])).ifPresent(itemHolder -> {
                stacks.add(new ItemStack(itemHolder.value(), amount, tag));
            });
        }
        iconsTex = new ItemStack[stacks.size()];
        for(int i = 0; i < stacks.size(); i++) {
            iconsTex[i] = stacks.get(i);
        }
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String[] getHiders() {
        return this.hiders;
    }

    public String[] getParents() {
        return this.parents;
    }

    public String[] getParentKeys() {
        String[] keys = new String[parents.length];
        for (int i = 0; i < parents.length; i++) {
            keys[i] = parents[i].split(";")[0];
        }
        return keys;
    }

    public SubResearch[] getStages() {
        return this.stages;
    }

    public SubResearch[] getAddenda() {
        return this.addenda;
    }

    public List<String> getRecipes() {
        List<String> recipes = new ArrayList<>();
        for(SubResearch sr : this.getStages()) {
            recipes.addAll(Arrays.asList(sr.getRecipes()));
        }
        for(SubResearch sr : this.getAddenda()) {
            recipes.addAll(Arrays.asList(sr.getRecipes()));
        }
        return recipes;
    }

    public boolean mustLearn() {
        return learn;
    }

    public int getX() {
        return this.location[0];
    }

    public int getY() {
        return this.location[1];
    }

    public ItemStack[] getIconStacks() {
        return this.iconsTex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(key).append("\n").append(name).append("\nIcons: ");
        for(String s : icons) {
            sb.append(s).append("; ");
        }
        sb.append("\n");
        sb.append("Location: ").append(location[0]).append(" ").append(location[1]).append("\nParents: ");
        if(parents != null)
            for(String s : parents) {
                sb.append(s).append("; ");
            }
        sb.append("\nHiders: ");
        if(hiders != null)
            for(String s : hiders) {
                sb.append(s).append("; ");
            }
        if(stages != null)
            sb.append("\nStages: ");
        for(SubResearch stage : stages) {
            sb.append(stage.toString());
        }
        return sb.toString();
    }


    public static class SubResearch {
        String text;
        private String[] required_research;
        String[] recipes;

        public boolean meetsRequirements(Player p) {
            return this.meetsRequirements(p.getCapability(PlayerDataProvider.PLAYER_DATA).orElse(PlayerData.DUMMY));
        }

        public boolean meetsRequirements(PlayerData data) {
            if(data == null) return false;
            if(required_research == null || required_research.length == 0) return true;
            for(String s : this.getRequirements()) {
                if(!data.getBoolean(s)) return false;
            }
            return true;
        }

        public String[] getRequirements() {
            if(this.required_research == null)
                this.required_research = new String[0];
            return this.required_research;
        }

        public String getTextKey() {
            return this.text;
        }

        public String[] getRecipes() {
            if(this.recipes == null)
                this.recipes = new String[0];
            return this.recipes;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n").append(" " + text).append("\n").append(" Required Research: ");
            if(required_research != null)
                for(String s : required_research) {
                    sb.append(s + "; ");
                }
            return sb.toString();
        }


    }

}
