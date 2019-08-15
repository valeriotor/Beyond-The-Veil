package com.valeriotor.BTV.animations;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.valeriotor.BTV.animations.AnimationTemplate.Transformation;
import com.valeriotor.BTV.entities.models.ModelAnimated;

import net.minecraft.client.model.ModelRenderer;

public abstract class Transformator {
	
	public int length;
	public Map<IntervalDoubleBiOperator, Double> ops = new LinkedHashMap<>();
	
	public Transformator(HashMap<IntervalDoubleBiOperator, Double> ops) {
		this.ops = ops;
	}
	
	public void apply(ModelRenderer bodyPart, double ticks) {
		for(Entry<IntervalDoubleBiOperator, Double> entry : ops.entrySet()) {
			if(ticks > entry.getKey().end) continue;
			this.modify(bodyPart, ticks, entry.getValue(), entry.getKey());
			break;
		}
	}
	
	@Override
	public String toString() {
		return ops.keySet().toString();
	}
	
	public abstract void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op);
	
	public static Transformator getTransformator(Transformation trans, List<IntervalDoubleBiOperator> list, ModelAnimated model, int index, int length) {
		if(list.size() < 1) return null;
		LinkedHashMap<IntervalDoubleBiOperator, Double> map = new LinkedHashMap<>();
		double amount = 0;
		if(trans == Transformation.ROTX || trans == Transformation.ROTY || trans == Transformation.ROTZ) {
			amount = model.getDefaultAngles().get(model.getBodyParts().get(index)).get(trans);
		}
		IntervalDoubleBiOperator op, lastOp = null;
		for(int i = 0; i < list.size(); i++) {
			op  = list.get(i);
			if(i > 0 && op.start > lastOp.end+1) map.put(new IntervalDoubleBiOperator((a,b) -> b, lastOp.end+1, op.start-1, 0), trans != Transformation.VISI ? amount : lastOp.amount);
			map.put(op, amount);
			amount += op.amount;
			lastOp = op;
		}
		if(lastOp.end < length) {
			map.put(new IntervalDoubleBiOperator((a,b) -> b, lastOp.end+1, length, 0), amount);
		}
		switch(trans) {
		case ROTX: return new RotatorX(map);
		case ROTY: return new RotatorY(map);
		case ROTZ: return new RotatorZ(map);
		case TRAX: return new TraslatorX(map);
		case TRAY: return new TraslatorY(map);
		case TRAZ: return new TraslatorZ(map);
		case VISI: return new Hider(map);
		
		}
		return null;
	}
	
	public static class RotatorX extends Transformator{
		public RotatorX(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}
		
		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleX = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class RotatorY extends Transformator{
		public RotatorY(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleY = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class RotatorZ extends Transformator{
		public RotatorZ(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleZ = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorX extends Transformator{
		public TraslatorX(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetX = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorY extends Transformator{
		public TraslatorY(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetY = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorZ extends Transformator{
		public TraslatorZ(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetZ = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class Hider extends Transformator{
		public Hider(HashMap<IntervalDoubleBiOperator, Double> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.isHidden = op.applyAsDouble(ticks, startAmount) < 0;
		}
		
	}
	
}
