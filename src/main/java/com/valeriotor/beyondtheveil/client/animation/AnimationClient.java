package com.valeriotor.beyondtheveil.client.animation;

import com.valeriotor.beyondtheveil.animation.Animation;
import com.valeriotor.beyondtheveil.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationClient implements Animation {

    public static Animation newAnimation(AnimationTemplate template) {
        return new AnimationClient(template);
    }
    private final AnimationTemplateClient template;
    private Transformer.OperatorWithStartAmount[] currentOperators;
    private List<Iterator<Transformer.OperatorWithStartAmount>> iterators;
    private boolean dynamicStart = false;
    private boolean started = false;
    private int ticks = 0;

    public AnimationClient(AnimationTemplate template) {
        this.template = (AnimationTemplateClient) template;
        currentOperators = new Transformer.OperatorWithStartAmount[this.template.transformers.size()];
        iterators = new ArrayList<>();

        for (int i = 0; i < this.template.transformers.size(); i++) {
            Transformer t = this.template.transformers.get(i);
            iterators.add(t.newIterator());
            if (t.getDynamicStartOperator() == null) {
                currentOperators[i] = iterators.get(i).next();
            } else {
                dynamicStart = true;
            }
        }
    }

    public boolean matchesModel(AnimatedModel<?> model) {
        return template.matchesModel(model);
    }

    private boolean dynamicStart() {
        boolean flag = false;
        for (int i = 0; i < template.transformers.size(); i++) {
            Transformer t = template.transformers.get(i);
            if (t.getDynamicStartOperator() != null) {
                currentOperators[i] = new Transformer.OperatorWithStartAmount(t.getDynamicStartOperator(), t.getType().amountFromPart.apply(t.getPart()));
                flag = true;
            }
        }
        return flag;
    }

    public AnimationTemplate getTemplate() {
        return template;
    }

    public boolean isDone() {
        return ticks >= template.getLength();
    }

    public void update() {
        ticks++;
    }

    public void apply(float partialTicks) {
        template.markDirty();
        if (dynamicStart && !started) {
            dynamicStart();
        }
        started = true;
        for (int i = 0; i < currentOperators.length; i++) {
            if (currentOperators[i] == null) {
                continue;
            }
            while (ticks + partialTicks > currentOperators[i].operator().period.end()) {
                Iterator<Transformer.OperatorWithStartAmount> iter = iterators.get(i);
                if (iter.hasNext()) {
                    currentOperators[i] = iter.next();
                } else {
                    currentOperators[i] = null;
                    break;
                }
            }
            if (currentOperators[i] != null) {
                template.transformers.get(i).modify(ticks+partialTicks, currentOperators[i]);
            }
        }
    }
}