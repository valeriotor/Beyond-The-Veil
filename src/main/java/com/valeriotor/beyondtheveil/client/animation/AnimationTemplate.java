package com.valeriotor.beyondtheveil.client.animation;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import it.unimi.dsi.fastutil.floats.FloatBinaryOperator;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class AnimationTemplate {
    private final String name;
    private final int id;
    private int length;
    private AnimatedModel<?> model;
    public List<Transformer> transformers;
    private static final boolean DEBUG = false;


    public AnimationTemplate(String name, boolean client, int id) {
        this.name = name;
        this.id = id;
        if(client)
            parseFile(name);
    }

    public int getId() {
        return id;
    }

    private void parseFile(String name) {
        try {
            List<String> lines = Resources.readLines(BeyondTheVeil.class.getResource(String.format("/assets/beyondtheveil/animations/%s.btvanim", name)), Charsets.UTF_8);

            Period period = null;
            Map<ModelPart, EnumMap<TransformationType, List<FloatBinaryOperatorWithInterval>>> modelsToTransformations = new HashMap<>();
            for (String line : lines) {
                period = processLine(line, period, modelsToTransformations);
                if (DEBUG && modelsToTransformations.containsKey(null)) {
                    System.out.println("ModelPart is null when processing line:");
                    System.out.println(line);
                }
            }

            List<Transformer> tempTransformers = new ArrayList<>();
            for (Map.Entry<ModelPart, EnumMap<TransformationType, List<FloatBinaryOperatorWithInterval>>> entry : modelsToTransformations.entrySet()) {
                for (Map.Entry<TransformationType, List<FloatBinaryOperatorWithInterval>> entry2 : entry.getValue().entrySet()) {
                    tempTransformers.add(Transformer.createTransformer(entry.getKey(), entry2.getKey(), entry2.getValue(), length));
                }
            }
            transformers = Collections.unmodifiableList(tempTransformers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLength() {
        return length;
    }

    private Period processLine(String line, Period period, Map<ModelPart, EnumMap<TransformationType, List<FloatBinaryOperatorWithInterval>>> modelsToTransformations) {
        if(line.isEmpty()) return period;
        String[] split = line.replaceAll("\\s+", "").split(":");
        switch (split[0]) {
            case "length":
                length = Integer.parseInt(split[1]);
                break;
            case "model":
                model = AnimatedModel.getModel(split[1]);
                break;
            case "start":
                return new Period(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            default:
                if(model == null) return period;
                ModelPart part = model.getPart(split[1]);
                TransformationType type = TransformationType.valueOf(split[0].toUpperCase());
                String func = split.length >= 4 ? split[3] : "linear"; // linear is default value, if line stops at third parameter or if field is left blank
                if (func.isEmpty()) func = "linear";
                Relativity relativity = split.length >= 5 ? Relativity.valueOf(split[4].toUpperCase()) : Relativity.RELATIVE;

                float amount = Float.parseFloat(split[2]);
                FloatBinaryOperator operator = getOperator(func, period.start, period.end, amount, relativity.isAbsolute);
                FloatBinaryOperatorWithInterval finalOperator = new FloatBinaryOperatorWithInterval(relativity, period, amount, operator);
                EnumMap<TransformationType, List<FloatBinaryOperatorWithInterval>> transformToOperators = modelsToTransformations.computeIfAbsent(part, k -> new EnumMap<>(TransformationType.class));
                List<FloatBinaryOperatorWithInterval> operatorList = transformToOperators.computeIfAbsent(type, k -> new ArrayList<>());
                operatorList.add(finalOperator);
                break;
        }
        return period;
    }

    private FloatBinaryOperator getOperator(String func, final int start, final int end, final float amount, boolean absolute) {
        if (absolute) {
            return switch (func) {
                case "quadratic" -> (a, b) -> (float) ((amount - b) * Math.pow((a - start) / (end - start), 2) + b);
                case "sqrt" -> (a, b) -> (float) ((amount - b) * Mth.sqrt((a - start) / (end - start)) + b); // is there something faster than square root with similar behaviour..?
                case "linear" -> (a, b) -> (amount - b) * (a - start) / (end - start) + b;
                case "immediate" -> (a, b) -> amount;
                default -> null;
            };
        } else {
            return switch (func) {
                case "quadratic" -> (a, b) -> (float) (amount * Math.pow((a - start) / (end - start), 2) + b);
                case "sqrt" -> (a, b) -> (float) (amount * Mth.sqrt((a - start) / (end - start)) + b);
                case "linear" -> (a, b) -> amount * (a - start) / (end - start) + b;
                case "immediate" -> (a, b) -> b + amount;
                default -> null;
            };
        }
    }

    public void markDirty() {
        model.markDirty();
    }

    public boolean matchesModel(AnimatedModel<?> model) {
        return model == this.model;
    }

    record Period(int start, int end) {
    }

    enum TransformationType {
        ROTX(part -> part.storePose().xRot),
        ROTY(part -> part.storePose().yRot),
        ROTZ(part -> part.storePose().zRot),
        TRAX(part -> part.storePose().x),
        TRAY(part -> part.storePose().y),
        TRAZ(part -> part.storePose().z),
        RESX(part -> part.xScale),
        RESY(part -> part.yScale),
        RESZ(part -> part.zScale),
        VISI(part -> part.visible ? 1F : -1F);

        public final Function<ModelPart, Float> amountFromPart;

        TransformationType(Function<ModelPart, Float> amountFromPart) {
            this.amountFromPart = amountFromPart;
        }
    }

    enum Relativity {
        RELATIVE(false),
        ABSOLUTE(true),
        DYNAMIC(true); // MUST have start 0 in btvanim file

        final boolean isAbsolute;

        Relativity(boolean isAbsolute) {
            this.isAbsolute = isAbsolute;
        }
    }

    static class FloatBinaryOperatorWithInterval {
        Relativity relativity;
        Period period;
        float amount;
        FloatBinaryOperator operator;

        public FloatBinaryOperatorWithInterval(Relativity relativity, Period period, float amount, FloatBinaryOperator operator) {
            this.relativity = relativity;
            this.period = period;
            this.amount = amount;
            this.operator = operator;
        }

        public float apply(float ticks, float startAmount) {
            return operator.apply(ticks, startAmount);
        }


    }


}
