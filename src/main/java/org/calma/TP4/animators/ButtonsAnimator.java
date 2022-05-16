package org.calma.TP4.animators;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonsAnimator {

    public static Transition playOnClickAnimation(Button button) {
        ScaleTransition scaleTransition = createScaleTransition(button, 0.2);
        Transition rotateTransitions = createRotationTransition(button);

        ParallelTransition onClickTransitions = new ParallelTransition(scaleTransition, rotateTransitions);

        onClickTransitions.statusProperty().addListener(
                (observableValue, oldStatus, newStatus) -> {
                    if (newStatus == Animation.Status.STOPPED) {
                        button.scaleXProperty().setValue(1.2);
                        button.scaleYProperty().setValue(1.2);
                        button.rotateProperty().setValue(0);
                    }
                }
        );
        onClickTransitions.play();
        return onClickTransitions;
    }

    public static Transition playOnReleaseAnimation(Button button) {
        ScaleTransition transition = createScaleTransition(button, -0.2);
        transition.statusProperty().addListener(
                (observableValue, oldStatus, newStatus) -> {
                    if (newStatus == Animation.Status.STOPPED) {
                        button.scaleXProperty().setValue(1);
                        button.scaleYProperty().setValue(1);
                    }
                }
        );
        transition.play();
        return transition;
    }

    public static Transition getFullOnClickAnimation(Button button) {
        ScaleTransition scaleTransition = createScaleTransition(button, 0.2);
        Transition rotateTransitions = createRotationTransition(button);
        ParallelTransition onClickTransitions = new ParallelTransition(scaleTransition, rotateTransitions);
        SequentialTransition fullAnimation = new SequentialTransition(onClickTransitions, createScaleTransition(button, -0.2));

        fullAnimation.statusProperty().addListener(
                (observableValue, oldStatus, newStatus) -> {
                    if (newStatus == Animation.Status.STOPPED) {
                        button.scaleXProperty().setValue(1);
                        button.scaleYProperty().setValue(1);
                        button.rotateProperty().setValue(0);
                    }
                }
        );

        fullAnimation.play();
        return fullAnimation;
    }

    private static ScaleTransition createScaleTransition(Button button, double amount) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setByX(amount);
        scaleTransition.setByY(amount);
        return scaleTransition;
    }

    private static Transition createRotationTransition(Button button) {
        RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(50), button);
        rotateTransition1.setByAngle(2.5);
        RotateTransition rotateTransition2 = new RotateTransition(Duration.millis(50), button);
        rotateTransition2.setByAngle(-2.5);
        return new SequentialTransition(rotateTransition1, rotateTransition2);
    }
}
