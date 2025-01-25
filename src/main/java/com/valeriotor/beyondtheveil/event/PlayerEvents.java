package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.block.SurgeryBedBlock;
import com.valeriotor.beyondtheveil.block.WateryCradleBlock;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.LazyOptional;
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
                    heldPatientEntity.setPos(event.getHitVec().getLocation());
                    //TODO wait why did I comment this? ((SurgeryPatient) heldPatientEntity).setHeld(false);
                    level.addFreshEntity(heldPatientEntity);
                    crossSync.setHeldPatient(null, player);
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
                    EntityType<?> type = switch (data.getCapacity()) {
                        case 1 -> Registration.ABOMINATION_0.get();
                        case 2 -> Registration.ABOMINATION_1.get();
                        default -> Registration.CRAWLER.get();
                    };
                    if (type == Registration.CRAWLER.get()) {
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


}
