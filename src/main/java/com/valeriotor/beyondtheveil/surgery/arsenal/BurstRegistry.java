package com.valeriotor.beyondtheveil.surgery.arsenal;

import java.util.HashMap;
import java.util.Map;

public class BurstRegistry {

    static final Map<String, BurstType> REGISTRY = new HashMap<>();

    private static BurstType register(BurstType type) {
        REGISTRY.put(type.getName(), type);
        return type;
    }


}
