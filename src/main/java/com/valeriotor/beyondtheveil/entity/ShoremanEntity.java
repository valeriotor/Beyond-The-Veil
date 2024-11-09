package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.ai.goals.LookAtTalkingPlayerGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.TalkToPlayerGoal;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;

public class ShoremanEntity extends PathfinderMob implements Talkable{

    private static final EntityDataAccessor<Integer> PROFESSION = SynchedEntityData.defineId(ShoremanEntity.class, EntityDataSerializers.INT);

    private Player talkingPlayer;
    private Vec3 lighthouseKeeperStand;
    private Direction lighthouseKeeperDirection;
    private BlockPos villageCenter;
    private BlockPos spawnPoint;


    public ShoremanEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TalkToPlayerGoal<>(this));
        this.goalSelector.addGoal(1, new LookAtTalkingPlayerGoal<>(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public void addLighthouseKeeperStandCoords(Vec3 vec3, Direction direction) {
        this.lighthouseKeeperStand = vec3;
        this.lighthouseKeeperDirection = direction;
        addLighthouseKeeperStandGoal();
    }

    private void addLighthouseKeeperStandGoal() {
        for (WrappedGoal availableGoal : goalSelector.getAvailableGoals()) {
            if (availableGoal.getGoal() instanceof LighthouseKeeperStandGoal) {
                return;
            }
        }
        this.goalSelector.addGoal(5, new LighthouseKeeperStandGoal(this, lighthouseKeeperStand, lighthouseKeeperDirection));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PROFESSION, ShoremanProfession.FISHERMAN.ordinal());
    }

    public ShoremanProfession getProfession() {
        return ShoremanProfession.values()[entityData.get(PROFESSION)];
    }

    public void setProfession(int value) {
        entityData.set(PROFESSION, value);
        if (value == ShoremanProfession.LIGHTHOUSE_KEEPER.ordinal()) {
            List<WrappedGoal> toRemove = new ArrayList<>();
            for (WrappedGoal goal : goalSelector.getAvailableGoals()) {
                if (goal.getGoal().getClass() == LookAtPlayerGoal.class || goal.getGoal().getClass() == RandomStrollGoal.class || goal.getGoal().getClass() == RandomLookAroundGoal.class) {
                    toRemove.add(goal);
                }
            }
            for (WrappedGoal wrappedGoal : toRemove) {
                goalSelector.removeGoal(wrappedGoal.getGoal());
            }
        }
    }
    public void setProfession(ShoremanProfession profession) {
        setProfession(profession.ordinal());
    }

    public void setSpawnAndVillageCenter(BlockPos spawnPoint, BlockPos villageCenter) {
        this.villageCenter = villageCenter;
        this.spawnPoint = spawnPoint;
    }

    @Override
    public Player getTalkingPlayer() {
        return talkingPlayer;
    }

    @Override
    public void setTalkingPlayer(Player player) {
        talkingPlayer = player;
    }

    private void startTalking(ServerPlayer player) {
        setTalkingPlayer(player);
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeUtf(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER.name());
        byteBuf.writeUtf("initial");
        NetworkHooks.openScreen(player, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new ShoremanDialogueMenu(pContainerId, pPlayerInventory, player, this, null), Component.translatable("gui.dialogue." + getProfession().name().toLowerCase() + ".display_name")));
        //OptionalInt optionalint = player.openMenu(new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new ShoremanDialogueMenu(pContainerId, pPlayerInventory, player, this, null), Component.translatable("gui.dialogue." + getProfession().name().toLowerCase() + ".display_name")));
        //if (optionalint.isPresent()) {
        //    MerchantOffers merchantoffers = this.getOffers();
        //    if (!merchantoffers.isEmpty()) {
        //        player.sendMerchantOffers(optionalint.getAsInt(), merchantoffers, pLevel, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
        //    }
        //}
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        //System.out.println(getProfession() + " " + level().isClientSide);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() != Registration.SHOREMAN_EGG.get() && this.isAlive() && !this.isTalking() && !pPlayer.isSecondaryUseActive()) { // && !this.isSleeping() ?

            if (!this.level().isClientSide) {
                this.startTalking((ServerPlayer) pPlayer);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("profession", getProfession().name());
        if (lighthouseKeeperStand != null) {
            pCompound.putDouble("lighthouseKeeperStandX", lighthouseKeeperStand.x);
            pCompound.putDouble("lighthouseKeeperStandY", lighthouseKeeperStand.y);
            pCompound.putDouble("lighthouseKeeperStandZ", lighthouseKeeperStand.z);
            pCompound.putInt("lighthouseKeeperDirection", lighthouseKeeperDirection.ordinal());
            pCompound.putLong("villageCenter", villageCenter.asLong());
            pCompound.putLong("spawnPoint", spawnPoint.asLong());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (!level().isClientSide) {
            ShoremanProfession shoremanProfession = pCompound.contains("profession") ? ShoremanProfession.valueOf(pCompound.getString("profession")) : ShoremanProfession.FISHERMAN;
            setProfession(shoremanProfession.ordinal());
            if (pCompound.contains("lighthouseKeeperStandX")) {
                lighthouseKeeperStand = new Vec3(pCompound.getDouble("lighthouseKeeperStandX"), pCompound.getDouble("lighthouseKeeperStandY"), pCompound.getDouble("lighthouseKeeperStandZ"));
                lighthouseKeeperDirection = Direction.values()[pCompound.getInt("lighthouseKeeperDirection")];
                addLighthouseKeeperStandGoal();
            }
            if (pCompound.contains("spawnPoint")) {
                villageCenter = BlockPos.of(pCompound.getLong("villageCenter"));
                spawnPoint = BlockPos.of(pCompound.getLong("spawnPoint"));
            }
        }
    }

    public enum ShoremanProfession {
        BARTENDER, CARPENTER, CLERK, DRUNK, FISHERMAN, LIGHTHOUSE_KEEPER, MINER, SCHOLAR, SMITH,
    }

    private static class LighthouseKeeperStandGoal extends Goal {

        private final ShoremanEntity keeper;
        private final Vec3 vec3;
        private final Direction direction;

        public LighthouseKeeperStandGoal(ShoremanEntity keeper, Vec3 vec3, Direction direction) {
            this.keeper = keeper;
            this.vec3 = vec3;
            this.direction = direction;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.keeper.getRandom().nextFloat() >= 0.02) {
                return false;
            }
            return true;
        }

        @Override
        public void start() {
            keeper.getNavigation().moveTo(vec3.x(), vec3.y(), vec3.z(), 1);
            //Direction direction = this.direction.getCounterClockWise();
            //keeper.getLookControl().setLookAt(direction.getStepX(), keeper.getEyeY(), direction.getStepZ());
            float direction = this.direction.toYRot();
            keeper.setYRot(direction);
            keeper.setYHeadRot(direction);
            keeper.setYBodyRot(direction);
            //keeper.setXRot(45F);
        }

    }


}
