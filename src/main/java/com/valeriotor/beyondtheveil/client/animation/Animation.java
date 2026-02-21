package com.valeriotor.beyondtheveil.client.animation;

import com.valeriotor.beyondtheveil.client.animation.Transformer.OperatorWithStartAmount;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Animation {

    private final AnimationTemplate template;
    private OperatorWithStartAmount[] currentOperators;
    private List<Iterator<OperatorWithStartAmount>> iterators;
    private boolean dynamicStart = false;
    private boolean started = false;
    private int ticks = 0;

    public Animation(AnimationTemplate template) {
        this.template = template;
        currentOperators = new OperatorWithStartAmount[template.transformers.size()];
        iterators = new ArrayList<>();

        for (int i = 0; i < template.transformers.size(); i++) {
            Transformer t = template.transformers.get(i);
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
                currentOperators[i] = new OperatorWithStartAmount(t.getDynamicStartOperator(), t.getType().amountFromPart.apply(t.getPart()));
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
                Iterator<OperatorWithStartAmount> iter = iterators.get(i);
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
