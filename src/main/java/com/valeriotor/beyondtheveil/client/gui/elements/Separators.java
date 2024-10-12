package com.valeriotor.beyondtheveil.client.gui.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public class Separators {

    public static Separator paragraphSeparator(int width) {
        return new ParagraphSeparator(width);
    }

    public static Separator pageSeparator(int width) {
        return new PageSeparator(width);
    }

    public static class Separator extends Element {
        protected Separator(int width, int height) {
            super(width, height);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {

        }

        @Override
        public boolean insertOutOfBounds() {
            return true;
        }
    }


    private static class ParagraphSeparator extends Separator {
        private ParagraphSeparator(int width) {
            super(width, 17);
        }

    }

    private static class PageSeparator extends Separator {
        private PageSeparator(int width) {
            super(width, 99999);
        }

    }

}
