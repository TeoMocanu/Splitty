package client.scenes;

import javafx.scene.control.Alert;

public class InfoMessage {
    public static void showInfo(String message, MainCtrl mainCtrl) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(mainCtrl.getString("information"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
