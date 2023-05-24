package com.valeriotor.beyondtheveil.util;

public interface CounterType {

    public static CounterType fromName(String name) {
        for (WaypointType waypointType : WaypointType.values()) {
            if (waypointType.name().equals(name)) {
                return waypointType;
            }
        }
        return null;
    }

    public String name();
}
