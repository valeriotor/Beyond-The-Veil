package com.valeriotor.beyondtheveil.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.entity.ictya.CephalopodianEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CephalopodianModel extends AnimatedModel<CephalopodianEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(References.MODID, "cephalopodian"), "main");
    private static final String name = "cephalopodian";
    private final ModelPart body;
    private final ModelPart front;
    private final ModelPart leftpad;
    private final ModelPart leftupperpad;
    private final ModelPart leftupperspike;
    private final ModelPart leftlowerpad;
    private final ModelPart leftlowerspike;
    private final ModelPart lefteye;
    private final ModelPart leftupperlid;
    private final ModelPart leftlowerlid;
    private final ModelPart leftiris;
    private final ModelPart rightpad;
    private final ModelPart rightlowerpad;
    private final ModelPart rightlowerspike;
    private final ModelPart rightupperpad;
    private final ModelPart rightupperspike;
    private final ModelPart righteye;
    private final ModelPart rightupperlid;
    private final ModelPart rightlowerlid;
    private final ModelPart rightiris;
    private final ModelPart node;
    private final ModelPart tent1;
    private final ModelPart tent11;
    private final ModelPart tmouth1;
    private final ModelPart tm1upper;
    private final ModelPart tm1lower;
    private final ModelPart tent3;
    private final ModelPart tent31;
    private final ModelPart tmouth3;
    private final ModelPart tm1upper3;
    private final ModelPart tm1lower3;
    private final ModelPart tent4;
    private final ModelPart tent41;
    private final ModelPart tmouth4;
    private final ModelPart tm1upper4;
    private final ModelPart tm1lower4;
    private final ModelPart tent2;
    private final ModelPart tent21;
    private final ModelPart tmouth2;
    private final ModelPart tm1upper2;
    private final ModelPart tm1lower2;
    private final ModelPart tent5;
    private final ModelPart tent51;
    private final ModelPart tent52;
    private final ModelPart tent6;
    private final ModelPart tent61;
    private final ModelPart ten62;
    private final ModelPart tent7;
    private final ModelPart tent71;
    private final ModelPart tent72;
    private final ModelPart tent8;
    private final ModelPart tent81;
    private final ModelPart tent82;
    private float pPartialTick;

    public CephalopodianModel(ModelPart root) {
        super(name);
        this.body = registerAnimatedPart(root, "body");
        this.front = registerAnimatedPart(body, "front");
        this.leftpad = registerAnimatedPart(front, "leftpad");
        this.leftupperpad = registerAnimatedPart(leftpad, "leftupperpad");
        this.leftupperspike = registerAnimatedPart(leftupperpad, "leftupperspike");
        this.leftlowerpad = registerAnimatedPart(leftpad, "leftlowerpad");
        this.leftlowerspike = registerAnimatedPart(leftlowerpad, "leftlowerspike");
        this.lefteye = registerAnimatedPart(leftpad, "lefteye");
        this.leftupperlid = registerAnimatedPart(lefteye, "leftupperlid");
        this.leftlowerlid = registerAnimatedPart(lefteye, "leftlowerlid");
        this.leftiris = registerAnimatedPart(lefteye, "leftiris");
        this.rightpad = registerAnimatedPart(front, "rightpad");
        this.rightlowerpad = registerAnimatedPart(rightpad, "rightlowerpad");
        this.rightlowerspike = registerAnimatedPart(rightlowerpad, "rightlowerspike");
        this.rightupperpad = registerAnimatedPart(rightpad, "rightupperpad");
        this.rightupperspike = registerAnimatedPart(rightupperpad, "rightupperspike");
        this.righteye = registerAnimatedPart(rightpad, "righteye");
        this.rightupperlid = registerAnimatedPart(righteye, "rightupperlid");
        this.rightlowerlid = registerAnimatedPart(righteye, "rightlowerlid");
        this.rightiris = registerAnimatedPart(righteye, "rightiris");
        this.node = registerAnimatedPart(body, "node");
        this.tent1 = registerAnimatedPart(node, "tent1");
        this.tent11 = registerAnimatedPart(tent1, "tent11");
        this.tmouth1 = registerAnimatedPart(tent11, "tmouth1");
        this.tm1upper = registerAnimatedPart(tmouth1, "tm1upper");
        this.tm1lower = registerAnimatedPart(tmouth1, "tm1lower");
        this.tent3 = registerAnimatedPart(node, "tent3");
        this.tent31 = registerAnimatedPart(tent3, "tent31");
        this.tmouth3 = registerAnimatedPart(tent31, "tmouth3");
        this.tm1upper3 = registerAnimatedPart(tmouth3, "tm1upper3");
        this.tm1lower3 = registerAnimatedPart(tmouth3, "tm1lower3");
        this.tent4 = registerAnimatedPart(node, "tent4");
        this.tent41 = registerAnimatedPart(tent4, "tent41");
        this.tmouth4 = registerAnimatedPart(tent41, "tmouth4");
        this.tm1upper4 = registerAnimatedPart(tmouth4, "tm1upper4");
        this.tm1lower4 = registerAnimatedPart(tmouth4, "tm1lower4");
        this.tent2 = registerAnimatedPart(node, "tent2");
        this.tent21 = registerAnimatedPart(tent2, "tent21");
        this.tmouth2 = registerAnimatedPart(tent21, "tmouth2");
        this.tm1upper2 = registerAnimatedPart(tmouth2, "tm1upper2");
        this.tm1lower2 = registerAnimatedPart(tmouth2, "tm1lower2");
        this.tent5 = registerAnimatedPart(node, "tent5");
        this.tent51 = registerAnimatedPart(tent5, "tent51");
        this.tent52 = registerAnimatedPart(tent51, "tent52");
        this.tent6 = registerAnimatedPart(node, "tent6");
        this.tent61 = registerAnimatedPart(tent6, "tent61");
        this.ten62 = registerAnimatedPart(tent61, "ten62");
        this.tent7 = registerAnimatedPart(node, "tent7");
        this.tent71 = registerAnimatedPart(tent7, "tent71");
        this.tent72 = registerAnimatedPart(tent71, "tent72");
        this.tent8 = registerAnimatedPart(node, "tent8");
        this.tent81 = registerAnimatedPart(tent8, "tent81");
        this.tent82 = registerAnimatedPart(tent81, "tent82");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -22.75F, 12.0F, 12.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -33.25F));

        PartDefinition front = body.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -12.0F, -7.5F, 25.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -22.25F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leftpad = front.addOrReplaceChild("leftpad", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, -19.0F, -5.0F, 24.0F, 37.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, -3.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition leftupperpad = leftpad.addOrReplaceChild("leftupperpad", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -7.0F, -2.0F, 10.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -17.0F, 0.0F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition leftupperspike = leftupperpad.addOrReplaceChild("leftupperspike", CubeListBuilder.create().texOffs(90, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -6.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition leftlowerpad = leftpad.addOrReplaceChild("leftlowerpad", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -2.0F, -2.0F, 10.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 15.0F, 0.0F, 0.2618F, -0.2618F, 0.0F));

        PartDefinition leftlowerspike = leftlowerpad.addOrReplaceChild("leftlowerspike", CubeListBuilder.create().texOffs(90, 0).addBox(-1.0F, 45.0F, 0.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -38.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition lefteye = leftpad.addOrReplaceChild("lefteye", CubeListBuilder.create(), PartPose.offset(13.0F, 0.0F, -1.0F));

        PartDefinition leftupperlid = lefteye.addOrReplaceChild("leftupperlid", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, -2.0F, 13.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -7.0F, -3.0F));

        PartDefinition leftlowerlid = lefteye.addOrReplaceChild("leftlowerlid", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -7.0F, 0.0F, 13.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 7.0F, -5.0F));

        PartDefinition leftiris = lefteye.addOrReplaceChild("leftiris", CubeListBuilder.create().texOffs(102, 48).addBox(-3.0F, -5.0F, -4.0F, 10.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(90, 55).addBox(0.0F, -2.0F, -4.9F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightpad = front.addOrReplaceChild("rightpad", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, -19.0F, -5.0F, 24.0F, 37.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -3.0F, 0.0F, -0.3491F, 3.1416F));

        PartDefinition rightlowerpad = rightpad.addOrReplaceChild("rightlowerpad", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -7.0F, -2.0F, 10.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -17.0F, 0.0F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition rightlowerspike = rightlowerpad.addOrReplaceChild("rightlowerspike", CubeListBuilder.create().texOffs(90, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -6.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition rightupperpad = rightpad.addOrReplaceChild("rightupperpad", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -2.0F, -2.0F, 10.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 15.0F, 0.0F, 0.2618F, -0.2618F, 0.0F));

        PartDefinition rightupperspike = rightupperpad.addOrReplaceChild("rightupperspike", CubeListBuilder.create().texOffs(90, 0).addBox(-1.0F, 45.0F, 0.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -38.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition righteye = rightpad.addOrReplaceChild("righteye", CubeListBuilder.create(), PartPose.offset(30.0F, 0.0F, -1.0F));

        PartDefinition rightupperlid = righteye.addOrReplaceChild("rightupperlid", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -1.0F, -2.0F, 13.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -7.0F, -3.0F));

        PartDefinition rightlowerlid = righteye.addOrReplaceChild("rightlowerlid", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -7.0F, 0.0F, 13.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 7.0F, -5.0F));

        PartDefinition rightiris = righteye.addOrReplaceChild("rightiris", CubeListBuilder.create().texOffs(102, 48).addBox(-20.0F, -5.0F, -4.0F, 10.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(90, 55).addBox(-17.0F, -2.0F, -4.9F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition node = body.addOrReplaceChild("node", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 10.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tent1 = node.addOrReplaceChild("tent1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -4.0F, 7.0F, 0.3491F, -0.3491F, 0.0F));

        PartDefinition tent11 = tent1.addOrReplaceChild("tent11", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -1.0F, 3.0F, 3.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 31.0F, -0.3491F, 0.3491F, 0.0F));

        PartDefinition tmouth1 = tent11.addOrReplaceChild("tmouth1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 56.0F));

        PartDefinition tm1upper = tmouth1.addOrReplaceChild("tm1upper", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition tm1lower = tmouth1.addOrReplaceChild("tm1lower", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tent3 = node.addOrReplaceChild("tent3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, 7.0F, 0.3491F, -0.3491F, 1.6581F));

        PartDefinition tent31 = tent3.addOrReplaceChild("tent31", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -1.0F, 3.0F, 3.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 31.0F, -0.3491F, 0.3491F, 0.0F));

        PartDefinition tmouth3 = tent31.addOrReplaceChild("tmouth3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 58.0F));

        PartDefinition tm1upper3 = tmouth3.addOrReplaceChild("tm1upper3", CubeListBuilder.create().texOffs(96, 19).addBox(-2.9F, -1.1F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition tm1lower3 = tmouth3.addOrReplaceChild("tm1lower3", CubeListBuilder.create().texOffs(96, 19).addBox(-2.9F, -1.1F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tent4 = node.addOrReplaceChild("tent4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 7.0F, 0.3491F, -0.3491F, -1.6581F));

        PartDefinition tent41 = tent4.addOrReplaceChild("tent41", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -1.0F, 3.0F, 3.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 31.0F, -0.3491F, 0.3491F, 0.0F));

        PartDefinition tmouth4 = tent41.addOrReplaceChild("tmouth4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 58.0F));

        PartDefinition tm1upper4 = tmouth4.addOrReplaceChild("tm1upper4", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition tm1lower4 = tmouth4.addOrReplaceChild("tm1lower4", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tent2 = node.addOrReplaceChild("tent2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 7.0F, 0.3491F, -0.3491F, -2.9671F));

        PartDefinition tent21 = tent2.addOrReplaceChild("tent21", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -1.0F, 3.0F, 3.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 31.0F, -0.3491F, 0.3491F, 0.0F));

        PartDefinition tmouth2 = tent21.addOrReplaceChild("tmouth2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 58.0F));

        PartDefinition tm1upper2 = tmouth2.addOrReplaceChild("tm1upper2", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition tm1lower2 = tmouth2.addOrReplaceChild("tm1lower2", CubeListBuilder.create().texOffs(96, 19).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tent5 = node.addOrReplaceChild("tent5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tent51 = tent5.addOrReplaceChild("tent51", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent52 = tent51.addOrReplaceChild("tent52", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent6 = node.addOrReplaceChild("tent6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 7.5F, 0.2618F, 0.0F, 1.5708F));

        PartDefinition tent61 = tent6.addOrReplaceChild("tent61", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition ten62 = tent61.addOrReplaceChild("ten62", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent7 = node.addOrReplaceChild("tent7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.0F, 7.5F, 0.2618F, 0.0F, -1.5708F));

        PartDefinition tent71 = tent7.addOrReplaceChild("tent71", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1F, -1.0F, 0.0F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent72 = tent71.addOrReplaceChild("tent72", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent8 = node.addOrReplaceChild("tent8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 7.5F, 0.2618F, 0.0F, 3.1416F));

        PartDefinition tent81 = tent8.addOrReplaceChild("tent81", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9F, -1.0F, 0.0F, 2.0F, 2.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tent82 = tent81.addOrReplaceChild("tent82", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 39.0F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(CephalopodianEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetParts();


        float tentacleSwing = (Mth.cos(limbSwing * 0.4F) + 0.667F) * limbSwingAmount;

        tent1.xRot = 0.3491F + tentacleSwing * 0.5F;
        tent1.yRot = -0.3491F - tentacleSwing * 0.5F;
        tent2.xRot = 0.3491F + tentacleSwing * 0.5F;
        tent2.yRot = -0.3491F - tentacleSwing * 0.5F;
        tent3.xRot = 0.3491F + tentacleSwing * 0.5F;
        tent3.yRot = -0.3491F - tentacleSwing * 0.5F;
        tent4.xRot = 0.3491F + tentacleSwing * 0.5F;
        tent4.yRot = -0.3491F - tentacleSwing * 0.5F;

        tent5.xRot = 0.2618F + tentacleSwing * 0.1F;
        tent6.xRot = 0.2618F + tentacleSwing * 0.1F;
        tent7.xRot = 0.2618F + tentacleSwing * 0.1F;
        tent8.xRot = 0.2618F + tentacleSwing * 0.1F;


        tentacleSwing += 0.333 * limbSwingAmount;
        tent11.yRot = 0.3491F + 2.5F * tentacleSwing * 0.3491F / 2;
        tent11.xRot = -0.3491F - 0.85F * tentacleSwing * 0.3491F / 2;
        tent21.yRot = 0.3491F + 2.5F * tentacleSwing * 0.3491F / 2;
        tent21.xRot = -0.3491F - 0.85F * tentacleSwing * 0.3491F / 2;
        tent31.yRot = 0.3491F + 2.5F * tentacleSwing * 0.3491F / 2;
        tent31.xRot = -0.3491F - 0.85F * tentacleSwing * 0.3491F / 2;
        tent41.yRot = 0.3491F + 2.5F * tentacleSwing * 0.3491F / 2;
        tent41.xRot = -0.3491F - 0.85F * tentacleSwing * 0.3491F / 2;


        float offset = Mth.sin((float) Math.PI * 2 * ageInTicks / (12 * 3.5F)) / 30;
        tent1.xRot += offset;
        tent2.yRot -= offset;
        tent3.yRot += offset;
        tent4.xRot -= offset;
        tent5.xRot += offset;
        tent7.xRot -= offset;
        tent11.yRot -= offset / 2;
        tent21.xRot += offset / 2;
        tent31.xRot -= offset / 2;
        tent41.yRot += offset / 2;
        tent61.xRot = -0.1745F - offset / 2;
        tent81.xRot = -0.1745F + offset / 2;
        leftupperpad.yRot = -0.2618F + offset / 2;
        rightupperpad.xRot = 0.2618F - offset / 2;
        leftlowerpad.xRot = 0.2618F + offset / 2;
        rightlowerpad.yRot = -0.2618F - offset / 2;
        offset = Mth.sin((float) Math.PI * 2 * ageInTicks / (13 * 3.5F)) / 30;
        tent1.yRot += offset;
        tent2.xRot -= offset;
        tent3.xRot += offset;
        tent4.yRot -= offset;
        tent6.xRot += offset;
        tent8.xRot -= offset;
        tent11.xRot -= offset / 2;
        tent21.yRot += offset / 2;
        tent31.yRot -= offset / 2;
        tent41.xRot += offset / 2;
        tent51.xRot = -0.1745F - offset / 2;
        tent71.xRot = -0.1745F + offset / 2;
        leftupperpad.xRot = -0.2618F + offset / 2;
        rightupperpad.yRot = -0.2618F - offset / 2;
        leftlowerpad.yRot = -0.2618F + offset / 2;
        rightlowerpad.xRot = -0.2618F - offset / 2;

        Animation attackAnimation = entity.getAttackAnimation();
        if (attackAnimation != null) {
            attackAnimation.apply(pPartialTick);
        }
    }

    @Override
    public void prepareMobModel(CephalopodianEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        this.pPartialTick = pPartialTick;

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
