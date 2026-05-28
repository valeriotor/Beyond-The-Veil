package com.valeriotor.beyondtheveil.entity.dream_focus;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.tile.DreamFocusBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DreamFocusFluidEntity extends Entity implements DreamFocusMovable {

    private static final EntityDataAccessor<CompoundTag> DATA_FLUID = SynchedEntityData.defineId(DreamFocusFluidEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(DreamFocusItemEntity.class, EntityDataSerializers.INT);
    private List<Vec3> points;
    private int pointCounter = 0;
    private BlockPos focusPos;

    private boolean toBeRemoved;
    public DreamFocusFluidEntity(EntityType<?> pEntityType, Level pLevel) {
        this(pEntityType, pLevel, FluidStack.EMPTY, List.of(), null);
    }

    public DreamFocusFluidEntity(EntityType<?> pEntityType, Level pLevel, FluidStack stack, List<Vec3> points, BlockPos focusPos) {
        super(pEntityType, pLevel);
        this.points = points;
        this.focusPos = focusPos;
        setFluid(stack);
        setNoGravity(true);
    }

    public void setParticleColor(int particleColor) {
        getEntityData().set(COLOR, particleColor);
    }

    public void setFluid(FluidStack fluid) {
        CompoundTag tag = fluid.writeToNBT(new CompoundTag());
        entityData.set(DATA_FLUID, tag);
    }

    public FluidStack getFluid() {
        return FluidStack.loadFluidStackFromNBT(entityData.get(DATA_FLUID));
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FLUID, new CompoundTag());
        this.getEntityData().define(COLOR, 0);
    }


    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {

            this.move(MoverType.SELF, this.getDeltaMovement());

            if (!moveToNextPoint(points, pointCounter++)) {
                toBeRemoved = true;
            }
            Vec3 direction = getDeltaMovement().normalize().scale(0.5);
            BlockPos collisionPos = new BlockPos(Mth.floor(getX() + direction.x), Mth.floor(getY() + direction.y), Mth.floor(getZ() + direction.z));
            BlockPos diff = collisionPos.subtract(blockPosition());
            Direction dir = diff.getY() == 1 ? Direction.DOWN : diff.getY() == -1 ? Direction.UP : diff.getX() == 1 ? Direction.WEST : diff.getX() == -1 ? Direction.EAST : diff.getZ() == 1 ? Direction.NORTH : Direction.SOUTH;
            BlockEntity be = level().getBlockEntity(collisionPos);
            BlockState state = level().getBlockState(collisionPos);
            if (be != null && be.getCapability(ForgeCapabilities.FLUID_HANDLER, dir).isPresent()) {
                FluidStack fluid = getFluid();
                int fill = be.getCapability(ForgeCapabilities.FLUID_HANDLER, dir).resolve().get().fill(fluid, IFluidHandler.FluidAction.EXECUTE);
                be.setChanged();
                level().sendBlockUpdated(collisionPos, state, state, 2);

                if (fluid.getAmount() > fill && fill > 0) {
                    setFluid(new FluidStack(fluid.getFluid(), fluid.getAmount() - fill));
                } else {
                    toBeRemoved = true;
                }
            }
            if (toBeRemoved) {
                //ItemEntity item = new ItemEntity(level(), getX(), getY(), getZ(), getItem());
                //level().addFreshEntity(item);
                discard();
            }
        } else {
            noPhysics = false;
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (tickCount % 3 == 0) {
                ClientMethods.colorParticle(DustParticleOptions.REDSTONE, getX(), getY(), getZ(), 0,0,0, entityData.get(COLOR));
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        entityData.set(DATA_FLUID, pCompound.getCompound("fluid"));
        entityData.set(COLOR, pCompound.getInt("particleColor"));
        pointCounter = pCompound.getInt("pointCounter");
        points = DreamFocusBE.loadPoints(pCompound); // here we effectively create a copy of the list. Not great for performance, but reading from NBT is the exception and not the rule
        if (pCompound.contains("focusPos")) {
            focusPos = BlockPos.of(pCompound.getLong("focusPos"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("pointCounter", pointCounter);
        pCompound.put("fluid", entityData.get(DATA_FLUID).copy());
        pCompound.putInt("particleColor", entityData.get(COLOR));
        pCompound.put("points", DreamFocusBE.savePoints(points));
        if (focusPos != null) {
            pCompound.putLong("focusPos", focusPos.asLong());
        }
    }
}
