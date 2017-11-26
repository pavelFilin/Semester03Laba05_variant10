package ru.feeleen.schoolSocialNetwork.PL.FXUI;

import javafx.scene.control.Alert;

public class AlertManager {
    public static void printBasicWarringAlert(String s){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING Dialog");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
