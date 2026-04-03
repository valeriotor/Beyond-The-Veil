package com.valeriotor.beyondtheveil.client.model.entity;

import com.valeriotor.beyondtheveil.client.model.entity.wrapper.PlayerDefaultModelWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public abstract class AnimatedModel<T extends LivingEntity> extends EntityModel<T> {

    private static final Map<String, AnimatedModel<? extends LivingEntity>> REGISTRY = new HashMap<>();
    public static PlayerDefaultModelWrapper playerModel;
    public static PlayerDefaultModelWrapper playerSlimModel;
    public static void initWrappers() {
        EntityRenderer<? extends Player> playerDefault = Minecraft.getInstance().getEntityRenderDispatcher().playerRenderers.get("default");
        EntityRenderer<? extends Player> playerSlim = Minecraft.getInstance().getEntityRenderDispatcher().playerRenderers.get("slim");
        if (playerDefault instanceof PlayerRenderer renderer) {
            new PlayerDefaultModelWrapper("player", renderer.getModel());
        }
        if (playerSlim instanceof PlayerRenderer renderer) {
            new PlayerDefaultModelWrapper("player_slim", renderer.getModel());
        }
    }
    private boolean dirty;

    public static AnimatedModel<? extends LivingEntity> getModel(String name) {
        return REGISTRY.get(name);
    }

    protected final Map<String, ModelPart> animatedParts = new HashMap<>();
    protected final List<ModelPartAndDefaultPose> defaultPartPoses = new ArrayList<>();

    protected AnimatedModel(String name) {
        REGISTRY.put(name, this);
        if(this instanceof PlayerDefaultModelWrapper wrapper) {
            if (Objects.equals(name, "player")) {
                playerModel = wrapper;
            } else if (Objects.equals(name, "player_slim")) {
                playerSlimModel = wrapper;
            }
        }
    }

    public ModelPart getPart(String name) {
        return animatedParts.get(name);
    }

    protected ModelPart registerAnimatedPart(String name, ModelPart part) {
        return registerAnimatedPart(name, part, true);
    }

    protected ModelPart registerAnimatedPart(ModelPart parent, String name) {
        return registerAnimatedPart(name, parent.getChild(name), true);
    }

    protected ModelPart registerAnimatedPart(ModelPart parent, String name, boolean visible) {
        return registerAnimatedPart(name, parent.getChild(name), visible);
    }

    protected ModelPart registerAnimatedPart(String name, ModelPart part, boolean visible) {
        part.visible = visible;
        animatedParts.put(name, part);
        defaultPartPoses.add(new ModelPartAndDefaultPose(part, part.storePose(), part.xScale, part.yScale, part.zScale, visible));
        return part;
    }

    public void markDirty() {
        dirty = true;
    }

    public void resetParts() {
        if (dirty) {
            for (ModelPartAndDefaultPose defaultPartPose : defaultPartPoses) {
                defaultPartPose.part().loadPose(defaultPartPose.pose());
                defaultPartPose.part.visible = defaultPartPose.visible;
                defaultPartPose.part.xScale = defaultPartPose.xScale;
                defaultPartPose.part.yScale = defaultPartPose.yScale;
                defaultPartPose.part.zScale = defaultPartPose.zScale;
            }
        }
    }

    public record ModelPartAndDefaultPose(ModelPart part, PartPose pose, float xScale, float yScale, float zScale,
                                          boolean visible) {
    }


}
