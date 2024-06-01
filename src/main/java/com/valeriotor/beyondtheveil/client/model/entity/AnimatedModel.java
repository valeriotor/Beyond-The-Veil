package com.valeriotor.beyondtheveil.client.model.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AnimatedModel<T extends LivingEntity> extends EntityModel<T> {

    private static final Map<String, AnimatedModel<? extends LivingEntity>> REGISTRY = new HashMap<>();

    public static AnimatedModel<? extends LivingEntity> getModel(String name) {
        return REGISTRY.get(name);
    }

    protected final Map<String, ModelPart> animatedParts = new HashMap<>();
    protected final List<ModelPartAndDefaultPose> defaultPartPoses = new ArrayList<>();

    protected AnimatedModel(String name) {
        REGISTRY.put(name, this);
    }

    public ModelPart getPart(String name) {
        return animatedParts.get(name);
    }
    protected ModelPart registerAnimatedPart(String name, ModelPart part) {
        return registerAnimatedPart(name, part, true);
    }

    protected ModelPart registerAnimatedPart(String name, ModelPart part, boolean visible) {
        part.visible = visible;
        animatedParts.put(name, part);
        defaultPartPoses.add(new ModelPartAndDefaultPose(part, part.storePose(), part.xScale, part.yScale, part.zScale, visible));
        return part;
    }

    protected void resetParts() {
        for (ModelPartAndDefaultPose defaultPartPose : defaultPartPoses) {
            defaultPartPose.part().loadPose(defaultPartPose.pose());
            defaultPartPose.part.visible = defaultPartPose.visible;
            defaultPartPose.part.xScale = defaultPartPose.xScale;
            defaultPartPose.part.yScale = defaultPartPose.yScale;
            defaultPartPose.part.zScale = defaultPartPose.zScale;
        }
    }

    public record ModelPartAndDefaultPose(ModelPart part, PartPose pose, float xScale, float yScale, float zScale, boolean visible) {
    }


}
