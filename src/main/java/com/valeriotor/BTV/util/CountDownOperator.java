package com.valeriotor.BTV.util;

import java.util.function.DoubleUnaryOperator;

public class CountDownOperator {
	private final int startTicks;
	private int ticks;
	private DoubleUnaryOperator op;
	
	private CountDownOperator(int ticks, DoubleUnaryOperator op) {
		this.startTicks = ticks;
		this.ticks = ticks;
		this.op = op;
	}
	
	public static CountDownOperator getOperator(double currentValue, double desiredValue, final int time) {
		return new CountDownOperator(time,  a -> (a/time)*(desiredValue - currentValue) + currentValue);
	}
	
	public float apply(double partialTicks) {
		return (float) this.op.applyAsDouble(startTicks - ticks + partialTicks);
	}
	
	public void update() {
		this.ticks--;
	}
	
	public boolean isDone() {
		return this.ticks <= 0;
	}
	
}
