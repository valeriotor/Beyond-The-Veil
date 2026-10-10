package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.ai.goals.LivingAmmunitionGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.WeepGoal;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FletumEntity extends PathfinderMob implements PlayerMinion, Talkable, Weeping {

    private BlockPos lacrymatoryPos;
    private UUID master;
    private Player talkingPlayer;

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
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() != Registration.FLETUM_EGG.get() && this.isAlive() && !this.isTalking() && !pPlayer.isSecondaryUseActive() && (!pPlayer.isShiftKeyDown() || !pPlayer.getUUID().equals(master))) {
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

    @Override
    public void orderToFollow(boolean follow) {

    }

    @Override
    public boolean isOrderedToFollow() {
        return false;
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

    @Override
    public Player getTalkingPlayer() {
        return talkingPlayer;
    }

    @Override
    public void setTalkingPlayer(Player player) {
        talkingPlayer = player;
    }

    public void startTalking(ServerPlayer player) {
        DialogueTemplate template;
        if (!DataUtil.getBoolean(player, PlayerDataLib.talked_to_fletum.name())) {
            template = DialogueRegistry.getTemplate(DialogueType.FLETUM, "happy");
        } else {
            template = DialogueRegistry.getTemplate(DialogueType.FLETUM, randomTemplate(player));
            DataUtil.setBoolean(player, PlayerDataLib.talked_to_fletum2.name(), true, false);
        }
        if (template != null) {
            setTalkingPlayer(player);
            NetworkHooks.openScreen(player, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new EntityDialogueMenu(pContainerId, pPlayerInventory, player, this, template, getId()), Component.translatable("gui.dialogue.fletum.display_name")), b -> {
                b.writeUtf(DialogueType.FLETUM.name());
                b.writeUtf(template.getID());
                b.writeInt(getId());
            });
        }
    }

    private String randomTemplate(Player player) {
        boolean ttf2 = DataUtil.getBoolean(player, PlayerDataLib.talked_to_fletum2.name());
        WeightedRandomList<WeightedEntry.Wrapper<String>> list = WeightedRandomList.create(
                WeightedEntry.wrap("happy", ttf2 ? 5 : 0),
                WeightedEntry.wrap("rubies", 5),
                WeightedEntry.wrap("splish", 4),
                WeightedEntry.wrap("cry", 4),
                WeightedEntry.wrap("chaos", 1));
        return list.getRandom(player.getRandom()).orElse(WeightedEntry.wrap("happy", 1)).getData();
    }
}
