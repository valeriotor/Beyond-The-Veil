package com.valeriotor.beyondtheveil.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Canoe - Valeriotor
 * Created using Tabula 7.0.0
 */
public class ModelCanoe extends ModelBase {
    public ModelRenderer Bottom;
    public ModelRenderer LeftSide;
    public ModelRenderer RightSide;
    public ModelRenderer shape4;
    public ModelRenderer shape4_1;
    public ModelRenderer shape6;
    public ModelRenderer shape7;
    public ModelRenderer shape7_1;
    public ModelRenderer shape9;
    public ModelRenderer shape10;
    public ModelRenderer shape11;
    public ModelRenderer shape10_1;
    public ModelRenderer shape4_2;
    public ModelRenderer shape11_1;
    public ModelRenderer shape15;

    public ModelCanoe() {
    	this.textureWidth = 256;
        this.textureHeight = 64;
        this.shape15 = new ModelRenderer(this, 0, 12);
        this.shape15.setRotationPoint(0.0F, -1.900000000000001F, -11.0F);
        this.shape15.addBox(-9.5F, 0.0F, 0.0F, 19, 7, 2, 0.0F);
        this.shape4_2 = new ModelRenderer(this, 120, 14);
        this.shape4_2.setRotationPoint(0.0F, -5.899999999999995F, -33.5F);
        this.shape4_2.addBox(-8.0F, 0.0F, 0.0F, 16, 9, 1, 0.0F);
        this.shape7 = new ModelRenderer(this, 0, 30);
        this.shape7.setRotationPoint(8.0F, -5.899999999999995F, -33.5F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 1, 9, 10, 0.0F);
        this.shape11_1 = new ModelRenderer(this, 0, 0);
        this.shape11_1.setRotationPoint(0.0F, -5.899999999999995F, -45.5F);
        this.shape11_1.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 5, 0.0F);
        this.shape7_1 = new ModelRenderer(this, 0, 30);
        this.shape7_1.setRotationPoint(-9.0F, -5.899999999999995F, -33.5F);
        this.shape7_1.addBox(0.0F, 0.0F, 0.0F, 1, 9, 10, 0.0F);
        this.shape4 = new ModelRenderer(this, 200, 52);
        this.shape4.setRotationPoint(-10.0F, -5.899999999999995F, 22.5F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 20, 11, 1, 0.0F);
        this.Bottom = new ModelRenderer(this, 126, 0);
        this.Bottom.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.Bottom.addBox(-10.0F, 0.0F, -22.5F, 20, 1, 45, 0.0F);
        this.shape6 = new ModelRenderer(this, 70, 0);
        this.shape6.setRotationPoint(0.0F, 3.100000000000008F, -22.4F);
        this.shape6.addBox(-8.0F, 0.0F, -11.0F, 16, 1, 10, 0.0F);
        this.shape9 = new ModelRenderer(this, 80, 24);
        this.shape9.setRotationPoint(0.0F, -5.899999999999995F, 23.5F);
        this.shape9.addBox(-8.0F, 0.0F, 0.0F, 16, 9, 10, 0.0F);
        this.shape4_1 = new ModelRenderer(this, 200, 52);
        this.shape4_1.setRotationPoint(-10.0F, -5.899999999999995F, -23.5F);
        this.shape4_1.addBox(0.0F, 0.0F, 0.0F, 20, 11, 1, 0.0F);
        this.RightSide = new ModelRenderer(this, 0, 7);
        this.RightSide.setRotationPoint(-10.0F, -5.899999999999995F, 0.0F);
        this.RightSide.addBox(-0.5F, 0.0F, -22.5F, 1, 11, 45, 0.0F);
        this.LeftSide = new ModelRenderer(this, 0, 7);
        this.LeftSide.setRotationPoint(10.0F, -5.899999999999995F, 0.0F);
        this.LeftSide.addBox(-0.5F, 0.0F, -22.5F, 1, 11, 45, 0.0F);
        this.shape11 = new ModelRenderer(this, 120, 0);
        this.shape11.setRotationPoint(0.0F, -5.899999999999995F, 40.5F);
        this.shape11.addBox(-4.0F, 0.0F, 0.0F, 8, 4, 5, 0.0F);
        this.shape10_1 = new ModelRenderer(this, 133, 28);
        this.shape10_1.setRotationPoint(0.0F, -5.899999999999995F, -40.5F);
        this.shape10_1.addBox(-6.0F, 0.0F, 0.0F, 12, 7, 7, 0.0F);
        this.shape10 = new ModelRenderer(this, 133, 28);
        this.shape10.setRotationPoint(0.0F, -5.899999999999995F, 33.5F);
        this.shape10.addBox(-6.0F, 0.0F, 0.0F, 12, 7, 7, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape11.render(f5);
        this.RightSide.render(f5);
        this.shape6.render(f5);
        this.shape7_1.render(f5);
        this.shape10.render(f5);
        this.LeftSide.render(f5);
        this.shape7.render(f5);
        this.Bottom.render(f5);
        this.shape4_1.render(f5);
        this.shape9.render(f5);
        this.shape4.render(f5);
        this.shape10_1.render(f5);
        this.shape4_2.render(f5);
        this.shape11_1.render(f5);
        this.shape15.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
