package com.valeriotor.beyondtheveil.entity;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Set;

public class CrawlerEntity extends PathfinderMob implements VillagerDataHolder, SurgeryPatient {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(CrawlerEntity.class, EntityDataSerializers.VILLAGER_DATA);
    private static final EntityDataAccessor<Boolean> DATA_CRAWLING = SynchedEntityData.defineId(CrawlerEntity.class, EntityDataSerializers.BOOLEAN);
    private int startCrawl = -20;
    private boolean surgeryPatient = false;
    @Nullable
    private Tag gossips;
    @Nullable
    private CompoundTag tradeOffers;
    private int villagerXp;
    private PatientStatus patientStatus;
    private Animation painAnimation;
    private Animation deathAnimation;
    private boolean held;


    public CrawlerEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.moveControl = new CrawlerMoveControl(this);
    }


    public void setData(Villager source) {
        setVillagerData(source.getVillagerData());
        setGossips(source.getGossips().store(NbtOps.INSTANCE).copy()); // TODO this was previously getValue() instead of copy(), check if it works
        setTradeOffers(source.getOffers().createTag());
        setVillagerXp(source.getVillagerXp());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new PanicGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        if (pPlayer.isShiftKeyDown() && pPlayer.getItemInHand(pHand).isEmpty() && pHand == InteractionHand.MAIN_HAND) {
        //if (pPlayer.getItemInHand(pHand).isEmpty() && pHand == InteractionHand.MAIN_HAND) {
            if (!level().isClientSide) {
                //startRiding(pPlayer);
                //ItemStack heldVillager = new ItemStack(Registration.HELD_VILLAGER.get());
                //CompoundTag tag = new CompoundTag();
                //addAdditionalSaveData(tag);
                //heldVillager.getOrCreateTag().put("data", tag);
                //pPlayer.setItemSlot(EquipmentSlot.MAINHAND, heldVillager);
                //discard();
                pPlayer.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(data -> data.getCrossSync().setHeldPatient(this, pPlayer));
                discard();
            }
            return InteractionResult.SUCCESS;
        }
        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public VillagerData getVillagerData() {
        return this.entityData.get(DATA_VILLAGER_DATA);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((p_204072_) -> {
            pCompound.put("VillagerData", p_204072_);
        });
        if (this.tradeOffers != null) {
            pCompound.put("Offers", this.tradeOffers);
        }

        if (this.gossips != null) {
            pCompound.put("Gossips", this.gossips);
        }

        pCompound.putInt("Xp", this.villagerXp);
        pCompound.putBoolean("held", held);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }

        if (pCompound.contains("Offers", 10)) {
            this.tradeOffers = pCompound.getCompound("Offers");
        }

        if (pCompound.contains("Gossips", 10)) {
            this.gossips = pCompound.getList("Gossips", 10);
        }

        if (pCompound.contains("Xp", 3)) {
            this.villagerXp = pCompound.getInt("Xp");
        }
        if (pCompound.contains("held")) {
            held = pCompound.getBoolean("held");
        }

    }


    protected void addOffersFromItemListings(MerchantOffers pGivenMerchantOffers, VillagerTrades.ItemListing[] pNewTrades, int pMaxNumbers) {
        Set<Integer> set = Sets.newHashSet();
        if (pNewTrades.length > pMaxNumbers) {
            while (set.size() < pMaxNumbers) {
                set.add(this.random.nextInt(pNewTrades.length));
            }
        } else {
            for (int i = 0; i < pNewTrades.length; ++i) {
                set.add(i);
            }
        }

        for (Integer integer : set) {
            VillagerTrades.ItemListing villagertrades$itemlisting = pNewTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if (merchantoffer != null) {
                pGivenMerchantOffers.add(merchantoffer);
            }
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
        this.entityData.define(DATA_CRAWLING, false);
    }

    public int getCrawling() {
        return 20 - (tickCount - startCrawl);
    }

    public void setGossips(Tag pGossips) {
        this.gossips = pGossips;
    }

    public void setTradeOffers(CompoundTag pTradeOffers) {
        this.tradeOffers = pTradeOffers;
    }

    @Override
    public void setVillagerData(VillagerData p_34376_) {
        VillagerData villagerdata = this.getVillagerData();
        if (villagerdata.getProfession() != p_34376_.getProfession()) {
            this.tradeOffers = null;
        }

        this.entityData.set(DATA_VILLAGER_DATA, p_34376_);
    }

    public void setVillagerXp(int pVillagerXp) {
        this.villagerXp = pVillagerXp;
    }

    // Animation sync code became a bit convoluted as I converged on the right technique. Don't look too much into it
    @Override
    public void tick() {
        if (!isSurgeryPatient()) {
            super.tick();
            if (level().isClientSide) {
                boolean crawl = entityData.get(DATA_CRAWLING);
                if (crawl && getCrawling() < 1) {
                    startCrawl = tickCount;
                }

            }
        } else {
            tickCount++;
            if (painAnimation == null && !patientStatus.isDead()) {
                int currentAbsolutePainThreshold = patientStatus.getCurrentAbsolutePainThreshold();
                int currentMissingPainThreshold = patientStatus.getCurrentMissingPainThreshold();
                if (currentMissingPainThreshold >= 3 || currentAbsolutePainThreshold >= 3) {
                    painAnimation = new Animation(switch (patientStatus.getExposedLocation()){
                        case BACK -> AnimationRegistry.crawler_back_pain_high_1;
                        case CHEST -> AnimationRegistry.crawler_chest_pain_high;
                        case SKULL -> AnimationRegistry.crawler_skull_pain_high;
                    });
                } else if (currentMissingPainThreshold == 2 || currentAbsolutePainThreshold == 2) {
                    painAnimation = new Animation(switch (patientStatus.getExposedLocation()){
                        case BACK -> AnimationRegistry.crawler_back_pain_medium;
                        case CHEST -> AnimationRegistry.crawler_chest_pain_medium;
                        case SKULL -> AnimationRegistry.crawler_skull_pain_medium;
                    });
                } else if (currentMissingPainThreshold == 1 || currentAbsolutePainThreshold == 1) {
                    painAnimation = new Animation(switch (patientStatus.getExposedLocation()){
                        case BACK -> AnimationRegistry.crawler_back_pain_low;
                        case CHEST -> AnimationRegistry.crawler_chest_pain_low;
                        case SKULL -> AnimationRegistry.crawler_skull_pain_low;
                    });
                }
            } else {
                if (painAnimation != null) {
                    if (painAnimation.isDone()) {
                        painAnimation = null;
                    } else {
                        painAnimation.update();
                    }
                }
                if (deathAnimation == null) {
                    if (patientStatus.isDead() && !patientStatus.didFinalAnimation()) {
                        deathAnimation = switch (patientStatus.getExposedLocation()) {
                            case BACK -> new Animation(AnimationRegistry.crawler_back_death);
                            case CHEST -> new Animation(AnimationRegistry.crawler_chest_death);
                            case SKULL -> null;
                        };
                    }
                } else {
                    if (deathAnimation.isDone()) {
                        deathAnimation = null;
                    } else {
                        deathAnimation.update();
                    }
                }
            }
            // TODO do pain animations
        }
    }

    @Override
    public void markAsPatient() {
        surgeryPatient = true;
    }

    @Override
    public boolean isSurgeryPatient() {
        return surgeryPatient;
    }

    @Override
    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    @Override
    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    @Override
    public PatientType getPatientType() {
        return PatientType.VILLAGER;
    }

    @Override
    public void setHeld(boolean held) {
        this.held = held;
    }

    @Override
    public boolean isHeld() {
        return held;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setHeld(false);
    }

    public Animation getPainAnimation() {
        return painAnimation;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    private static class CrawlerMoveControl extends MoveControl {
        public CrawlerMoveControl(CrawlerEntity pMob) {
            super(pMob);
        }

        @Override
        public void tick() {
            super.tick();
            if (mob.level().isClientSide) {
                return;
            }
            CrawlerEntity mob = (CrawlerEntity) this.mob;
            if (mob.getSpeed() == 0) {
                mob.getEntityData().set(DATA_CRAWLING, false);
                mob.startCrawl = -20;
            } else {
                if (mob.getCrawling() <= 0) {
                    mob.entityData.set(DATA_CRAWLING, true);
                    mob.startCrawl = mob.tickCount;
                } else if (mob.getCrawling() < 10) {
                    mob.setSpeed(0);
                }
            }
        }
    }

}
