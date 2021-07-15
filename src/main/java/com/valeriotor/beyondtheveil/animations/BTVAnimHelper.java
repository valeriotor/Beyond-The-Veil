package com.valeriotor.beyondtheveil.animations;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import com.valeriotor.beyondtheveil.animations.AnimationTemplate.Transformation;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.models.ModelAnimated;
import com.valeriotor.beyondtheveil.entities.models.ModelDeepOne;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class BTVAnimHelper {
	
	public String name;
	private AnimationTemplate template;
	
	public BTVAnimHelper(String name, AnimationTemplate template) {
		this.name = name;
		this.template = template;
	}
	
	
	public void processLine(String s, boolean readingKeys, boolean makingOps, int start, int end, HashMap<String, Integer> keys, HashMap<Integer, EnumMap<Transformation, List<IntervalDoubleBiOperator>>> transforms) {
		if(readingKeys) {
			String[] ss = s.replaceAll("\\s+", "").split(":");
			if(ss.length < 2) return; // line might be blank
			keys.put(ss[0], Integer.valueOf(ss[1]));
			return;
		}
		if(makingOps) {
			String[] ss = s.replaceAll("\\s+", "").split(":");
			if(ss.length < 2) return; // line might be blank
			String func = ss.length == 3 ? "linear" : ss[3];
			Transformation trans = getTransform(ss[1]);
			double amount = Double.parseDouble(ss[2]);
			DoubleBinaryOperator op = getOperator(func, start, end, amount);
			Integer key = keys.get(ss[0]);
			if(transforms.containsKey(key)) {
				if(transforms.get(key).containsKey(trans))
					transforms.get(key).get(trans).add(new IntervalDoubleBiOperator(op, start, end, amount));
				else {
					transforms.get(key).put(getTransform(ss[1]), new ArrayList<>());
					transforms.get(key).get(trans).add(new IntervalDoubleBiOperator(op, start, end, amount));
				}
			}			
			else {
				transforms.put(key, new EnumMap<>(Transformation.class));
				transforms.get(key).put(getTransform(ss[1]), new ArrayList<>());
				transforms.get(key).get(trans).add(new IntervalDoubleBiOperator(op, start, end, amount));
			}
		}
		
		if(s.contains("length: ")) {
			this.template.length = Integer.parseInt(s.split(":")[1].replaceAll("\\s+", ""));
		}else if(s.contains("entity:")) {
			this.template.entityType = this.getEntity(s.split(":")[1].replaceAll("\\s+", ""));
		}else if(s.contains("model:")) {
			this.template.modelType = this.getModel(s.split(":")[1].replaceAll("\\s+", ""));
		}
	}
	
	private Class<? extends EntityLivingBase> getEntity(String s){
		switch(s) {
		case "deep_one": return EntityDeepOne.class;
		case "player": return EntityPlayer.class;
		default:
			System.err.println("Error reading entity type in " + name + ".btvanim");
			return null;
		}
	}
	
	private ModelAnimated getModel(String s){
		switch(s) {
			case "deep_one": return ModelRegistry.deep_one;
			case "shoggoth": return ModelRegistry.shoggoth;
			case "blood_zombie": return ModelRegistry.blood_zombie;
			case "blood_skeleton": return ModelRegistry.blood_skeleton;
			case "crazed_weeper" : return ModelRegistry.crazed_weeper;
			case "surgeon" : return ModelRegistry.surgeon;
			case "muray" : return ModelRegistry.muray;
			case "deep_one_brute" : return ModelRegistry.deepOneBrute;
			case "deep_one_myrmidon" : return ModelRegistry.deepOneMyrmidon;
			case "cephalopodian" : return ModelRegistry.cephalopodian;
		default:
			System.err.println("Error reading model type in " + name + ".btvanim");
			return null;
		}
	}
	
	private DoubleBinaryOperator getOperator(String func, final int start, final int end, final double amount) {
		switch(func) {
			case "stop": 		return  (a, b) -> b;
			case "linear": 		return  (a, b) -> b+(a-start)/(end-start)*amount;
			case "quadratic": 	return  (a, b) -> b+Math.pow((a-start)/(end-start), 2)*amount;
			case "sin": 		return  (a, b) -> b+Math.sin((a-start)/(end-start)*Math.PI/2)*amount;
			case "constant":
			case "costant": 	return  (a, b) -> b+amount;
			case "newconstant":
			case "newcostant": 	return  (a, b) -> amount;
		}
		throw new IllegalArgumentException();
	}
	
	private Transformation getTransform(String s) {
		for(Transformation t : Transformation.values()) {
			if(t.toString().toLowerCase().equals(s.toLowerCase())) return t;
		}
		template.sendError();
		return null;
	}
	
}
