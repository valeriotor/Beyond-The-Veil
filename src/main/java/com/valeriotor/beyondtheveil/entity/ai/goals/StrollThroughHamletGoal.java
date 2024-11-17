package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumSet;

public class StrollThroughHamletGoal extends Goal {

    private final ShoremanEntity shoreman;
    private final double speed;
    private BlockPos destination;
    private boolean convened, returned;

    public StrollThroughHamletGoal(ShoremanEntity shoreman, double speed) {
        this.shoreman = shoreman;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (shoreman.getProfession() == ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER) {
            return false;
        }
        if (shoreman.getVillageCenter() == null || shoreman.getSpawnPoint() == null) {
            return false;
        }
        return shoreman.level().getDayTime() > 12000 && (conveneInCenter() || returnHome());
    }

    @Override
    public void tick() {
        if (destination != null && (shoreman.tickCount & 127) == 0) {
            shoreman.getNavigation().stop();
            shoreman.getNavigation().moveTo(destination.getX(), destination.getY(), destination.getZ(), speed);
        }
        BlockPos spawnPoint = shoreman.getSpawnPoint();
        BlockPos villageCenter = shoreman.getVillageCenter();
        ShoremanEntity.ShoremanProfession profession = shoreman.getProfession();

        if (destination == null) {
            if (conveneInCenter() && !convened && profession.isLeavesPost()) {
                double angle = shoreman.getRandom().nextDouble() * Math.PI * 2;
                double radius = shoreman.getRandom().nextDouble() * 5 + 5;
                double x = Math.sin(angle) * radius;
                double z = Math.cos(angle) * radius;
                destination = villageCenter.offset((int) x, 0, (int) z);
            } else if (returnHome() && !returned) {
                destination = spawnPoint;
            }
            if (destination != null) {
                destination = shoreman.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, destination);
            }
        }
        if (destination != null && (shoreman.position().distanceToSqr(destination.getX(), shoreman.getY(), destination.getZ()) < 9 || (!conveneInCenter() && !returnHome()))) { // it currently can't be true that neither conveneInCenter or returnHome are true
            destination = null;
            if (conveneInCenter()) {
                convened = true;
                returned = false;
            } else if (returnHome()) {
                returned = true;
                convened = false;
            } else {
                returned = false;
                convened = false;
            }
        }
        if (!profession.isLeavesPost()) {
            returned = false;
        }
    }

    private boolean conveneInCenter() {
        return shoreman.level().getDayTime() < 17000;
    }

    private boolean returnHome() {
        return shoreman.level().getDayTime() > 21000;
    }

    @Override
    public void stop() {
        returned = false;
        convened = false;
        destination = null;
    }
}
