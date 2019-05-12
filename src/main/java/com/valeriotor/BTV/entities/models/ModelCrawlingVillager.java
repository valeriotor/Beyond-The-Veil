package com.valeriotor.BTV.entities.models;

import com.valeriotor.BTV.entities.EntityCrawlingVillager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelVillager - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelCrawlingVillager extends ModelBase {
    public ModelRenderer field_78191_a;
    public ModelRenderer field_78190_c0;
    public ModelRenderer field_78190_c1;
    public ModelRenderer field_78190_c2;
    public ModelRenderer field_78188_e;
    public ModelRenderer field_78189_b0;
    public ModelRenderer field_78189_b1;
    public ModelRenderer field_78187_d;
    public ModelRenderer field_78191_aChild;

    public ModelCrawlingVillager() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_78188_e = new ModelRenderer(this, 0, 22);
        this.field_78188_e.mirror = true;
        this.field_78188_e.setRotationPoint(2.0F, 21.6F, 6.0F);
        this.field_78188_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(field_78188_e, 1.5707963267948966F, 0.0F, 0.0F);
        this.field_78187_d = new ModelRenderer(this, 0, 22);
        this.field_78187_d.setRotationPoint(-2.0F, 21.6F, 6.0F);
        this.field_78187_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(field_78187_d, 1.5707963267948966F, 0.0F, 0.0F);
        this.field_78191_a = new ModelRenderer(this, 0, 0);
        this.field_78191_a.setRotationPoint(0.0F, 19.0F, -10.0F);
        this.field_78191_a.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.field_78190_c2 = new ModelRenderer(this, 40, 38);
        this.field_78190_c2.setRotationPoint(0.0F, 19.0F, -7.0F);
        this.field_78190_c2.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(field_78190_c2, -0.7499679795819634F, 0.0F, 0.0F);
        this.field_78190_c0 = new ModelRenderer(this, 44, 22);
        this.field_78190_c0.setRotationPoint(0.0F, 19.0F, -7.0F);
        this.field_78190_c0.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(field_78190_c0, -0.7499679795819634F, 0.0F, 0.0F);
        this.field_78190_c1 = new ModelRenderer(this, 44, 22);
        this.field_78190_c1.setRotationPoint(0.0F, 19.0F, -7.0F);
        this.field_78190_c1.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(field_78190_c1, -0.7499679795819634F, 0.0F, 0.0F);
        this.field_78189_b1 = new ModelRenderer(this, 0, 38);
        this.field_78189_b1.setRotationPoint(0.0F, 18.0F, -10.0F);
        this.field_78189_b1.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.setRotateAngle(field_78189_b1, 1.4311699866353502F, 0.0F, 0.0F);
        this.field_78191_aChild = new ModelRenderer(this, 24, 0);
        this.field_78191_aChild.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.field_78191_aChild.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.field_78189_b0 = new ModelRenderer(this, 16, 20);
        this.field_78189_b0.setRotationPoint(0.0F, 18.0F, -10.0F);
        this.field_78189_b0.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.setRotateAngle(field_78189_b0, 1.4311699866353502F, 0.0F, 0.0F);
        this.field_78191_a.addChild(this.field_78191_aChild);
        /*this.field_78188_e.addChild(this.field_78189_b0);
        this.field_78188_e.addChild(this.field_78189_b1);
        this.field_78188_e.addChild(this.field_78191_a);
        this.field_78188_e.addChild(this.field_78190_c0);
        this.field_78188_e.addChild(this.field_78190_c1);
        this.field_78188_e.addChild(this.field_78190_c2);
        this.field_78188_e.addChild(this.field_78187_d);*/
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.field_78188_e.render(f5);
        this.field_78187_d.render(f5);
        this.field_78191_a.render(f5);
        this.field_78190_c2.render(f5);
        this.field_78190_c0.render(f5);
        this.field_78190_c1.render(f5);
        this.field_78189_b1.render(f5);
        this.field_78189_b0.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void knockUnconscious(EntityCrawlingVillager e, float partialTicks) {
    	//this.field_78188_e.rotateAngleX = (e.getTicksToFall()-partialTicks)/20;
    }
}
