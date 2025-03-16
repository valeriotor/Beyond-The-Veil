package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CultistPodGoal extends Goal {

    private final BloodCultistEntity cultist;
    private final double speed;
    private int counter = 120;

    public CultistPodGoal(BloodCultistEntity cultist, double speed) {
        this.cultist = cultist;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return cultist.getPodPos() != null;
    }

    @Override
    public void tick() {
        super.tick();
        if (cultist.level() instanceof ServerLevel sl && counter > 0) {
            counter--;
            BlockPos podPos = cultist.getPodPos();
            if ((counter & 7) == 0) {
                cultist.getNavigation().moveTo(podPos.getX(), podPos.getY(), podPos.getZ(), speed);
            }
            if (counter == 0 || cultist.getPodPos().distSqr(cultist.getOnPos()) < 4) {
                counter = 0;
                LifeEconomyData.PodData podData = LifeEconomyData.getInstance(sl).getPodData(podPos);
                if (podData != null && podData.getPatient() == null) {
                    podData.setPatientAndSync(PatientType.VILLAGER, new CompoundTag(), sl);
                    cultist.setHeldVillagerType(null);
                    cultist.setLeaving(60);
                    cultist.getNavigation().stop();
                }
            }
        }
    }
}
