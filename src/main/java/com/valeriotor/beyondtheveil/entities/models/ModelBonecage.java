package com.valeriotor.beyondtheveil.entities.models;//Made with Blockbench
//Paste this code into your mod.

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.entities.ictya.EntityBonecage;
import com.valeriotor.beyondtheveil.entities.ictya.EntityMuray;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelBonecage extends ModelAnimated {
    private final ModelRenderer head;                    //0
    private final ModelRenderer tail1;                    //1
    private final ModelRenderer tail2;                    //2
    private final ModelRenderer tail3;                    //3
    private final ModelRenderer tail4;                    //4
    private final ModelRenderer tail5;                    //5
    private final ModelRenderer tail6;                    //6
    private final ModelRenderer tail7;                    //7
    private final ModelRenderer fin1;                    //8
    private final ModelRenderer finUp1;                    //9
    private final ModelRenderer finRight1;                //10
    private final ModelRenderer finDown1;                //11
    private final ModelRenderer finLeft1;                //12
    private final ModelRenderer fin2;                    //13
    private final ModelRenderer finUp2;                    //14
    private final ModelRenderer finRight2;                //15
    private final ModelRenderer finDown2;                //16
    private final ModelRenderer finLeft2;                //17
    private final ModelRenderer head1;                    //18
    private final ModelRenderer head2;                    //19
    private final ModelRenderer spikeUp;                //20
    private final ModelRenderer spikeUp1;                //21
    private final ModelRenderer spikeUpLeft;            //22
    private final ModelRenderer spikeUpLeft1;            //23
    private final ModelRenderer spikeLeft;                //24
    private final ModelRenderer spikeLeft1;                //25
    private final ModelRenderer spikeDownLeft;            //26
    private final ModelRenderer spikeDownLeft1;            //27
    private final ModelRenderer spikeDown;                //28
    private final ModelRenderer spikeDown1;                //29
    private final ModelRenderer spikeDownRight;            //30
    private final ModelRenderer spikeDownRight1;        //31
    private final ModelRenderer spikeRight;                //32
    private final ModelRenderer spikeRight1;            //33
    private final ModelRenderer spikeUpRight;            //34
    private final ModelRenderer spikeUpRight1;            //35

    public ModelBonecage() {
        textureWidth = 256;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 9.0F, -51.0F);

        tail1 = new ModelRenderer(this);
        tail1.setRotationPoint(0.0F, -1.5F, -0.5F);
        head.addChild(tail1);
        tail1.cubeList.add(new ModelBox(tail1, 0, 0, -8.0F, -8.0F, -0.5F, 16, 16, 25, 0.0F, false));

        tail2 = new ModelRenderer(this);
        tail2.setRotationPoint(0.0F, 0.0F, 21.0F);
        tail1.addChild(tail2);
        tail2.cubeList.add(new ModelBox(tail2, 49, 15, -7.9F, -8.01F, -0.5F, 16, 16, 25, 0.0F, false));

        tail3 = new ModelRenderer(this);
        tail3.setRotationPoint(0.0F, 0.0F, 19.0F);
        tail2.addChild(tail3);
        tail3.cubeList.add(new ModelBox(tail3, 25, 1, -8.0F, -8.0F, 1.5F, 16, 16, 25, 0.0F, false));

        tail4 = new ModelRenderer(this);
        tail4.setRotationPoint(0.0F, 0.0F, 24.0F);
        tail3.addChild(tail4);
        tail4.cubeList.add(new ModelBox(tail4, 39, 18, -7.5F, -7.5F, 0.5F, 15, 15, 25, 0.0F, false));

        tail5 = new ModelRenderer(this);
        tail5.setRotationPoint(0.0F, 0.0F, 22.0F);
        tail4.addChild(tail5);
        tail5.cubeList.add(new ModelBox(tail5, 74, 1, -6.5F, -6.5F, 1.5F, 13, 13, 25, 0.0F, false));

        tail6 = new ModelRenderer(this);
        tail6.setRotationPoint(0.0F, 0.0F, 23.0F);
        tail5.addChild(tail6);
        tail6.cubeList.add(new ModelBox(tail6, 60, 27, -5.5F, -5.5F, 1.5F, 11, 11, 25, 0.0F, false));

        tail7 = new ModelRenderer(this);
        tail7.setRotationPoint(0.0F, 0.0F, 24.0F);
        tail6.addChild(tail7);
        tail7.cubeList.add(new ModelBox(tail7, 27, 27, -5.0F, -5.0F, 0.5F, 10, 10, 25, 0.0F, false));

        fin1 = new ModelRenderer(this);
        fin1.setRotationPoint(0.0F, 0.5F, -14.5F);
        tail3.addChild(fin1);

        finUp1 = new ModelRenderer(this);
        finUp1.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin1.addChild(finUp1);
        finUp1.cubeList.add(new ModelBox(finUp1, 0, 87, 0.0F, -26.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finRight1 = new ModelRenderer(this);
        finRight1.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin1.addChild(finRight1);
        finRight1.cubeList.add(new ModelBox(finRight1, 0, 87, 0.0F, -26.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finDown1 = new ModelRenderer(this);
        finDown1.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin1.addChild(finDown1);
        finDown1.cubeList.add(new ModelBox(finDown1, 0, 87, 0.0F, -26.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finLeft1 = new ModelRenderer(this);
        finLeft1.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin1.addChild(finLeft1);
        finLeft1.cubeList.add(new ModelBox(finLeft1, 0, 87, 0.0F, -26.0F, 15.0F, 0, 18, 23, 0.0F, false));

        fin2 = new ModelRenderer(this);
        fin2.setRotationPoint(0.0F, 0.5F, -9.5F);
        tail5.addChild(fin2);

        finUp2 = new ModelRenderer(this);
        finUp2.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin2.addChild(finUp2);
        finUp2.cubeList.add(new ModelBox(finUp2, 210, 0, 0.0F, -24.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finRight2 = new ModelRenderer(this);
        finRight2.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin2.addChild(finRight2);
        finRight2.cubeList.add(new ModelBox(finRight2, 210, 0, 0.0F, -24.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finDown2 = new ModelRenderer(this);
        finDown2.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin2.addChild(finDown2);
        finDown2.cubeList.add(new ModelBox(finDown2, 210, 0, 0.0F, -23.0F, 15.0F, 0, 18, 23, 0.0F, false));

        finLeft2 = new ModelRenderer(this);
        finLeft2.setRotationPoint(0.0F, 0.0F, 0.0F);
        fin2.addChild(finLeft2);
        finLeft2.cubeList.add(new ModelBox(finLeft2, 210, 0, 0.0F, -24.0F, 15.0F, 0, 18, 23, 0.0F, false));


        head1 = new ModelRenderer(this);
        head1.setRotationPoint(0.0F, -1.25F, 2.0F);
        head.addChild(head1);
        head1.cubeList.add(new ModelBox(head1, 179, 72, -6.0F, -6.0F, -20.0F, 12, 12, 18, 0.0F, false));

        head2 = new ModelRenderer(this);
        head2.setRotationPoint(0.0F, 1.25F, -19.0F);
        head1.addChild(head2);
        head2.cubeList.add(new ModelBox(head2, 183, 102, -4.0F, -5.0F, -13.0F, 8, 8, 18, 0.0F, false));

        spikeUp = new ModelRenderer(this);
        spikeUp.setRotationPoint(0.0F, -5.0F, -13.0F);
        head2.addChild(spikeUp);
        spikeUp.cubeList.add(new ModelBox(spikeUp, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeUp1 = new ModelRenderer(this);
        spikeUp1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeUp.addChild(spikeUp1);
        spikeUp1.cubeList.add(new ModelBox(spikeUp1, 92, 46, -0.9F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeUpLeft = new ModelRenderer(this);
        spikeUpLeft.setRotationPoint(4.0F, -5.0F, -13.0F);
        head2.addChild(spikeUpLeft);
        spikeUpLeft.cubeList.add(new ModelBox(spikeUpLeft, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeUpLeft1 = new ModelRenderer(this);
        spikeUpLeft1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeUpLeft.addChild(spikeUpLeft1);
        spikeUpLeft1.cubeList.add(new ModelBox(spikeUpLeft1, 92, 46, -0.9F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeLeft = new ModelRenderer(this);
        spikeLeft.setRotationPoint(4.0F, -1.0F, -13.0F);
        head2.addChild(spikeLeft);
        spikeLeft.cubeList.add(new ModelBox(spikeLeft, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeLeft1 = new ModelRenderer(this);
        spikeLeft1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeLeft.addChild(spikeLeft1);
        spikeLeft1.cubeList.add(new ModelBox(spikeLeft1, 92, 46, -0.9F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDownLeft = new ModelRenderer(this);
        spikeDownLeft.setRotationPoint(4.0F, 3.0F, -13.0F);
        head2.addChild(spikeDownLeft);
        spikeDownLeft.cubeList.add(new ModelBox(spikeDownLeft, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDownLeft1 = new ModelRenderer(this);
        spikeDownLeft1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeDownLeft.addChild(spikeDownLeft1);
        spikeDownLeft1.cubeList.add(new ModelBox(spikeDownLeft1, 92, 46, -0.9F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDown = new ModelRenderer(this);
        spikeDown.setRotationPoint(0.0F, 3.0F, -13.0F);
        head2.addChild(spikeDown);
        spikeDown.cubeList.add(new ModelBox(spikeDown, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDown1 = new ModelRenderer(this);
        spikeDown1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeDown.addChild(spikeDown1);
        spikeDown1.cubeList.add(new ModelBox(spikeDown1, 92, 46, -0.9F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDownRight = new ModelRenderer(this);
        spikeDownRight.setRotationPoint(-4.0F, 3.0F, -13.0F);
        head2.addChild(spikeDownRight);
        spikeDownRight.cubeList.add(new ModelBox(spikeDownRight, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeDownRight1 = new ModelRenderer(this);
        spikeDownRight1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeDownRight.addChild(spikeDownRight1);
        spikeDownRight1.cubeList.add(new ModelBox(spikeDownRight1, 92, 46, -1.1F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeRight = new ModelRenderer(this);
        spikeRight.setRotationPoint(-4.0F, -1.0F, -13.0F);
        head2.addChild(spikeRight);
        spikeRight.cubeList.add(new ModelBox(spikeRight, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeRight1 = new ModelRenderer(this);
        spikeRight1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeRight.addChild(spikeRight1);
        spikeRight1.cubeList.add(new ModelBox(spikeRight1, 92, 46, -1.1F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeUpRight = new ModelRenderer(this);
        spikeUpRight.setRotationPoint(-4.0F, -5.0F, -13.0F);
        head2.addChild(spikeUpRight);
        spikeUpRight.cubeList.add(new ModelBox(spikeUpRight, 92, 46, -1.0F, 0.0F, 0.0F, 2, 2, 80, 0.0F, false));

        spikeUpRight1 = new ModelRenderer(this);
        spikeUpRight1.setRotationPoint(0.0F, 1.0F, 80.0F);
        spikeUpRight.addChild(spikeUpRight1);
        spikeUpRight1.cubeList.add(new ModelBox(spikeUpRight1, 92, 46, -1.1F, -1.0F, 0.0F, 2, 2, 80, 0.0F, false));

        bodyParts.add(head);
        bodyParts.add(tail1);
        bodyParts.add(tail2);
        bodyParts.add(tail3);
        bodyParts.add(tail4);
        bodyParts.add(tail5);
        bodyParts.add(tail6);
        bodyParts.add(tail7);
        bodyParts.add(fin1);
        bodyParts.add(finUp1);
        bodyParts.add(finRight1);
        bodyParts.add(finDown1);
        bodyParts.add(finLeft1);
        bodyParts.add(fin2);
        bodyParts.add(finUp2);
        bodyParts.add(finRight2);
        bodyParts.add(finDown2);
        bodyParts.add(finLeft2);
        bodyParts.add(head1);
        bodyParts.add(head2);
        bodyParts.add(spikeUp);
        bodyParts.add(spikeUp1);
        bodyParts.add(spikeUpLeft);
        bodyParts.add(spikeUpLeft1);
        bodyParts.add(spikeLeft);
        bodyParts.add(spikeLeft1);
        bodyParts.add(spikeDownLeft);
        bodyParts.add(spikeDownLeft1);
        bodyParts.add(spikeDown);
        bodyParts.add(spikeDown1);
        bodyParts.add(spikeDownRight);
        bodyParts.add(spikeDownRight1);
        bodyParts.add(spikeRight);
        bodyParts.add(spikeRight1);
        bodyParts.add(spikeUpRight);
        bodyParts.add(spikeUpRight1);

        setAngles(true);
        fillUpDefaultAngles();

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        head.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    protected void setAngles(boolean addToDefault) {
        setRotateAngle(spikeUp, 0.2618F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeUp1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeUpLeft, 0.2618F, 0.0F, 0.7854F, addToDefault);
        setRotateAngle(spikeUpLeft1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeLeft, 0.2618F, 0.0F, 1.5708F, addToDefault);
        setRotateAngle(spikeLeft1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeDownLeft, 0.2618F, 0.0F, 2.3562F, addToDefault);
        setRotateAngle(spikeDownLeft1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeDown, 0.2618F, 0.0F, 3.1416F, addToDefault);
        setRotateAngle(spikeDown1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeDownRight, 0.2618F, 0.0F, -2.3562F, addToDefault);
        setRotateAngle(spikeDownRight1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeRight, 0.2618F, 0.0F, -1.5708F, addToDefault);
        setRotateAngle(spikeRight1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(spikeUpRight, 0.2618F, 0.0F, -0.7854F, addToDefault);
        setRotateAngle(spikeUpRight1, 3.0543F, 0.0F, 0.0F, addToDefault);
        setRotateAngle(finRight1, 0.0F, 0.0F, -1.5708F, addToDefault);
        setRotateAngle(finDown1, 0.0F, 0.0F, 3.1416F, addToDefault);
        setRotateAngle(finLeft1, 0.0F, 0.0F, 1.5708F, addToDefault);
        setRotateAngle(finRight2, 0.0F, 0.0F, -1.5708F, addToDefault);
        setRotateAngle(finDown2, 0.0F, 0.0F, 3.1416F, addToDefault);
        setRotateAngle(finLeft2, 0.0F, 0.0F, 1.5708F, addToDefault);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase e, float limbSwing, float limbSwingAmount, float partialTickTime) {
        setAngles(false);
        float frequency = 0.5F;
        float time = (float) (2 * Math.PI * (e.ticksExisted + partialTickTime) / 20 * frequency);
        for (int i = 0; i <= 7; i++) {
            bodyParts.get(i).rotateAngleY = MathHelper.cos(time + (float) (2 * i * Math.PI / 7)) * (limbSwingAmount / 3 + 0.06F);
            bodyParts.get(i).rotateAngleX = MathHelper.cos(time + (float) (2 * (i+1) * Math.PI / 7)) * (limbSwingAmount / 6 + 0.025F);
        }
        Animation a = ((EntityBonecage) e).getAttackAnimation();
        if (a != null) {
            a.applyTransformations(bodyParts, partialTickTime);
        }
    }
}