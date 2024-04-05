package client.scenes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessage {
    public static void showError(String message, String title) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}