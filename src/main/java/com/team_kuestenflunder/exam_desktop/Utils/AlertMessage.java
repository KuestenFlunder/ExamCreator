package com.team_kuestenflunder.exam_desktop.Utils;

import javafx.scene.control.Alert;

public class AlertMessage {
    public static Alert alertMessage(Alert.AlertType alertType, String titelText, String messageText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titelText);
        alert.setContentText(messageText);
        alert.showAndWait();
        return alert;
    }
}
