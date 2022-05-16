package org.calma.TP4.views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ButtonsView {
    private BorderPane view;

    private Button buttonTop;
    private Button buttonRight;
    private Button buttonBottom;
    private Button buttonLeft;
    private Button buttonCenter;

    float sideButtonsSize;
    float topAndBottomButtonSize;

    public ButtonsView() {
        view = new BorderPane();

        buttonTop = new Button();
        buttonRight = new Button();
        buttonBottom = new Button();
        buttonLeft = new Button();
        buttonCenter = new Button();



        sideButtonsSize = 150;
        topAndBottomButtonSize = 75;

        var cornerRadius = new CornerRadii(15);

        buttonTop.backgroundProperty().setValue(new Background(new BackgroundFill(Color.BLUE, cornerRadius, Insets.EMPTY)));
        buttonRight.backgroundProperty().setValue(new Background(new BackgroundFill(Color.RED, cornerRadius, Insets.EMPTY)));
        buttonBottom.backgroundProperty().setValue(new Background(new BackgroundFill(Color.ORANGE, cornerRadius, Insets.EMPTY)));
        buttonLeft.backgroundProperty().setValue(new Background(new BackgroundFill(Color.YELLOW, cornerRadius, Insets.EMPTY)));
        buttonCenter.backgroundProperty().setValue(new Background(new BackgroundFill(Color.GREEN, cornerRadius, Insets.EMPTY)));

        buttonCenter.fontProperty().setValue(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 34));
        buttonCenter.textFillProperty().setValue(Color.WHITE);
        buttonCenter.textProperty().setValue("Nouvelle partie");

        setButtonTop(buttonTop);
        setButtonRight(buttonRight);
        setButtonBottom(buttonBottom);
        setButtonLeft(buttonLeft);
        setButtonCenter(buttonCenter);

        view.setMinHeight(480);
        view.setMinWidth(700);

        VBox.setVgrow(view, Priority.ALWAYS);
    }

    private Parent createContainer(Button button) {
        double margin = 2;
        AnchorPane container = new AnchorPane(button);
        AnchorPane.setTopAnchor(button, margin);
        AnchorPane.setRightAnchor(button, margin);
        AnchorPane.setBottomAnchor(button, margin);
        AnchorPane.setLeftAnchor(button, margin);
        return container;
    }


    public BorderPane getView() {
        return view;
    }

    public void setView(BorderPane view) {
        this.view = view;
    }

    public Button getButtonTop() {
        return buttonTop;
    }

    public void setButtonTop(Button buttonTop) {
        this.view.setTop(createContainer(buttonTop));
        buttonTop.setPrefHeight(topAndBottomButtonSize);
    }

    public Button getButtonRight() {
        return buttonRight;
    }

    public void setButtonRight(Button buttonRight) {
        this.view.setRight(createContainer(buttonRight));
        buttonRight.setPrefWidth(sideButtonsSize);
    }

    public Button getButtonBottom() {
        return buttonBottom;
    }

    public void setButtonBottom(Button buttonBottom) {
        this.view.setBottom(createContainer(buttonBottom));
        buttonBottom.setPrefHeight(topAndBottomButtonSize);
    }

    public Button getButtonLeft() {
        return buttonLeft;
    }

    public void setButtonLeft(Button buttonLeft) {
        this.view.setLeft(createContainer(buttonLeft));
        buttonLeft.setPrefWidth(sideButtonsSize);
    }

    public Button getButtonCenter() {
        return buttonCenter;
    }

    public void setButtonCenter(Button buttonCenter) {
        this.view.setCenter(createContainer(buttonCenter));
    }
}
