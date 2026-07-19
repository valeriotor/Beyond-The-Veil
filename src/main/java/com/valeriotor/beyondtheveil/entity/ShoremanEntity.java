package com.valeriotor.beyondtheveil.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.PlayerTransformation;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.ai.control.SuspiciousBodyRotationControl;
import com.valeriotor.beyondtheveil.entity.ai.goals.LookAtTalkingPlayerGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.StrollThroughHamletGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.SuspiciousLookAtPlayerGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.TalkToPlayerGoal;
import com.valeriotor.beyondtheveil.item.DrinkItem;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ShoremanEntity extends PathfinderMob implements AnimatedEntity, AnimatedTalkable, Merchant, Suspicious {

    private static final EntityDataAccessor<Integer> PROFESSION = SynchedEntityData.defineId(ShoremanEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SUSPICIOUS_LOOK = SynchedEntityData.defineId(ShoremanEntity.class, EntityDataSerializers.BOOLEAN);

    private Player talkingPlayer;
    private Vec3 lighthouseKeeperStand;
    private Direction lighthouseKeeperDirection;
    private BlockPos villageCenter;
    private BlockPos spawnPoint;
    private Player tradingPlayer;
    protected MerchantOffers offers;
    private Animation dialogueAnimation;
    private int countdownTillDialogueAnimation = -1;
    private int talkingTicks;
    private Animation finishDialogueAnimation;
    private Animation deathAnimation;
    private int aboutToDieByCultistCountdown = -1;
    private boolean inFinalCutscene;
    private final Set<PlayerTransformation> monstrousTransformations = Set.of(PlayerTransformation.ABOMINATION_0, PlayerTransformation.ABOMINATION_1, PlayerTransformation.ABOMINATION_2);
    private int aboutToDieByPlayerCountdown = -1;


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
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, BloodCultistEntity.class, 10, 1.0D, 1.2D, c -> this.getVehicle() != c));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 10, 1.0D, 1.2D, e -> {
            if (e instanceof Player p && p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent() && this.getProfession() != ShoremanProfession.LIGHTHOUSE_KEEPER) {
                CrossSyncData csData = p.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
                PlayerTransformation transformation = csData.getCrossSync().getTransformation();
                return transformation != null && monstrousTransformations.contains(transformation); // null check cause set.of doesn't admit nulls...
            }
            return false;
        }));
        this.goalSelector.addGoal(3, new StrollThroughHamletGoal(this, 1.5D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new SuspiciousLookAtPlayerGoal<>(this, Player.class, 10.0F));
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
        this.entityData.define(SUSPICIOUS_LOOK, false);
    }

    @Override
    public void setSuspiciousLook(boolean value) {
        entityData.set(SUSPICIOUS_LOOK, value);
    }

    @Override
    public boolean getSuspiciousLook() {
        return entityData.get(SUSPICIOUS_LOOK);
    }

    public ShoremanProfession getProfession() {
        return ShoremanProfession.values()[entityData.get(PROFESSION)];
    }

    public void setProfession(int value) {
        entityData.set(PROFESSION, value);
        if (value == ShoremanProfession.LIGHTHOUSE_KEEPER.ordinal()) {
            List<WrappedGoal> toRemove = new ArrayList<>();
            for (WrappedGoal goal : goalSelector.getAvailableGoals()) {
                if (goal.getGoal().getClass() == SuspiciousLookAtPlayerGoal.class || goal.getGoal().getClass() == WaterAvoidingRandomStrollGoal.class || goal.getGoal().getClass() == RandomLookAroundGoal.class) {
                    toRemove.add(goal);
                }
            }
            for (WrappedGoal wrappedGoal : toRemove) {
                goalSelector.removeGoal(wrappedGoal.getGoal());
            }
        } else if (!ShoremanProfession.values()[value].isLeavesPost()) {
            List<WrappedGoal> toRemove = new ArrayList<>();
            for (WrappedGoal goal : goalSelector.getAvailableGoals()) {
                if (goal.getGoal().getClass() == WaterAvoidingRandomStrollGoal.class) {
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

    public BlockPos getVillageCenter() {
        return villageCenter;
    }

    public BlockPos getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new SuspiciousBodyRotationControl<>(this);
    }

    @Override
    public Player getTalkingPlayer() {
        return talkingPlayer;
    }

    @Override
    public void setTalkingPlayer(Player player) {
        talkingPlayer = player;
        animationFromServer(player != null);
    }

    private void startTalking(ServerPlayer player) {
        if (getProfession() != ShoremanProfession.LIGHTHOUSE_KEEPER && player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent()) {
            PlayerTransformation transformation = player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get().getCrossSync().getTransformation();
            if (transformation != null && monstrousTransformations.contains(transformation)) { // null check cause set.of doesn't admit nulls...
                return;
            }
        }
        DialogueType dialogueType = getProfession().toType();
        DialogueTemplate template = DialogueData.for_(player).getDialogue(dialogueType);
        if (template != null) {
            setTalkingPlayer(player);
            NetworkHooks.openScreen(player, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new EntityDialogueMenu(pContainerId, pPlayerInventory, player, this, template, getId()), Component.translatable("gui.dialogue." + getProfession().name().toLowerCase() + ".display_name")), b -> {
                b.writeUtf(dialogueType.name());
                b.writeUtf(template.getID());
                b.writeInt(getId());
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

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        //System.out.println(getProfession() + " " + level().isClientSide);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() != Registration.SHOREMAN_EGG.get() && this.isAlive() && !this.isTalking() && !pPlayer.isSecondaryUseActive()) { // && !this.isSleeping() ?

            if (!this.level().isClientSide) {
                if (giveDrinkToDrunk(pPlayer, pHand, itemstack)) {
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }
                this.startTalking((ServerPlayer) pPlayer);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    private boolean giveDrinkToDrunk(Player player, InteractionHand hand, ItemStack stack) {
        String dialogueId = DialogueData.for_(player).getDialogue(DialogueType.SHOREMAN_DRUNK).getID();
        if (getProfession() == ShoremanProfession.DRUNK && stack.getItem() instanceof DrinkItem && (dialogueId.equals("initial") || dialogueId.equals("initial1"))) {
            stack.shrink(1);
            DialogueData.for_(player).setDialogue(DialogueType.SHOREMAN_DRUNK, DialogueRegistry.getTemplate(DialogueType.SHOREMAN_DRUNK, "drunk"));
            return true;
        }
        return false;
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
        }
        if (villageCenter != null) {
            pCompound.putLong("villageCenter", villageCenter.asLong());
            pCompound.putLong("spawnPoint", spawnPoint.asLong());
        }
        MerchantOffers merchantoffers = this.getOffers();
        if (!merchantoffers.isEmpty()) {
            pCompound.put("Offers", merchantoffers.createTag());
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
        if (pCompound.contains("Offers", 10)) {
            this.offers = new MerchantOffers(pCompound.getCompound("Offers"));
        }
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.updateTrades();
        }
        return this.offers;
    }

    protected void updateTrades() {
        VillagerTrades.ItemListing[] itemListings = TRADES.get(getProfession());
        if (itemListings != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, itemListings, 5);
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
    public void overrideOffers(MerchantOffers pOffers) {
    }

    @Override
    public void notifyTrade(MerchantOffer pOffer) {
    }

    @Override
    public void notifyTradeUpdated(ItemStack pStack) {
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int pXp) {

    }

    @Override
    public boolean showProgressBar() {
        return true;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return null;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide();
    }

    @Override
    public void toggleDialogueAnimation(boolean start) {
        if (start && getProfession() != ShoremanProfession.LIGHTHOUSE_KEEPER) {
            countdownTillDialogueAnimation = 45;
        } else {
            if (dialogueAnimation != null) {
                finishDialogueAnimation = new Animation(AnimationRegistry.shoreman_dialogue_reset);
            }
            countdownTillDialogueAnimation = -1;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (countdownTillDialogueAnimation >= 0) {
                countdownTillDialogueAnimation--;
                if (countdownTillDialogueAnimation == 0) {
                    dialogueAnimation = new Animation(AnimationRegistry.shoreman_dialogue1);
                }
                if (finishDialogueAnimation != null) {
                    finishDialogueAnimation.update();
                    if (finishDialogueAnimation.isDone()) {
                        finishDialogueAnimation = null;
                    }
                }
            } else if (dialogueAnimation != null) {
                dialogueAnimation.update();
                if (dialogueAnimation.isDone()) {
                    if (dialogueAnimation.getTemplate() == AnimationRegistry.shoreman_dialogue1) {
                        dialogueAnimation = new Animation(AnimationRegistry.shoreman_dialogue2);
                    } else if (dialogueAnimation.getTemplate() == AnimationRegistry.shoreman_dialogue2) {
                        countdownTillDialogueAnimation = 30;
                        dialogueAnimation = null;
                    }
                }
            } else if (finishDialogueAnimation != null) {
                finishDialogueAnimation.update();
                if (finishDialogueAnimation.isDone()) {
                    finishDialogueAnimation = null;
                }
            }
            if (deathAnimation != null) {
                deathAnimation.update();
                if (deathAnimation.isDone()) {
                    deathAnimation = null;
                }
            }
            if (aboutToDieByCultistCountdown > 0) {
                double xComponent = -Math.sin(Math.toRadians(50.3 + yHeadRot));
                double zComponent = Math.cos(Math.toRadians(50.3 + yHeadRot));
                for (int i = 0; i < 10; i++) {
                    level().addParticle(BTVParticles.TEARSPILL.get(), getX() + xComponent / 2, getY() + 1.65, getZ() + zComponent / 2, xComponent * (2 + Math.random()) / 2, 0.5 / 2, zComponent * (2 + Math.random()) / 2);
                }
            }
        } else {
            if (isTalking()) {
                talkingTicks++;
            } else {
                talkingTicks = 0;
            }
            if (aboutToDieByCultistCountdown > 0) {
                aboutToDieByCultistCountdown--;
                if (aboutToDieByCultistCountdown == 0) {
                    kill();
                }
            } else if (aboutToDieByPlayerCountdown > 0) {
                aboutToDieByPlayerCountdown--;
                if (aboutToDieByPlayerCountdown == 0) {
                    kill();
                } else if (aboutToDieByPlayerCountdown == 120) {
                    level().playSound(null, blockPosition(), BTVSounds.SPINE_RIP.get(), SoundSource.NEUTRAL, 0.7F, 1);
                } else if (aboutToDieByPlayerCountdown == 100) {
                    level().playSound(null, blockPosition(), BTVSounds.KEEPER_SUFFOCATE.get(), SoundSource.NEUTRAL, 1, 1);
                }
            }
        }
    }

    public int getTalkingTicks() {
        return talkingTicks;
    }

    public void stopDialogueAnimation() {
        this.dialogueAnimation = null;
    }

    public Animation getDialogueAnimation() {
        return dialogueAnimation;
    }

    public Animation getFinishDialogueAnimation() {
        return finishDialogueAnimation;
    }

    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        if (channel == 0) {
            deathAnimation = new Animation(animationTemplate);
            if (animationTemplate == AnimationRegistry.shoreman_keeper_death_cultist) {
                aboutToDieByCultistCountdown = 500;
            }
        }
    }

    public boolean isAboutToDie() {
        return aboutToDieByCultistCountdown >= 0;
    }

    public void aboutToDieByCultist() {
        aboutToDieByCultistCountdown = 25;
    }

    public void inFinalCutscene() {
        inFinalCutscene = true;
    }

    public boolean isInFinalCutscene() {
        return inFinalCutscene;
    }

    public void startKeeperDeath() {
        Player p = getTalkingPlayer();
        if (p instanceof ServerPlayer sp) {
            sp.serverLevel().playSound(null, sp.getOnPos(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 1, 1);
            inFinalCutscene();
            p.startRiding(this);
            //Messages.sendToPlayer(GenericToClientPacket.killKeeper(), sp);
            if (sp.containerMenu instanceof EntityDialogueMenu menu) {

            }
            Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(sp, AnimationRegistry.player_kill_keeper_tp), p);
            Messages.sendToTrackingAndSelf(GenericToClientPacket.startPlayerAnimation(sp, AnimationRegistry.player_slim_kill_keeper_tp), p);
            sendAnimation(AnimationRegistry.shoreman_keeper_death_player, 0);
            aboutToDieByPlayerCountdown = 180;
        }
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
        if (hasPassenger(pPassenger)) {
            final double BASE_DISTANCE = 0.75;
            final double EXTENSION_DISTANCE = 0.6;
            double x = BASE_DISTANCE;
            Vec3 vec3 = (new Vec3(1.75*x, 0, 0.0D)).yRot(-this.getYHeadRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
            pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    public enum ShoremanProfession {
        BARTENDER(DialogueType.SHOREMAN_BARTENDER, false),
        CARPENTER(DialogueType.SHOREMAN_CARPENTER, true),
        CLERK(DialogueType.SHOREMAN_CLERK, true),
        DRUNK(DialogueType.SHOREMAN_DRUNK, false),
        FISHERMAN(DialogueType.SHOREMAN_FISHERMAN, true),
        LIGHTHOUSE_KEEPER(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, false),
        MINER(null, true),
        SCHOLAR(DialogueType.SHOREMAN_SCHOLAR, false),
        SMITH(null, true);


        private final DialogueType type;
        private final boolean leavesPost;

        ShoremanProfession(DialogueType type, boolean leavesPost) {
            this.type = type;
            this.leavesPost = leavesPost;
        }

        public DialogueType toType() {
            return type;
        }

        public boolean isLeavesPost() {
            return leavesPost;
        }
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
            if (this.keeper.getRandom().nextFloat() >= 0.02 || this.keeper.isTalking()) {
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

    private static final Map<ShoremanProfession, VillagerTrades.ItemListing[]> TRADES = Util.make(Maps.newHashMap(), (p_35633_) -> {
        p_35633_.put(ShoremanProfession.CLERK, new VillagerTrades.ItemListing[]{new EmeraldForItems(Items.GOLD_INGOT, 8, 16, 2), new EmeraldForItems(Items.DIAMOND, 2, 16, 2), new EmeraldForItems(Items.HONEYCOMB, 22, 16, 2), new EmeraldForItems(Items.COAL, 34, 16, 2), new EmeraldForItems(Items.IRON_INGOT, 24, 16, 2), new ItemsForEmeralds(Items.COD, 1, 6, 16, 1), new ItemsForEmeralds(Items.SALMON, 1, 6, 16, 1), new ItemsForEmeralds(Items.TADPOLE_BUCKET, 1, 1, 16, 1), new ItemsForEmeralds(Items.PUFFERFISH, 1, 3, 16, 1), new ItemsForEmeralds(Items.TROPICAL_FISH, 1, 3, 16, 1), new ItemsForEmeralds(Registration.SLUG.get(), 1, 4, 16, 1)});
        p_35633_.put(ShoremanProfession.CARPENTER, new VillagerTrades.ItemListing[]{new EmeraldForItems(Blocks.OAK_PLANKS, 64, 16, 2), new EmeraldForItems(Items.BIRCH_PLANKS, 64, 16, 2), new EmeraldForItems(Items.DARK_OAK_PLANKS, 64, 16, 2), new EmeraldForItems(Items.COAL, 34, 16, 2), new ItemsForEmeralds(Items.DARK_OAK_BOAT, 1, 1, 16, 1), new ItemsForEmeralds(Registration.CANOE.get(), 1, 1, 16, 1), new ItemsForEmeralds(Registration.DAMP_WOOD.get(), 1, 12, 16, 1), new ItemsForEmeralds(Registration.DAMP_WOOD_STAIRS.get(), 1, 12, 16, 1), new ItemsForEmeralds(Registration.DAMP_CANOPY.get(), 1, 20, 16, 1), new ItemsForEmeralds(Registration.DAMP_WOOD_FENCE.get(), 1, 12, 16, 1)});
        p_35633_.put(ShoremanProfession.BARTENDER, new VillagerTrades.ItemListing[]{new ItemsForEmeralds(Registration.VODKA.get(), 1, 4, 16, 1), new ItemsForEmeralds(Registration.RUM.get(), 1, 4, 16, 1), new ItemsForEmeralds(Registration.ALE.get(), 1, 4, 16, 1), new ItemsForEmeralds(Registration.MEAD.get(), 1, 4, 16, 1), new ItemsForEmeralds(Registration.WINE.get(), 1, 4, 16, 1)});
    });

    static class EmeraldForItems implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItems(ItemLike pItem, int pCost, int pMaxUses, int pVillagerXp) {
            this.item = pItem.asItem();
            this.cost = pCost;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    static class ItemsForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeralds(Block pBlock, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
            this(new ItemStack(pBlock), pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp);
        }

        public ItemsForEmeralds(Item pItem, int pEmeraldCost, int pNumberOfItems, int pVillagerXp) {
            this(new ItemStack(pItem), pEmeraldCost, pNumberOfItems, 12, pVillagerXp);
        }

        public ItemsForEmeralds(Item pItem, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
            this(new ItemStack(pItem), pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp);
        }

        public ItemsForEmeralds(ItemStack pItemStack, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
            this(pItemStack, pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp, 0.05F);
        }

        public ItemsForEmeralds(ItemStack pItemStack, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
            this.itemStack = pItemStack;
            this.emeraldCost = pEmeraldCost;
            this.numberOfItems = pNumberOfItems;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = pPriceMultiplier;
        }

        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> pMap) {
        return new Int2ObjectOpenHashMap<>(pMap);
    }


}
