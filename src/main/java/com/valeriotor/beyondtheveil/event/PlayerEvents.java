package com.valeriotor.beyondtheveil.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.effect.ImmunityEffect;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.entity.Weeping;
import com.valeriotor.beyondtheveil.item.AntidoteCapsuleItem;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.tile.LacrymatoryBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.saved.PlayerSavedData;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void wakeUpEvent(PlayerWakeUpEvent event) {
        Player p = event.getEntity();
        if (p != null && !p.level().isClientSide() && !event.wakeImmediately() && p.level().getDayTime() > 23900) {
            DreamHandler.dream(p);
            if (ResearchUtil.getResearchStage(p, "FIRSTDREAMS") == 0)
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DIDDREAM, true, false);
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player p = event.getEntity();

        if (event.getFrom() == Level.NETHER && event.getTo() == Level.OVERWORLD && !DataUtil.getBoolean(p, PlayerDataLib.THEBEGINNING)) {
            boolean added = p.addItem(new ItemStack(Registration.NECRONOMICON.get()));
            if (added) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.THEBEGINNING, true, false);
                p.sendSystemMessage(Component.translatable("beginning.netherreturn"));
            }
        }
    }

    @SubscribeEvent
    public static void activateBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        Block block = level.getBlockState(event.getPos()).getBlock();
        //if (block instanceof FlaskBlock || block == Registration.FLASK_SHELF.get()) {
        //    if (player.getItemInHand(event.getHand()).getItem() == Registration.SYRINGE.get()) {
        //        //event.setUseItem(Event.Result.DENY);
        //        //event.setUseBlock(Event.Result.DENY);
        //    }
        //}
        ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemInHand.getItem() == Registration.SYRINGE.get() && level.getBlockEntity(event.getPos()) instanceof SurgicalBE) {
            event.setUseBlock(Event.Result.DENY);
        }
        if (itemInHand.isEmpty() && player.isShiftKeyDown() && level instanceof ServerLevel sl) {
            if (player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).isPresent() && player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().isPresent()) {
                CrossSyncData csData = player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().get();
                CrossSync crossSync = csData.getCrossSync();
                Mob heldPatientEntity = crossSync.getHeldPatientEntity(level);
                heldPatientEntity = transformHeldPatient(heldPatientEntity, sl);
                if (heldPatientEntity != null) {
                    if (level.getBlockEntity(event.getPos()) != null && level.getBlockEntity(event.getPos()).getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                        heldPatientEntity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> c.setChestPos(event.getPos()));
                    } else {
                        heldPatientEntity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> c.setChestPos(null));
                    }
                    if (heldPatientEntity instanceof Weeping weeping && level.getBlockEntity(event.getPos()) instanceof LacrymatoryBE) {
                        weeping.setLacrymatoryPos(event.getPos());
                    }
                    heldPatientEntity.setPos(event.getHitVec().getLocation());
                    //TODO wait why did I comment this? ((SurgeryPatient) heldPatientEntity).setHeld(false);
                    level.addFreshEntity(heldPatientEntity);
                    crossSync.setHeldPatient(null, player);
                    if (heldPatientEntity instanceof WeeperEntity weeper) {
                        weeper.standUp();
                    }
                    if (heldPatientEntity instanceof PlayerMinion minion) {
                        minion.setMaster(player);
                    }
                }
            }
        }
    }

    private static Mob transformHeldPatient(Mob heldPatient, ServerLevel sl) {
        if (heldPatient instanceof CrawlerEntity e) {
            LazyOptional<ConvalescentData> c = e.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
            if (c.isPresent()) {
                ConvalescentData data = c.resolve().get();
                TriggerData triggerData = data.getTriggerData();
                if (triggerData != null) {
                    EntityType<?> type = BTVEntities.getTriggerEntity(data, triggerData).getA();

                    if (type == BTVEntities.CRAWLER.get()) {
                        return heldPatient;
                    } else {
                        Entity o = type.create(sl);
                        if (o instanceof Mob mob) {
                            mob.getCapability(TriggerDataProvider.TRIGGER_DATA).ifPresent(t -> {
                                t.loadFromNBT(triggerData.saveToNBT(new CompoundTag()));
                            });
                            return mob;
                        }
                    }
                }
            }
        }
        return heldPatient;
    }

    public static void setBooleanEvent(Player player, String key, boolean value) {

    }

    @SubscribeEvent
    public static void onApplyEffectEvent(MobEffectEvent.Added event) {
        MobEffect effect = event.getEffectInstance().getEffect();
        if (event.getEntity() instanceof ServerPlayer sp && ImmunityEffect.CURABLE.contains(effect) && !sp.hasEffect(BTVEffects.IMMUNITY.get())) {
            for (ItemStack item : sp.getInventory().items) {
                if (item.getItem() == Registration.ANTIDOTE_CAPSULE.get()) {
                    AntidoteCapsuleItem.applyCapsule(sp, item);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && sp.getServer() != null) {
            PlayerSavedData.getInstance(sp.getServer().overworld()).death(sp);
        }
    }

    @SubscribeEvent
    public static void loggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        addBaptismAttributes(event.getEntity());
        if (event.getEntity() instanceof ServerPlayer sp) {
            syncBloodPool(sp);
        }
    }

    @SubscribeEvent
    public static void cloneEvent(PlayerEvent.Clone event) {
        addBaptismAttributes(event.getEntity());
    }

    public static void addBaptismAttributes(Player p) {
        if (!p.level().isClientSide) {
            if (DataUtil.getBoolean(p, PlayerDataLib.BAPTIZED)) {
                Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
                map.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier("baptism_swim_speed", 2.2, AttributeModifier.Operation.MULTIPLY_BASE));
                map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("baptism_attack", 1.1, AttributeModifier.Operation.MULTIPLY_BASE));
                p.getAttributes().addTransientAttributeModifiers(map);
            }
        }
    }

    public static void syncBloodPool(ServerPlayer player) {
        if (player.getServer() != null) {
            Messages.sendToPlayer(GenericToClientPacket.syncBloodPool(player, BloodPoolData.getInstance(player.getServer())), player);
        }
    }

}
