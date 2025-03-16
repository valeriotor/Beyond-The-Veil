package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.render.PatientHolderType;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.ai.goals.CultistKillGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.CultistPodGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.LookAtTalkingPlayerGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.TalkToPlayerGoal;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Arrays;

public class BloodCultistEntity extends PathfinderMob implements Talkable {

    private LivingEntity killingEntity;
    private Player talkingPlayer;
    private CrawlerEntity heldVillager;
    private static final EntityDataAccessor<String> HELD_VILLAGER_TYPE = SynchedEntityData.defineId(BloodCultistEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> KILLING_ENTITY_ID = SynchedEntityData.defineId(BloodCultistEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BOWING = SynchedEntityData.defineId(BloodCultistEntity.class, EntityDataSerializers.BOOLEAN);
    private Animation backStabAnimation;
    private Animation bowingAnimation;
    private boolean didBackStabAnimation;
    private boolean startedBowing;
    private int bowing;
    private BlockPos podPos;
    private int leaving;


    public BloodCultistEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new CultistPodGoal(this, 1D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(0, new CultistKillGoal(this));
        this.goalSelector.addGoal(1, new TalkToPlayerGoal<>(this));
        this.goalSelector.addGoal(1, new LookAtTalkingPlayerGoal<>(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public void startTalking(ServerPlayer player) {
        DialogueType dialogueType = DialogueType.BLOOD_CULTIST;
        DialogueTemplate template = DialogueData.for_(player).getDialogue(dialogueType);
        if (template != null) {
            setTalkingPlayer(player);
            NetworkHooks.openScreen(player, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new EntityDialogueMenu(pContainerId, pPlayerInventory, player, this, template), Component.translatable("gui.dialogue.blood_cultist.display_name")), b -> {
                b.writeUtf(dialogueType.name());
                b.writeUtf(template.getID());
            });

        }
        //OptionalInt optionalint = player.openMenu(new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new ShoremanDialogueMenu(pContainerId, pPlayerInventory, player, this, null), Component.translatable("gui.dialogue." + getProfession().name().toLowerCase() + ".display_name")));
        //if (optionalint.isPresent()) {
        //    MerchantOffers merchantoffers = this.getOffers();
        //    if (!merchantoffers.isEmpty()) {
        //        player.sendMerchantOffers(optionalint.getAsInt(), merchantoffers, pLevel, this.getVillagerXp(), this.showProgressBar(), this.canRestock());
        //    }
        //}
    }

    public void bowAndLeave() {
        entityData.set(BOWING, true);
        bowing = 40;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (heldVillager == null && !entityData.get(HELD_VILLAGER_TYPE).equals("null")) {
                heldVillager = new CrawlerEntity(Registration.CRAWLER.get(), level());
                heldVillager.setHolderType(PatientHolderType.CULTIST);
                VillagerType villagerType = BuiltInRegistries.VILLAGER_TYPE.get(new ResourceLocation(entityData.get(HELD_VILLAGER_TYPE)));
                heldVillager.setVillagerData(heldVillager.getVillagerData().setType(villagerType));
                heldVillager.setHeld(true);
            } else if (heldVillager != null && entityData.get(HELD_VILLAGER_TYPE).equals("null")) {
                heldVillager = null;
            }

            if (entityData.get(KILLING_ENTITY_ID) != -1) {
                Entity entity = level().getEntity(entityData.get(KILLING_ENTITY_ID));
                if (entity != null) {
                    lookAt(entity, 360, 360);
                    yBodyRot = getYRot();
                    if (!didBackStabAnimation) {
                        backStabAnimation = new Animation(AnimationRegistry.blood_cultist_backstab);
                        didBackStabAnimation = true;
                    }
                }
            }
            if (entityData.get(BOWING) && !startedBowing) {
                startedBowing = true;
                bowingAnimation = new Animation(AnimationRegistry.blood_cultist_bow);
            }

            if (backStabAnimation != null) {
                backStabAnimation.update();
                if (backStabAnimation.isDone()) {
                    backStabAnimation = null;
                }
            }
            if (bowingAnimation != null) {
                bowingAnimation.update();
                if (bowingAnimation.isDone()) {
                    bowingAnimation = null;
                }
            }
        } else {
            if (bowing > 0) {
                bowing--;
                if (bowing == 0) {
                    doParticles();
                    discard();
                }
            }
            if (leaving > 0) {
                leaving--;
                if (leaving == 0) {
                    doParticles();
                    discard();
                }
            }
        }
    }

    private void doParticles() {
        if (level() instanceof ServerLevel sl) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = 0; y < 7; y++) {
                        sl.sendParticles(BTVParticles.BLOODSPILL.get(), getX() + x * 0.3, getY() + y * 0.2, getZ() + z * 0.3, 3, 0, 0, 0, 0.1);
                    }
                }
            }
        }
    }

    public Animation getBackStabAnimation() {
        return backStabAnimation;
    }

    public Animation getBowingAnimation() {
        return bowingAnimation;
    }

    public CrawlerEntity getHeldVillager() {
        return heldVillager;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        //System.out.println(getProfession() + " " + level().isClientSide);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isAlive() && !this.isTalking() && !pPlayer.isSecondaryUseActive()) { // && !this.isSleeping() ?

            if (!this.level().isClientSide) {
                this.startTalking((ServerPlayer) pPlayer);
                // DEBUG setHeldVillagerType(VillagerType.PLAINS);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public Player getTalkingPlayer() {
        return talkingPlayer;
    }

    @Override
    public void setTalkingPlayer(Player player) {
        talkingPlayer = player;
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());

            Vec3 vec3 = (new Vec3(0.7, 0, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
            /*pPassenger.setYRot(pPassenger.getYRot() + this.deltaRotation);
            pPassenger.setYHeadRot(pPassenger.getYHeadRot() + this.deltaRotation);
            this.clampRotation(pPassenger);
            if (pPassenger instanceof Animal && this.getPassengers().size() == this.getMaxPassengers()) {
                int j = pPassenger.getId() % 2 == 0 ? 90 : 270;
                pPassenger.setYBodyRot(((Animal)pPassenger).yBodyRot + (float)j);
                pPassenger.setYHeadRot(pPassenger.getYHeadRot() + (float)j);
            }*/
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    public void setHeldVillagerType(VillagerType type) {
        if (type == null) {
            entityData.set(HELD_VILLAGER_TYPE, "null");
        } else {
            entityData.set(HELD_VILLAGER_TYPE, type.toString());
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HELD_VILLAGER_TYPE, "null");
        this.entityData.define(KILLING_ENTITY_ID, -1);
        this.entityData.define(BOWING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        String heldVillager = entityData.get(HELD_VILLAGER_TYPE);
        pCompound.putString("held", heldVillager);
        if (podPos != null) {
            pCompound.putLong("podPos", podPos.asLong());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(HELD_VILLAGER_TYPE, pCompound.contains("held") ? pCompound.getString("held") : "null");
        if (pCompound.contains("podPos")) {
            podPos = BlockPos.of(pCompound.getLong("podPos"));
        }
    }

    public void setKillingEntity(LivingEntity killingEntity) {
        this.killingEntity = killingEntity;
        if (killingEntity != null) {
            entityData.set(KILLING_ENTITY_ID, killingEntity.getId());
        } else {
            entityData.set(KILLING_ENTITY_ID, -1);
        }
    }

    public LivingEntity getKillingEntity() {
        return killingEntity;
    }

    public BlockPos getPodPos() {
        return podPos;
    }

    public void setPodPos(BlockPos podPos) {
        this.podPos = podPos;
    }

    public void setLeaving(int leaving) {
        this.leaving = leaving;
    }
}
