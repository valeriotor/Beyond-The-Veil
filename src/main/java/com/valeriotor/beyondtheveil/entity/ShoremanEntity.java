package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.entity.ai.goals.LookAtTalkingPlayerGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.TalkToPlayerGoal;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;

import java.util.OptionalInt;
import java.util.Random;

public class ShoremanEntity extends PathfinderMob implements Talkable{

    private static final EntityDataAccessor<Integer> PROFESSION = SynchedEntityData.defineId(ShoremanEntity.class, EntityDataSerializers.INT);

    private Player talkingPlayer;


    public ShoremanEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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
        OptionalInt optionalint = player.openMenu(new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new ShoremanDialogueMenu(pContainerId, pPlayerInventory, player, this), Component.translatable("gui.dialogue." + getProfession().name().toLowerCase() + ".display_name")));
        //if (optionalint.isPresent()) {
        //    MerchantOffers merchantoffers = this.getOffers();
        //    if (!merchantoffers.isEmpty()) {
        //        player.sendMerchantOffers(optionalint.getAsInt(), merchantoffers, pLevel, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
        //    }
        //}
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        System.out.println(getProfession() + " " + level().isClientSide);
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (!level().isClientSide) {
            ShoremanProfession shoremanProfession = pCompound.contains("profession") ? ShoremanProfession.valueOf(pCompound.getString("profession")) : ShoremanProfession.FISHERMAN;
            setProfession(shoremanProfession.ordinal());
        }
    }

    public enum ShoremanProfession {
        BARTENDER, CARPENTER, CLERK, DRUNK, FISHERMAN, LIGHTHOUSE_KEEPER, MINER, SCHOLAR, SMITH,
    }
}
