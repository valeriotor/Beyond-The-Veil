package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.lib.PlayerDataLib;

public enum WaypointType implements com.valeriotor.beyondtheveil.util.CounterType {
    OCEAN_MONUMENT(PlayerDataLib.waypoint_ocean.name(), "waypoint.ocean_monument");

    public String posDataKey;
    public String unlocalizedName;

    WaypointType( String posDataKey, String unlocalizedName) {
        this.posDataKey = posDataKey;
        this.unlocalizedName = unlocalizedName;
    }
}
