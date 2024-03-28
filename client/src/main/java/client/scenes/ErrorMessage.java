package client.scenes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessage {
    public static void showError(String message, String en) {
        Alert alert = new Alert(AlertType.ERROR);
        if(en.equals("en")){
            alert.setTitle("Error");
        } else if(en.equals("nl")) {
            alert.setTitle("Fout");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}