package org.calma.TP4.services;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import org.calma.TP4.SoundPlayer;
import org.calma.TP4.animators.ButtonsAnimator;
import org.calma.TP4.repositories.IButtonRepository;
import org.calma.TP4.repositories.ButtonRepository;
import org.calma.TP4.views.ButtonsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimonService {
    private IButtonRepository availableButtons;
    private IButtonRepository buttonsSequence;
    private IButtonRepository ButtonPlacement;

    private ButtonsView view;

    private List<ChangeListener<Boolean>> listeners;

    private SoundPlayer soundPlayer;
    private HashMap<Button, Integer> notes;
    private int instrument;

    private BooleanProperty partieActive;

    private int sequenceIndex;
    private int winLimit;

    public SimonService(IButtonRepository availableButtons, SoundPlayer soundPlayer, ButtonsView view) {
        this.availableButtons = availableButtons;
        this.soundPlayer = soundPlayer;
        this.view = view;
        this.listeners = new ArrayList<>();
        this.buttonsSequence = new ButtonRepository();
        this.partieActive = new SimpleBooleanProperty();
        this.partieActive.setValue(false);

        this.ButtonPlacement = new ButtonRepository();
        ButtonPlacement();
    }

    public void start(HashMap<Button, Integer> notes, int instrument, int winLimit) {
        this.notes = notes;
        this.instrument = instrument;
        this.sequenceIndex = 0;
        this.partieActive.setValue(true);
        this.winLimit = winLimit;

        disableButtons();
        addNextInSequence();
        addListeners();
        playSequence();
    }

    public void stop() {
        clearListeners();
        enableButtons();
        this.buttonsSequence = new ButtonRepository();
        partieActive.setValue(false);
    }

    private void win() {
        for (Button button : availableButtons.getButtonList()) {
            soundPlayer.note_on(notes.get(button));
            wait(200);
            soundPlayer.note_off(notes.get(button));
            wait(100);
        }

        for (Button button : availableButtons.getButtonList()) {
            soundPlayer.note_on(notes.get(button));
            wait(50);
        }

        wait(200);

        for (Button button : availableButtons.getButtonList()) {
            soundPlayer.note_off(notes.get(button));
        }
        stop();
    }

    private void lose() {
        for (int i = availableButtons.size() - 1; i >= 0; i--) {
            soundPlayer.note_on(notes.get(availableButtons.get(i)));
            wait(i == 0 ? 200 : 50);
            soundPlayer.note_off(notes.get(availableButtons.get(i)));
            wait(50);
        }
        stop();
    }

    private boolean checkIfCorrect(Button button) {
        return button.equals(buttonsSequence.get(sequenceIndex));
    }

    private void addNextInSequence() {
        buttonsSequence.add(availableButtons.getRandom());
    }

    private void onClickAction(int note, Button button, boolean isPressed, boolean wasPressed) {
        if (isPressed) {
            soundPlayer.note_on(note, instrument);
            ButtonsAnimator.playOnClickAnimation(button);
        } else if (wasPressed) {
            soundPlayer.note_off(note, instrument);
            var transition = ButtonsAnimator.playOnReleaseAnimation(button);
            disableButtons();
            if (checkIfCorrect(button)) {
                if (sequenceIndex + 1 == buttonsSequence.size() && buttonsSequence.size() < winLimit) {
                    transition.statusProperty().addListener(
                            (observableValue, oldStatus, newStatus) -> {
                                if (newStatus == Animation.Status.STOPPED) {
                                    wait(1000);
                                    addNextInSequence();
                                    playSequence();
                                    sequenceIndex = 0;
                                }
                            }
                    );
                }
                else if (buttonsSequence.size() >= winLimit &&
                        sequenceIndex + 1 == buttonsSequence.size()) {
                    transition.statusProperty().addListener(
                            (observableValue, oldStatus, newStatus) -> {
                                if (newStatus == Animation.Status.STOPPED) {
                                    wait(1000);
                                    win();
                                }
                            }
                    );
                }
                else {
                    sequenceIndex++;
                    enableButtons();
                }
            }
            else {
                lose();
            }
        }
    }

    private void clearListeners() {
        for (int i = 0; i < availableButtons.size(); i++) {
            availableButtons.get(i).pressedProperty().removeListener(listeners.get(i));
        }
        listeners.clear();
    }

    private void disableButtons() {
        for (var button : availableButtons.getButtonList()) {
            button.disableProperty().setValue(true);
            button.setOpacity(1);
        }
    }

    private void enableButtons() {
        for (var button : availableButtons.getButtonList()) {
            button.disableProperty().setValue(false);
        }
    }

    private void addListeners() {
        for (int i = 0; i < availableButtons.size(); i++) {
            Button activeButton = availableButtons.get(i);

            ChangeListener<Boolean> listener = (observableValue, wasPressed, pressed) ->
                onClickAction(notes.get(activeButton), activeButton, pressed, wasPressed);

            listeners.add(listener);

            activeButton.pressedProperty().addListener(listener);
        }
    }

    private void playSequence() {
        playSequence(0);
    }

    private void playSequence(int startIndex) {
        Button buttonActif = buttonsSequence.get(startIndex);

        Transition transition = ButtonsAnimator.getFullOnClickAnimation(buttonActif);

        transition.statusProperty().addListener(
                (observableValue, oldStatus, newStatus) -> {
                    if (newStatus == Animation.Status.STOPPED) {
                        soundPlayer.note_off(notes.get(buttonActif), instrument);
                        if (startIndex + 1 < buttonsSequence.size()) {
                            wait(200);
                            playSequence(startIndex+1);
                        }
                        else {
                            enableButtons();
                        }
                    }
                }
        );
        soundPlayer.note_on(notes.get(buttonActif), instrument);
        transition.play();
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ButtonPlacement() {
        ButtonPlacement.addAll(
                this.view.getButtonTop(),
                this.view.getButtonRight(),
                this.view.getButtonBottom(),
                this.view.getButtonLeft(),
                this.view.getButtonCenter()
        );
    }

    public BooleanProperty partieActiveProperty() {
        return partieActive;
    }

}