package com.valeriotor.BTV.animations;

import java.util.List;

import com.valeriotor.BTV.animations.AnimationTemplate.PartMover;

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
		for(PartMover entry : this.type.transforms) {
			bodyPart = bodyParts.get(entry.part);
			for(Transformator t : entry.movers) {
				t.apply(bodyPart, ticks+partialTicks);
			}
		}
	}
}
