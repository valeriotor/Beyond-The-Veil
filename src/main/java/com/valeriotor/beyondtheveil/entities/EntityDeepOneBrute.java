package com.valeriotor.beyondtheveil.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityDeepOneBrute extends EntityMob implements IDamageCapper {

    public EntityDeepOneBrute(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getMaxDamage() {
        return 20;
    }
}
