package com.valeriotor.beyondtheveil.entities.bosses;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityArenaBoss extends EntityMob {

    private final UUID adversary;
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntityArenaBoss(World worldIn, EntityPlayer adversary) {
        super(worldIn);
        this.adversary = adversary == null ? null : adversary.getPersistentID();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!world.isRemote && (ticksExisted & 15) == 0) {
            if(adversary != null && !ArenaFightHandler.isPlayerInFight(adversary) && !isDead) {
                world.removeEntity(this);
            }
        }
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if(world.isRemote && dimension == DimensionRegistry.ARCHE.getId()) {
            BeyondTheVeil.proxy.playArenaSound(this);
        }
    }

    protected boolean isInArena() {
        return adversary != null;
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if(isInArena())
            ArenaFightHandler.endFight(adversary, false);
    }




}
