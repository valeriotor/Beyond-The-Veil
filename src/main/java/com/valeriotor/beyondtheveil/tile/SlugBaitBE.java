package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SlugBaitBE extends BlockEntity {

    private int mineralCounter = 0;
    private int checkWaterCounter = 0;
    private int slugCounter = 0;
    private boolean slugPresent = false;
    private double offsetX = 0;
    private double offsetZ = 0;

    public SlugBaitBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.SLUG_BAIT_BE.get(), pWorldPosition, pBlockState);
    }

    public void addMinerals() {
        mineralCounter = 300 * 20;
    }

    public boolean isEmpty() {
        return mineralCounter <= 0;
    }

    public void tickServer() {
        ServerLevel level = (ServerLevel) this.level;
        if (level == null) return;
        if (mineralCounter > 0) {
            level.sendParticles(ParticleTypes.SMOKE, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, 3, 0, 0, 0, 0.05);
            mineralCounter--;
        } else {
            slugPresent = false;
            return;
        }
        checkWaterCounter--;
        if (checkWaterCounter <= 0) {
            checkWaterCounter = 50;
            if (level.getBlockState(worldPosition.relative(Direction.SOUTH)).getBlock() != Blocks.WATER ||
                    level.getBlockState(worldPosition.relative(Direction.NORTH)).getBlock() != Blocks.WATER ||
                    level.getBlockState(worldPosition.relative(Direction.WEST)).getBlock() != Blocks.WATER ||
                    level.getBlockState(worldPosition.relative(Direction.EAST)).getBlock() != Blocks.WATER ||
                    level.getBlockState(worldPosition.relative(Direction.UP)).getBlock() == Blocks.WATER) {
                slugPresent = false;
                return;
            }
        }
        if (!slugPresent) {
            slugCounter--;
            if (slugCounter <= 0) {
                slugCounter = 80;
                slugPresent = true;
                offsetX = level.random.nextBoolean() ? -level.random.nextDouble() * 4 - 1 : level.random.nextDouble() * 4 + 1;
                offsetZ = level.random.nextBoolean() ? -level.random.nextDouble() * 4 - 1 : level.random.nextDouble() * 4 + 1;
                if (level.getBlockState(new BlockPos(worldPosition.getX() + offsetX + 0.5, worldPosition.getY(), worldPosition.getZ() + offsetZ + 0.5)) != Blocks.WATER.defaultBlockState()) {
                    slugPresent = false;
                    slugCounter = 20;
                }
            }
        } else {
            if (level.random.nextBoolean() && (Math.abs(offsetX) > 1 || Math.abs(offsetZ) < 1)) {
                offsetX += offsetX < 0 ? 0.06D : -0.06D;
            } else {
                offsetZ += offsetZ < 0 ? 0.06D : -0.06D;
            }
            if (level.getBlockState(new BlockPos(worldPosition.getX() + offsetX + 0.5, worldPosition.getY(), worldPosition.getZ() + offsetZ + 0.5)) != Blocks.WATER.defaultBlockState()) {
                slugPresent = false;
                slugCounter = 20;
                return;
            }
            slugCounter--;
            if ((Math.abs(offsetX) < 0.5 && Math.abs(offsetZ) < 0.5) || slugCounter <= 0) {
                slugPresent = false;
                return;
            }
            level.sendParticles(ParticleTypes.DRIPPING_WATER, worldPosition.getX() + 0.5 + offsetX, worldPosition.getY() + 1, worldPosition.getZ() + 0.5 + offsetZ, 5, 0, 0, 0, 1);
        }
    }

    public boolean isSlugCloseOnXZPlane(Vec3 vec, double distance) {
        double slugX = worldPosition.getX() + 0.5 + offsetX;
        double slugZ = worldPosition.getZ() + 0.5 + offsetZ;
        return vec.x < slugX + distance && vec.x > slugX - distance && vec.z < slugZ + distance && vec.z > slugZ - distance;
    }

    public void catchSlug() {
        slugPresent = false;
        if (level != null) {
            slugCounter = level.random.nextInt(30) + 10;
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        mineralCounter = pTag.getInt("mineral");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("mineral", mineralCounter);
    }
}
