package com.valeriotor.beyondtheveil.worship;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.entities.bosses.EntityArenaBoss;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneBrute;
import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneMyrmidon;
import com.valeriotor.beyondtheveil.entities.ictya.IctyaSize;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider.PLAYERDATA;

public enum DOSkill {
    QUICKSTEP(1, "doquickstep", true),
    UPPERCUT(2, "douppercut", true),
//    BAUBLES(2, "dobaubles", false),
    ROARSINK(2, "doroarsink", true),
    CLIMBING(2, "doclimbing", true),
    HEALTH(3, "dohealth", true),
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

        requiredIctyaSize = IctyaSize.values()[getEnemyLevel()];
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
        int killedIctya = data.getOrSetInteger(PlayerDataLib.ICTYA_BY_SIZE.apply(requiredIctyaSize.name().toLowerCase()), 0, false);
        int usedIctya = data.getOrSetInteger(PlayerDataLib.ICTYA_USED_BY_SIZE.apply(requiredIctyaSize.name().toLowerCase()), 0, false);
        return killedIctya > usedIctya;
    }
    public boolean hasPlayerKilledEnoughBosses(EntityPlayer p) {
        return hasPlayerKilledEnoughBosses(p.getCapability(PLAYERDATA, null));
    }

    public boolean hasPlayerKilledEnoughBosses(IPlayerData data) {
        String requiredBossName = getRequiredBossName(getEnemyLevel());
        int killedBosses = data.getOrSetInteger(PlayerDataLib.ARENA_BOSSES_KILLED_BY_NAME.apply(requiredBossName), 0, false);
        int usedBosses = data.getOrSetInteger(PlayerDataLib.ARENA_BOSSES_USED_BY_NAME.apply(requiredBossName), 0, false);
        return killedBosses > usedBosses;
    }

    public void unlock(EntityPlayer p) {
        unlock(p.getCapability(PLAYERDATA, null));
    }

    public void unlock(IPlayerData data) {
        data.addString(name, false);
        data.incrementOrSetInteger(PlayerDataLib.ICTYA_USED_BY_SIZE.apply(requiredIctyaSize.name().toLowerCase()), 1, 1, false);
        data.incrementOrSetInteger(PlayerDataLib.ARENA_BOSSES_USED_BY_NAME.apply(getRequiredBossName(getEnemyLevel())), 1, 1, false);
        if(toggleable)
            toggle(data);
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

    public String getLocalizedKillMoreBossesMessage() {
        return I18n.format("doskill.unlock." + getRequiredBossName(getEnemyLevel()));
    }

    private String getRequiredBossName(int enemyLevel) {
        enemyLevel -= 2;
        final String requiredBossName;
        Class<? extends EntityArenaBoss> requiredBoss = null;
        switch (enemyLevel) {
            case 0:
                requiredBoss = EntityDeepOneBrute.class;
                break;
            case 1:
                requiredBoss = EntityDeepOneMyrmidon.class;
                break;
        }
        requiredBossName = ForgeRegistries.ENTITIES.getKey(EntityRegistry.getEntry(requiredBoss)).getResourcePath();
        return requiredBossName;
    }

    private int getEnemyLevel() {
        return (level - 1) / 2 + 2;
    }

}
