package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.Map;

public class AdminOverviewCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }
    @FXML
    private void initialize(){

    }
    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }
    public void ok() {
        try {
            // TODO: Add admin functionality, like seeing server instances


        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }
    private void clearFields() {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    public void showServerInfo() {
        try {
            Map<String, Object> serverInfo = server.fetchServerInfo();
            System.out.println("Server Info: " + serverInfo);
        } catch (Exception e) {
            System.out.println("Failed to fetch server info: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
