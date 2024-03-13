package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.awt.*;

public class ChangeServerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField serverField;
    @FXML
    private Label title;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    @Inject
    public ChangeServerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void save(){
        String savedServer = this.serverField.getText();
        String current = server.getServer();

        try {
            server.changeServer(savedServer);
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Server does not exist or is offline. Please try another one/n/n" + e.getMessage());
            alert.showAndWait();
            server.changeServer("localhost:8080");
            return;
        }
        mainCtrl.getStarterPageCtrl().getServerLabel().setText(savedServer);
        this.serverField.setText("");
        mainCtrl.showStarterPage();
    }
    public void cancel(){
        this.serverField.setText("");
        mainCtrl.showStarterPage();
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                save();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
    public void language(boolean EN){
        if(EN) en();
        else nl();
    }
    public void en(){
        save.setLabel("Save");
        cancel.setLabel("Cancel");
        title.setText("Change Server");
    }
    public void nl(){
        save.setLabel("Redden");
        cancel.setLabel("Annuleren");
        title.setText("Server Wijzigen");
    }
}
