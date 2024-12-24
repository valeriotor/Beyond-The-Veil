package com.valeriotor.beyondtheveil.client.gui.elements;

import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropdownLists extends ScrollableList{

    public static DropdownLists makeList(int width, int height, List<? extends Element> rows, int rowHeight, int scrollbarWidth) {
        List<Tuple<Dropdown, List<Element>>> grouped = new ArrayList<>();
        for (Element row : rows) {
            if (row instanceof Dropdown dd) {
                grouped.add(new Tuple<>(dd, new ArrayList<>()));
            } else if(!grouped.isEmpty()){
                grouped.get(grouped.size() - 1).getB().add(row);
            }
        }
        return new DropdownLists(width, height, grouped, rows, rowHeight, scrollbarWidth);
    }

    private final List<Tuple<Dropdown, List<Element>>> grouped;
    private final boolean[] open;


    private DropdownLists(int width, int height, List<Tuple<Dropdown, List<Element>>> grouped, List<? extends Element> rows, int rowHeight, int scrollbarWidth) {
        //super(width, height, grouped.stream().map(Tuple::getA).toList(), rowHeight, scrollbarWidth);
        super(width, height, rows, rowHeight, scrollbarWidth);
        this.grouped = grouped;
        this.open = new boolean[grouped.size()];
        Arrays.fill(open, true);
    }

    @Override
    protected void clickElement(int element, double relativeMouseX, double relativeMouseY, int mouseButton) {
        super.clickElement(element, relativeMouseX, relativeMouseY, mouseButton);
        if (rows().get(element) instanceof Dropdown dd) {
            List<Element> elements = newList(element);
            dd.toggleOpen();
            changeElements(elements);
        }
    }

    private List<Element> newList(int element) {
        int dropdownIndex = 0;
        for (int i = 0; i < element; i++) {
            if (rows().get(i) instanceof Dropdown) {
                dropdownIndex++;
            }
        }
        open[dropdownIndex] = !open[dropdownIndex];
        List<Element> newList = new ArrayList<>();
        for (int i = 0; i < grouped.size(); i++) {
            Tuple<Dropdown, List<Element>> tuple = grouped.get(i);
            newList.add(tuple.getA());
            if (open[i]) {
                newList.addAll(tuple.getB());
            }
        }
        return newList;
    }

    public static abstract class Dropdown extends Element {

        private boolean open = true;

        protected Dropdown(int width, int height) {
            super(width, height);
        }

        private void toggleOpen() {
            open = !open;
        }

        protected boolean isOpen() {
            return open;
        }
    }


}
