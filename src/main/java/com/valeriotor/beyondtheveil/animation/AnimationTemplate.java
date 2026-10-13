package com.valeriotor.beyondtheveil.animation;

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

public interface AnimationTemplate {


     int getId();

    String getName();

    int getLength();

    void markDirty();

    boolean matchesModel(AnimatedModel<?> model);

    class AnimationTemplateServer implements AnimationTemplate {
        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean matchesModel(AnimatedModel<?> model) {
            return false;
        }
    }


}
