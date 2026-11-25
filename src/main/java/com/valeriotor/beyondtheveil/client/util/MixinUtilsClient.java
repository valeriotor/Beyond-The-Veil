package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import net.minecraft.world.entity.Entity;

public class MixinUtilsClient {

    public static boolean checkRevelationRing() {
        return ClientMethods.isRevelationRing();
    }

    public static boolean checkRevelationRingGlow(Entity entity) {
        return ClientMethods.isRevelationRingGlow(entity);
    }

}
