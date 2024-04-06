package client.scenes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessage {
    public static void showError(String message, MainCtrl mainCtrl) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(mainCtrl.getString("error"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}