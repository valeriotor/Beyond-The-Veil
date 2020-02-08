package com.valeriotor.BTV.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.valeriotor.BTV.animations.AnimationTemplate.Transformation;
import com.valeriotor.BTV.animations.Transformator.OperatorWithStart;
import com.valeriotor.BTV.entities.models.ModelAnimated;

import net.minecraft.client.model.ModelRenderer;

public abstract class Transformator {
	
	public int length;
	public List<OperatorWithStart> ops = new ArrayList<>();
	
	public Transformator(List<OperatorWithStart> ops) {
		this.ops = ops;
	}
	
	public void apply(ModelRenderer bodyPart, double ticks) {
		for(OperatorWithStart entry : ops) {
			if(ticks > entry.op.end) continue;
			this.modify(bodyPart, ticks, entry.val, entry.op);
			break;
		}
	}
	
	@Override
	public String toString() {
		return ops.toString();
	}
	
	public abstract void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op);
	
	public static Transformator getTransformator(Transformation trans, List<IntervalDoubleBiOperator> list, ModelAnimated model, int index, int length) {
		if(list.size() < 1) return null;
		List<OperatorWithStart> newList = new ArrayList<>();
		double amount = 0;
		if(trans == Transformation.ROTX || trans == Transformation.ROTY || trans == Transformation.ROTZ) {
			amount = model.getDefaultAngles().get(model.getBodyParts().get(index)).get(trans);
		}
		IntervalDoubleBiOperator op, lastOp = null;
		for(int i = 0; i < list.size(); i++) {
			op  = list.get(i);
			if(i > 0 && op.start > lastOp.end+1) newList.add(new OperatorWithStart(new IntervalDoubleBiOperator((a,b) -> b, lastOp.end+1, op.start-1, 0), trans != Transformation.VISI ? amount : lastOp.amount));
			newList.add(new OperatorWithStart(op, amount));
			amount += op.amount;
			lastOp = op;
		}
		if(lastOp.end < length) {
			newList.add(new OperatorWithStart(new IntervalDoubleBiOperator((a,b) -> b, lastOp.end+1, length, 0), amount));
		}
		switch(trans) {
		case ROTX: return new RotatorX(newList);
		case ROTY: return new RotatorY(newList);
		case ROTZ: return new RotatorZ(newList);
		case TRAX: return new TraslatorX(newList);
		case TRAY: return new TraslatorY(newList);
		case TRAZ: return new TraslatorZ(newList);
		case VISI: return new Hider(newList);
		
		}
		return null;
	}
	
	public static class RotatorX extends Transformator{
		public RotatorX(List<OperatorWithStart> ops) {super(ops);}
		
		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleX = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class RotatorY extends Transformator{
		public RotatorY(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleY = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class RotatorZ extends Transformator{
		public RotatorZ(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.rotateAngleZ = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorX extends Transformator{
		public TraslatorX(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetX = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorY extends Transformator{
		public TraslatorY(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetY = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class TraslatorZ extends Transformator{
		public TraslatorZ(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.offsetZ = (float) op.applyAsDouble(ticks, startAmount);
		}
		
	}
	
	public static class Hider extends Transformator{
		public Hider(List<OperatorWithStart> ops) {super(ops);}

		@Override
		public void modify(ModelRenderer bodyPart, double ticks, double startAmount, IntervalDoubleBiOperator op) {
			bodyPart.isHidden = op.applyAsDouble(ticks, startAmount) < 0;
		}
		
	}
	
	public static class OperatorWithStart {
		public final IntervalDoubleBiOperator op;
		public final double val;
		public OperatorWithStart(IntervalDoubleBiOperator op, double val) {
			this.op = op;
			this.val = val;
		}
	}
	
}
