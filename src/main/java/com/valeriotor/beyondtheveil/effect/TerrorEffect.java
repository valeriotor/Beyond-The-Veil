package com.valeriotor.beyondtheveil.effect;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TerrorEffect extends MobEffect {

    public TerrorEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.level().isClientSide) {
            return;
        }

        if (pLivingEntity instanceof ServerPlayer player) {
            Vec3 viewVector = player.getViewVector(0);
            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(player, player.getEyePosition(), player.getEyePosition().add(viewVector.normalize().scale(50)), AABB.ofSize(player.position(), 20, 10, 20), entity -> true, 50);
            //EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            if (entityHitResult != null) {
                Entity lookedAtEntity = entityHitResult.getEntity();
                if (isScaredByEntity(player, lookedAtEntity, pAmplifier)) {
                    Messages.sendToPlayer(GenericToClientPacket.rotateCamera(player.getRandom().nextBoolean() ? player.getYRot() + 100 : player.getYRot() - 100, 0, 20), (ServerPlayer) player);
                    BTVEntities.moveEntity(player, lookedAtEntity);
                }
            }
        } else if (!(pLivingEntity instanceof Player)) {
            List<Entity> entities = pLivingEntity.level().getEntities(pLivingEntity, AABB.ofSize(pLivingEntity.position(), 10, 3, 10));
            for (Entity entity : entities) {
                if (isScaredByEntity(pLivingEntity, entity, pAmplifier)) {
                    BTVEntities.moveEntity(pLivingEntity, entity);
                }
            }

        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 5 == 0;
    }

    private boolean isScaredByEntity(LivingEntity scared, Entity scary, int amplifier) {
        if (BTVEntities.isFearlessEntity(scared)) return false;
        if (BTVEntities.isScaryEntity(scary)) return true;
        //if(amplifier > 0 && false) return true;
        if (amplifier > 1 && (scary.getType().getCategory() == MobCategory.MONSTER)) return true;
        if (amplifier > 1 && (scary instanceof Player)) return true;
        if (amplifier > 2) return true;
        return false;
    }
}
