package com.valeriotor.beyondtheveil.entities.models;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.entities.ictya.EntityAdeline;
import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelAdeline extends ModelAnimated {
    private final ModelRenderer body;          //0
    private final ModelRenderer body1;         //1
    private final ModelRenderer body2;         //2
    private final ModelRenderer head1;         //3
    private final ModelRenderer spikeUp;       //4
    private final ModelRenderer spikeUp1;      //5
    private final ModelRenderer spikeRight;    //6
    private final ModelRenderer spikeRight1;   //7
    private final ModelRenderer spikeDown;     //8
    private final ModelRenderer spikeDown1;    //9
    private final ModelRenderer spikeLeft;     //10
    private final ModelRenderer spikeLeft1;    //11

    public ModelAdeline() {
        textureWidth = 128;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 8.0F, -0.5F);
        body.cubeList.add(new ModelBox(body, 0, 0, -10.0F, -10.0F, -12.5F, 20, 20, 25, 0.0F, false));

        body1 = new ModelRenderer(this);
        body1.setRotationPoint(0.0F, 0.0F, 12.0F);
        body.addChild(body1);
        body1.cubeList.add(new ModelBox(body1, 0, 0, -8.0F, -8.0F, 0.0F, 16, 16, 18, 0.0F, false));

        body2 = new ModelRenderer(this);
        body2.setRotationPoint(0.0F, 0.0F, 16.0F);
        body1.addChild(body2);
        body2.cubeList.add(new ModelBox(body2, 0, 0, -6.0F, -6.0F, 0.0F, 12, 12, 17, 0.0F, false));

        head1 = new ModelRenderer(this);
        head1.setRotationPoint(0.0F, 0.0F, -10.0F);
        body.addChild(head1);
        head1.cubeList.add(new ModelBox(head1, 0, 0, -5.0F, -5.0F, -17.0F, 10, 10, 18, 0.0F, false));

        spikeUp = new ModelRenderer(this);
        spikeUp.setRotationPoint(0.0F, -5.0F, -17.0F);
        head1.addChild(spikeUp);
        spikeUp.cubeList.add(new ModelBox(spikeUp, 0, 54, -0.5F, -7.0F, 0.0F, 1, 7, 1, 0.0F, false));

        spikeUp1 = new ModelRenderer(this);
        spikeUp1.setRotationPoint(0.0F, -7.0F, 0.0F);
        spikeUp.addChild(spikeUp1);
        spikeUp1.cubeList.add(new ModelBox(spikeUp1, 0, 54, -0.5F, -1.0F, -6.0F, 1, 1, 7, 0.0F, false));

        spikeRight = new ModelRenderer(this);
        spikeRight.setRotationPoint(-5.0F, -0.5F, -17.0F);
        head1.addChild(spikeRight);
        spikeRight.cubeList.add(new ModelBox(spikeRight, 0, 54, -1.0F, -7.0F, 0.0F, 1, 7, 1, 0.0F, false));

        spikeRight1 = new ModelRenderer(this);
        spikeRight1.setRotationPoint(5.0F, -12.0F, 0.0F);
        spikeRight.addChild(spikeRight1);
        spikeRight1.cubeList.add(new ModelBox(spikeRight1, 0, 54, -6.0F, 4.0F, -6.0F, 1, 1, 7, 0.0F, false));

        spikeDown = new ModelRenderer(this);
        spikeDown.setRotationPoint(0.0F, 5.0F, -17.0F);
        head1.addChild(spikeDown);
        spikeDown.cubeList.add(new ModelBox(spikeDown, 0, 54, -0.5F, -7.0F, 0.0F, 1, 7, 1, 0.0F, false));

        spikeDown1 = new ModelRenderer(this);
        spikeDown1.setRotationPoint(5.5F, -12.0F, 0.0F);
        spikeDown.addChild(spikeDown1);
        spikeDown1.cubeList.add(new ModelBox(spikeDown1, 0, 54, -6.0F, 4.0F, -6.0F, 1, 1, 7, 0.0F, false));

        spikeLeft = new ModelRenderer(this);
        spikeLeft.setRotationPoint(5.0F, 0.0F, -17.0F);
        head1.addChild(spikeLeft);
        spikeLeft.cubeList.add(new ModelBox(spikeLeft, 0, 54, -0.5F, -7.0F, 0.0F, 1, 7, 1, 0.0F, false));

        spikeLeft1 = new ModelRenderer(this);
        spikeLeft1.setRotationPoint(0.5F, -7.0F, 0.0F);
        spikeLeft.addChild(spikeLeft1);
        spikeLeft1.cubeList.add(new ModelBox(spikeLeft1, 0, 54, -1.0F, -1.0F, -6.0F, 1, 1, 7, 0.0F, false));

        bodyParts.add(body);
        bodyParts.add(body1);
        bodyParts.add(body2);
        bodyParts.add(head1);
        bodyParts.add(spikeUp);
        bodyParts.add(spikeUp1);
        bodyParts.add(spikeRight);
        bodyParts.add(spikeRight1);
        bodyParts.add(spikeDown);
        bodyParts.add(spikeDown1);
        bodyParts.add(spikeLeft);
        bodyParts.add(spikeLeft1);

        setAngles(true);
        fillUpDefaultAngles();

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    protected void setAngles(boolean addToDefault) {
        setRotateAngle(spikeUp, -0.6109F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeRight, -0.6109F, 0.0F, -1.5708F, addToDefault);
        setRotateAngle(spikeDown, -0.6109F, 0.0F, 3.1416F, addToDefault);
        setRotateAngle(spikeLeft, -0.6109F, 0.0F, 1.5708F, addToDefault);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount, float partialTickTime) {
        setAngles(false);
        float time = (float) (0.08 * Math.PI * (e.ticksExisted + partialTickTime));
        for(int i = 0; i < 7; i++) {
            bodyParts.get(i).rotateAngleX = (float) Math.atan(MathHelper.cos(time + (float)(2*i*Math.PI/3))) * (limbSwingAmount/3+0.02F);
        }
        EntityAdeline a = (EntityAdeline) e;
        Animation anim = a.getAttackAnimation();
        if (anim != null) {
            anim.applyTransformations(bodyParts, partialTickTime);
        }
    }
}