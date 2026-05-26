package com.valeriotor.beyondtheveil.entity.dream_focus;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.tile.DreamFocusBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DreamFocusItemEntity extends ItemEntity implements DreamFocusMovable {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(DreamFocusItemEntity.class, EntityDataSerializers.INT);
    private List<Vec3> points;
    private int pointCounter = 0;
    private BlockPos focusPos;

    public DreamFocusItemEntity(EntityType<? extends DreamFocusItemEntity> pEntityType, Level pLevel) {
        this(pEntityType, pLevel, ItemStack.EMPTY, List.of(), null);
    }

    public DreamFocusItemEntity(EntityType<? extends DreamFocusItemEntity> pEntityType, Level pLevel, ItemStack stack, List<Vec3> points, BlockPos focusPos) {
        super(pEntityType, pLevel);
        this.points = points;
        this.focusPos = focusPos;
        setItem(stack);
        setNoGravity(true);
    }

    public void setParticleColor(int particleColor) {
        getEntityData().set(COLOR, particleColor);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            moveToNextPoint(points, pointCounter++);
        } else {
            ClientMethods.colorParticle(DustParticleOptions.REDSTONE, getX(), getY(), getZ(), 0,0,0, entityData.get(COLOR));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("pointCounter", pointCounter);
        pCompound.putInt("particleColor", entityData.get(COLOR));
        if (focusPos != null) {
            pCompound.putLong("focusPos", focusPos.asLong());
        }
        pCompound.put("points", DreamFocusBE.savePoints(points));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        pointCounter = pCompound.getInt("pointCounter");
        entityData.set(COLOR, pCompound.getInt("particleColor"));
        if (pCompound.contains("focusPos")) {
            focusPos = BlockPos.of(pCompound.getLong("focusPos"));
        }
        points = DreamFocusBE.loadPoints(pCompound); // here we effectively create a copy of the list. Not great for performance, but reading from NBT is the exception and not the rule
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(COLOR, 0);
    }
}
