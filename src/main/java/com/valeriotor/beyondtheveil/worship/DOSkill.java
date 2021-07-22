package com.valeriotor.beyondtheveil.worship;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.entities.ictya.IctyaSize;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import static com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider.PLAYERDATA;

public enum DOSkill {
    QUICKSTEP(1, "doquickstep", false),
    UPPERCUT(2, "douppercut", true),
//    BAUBLES(2, "dobaubles", false),
    ROARSINK(2, "doroarsink", true),
    CLIMBING(2, "doclimbing", false),
    HEALTH(3, "dohealth", false),
//    INVISIBILITY(4, "doinvisibility", true),
    POISON(4, "dopoison", true),
    REGENERATION(4, "doregeneration", true);

    private final int level;
    private final String name;
    private final boolean toggleable;
    private final IctyaSize requiredIctyaSize;

    DOSkill(int level, String name, boolean toggleable) {
        this.level = level;
        this.name = name;
        this.toggleable = toggleable;
        requiredIctyaSize = IctyaSize.values()[(level - 1) / 2 + 2];
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public IctyaSize getRequiredIctyaSize() {
        return requiredIctyaSize;
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

    public boolean isUnlockable(EntityPlayer p) {
        return isUnlockable(p.getCapability(PLAYERDATA, null));
    }

    public boolean isUnlockable(IPlayerData data) {
        if(level == 1) return true;
        for (DOSkill skill : values()) {
            if (skill.level == level - 1 && !skill.isUnlocked(data))
                return false;
        }
        return true;
    }
    public boolean hasPlayerKilledEnoughIctya(EntityPlayer p) {
        return hasPlayerKilledEnoughIctya(p.getCapability(PLAYERDATA, null));
    }

    public boolean hasPlayerKilledEnoughIctya(IPlayerData data) {
        int killedIctya = data.getOrSetInteger("ictya-" + requiredIctyaSize.name().toLowerCase(), 0, false);
        int usedIctya = data.getOrSetInteger("ictya-used-" + requiredIctyaSize.name().toLowerCase(), 0, false);
        return killedIctya > usedIctya;
    }

    public void unlock(EntityPlayer p) {
        unlock(p.getCapability(PLAYERDATA, null));
    }

    public void unlock(IPlayerData data) {
        data.addString(name, false);
        data.incrementOrSetInteger("ictya-used-" + requiredIctyaSize.name().toLowerCase(), 1, 1, false);
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

    public String getLocalizedName() {
        return I18n.format("doskill." + getName() + ".name");
    }

    public String getLocalizedKillMoreIctyaMessage() {
        return I18n.format("doskill.unlock." + requiredIctyaSize.name().toLowerCase());
    }

}
