package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public enum PatientType {

    VILLAGER(l -> new CrawlerEntity(BTVEntities.CRAWLER.get(), l), l -> new Villager(EntityType.VILLAGER, l)),
    WEEPER(l -> new WeeperEntity(BTVEntities.WEEPER.get(), l)),
    PLAYER(l -> null)
    // ILLAGER, PILLAGER, PLAYER
    ;

    private final Function<Level, Mob> mobFunction;
    private final Function<Level, Mob> podFunction;

    <T extends Mob & SurgeryPatient> PatientType(Function<Level, Mob> mobFunction) {
        this(mobFunction, mobFunction);
    }

    PatientType(Function<Level, Mob> mobFunction, Function<Level, Mob> podFunction) {
        this.mobFunction = mobFunction;
        this.podFunction = podFunction;
    }

    public Function<Level, Mob> getMobFunction() {
        return mobFunction;
    }

    public Function<Level, Mob> getPodFunction() {
        return podFunction;
    }

}
