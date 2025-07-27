package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.entity.*;
import com.valeriotor.beyondtheveil.entity.ictya.CephalopodianEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {


    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(BTVEntities.DEEP_ONE.get(), DeepOneEntity.prepareAttributes().build());
        event.put(BTVEntities.BLOOD_SKELETON.get(), BloodSkeletonEntity.prepareAttributes().build());
        event.put(BTVEntities.BLOOD_ZOMBIE.get(), BloodZombieEntity.prepareAttributes().build());
        event.put(BTVEntities.BLOOD_WRAITH.get(), BloodWraithEntity.prepareAttributes().build());
        event.put(BTVEntities.CRAWLER.get(), CrawlerEntity.prepareAttributes().build());
        event.put(BTVEntities.WEEPER.get(), WeeperEntity.prepareAttributes().build());
        event.put(BTVEntities.FLETUM.get(), FletumEntity.prepareAttributes().build());
        event.put(BTVEntities.ABOMINATION_0.get(), Abomination0Entity.prepareAttributes().build());
        event.put(BTVEntities.ABOMINATION_1.get(), Abomination1Entity.prepareAttributes().build());
        event.put(BTVEntities.BLOOD_CULTIST.get(), BloodCultistEntity.prepareAttributes().build());
        event.put(BTVEntities.SHOREMAN.get(), ShoremanEntity.prepareAttributes().build());
        event.put(BTVEntities.CEPHALOPODIAN.get(), CephalopodianEntity.prepareAttributes().build());
        //event.put(Registration.NAUTILUS.get(), NautilusEntity.prepareAttributes().build());
    }

    @SubscribeEvent
    public static void spawnPlacementRegisterEvent(SpawnPlacementRegisterEvent event) {
        //event.register();
    }

    @SubscribeEvent
    public static void onSidedSetup(FMLDedicatedServerSetupEvent event) {
        AnimationRegistry.loadAnimations(false);
    }


}
