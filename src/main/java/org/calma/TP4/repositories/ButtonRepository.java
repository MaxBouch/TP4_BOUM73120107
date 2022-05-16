package org.calma.TP4.repositories;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonRepository implements IButtonRepository{
    List<Button> data;

    public ButtonRepository() {
        data = new ArrayList<>();
    }

    @Override
    public void add(Button button) {
        data.add(button);
    }

    @Override
    public void addAll(Button... buttons) {
        data.addAll(Arrays.asList(buttons));
    }

    @Override
    public Button get(int i) {
        return data.get(i);
    }

    @Override
    public Button getRandom() {
        return data.get((int)(Math.random() * data.size()));
    }

    @Override
    public List<Button> getButtonList() {
        return data;
    }

    @Override
    public int size() {
        return data.size();
    }
}
