package org.calma.TP4;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.calma.TP4.animators.ButtonsAnimator;
import org.calma.TP4.controllers.ButtonsController;
import org.calma.TP4.repositories.IButtonRepository;
import org.calma.TP4.repositories.ButtonRepository;
import org.calma.TP4.services.SimonService;
import org.calma.TP4.views.ButtonsView;
import org.calma.TP4.views.MenuView;

import java.util.HashMap;

public class App extends Application{

    @Override
        public void start(Stage stage) {
            ButtonsView buttonsView = new ButtonsView();
            MenuView menuView = new MenuView();

            SoundPlayer soundPlayer = new SoundPlayer();

            IButtonRepository boutons = new ButtonRepository();

            int instrument = 55;
            int winLimit = 12;

            boutons.addAll(buttonsView.getButtonBottom(),
                    buttonsView.getButtonLeft(),
                    buttonsView.getButtonCenter(),
                    buttonsView.getButtonRight(),
                    buttonsView.getButtonTop()
            );

            HashMap<Button, Integer> notesLinked = linkNotes(boutons, 59, 61, 64, 66, 69);

            SimonService simonService = new SimonService(boutons, soundPlayer, buttonsView);

            ButtonsController controller = new ButtonsController(buttonsView, simonService);

            activerListenerRejouer(simonService,
                    buttonsView.getButtonCenter(),
                    notesLinked,
                    instrument,
                    winLimit
            );

            VBox mainContainer = new VBox();

            mainContainer.getChildren().addAll(menuView.getView(), buttonsView.getView());

            Scene scene = new Scene(mainContainer);
            stage.setScene(scene);
            stage.setMinWidth(715);
            stage.setMinHeight(510);
            stage.show();
        }

        private HashMap<Button, Integer> linkNotes(IButtonRepository buttons, Integer... notes) {
            HashMap<Button, Integer> notesLinked = new HashMap<>();

            for (int i = 0; i < buttons.size(); i++) {
                notesLinked.put(buttons.get(i), notes[i]);
            }
            return notesLinked;
        }

        private void activerListenerRejouer(SimonService service, Button buttonRejouer,
                                            HashMap<Button, Integer> notesLinked,
                                            int instrument,
                                            int winLimit) {
            ChangeListener<Boolean> listenerNewGame = (observableValue, wasPressed, pressed) -> {
                if (pressed) {
                    ButtonsAnimator.playOnClickAnimation(buttonRejouer);
                }
                else if (wasPressed) {
                    ButtonsAnimator.playOnReleaseAnimation(buttonRejouer).statusProperty().addListener(
                            (observableValue1, status, t1) -> {
                                if (t1 == Animation.Status.STOPPED) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    service.start(notesLinked, instrument, winLimit);
                                }
                            });
                }
            };

            buttonRejouer.pressedProperty().addListener(listenerNewGame);

            service.partieActiveProperty().addListener(
                    (observable, oldvalue, newvalue) -> {
                        if (newvalue) {
                            buttonRejouer.pressedProperty().removeListener(listenerNewGame);
                        }
                        else {
                            buttonRejouer.pressedProperty().addListener(listenerNewGame);
                        }
                    }
            );
        }

        public static void main(String[] args) {
            launch();
        }

}
