package com.valeriotor.beyondtheveil.client.animation;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.FloatBinaryOperatorWithInterval;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.Period;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.TransformationType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.TransformationType.*;

public abstract class Transformer {

    private final List<OperatorWithStartAmount> operators;
    private FloatBinaryOperatorWithInterval dynamicStartOperator;
    protected final ModelPart part;

    public Transformer(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
        this.operators = operators;
        this.dynamicStartOperator = dynamicStartOperator;
        this.part = part;
    }

    public Iterator<OperatorWithStartAmount> newIterator() {
        return operators.listIterator();
    }

    public abstract void modify(float animationTicks, OperatorWithStartAmount operator);

    public abstract TransformationType getType();

    public ModelPart getPart() {
        return part;
    }

    public FloatBinaryOperatorWithInterval getDynamicStartOperator() {
        return dynamicStartOperator;
    }

    public static Transformer createTransformer(ModelPart part, TransformationType type, List<FloatBinaryOperatorWithInterval> operators, int length) {
        if (operators.isEmpty()) {
            throw new IllegalArgumentException("Null list of animation operators");
        }
        FloatBinaryOperatorWithInterval dynamicStartOperator = null;
        if (operators.get(0).relativity == AnimationTemplate.Relativity.DYNAMIC) {
            dynamicStartOperator = operators.get(0);
        }
        List<OperatorWithStartAmount> transformerOperators = new ArrayList<>();
        float currentAmount = type.amountFromPart.apply(part);
        int currentTick = 0;
        for (FloatBinaryOperatorWithInterval operator : operators) {
            if (operator.period.start() > currentTick) {
                final float constant = currentAmount;
                transformerOperators.add(new OperatorWithStartAmount(new FloatBinaryOperatorWithInterval(AnimationTemplate.Relativity.RELATIVE, new Period(currentTick, operator.period.start()), 0, (a, b)->constant), currentAmount));
            }
            if (operator.relativity != AnimationTemplate.Relativity.DYNAMIC) {
                transformerOperators.add(new OperatorWithStartAmount(operator, currentAmount));
            }
            currentAmount = operator.relativity.isAbsolute ? operator.amount : operator.amount + currentAmount;
            currentTick = operator.period.end();
        }
        if (currentTick < length - 1) {
            final float constant = currentAmount;
            transformerOperators.add(new OperatorWithStartAmount(new FloatBinaryOperatorWithInterval(AnimationTemplate.Relativity.RELATIVE, new Period(currentTick, length), 0, (a, b)->constant), currentAmount));
        }
        return switch (type) {
            case ROTX -> new RotatorX(transformerOperators, dynamicStartOperator, part);
            case ROTY -> new RotatorY(transformerOperators, dynamicStartOperator, part);
            case ROTZ -> new RotatorZ(transformerOperators, dynamicStartOperator, part);
            case TRAX -> new TranslatorX(transformerOperators, dynamicStartOperator, part);
            case TRAY -> new TranslatorY(transformerOperators, dynamicStartOperator, part);
            case TRAZ -> new TranslatorZ(transformerOperators, dynamicStartOperator, part);
            case RESX -> new RescalerX(transformerOperators, dynamicStartOperator, part);
            case RESY -> new RescalerY(transformerOperators, dynamicStartOperator, part);
            case RESZ -> new RescalerZ(transformerOperators, dynamicStartOperator, part);
            case VISI -> new Hider(transformerOperators, dynamicStartOperator, part);
        };

    }

    private static class RotatorX extends Transformer {

        public RotatorX(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.xRot = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return ROTX;
        }
    }

    private static class RotatorY extends Transformer {

        public RotatorY(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.yRot = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return ROTY;
        }
    }

    private static class RotatorZ extends Transformer {

        public RotatorZ(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.zRot = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return ROTZ;
        }
    }

    private static class TranslatorX extends Transformer {

        public TranslatorX(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.x = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return TRAX;
        }
    }

    private static class TranslatorY extends Transformer {

        public TranslatorY(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.y = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return TRAY;
        }
    }

    private static class TranslatorZ extends Transformer {

        public TranslatorZ(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.z = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return TRAZ;
        }
    }

    private static class RescalerX extends Transformer {

        public RescalerX(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.xScale = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return RESX;
        }
    }

    private static class RescalerY extends Transformer {

        public RescalerY(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.yScale = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return RESY;
        }
    }

    private static class RescalerZ extends Transformer {

        public RescalerZ(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.zScale = operator.operator.apply(animationTicks, operator.startAmount);
        }

        @Override
        public TransformationType getType() {
            return RESZ;
        }
    }

    private static class Hider extends Transformer {

        public Hider(List<OperatorWithStartAmount> operators, FloatBinaryOperatorWithInterval dynamicStartOperator, ModelPart part) {
            super(operators, dynamicStartOperator, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            float apply = operator.operator.apply(animationTicks, operator.startAmount);
            part.visible = apply >= 0;
            int x = 0;
        }

        @Override
        public TransformationType getType() {
            return VISI;
        }
    }

    record OperatorWithStartAmount(FloatBinaryOperatorWithInterval operator, float startAmount) {

    }

}
