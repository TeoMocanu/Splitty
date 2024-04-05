package client.scenes;

import javafx.scene.control.Alert;

public class InfoMessage {
    public static void showInfo(String message, String en) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(en.equals("en")){
            alert.setTitle("Information");
        } else if(en.equals("nl")) {
            alert.setTitle("Informatie");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
