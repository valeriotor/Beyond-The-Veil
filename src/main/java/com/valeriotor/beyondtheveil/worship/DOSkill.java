package com.valeriotor.beyondtheveil.worship;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import net.minecraft.entity.player.EntityPlayer;

import static com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider.PLAYERDATA;

public enum DOSkill {
    QUICKSTEP(1, "doquickstep", false),
    UPPERCUT(1, "douppercut", true),
    BAUBLES(2, "dobaubles", false),
    ROARSINK(2, "doroarsink", true),
    CLIMBING(2, "doclimbing", false),
    HEALTH(3, "dohealth", false),
    INVISIBILITY(4, "doinvisibility", true),
    POISON(4, "dopoison", true),
    REGENERATION(4, "doregeneration", true);

    private final int level;
    private final String name;
    private final boolean toggleable;

    DOSkill(int level, String name, boolean toggleable) {
        this.level = level;
        this.name = name;
        this.toggleable = toggleable;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public boolean isActive(EntityPlayer p) {
        return isActive(p.getCapability(PLAYERDATA, null));
    }

    public boolean isActive(IPlayerData data) {
        if(toggleable)
            return data.getString(name) && data.getString(name+"toggled");
        return data.getString(name);
    }

    public boolean isUnlocked(EntityPlayer p) {
        return isUnlocked(p.getCapability(PLAYERDATA, null));
    }

    public boolean isUnlocked(IPlayerData data) {
        return data.getString(name);
    }

    public void unlock(EntityPlayer p) {
        unlock(p.getCapability(PLAYERDATA, null));
    }

    public void unlock(IPlayerData data) {
        data.addString(name, false);
    }

    public void toggle(EntityPlayer p) {
        toggle(p.getCapability(PLAYERDATA, null));
    }

    public void toggle(IPlayerData data) {
        String tag = name+"toggled";
        if(data.getString(tag))
            data.removeString(tag);
        else
            data.addString(tag, false);
    }
}
