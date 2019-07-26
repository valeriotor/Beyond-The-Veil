package com.valeriotor.BTV.animations;

import java.util.function.DoubleBinaryOperator;

public class IntervalDoubleBiOperator {
	
	public final DoubleBinaryOperator op;
	public final int start;
	public final int end;
	public final double amount;
	
	public IntervalDoubleBiOperator(DoubleBinaryOperator op, int start, int end, double amount) {
		this.op = op;
		this.start = start;
		this.end = end;
		this.amount = amount;
	}
	
	public double applyAsDouble(double a, double b) {
		return op.applyAsDouble(a, b);
	}
	
	@Override
	public String toString() {
		return String.format("Operator: %d-%d, by %s", this.start, this.end, String.valueOf(this.amount));
	}
	
}
