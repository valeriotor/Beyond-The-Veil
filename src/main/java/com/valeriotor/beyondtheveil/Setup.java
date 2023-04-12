package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.entity.BloodSkeletonEntity;
import com.valeriotor.beyondtheveil.entity.BloodZombieEntity;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {


    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.DEEP_ONE.get(), DeepOneEntity.prepareAttributes().build());
        event.put(Registration.BLOOD_SKELETON.get(), BloodSkeletonEntity.prepareAttributes().build());
        event.put(Registration.BLOOD_ZOMBIE.get(), BloodZombieEntity.prepareAttributes().build());
    }

    @SubscribeEvent
    public static void onSidedSetup(FMLDedicatedServerSetupEvent event) {
        AnimationRegistry.loadAnimations(false);
    }


}
