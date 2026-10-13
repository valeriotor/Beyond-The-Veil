package com.valeriotor.beyondtheveil.animation;

public interface Animation {

    AnimationTemplate getTemplate();

    boolean isDone();

    void update();

    void apply(float partialTicks);

}
