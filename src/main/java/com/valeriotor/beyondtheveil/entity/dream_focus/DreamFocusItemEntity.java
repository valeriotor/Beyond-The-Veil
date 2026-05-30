package com.valeriotor.beyondtheveil.entity.dream_focus;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.item.BlackjackItem;
import com.valeriotor.beyondtheveil.tile.DreamFocusBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DreamFocusItemEntity extends ItemEntity implements DreamFocusMovable {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(DreamFocusItemEntity.class, EntityDataSerializers.INT);
    private List<Vec3> points;
    private int pointCounter = 0;
    private BlockPos focusPos;
    private boolean toBeRemoved;
    private Set<BlockPos> checkedCurtainsCache = new HashSet<>();

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
            if (!moveToNextPoint(points, pointCounter++)) {
                toBeRemoved = true;
            }
            interactWithEntity();
            BlockState inState = level().getBlockState(blockPosition());
            if (inState.getBlock() == Registration.CURTAIN.get() && !checkedCurtainsCache.contains(blockPosition())) {
                Direction.Axis axis = inState.getValue(HorizontalDirectionalBlock.FACING).getAxis();
                double fracX = Math.abs(getX() % 1);
                double fracZ = Math.abs(getZ() % 1);
                if ((axis == Direction.Axis.X && fracX >= 0.4 && fracX <= 0.6) || (axis == Direction.Axis.Z && fracZ >= 0.4 && fracZ <= 0.6)) {
                    checkedCurtainsCache.add(blockPosition());
                    BlockEntity be = level().getBlockEntity(blockPosition().above());
                    if (be != null) {
                        be.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(c -> {
                            ItemStack thisItem = getItem();
                            for (int i = 0; i < c.getSlots(); i++) {
                                if (c.getStackInSlot(i).getItem() == thisItem.getItem()) {
                                    toBeRemoved = true;
                                    break;
                                }
                            }
                        });
                    }
                }
            }
            if (toBeRemoved) {
                ItemEntity item = new ItemEntity(level(), getX(), getY(), getZ(), getItem(), 0, 0, 0);
                level().addFreshEntity(item);
                discard();
            }
        } else {
            ClientMethods.colorParticle(DustParticleOptions.REDSTONE, getX(), getY(), getZ(), 0,0,0, entityData.get(COLOR));
        }
    }

    private void interactWithEntity() {
        ItemStack item = getItem();
        if (item.getItem() == Registration.BLACKJACK.get()) { // TODO add more special behaviours?
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, e -> e instanceof Villager);
            if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hitResult).getEntity() instanceof Villager villager) {
                BlackjackItem.knockDownVillager(villager);
            }
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
