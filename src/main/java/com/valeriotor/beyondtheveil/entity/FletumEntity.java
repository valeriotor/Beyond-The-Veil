package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.ai.goals.LivingAmmunitionGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.WeepGoal;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FletumEntity extends PathfinderMob implements PlayerMinion, Weeping {

    private BlockPos lacrymatoryPos;
    private UUID master;

    public FletumEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new WeepGoal<>(this, 0));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 0);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pPlayer.level().isClientSide) {
            return InteractionResult.PASS;
        }
        if (pPlayer.isShiftKeyDown() && pPlayer.getUUID().equals(master)) {
            ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(Registration.HELD_FLETUM.get()));
            discard();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (lacrymatoryPos != null) {
            pCompound.putLong("lacrymatory", lacrymatoryPos.asLong());
        }
        if (master != null) {
            pCompound.putString("master", master.toString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("lacrymatory")) {
            lacrymatoryPos = BlockPos.of(pCompound.getLong("lacrymatory"));
        }
        if (pCompound.contains("master")) {
            master = UUID.fromString(pCompound.getString("master"));
        }
    }

    @Override
    public BlockPos getLacrymatoryPos() {
        return lacrymatoryPos;
    }

    @Override
    public void setLacrymatoryPos(BlockPos pos) {
        lacrymatoryPos = pos;
    }

    @Override
    public int mbWept() {
        return 10;
    }

    @Override
    public UUID getMasterID() {
        return master;
    }

    @Override
    public void setMasterID(UUID uuid) {
        master = uuid;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return BTVSounds.FLETUM_WEEPING.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }
}
