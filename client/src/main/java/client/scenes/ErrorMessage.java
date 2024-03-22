package client.scenes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessage {
    public static void showError(String message, boolean en) {
        Alert alert = new Alert(AlertType.ERROR);
        if(en){
            alert.setTitle("Error");
        } else{
            alert.setTitle("Fout");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}