package com.valeriotor.beyondtheveil.client.animation;

import com.valeriotor.beyondtheveil.client.animation.Transformer.OperatorWithStartAmount;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Animation {

    private final AnimationTemplate template;
    private OperatorWithStartAmount[] currentOperators;
    private List<Iterator<OperatorWithStartAmount>> iterators;
    private int ticks = 0;

    public Animation(AnimationTemplate template) {
        this.template = template;
        currentOperators = new OperatorWithStartAmount[template.transformers.size()];
        iterators = new ArrayList<>();

        for (int i = 0; i < template.transformers.size(); i++) {
            Transformer t = template.transformers.get(i);
            iterators.add(t.newIterator());
            currentOperators[i] = iterators.get(i).next();
        }
    }

    public boolean isDone() {
        return ticks >= template.getLength();
    }

    public void update() {
        ticks++;
    }

    public void apply(float partialTicks) {
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
