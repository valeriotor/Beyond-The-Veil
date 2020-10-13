package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAnimated extends ModelBase{
	

    public List<ModelRenderer> bodyParts = Lists.newArrayList();
    public HashMap<ModelRenderer, EnumMap<Transformation, Float>> defaultAngles = new HashMap<>();
    
    public List<ModelRenderer> getBodyParts(){
    	return this.bodyParts;
    }
    
    public HashMap<ModelRenderer, EnumMap<Transformation, Float>> getDefaultAngles(){
    	return this.defaultAngles;
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z, boolean addToDefault) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
        if(addToDefault) {
	        EnumMap<Transformation, Float> map = new EnumMap<>(Transformation.class);
	        map.put(Transformation.ROTX, x);
	        map.put(Transformation.ROTY, y);
	        map.put(Transformation.ROTZ, z);
	        defaultAngles.put(modelRenderer, map);
        }
    }
    
    protected abstract void setAngles(boolean addToDefault);
    
    protected void fillUpDefaultAngles() {
    	for(ModelRenderer mr : bodyParts) {
    		if(!defaultAngles.containsKey(mr)) {
    			EnumMap<Transformation, Float> map = new EnumMap<>(Transformation.class);
    	        map.put(Transformation.ROTX, 0F);
    	        map.put(Transformation.ROTY, 0F);
    	        map.put(Transformation.ROTZ, 0F);
    	        defaultAngles.put(mr, map);
    		}
    	}
    }
	
}
