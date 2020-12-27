package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityDeepOneBrute extends EntityMob implements IDamageCapper {
    private Animation attackAnimation;
    public EntityDeepOneBrute(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getMaxDamage() {
        return 20;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(world.isRemote) {
            if((world.getTotalWorldTime() & 127) == 0) {
                attackAnimation = new Animation(world.rand.nextBoolean() ? AnimationRegistry.deep_one_brute_right_swing : AnimationRegistry.deep_one_brute_left_swing);
            }
            if(attackAnimation != null) {
                attackAnimation.update();
                if(attackAnimation.isDone())
                    attackAnimation = null;
            }
        }
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }
}
