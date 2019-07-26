package com.valeriotor.BTV.animations;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.model.ModelRenderer;

public class Animation {
	public final AnimationTemplate type;
	private int ticks = 0;
	
	public Animation(AnimationTemplate type) {
		this.type = type;
	}
	
	public boolean isDone() {
		return this.ticks > this.type.length;
	}
	
	public void update() {
		this.ticks++;
	}
	
	public void applyTransformations(List<ModelRenderer> bodyParts, double partialTicks) {
		ModelRenderer bodyPart = null;
		for(Entry<Integer, List<Transformator>> entry : this.type.transforms.entrySet()) {
			if(entry.getKey() == null) {
				System.err.println("The key section in " + this.type.name + ".btvanim is incomplete");
				continue;
			}
			bodyPart = bodyParts.get(entry.getKey().intValue());
			for(Transformator t : entry.getValue()) {
				t.apply(bodyPart, ticks+partialTicks);
			}
		}
	}
}
