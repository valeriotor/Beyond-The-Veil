package com.valeriotor.beyondtheveil.entities.bosses;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityDeepOneMyrmidon extends EntityMob {

    public EntityDeepOneMyrmidon(World worldIn) {
        super(worldIn);
        ignoreFrustumCheck = true;
    }


}
