package org.calma.TP4.repositories;

import javafx.scene.control.Button;

import java.util.List;

public interface IButtonRepository {
    void add(Button button);
    void addAll(Button... buttons);
    Button get(int i);
    Button getRandom();
    List<Button> getButtonList();
    int size();
}
