package com.valeriotor.beyondtheveil.entities.models;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelAnimated extends ModelBase{
	

    public List<ModelRenderer> bodyParts = Lists.newArrayList();
    public HashMap<ModelRenderer, EnumMap<Transformation, Float>> defaultAngles = new HashMap<>();
    
    public List<ModelRenderer> getBodyParts(){
    	return this.bodyParts;
    }
    
    public HashMap<ModelRenderer, EnumMap<Transformation, Float>> getDefaultAngles(){
    	return this.defaultAngles;
    }
	
}
