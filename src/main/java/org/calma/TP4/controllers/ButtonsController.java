package org.calma.TP4.controllers;

import javafx.beans.binding.Bindings;
import javafx.util.StringConverter;
import org.calma.TP4.services.SimonService;
import org.calma.TP4.views.ButtonsView;

public class ButtonsController {
    private ButtonsView view;
    private SimonService service;

    public ButtonsController(ButtonsView view, SimonService service) {
        this.view = view;
        this.service = service;

        Bindings.bindBidirectional(view.getButtonCenter().textProperty(), service.partieActiveProperty(), new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean aBoolean) {
                return !aBoolean ? "Nouvelle partie" : "";
            }

            @Override
            public Boolean fromString(String s) {
                return !s.equals("Nouvelle partie");
            }
        });
    }
}
