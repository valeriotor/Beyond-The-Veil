package com.valeriotor.beyondtheveil.client.animation;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.FloatBinaryOperatorWithInterval;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.Period;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate.TransformationType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Transformer {

    private final List<OperatorWithStartAmount> operators;
    protected final ModelPart part;

    public Transformer(List<OperatorWithStartAmount> operators, ModelPart part) {
        this.operators = operators;
        this.part = part;
    }

    public Iterator<OperatorWithStartAmount> newIterator() {
        return operators.listIterator();
    }

    public abstract void modify(float animationTicks, OperatorWithStartAmount operator);

    public static Transformer createTransformer(ModelPart part, TransformationType type, List<FloatBinaryOperatorWithInterval> operators, int length) {
        if (operators.isEmpty()) {
            throw new IllegalArgumentException("Null list of animation operators");
        }
        List<OperatorWithStartAmount> transformerOperators = new ArrayList<>();
        PartPose defaultValues = part.storePose();
        float currentAmount = switch (type) {
            case ROTX -> defaultValues.xRot;
            case ROTY -> defaultValues.yRot;
            case ROTZ -> defaultValues.zRot;
            case TRAX -> defaultValues.x;
            case TRAY -> defaultValues.y;
            case TRAZ -> defaultValues.z;
            case RESX -> part.xScale;
            case RESY -> part.yScale;
            case RESZ -> part.zScale;
            case VISI -> part.visible ? 1 : -1;
        };
        int currentTick = 0;
        for (FloatBinaryOperatorWithInterval operator : operators) {
            if (operator.period.start() > currentTick) {
                final float constant = currentAmount;
                transformerOperators.add(new OperatorWithStartAmount(new FloatBinaryOperatorWithInterval(false, new Period(currentTick, operator.period.start()), 0, (a,b)->constant), currentAmount));
            }
            transformerOperators.add(new OperatorWithStartAmount(operator, currentAmount));
            currentAmount = operator.absolute ? operator.amount : operator.amount + currentAmount;
            currentTick = operator.period.end();
        }
        if (currentTick < length - 1) {
            final float constant = currentAmount;
            transformerOperators.add(new OperatorWithStartAmount(new FloatBinaryOperatorWithInterval(false, new Period(currentTick, length), 0, (a,b)->constant), currentAmount));
        }
        return switch (type) {
            case ROTX -> new RotatorX(transformerOperators, part);
            case ROTY -> new RotatorY(transformerOperators, part);
            case ROTZ -> new RotatorZ(transformerOperators, part);
            case TRAX -> new TranslatorX(transformerOperators, part);
            case TRAY -> new TranslatorY(transformerOperators, part);
            case TRAZ -> new TranslatorZ(transformerOperators, part);
            case RESX -> new RescalerX(transformerOperators, part);
            case RESY -> new RescalerY(transformerOperators, part);
            case RESZ -> new RescalerZ(transformerOperators, part);
            case VISI -> new Hider(transformerOperators, part);
        };

    }

    private static class RotatorX extends Transformer {

        public RotatorX(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.xRot = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class RotatorY extends Transformer {

        public RotatorY(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.yRot = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class RotatorZ extends Transformer {

        public RotatorZ(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.zRot = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class TranslatorX extends Transformer {

        public TranslatorX(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.x = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class TranslatorY extends Transformer {

        public TranslatorY(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.y = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class TranslatorZ extends Transformer {

        public TranslatorZ(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.z = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class RescalerX extends Transformer {

        public RescalerX(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.xScale = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class RescalerY extends Transformer {

        public RescalerY(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.yScale = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class RescalerZ extends Transformer {

        public RescalerZ(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            part.zScale = operator.operator.apply(animationTicks, operator.startAmount);
        }
    }

    private static class Hider extends Transformer {

        public Hider(List<OperatorWithStartAmount> operators, ModelPart part) {
            super(operators, part);
        }

        @Override
        public void modify(float animationTicks, OperatorWithStartAmount operator) {
            float apply = operator.operator.apply(animationTicks, operator.startAmount);
            part.visible = apply >= 0;
            int x = 0;
        }
    }

    record OperatorWithStartAmount(FloatBinaryOperatorWithInterval operator, float startAmount) {

    }

}
