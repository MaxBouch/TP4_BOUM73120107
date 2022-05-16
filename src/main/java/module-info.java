module com.example.jeudesimon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.calma.TP4 to javafx.fxml;
    exports org.calma.TP4;
}