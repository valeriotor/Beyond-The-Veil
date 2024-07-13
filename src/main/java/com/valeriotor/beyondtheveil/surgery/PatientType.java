package com.valeriotor.beyondtheveil.surgery;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public enum PatientType {

    VILLAGER(l -> new CrawlerEntity(Registration.CRAWLER.get(), l)),
    WEEPER(l -> new WeeperEntity(Registration.WEEPER.get(), l))
    // ILLAGER, PILLAGER, PLAYER
    ;

    private final Function<Level, Mob> mobFunction;

    <T extends Mob & SurgeryPatient> PatientType(Function<Level, Mob> mobFunction) {
        this.mobFunction = mobFunction;
    }

    public Function<Level, Mob> getMobFunction() {
        return mobFunction;
    }
}
