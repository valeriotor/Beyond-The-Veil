package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CanoeEntity extends Boat {

    private static final EntityDataAccessor<Boolean> DEEP_ONE_BELOW = SynchedEntityData.defineId(CanoeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ASKEW = SynchedEntityData.defineId(CanoeEntity.class, EntityDataSerializers.INT);
    public CanoeEntity(Level pLevel) {
        super(BTVEntities.CANOE.get(), pLevel);
        noCulling = true;
    }

    public CanoeEntity(Level pLevel, double pX, double pY, double pZ) {
        this(pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DEEP_ONE_BELOW, false);
        this.entityData.define(ASKEW, 0);
    }

    @Override
    public Item getDropItem() {
        return Registration.CANOE.get();
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
        if (this.hasPassenger(pPassenger)) {
            float f = this.getSinglePassengerXOffset();
            float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(pPassenger);
                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (pPassenger instanceof Animal) {
                    f += 0.2F;
                }
            }

            Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + (double)f1 + 0.3, this.getZ() + vec3.z);

        }

    }

    private final WeightedRandomList<WeightedEntry.Wrapper<Item>> list = WeightedRandomList.create(WeightedEntry.wrap(Items.COD, 5), WeightedEntry.wrap(Items.SALMON, 3), WeightedEntry.wrap(Items.TROPICAL_FISH, 1), WeightedEntry.wrap(Items.PUFFERFISH, 1));

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (getVehicle() instanceof DeepOneEntity deepOne) {
                entityData.set(DEEP_ONE_BELOW, true);
                entityData.set(ASKEW, deepOne.getCanoeAskew());
            } else {
                entityData.set(DEEP_ONE_BELOW, false);
                entityData.set(ASKEW, 0);
            }
            if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof ServerPlayer player && isInWater()) {
                if (false && player.getRandom().nextInt(200) == 0) {
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(list.getRandom(player.getRandom()).orElse(WeightedEntry.wrap(Items.COD, 1)).getData()));
                    if (!DataUtil.getBoolean(player, PlayerDataLib.RECEIVED_FISH) && ResearchUtil.getResearchStage(player, "CUSTOMS") > -1) { // TODO change customs with carpentry
                        DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.RECEIVED_FISH, true, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return super.isControlledByLocalInstance() && !entityData.get(DEEP_ONE_BELOW);
    }

    @Override
    public void absMoveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
        if (!level().isClientSide && !this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof ServerPlayer player && isInWater()) {
            double d0 = Mth.clamp(pX, -3.0E7D, 3.0E7D);
            double d1 = Mth.clamp(pZ, -3.0E7D, 3.0E7D);
            double v = Math.pow(xo - d0, 2) + Math.pow(zo - d1, 2);
            if (v > 0.05 && player.getRandom().nextInt(70) == 0) {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(list.getRandom(player.getRandom()).orElse(WeightedEntry.wrap(Items.COD, 1)).getData()));
                if (!DataUtil.getBoolean(player, PlayerDataLib.RECEIVED_FISH) && ResearchUtil.getResearchStage(player, "CUSTOMS") > -1) { // TODO change customs with carpentry
                    DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.RECEIVED_FISH, true, false);
                }
            }
        }
        super.absMoveTo(pX, pY, pZ, pYRot, pXRot);
    }

    @Nullable
    @Override
    protected SoundEvent getPaddleSound() {
        return super.getPaddleSound();
    }

    public int getAskew() {
        return entityData.get(ASKEW);
    }
}
