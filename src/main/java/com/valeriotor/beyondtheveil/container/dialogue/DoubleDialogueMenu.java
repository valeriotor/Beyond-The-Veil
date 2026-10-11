package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerData;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class DoubleDialogueMenu extends AbstractContainerMenu {
    private final Talkable npc;
    private final Player player;
    private final int entityId;
    private boolean bowing = false;

    public DoubleDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, new ClientTalkable(player), player, byteBuf.readInt());
    }

    public DoubleDialogueMenu(int pContainerId, Talkable talkable, Player player, int entityId) {
        super(Registration.DOUBLE_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;
        this.entityId = entityId;
        this.player = player;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return npc.getTalkingPlayer() == pPlayer && npc instanceof LivingEntity e && e.isAlive() && !bowing;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.npc.setTalkingPlayer((Player) null);
    }

    public void chooseOptionOnServer(ServerPlayer player, int optionIndex) {
        // TODO eh I guess all handled client side?
    }

    public void killKeeper(boolean killCultist) {
        if (npc instanceof BloodCultistEntity cultist) {
            if (cultist.getKillingEntity() instanceof ShoremanEntity shoreman) {
                shoreman.sendAnimation(AnimationRegistry.shoreman_keeper_death_cultist, 0);
                shoreman.aboutToDieByCultist();
            }
            if (killCultist) {
                cultist.sendAnimation(AnimationRegistry.blood_cultist_kill_keeper, 0);
                cultist.killCutscene(true);
            } else {
                cultist.sendAnimation(AnimationRegistry.blood_cultist_kill_keeper_spare_cultist, 0);
            }
            cultist.level().playSound(null, cultist.blockPosition(), BTVSounds.KILL_KEEPER.get(), SoundSource.NEUTRAL, 1, 1);
            if (npc.getTalkingPlayer() != null) {
                DataUtil.setBooleanOnServerAndSync(npc.getTalkingPlayer(), PlayerDataLib.slew_keeper.name(), true, false);
            }
        }
    }

    public void spareCultist() {
        if (npc instanceof BloodCultistEntity cultist) {
            cultist.bowAndLeave();
            bowing = true;
        }
    }

    public void archeCultist() {
        if (npc instanceof BloodCultistEntity cultist) {
            DataUtil.setBooleanOnServerAndSync(npc.getTalkingPlayer(), PlayerDataLib.slew_keeper.name(), true, false);
            ShoremanEntity keeper = cultist.ejectKeeper();
            if (keeper != null) {
                keeper.setArcheEnding();
            }
            if (false && cultist.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.DRIPPING_WATER, cultist.getX(), cultist.getY(), cultist.getZ(), 20, 1, 1, 1, 1);
                sl.sendParticles(ParticleTypes.BUBBLE, cultist.getX(), cultist.getY(), cultist.getZ(), 10, 1, 1, 1, 1);
                sl.playSound(null, cultist.blockPosition(), SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 1, 0.75F);
            }
            PlayerTimer timer = new PlayerTimer.Builder("arche_cultist", 20)
                    .addContinuousAction((player, playerTimer) -> {
                        if (playerTimer.getRemainingTime() > 10 && playerTimer.getRemainingTime() % 2 == 0) {
                            if (cultist.level() instanceof ServerLevel sl) {
                                sl.sendParticles(ParticleTypes.DRIPPING_WATER, cultist.getX(), cultist.getY(), cultist.getZ(), 20, 1, 1, 1, 1);
                                sl.sendParticles(ParticleTypes.BUBBLE, cultist.getX(), cultist.getY(), cultist.getZ(), 10, 1, 1, 1, 1);
                                sl.playSound(null, cultist.blockPosition(), SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 1, 0.75F);
                            }
                            cultist.setDeltaMovement(new Vec3(0.2, 0, 0).yRot((float) (-cultist.getYRot() * Math.PI / 180)));
                            keeper.setDeltaMovement(new Vec3(0.2, 0, 0).yRot((float) (-cultist.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F) * 1.35F)));
                            //cultist.setPos(cultist.position().add(new Vec3(0.2, 0, 0).yRot((float) (-cultist.getYRot() * Math.PI / 180))));
                            //keeper.setPos(keeper.position().add(new Vec3(0.2, 0, 0).yRot((float) (-cultist.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F) * 1.35F))));
                        }
                    })
                    .addFinalActions((player, playerTimer) -> {
                        cultist.sendAnimation(AnimationRegistry.blood_cultist_killed_by_player, 0);
                        cultist.killCutscene(false);
                        cultist.setKillingEntity(null);
                    }).toTimer();

            PlayerTimer timer2 = new PlayerTimer.Builder("arche_keeper", 120)
                    .addFinalActions((player, playerTimer) -> {
                        if (keeper != null && player instanceof ServerPlayer sp) {
                            keeper.setTalkingPlayer(player);
                            DialogueTemplate template = DialogueRegistry.getTemplate(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "arche_ending");
                            NetworkHooks.openScreen(sp, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new EntityDialogueMenu(pContainerId, pPlayerInventory, player, keeper, template, keeper.getId()), Component.translatable("gui.dialogue.shoreman_lighthouse_keeper.display_name")), b -> {
                                b.writeUtf(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER.name());
                                b.writeUtf(template.getID());
                                b.writeInt(keeper.getId());
                            });
                        }
                    }).toTimer();
            PlayerTimerData data = PlayerTimerData.for_(player);
            data.addTimer(timer);
            data.addTimer(timer2);
        }
    }

    public Talkable getNpc() {
        return npc;
    }

    public int getEntityId() {
        return entityId;
    }
}
