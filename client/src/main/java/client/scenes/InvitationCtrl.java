package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class InvitationCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField emails;
    @FXML
    private Label string1;
    @FXML
    private Label string2;
    @FXML
    private Label string3;
    @FXML
    private Button sendInvites;
    @FXML
    private Button cancelButton;

    @Inject
    public InvitationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    //nimic
    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }

    public void ok() {
        try {
            server.addInvitation(getInvitation());
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

    private String getInvitation() {
        var p = emails.getText();
        //var q = quote.getText();
        return p;
    }

    private void clearFields() {
        //firstName.clear();
        //lastName.clear();
        //quote.clear();
        emails.clear();
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
    public void language(boolean en){
        if(en) makeEn();
        else makeNl();
    }
    public void makeEn(){
        string1.setText("New Year Party");
        string2.setText("Give people the following invite code");
        string3.setText("Invite the following people by email (one address per line)");
        sendInvites.setText("send invites");
        cancelButton.setText("cancel");
    }
    public void makeNl(){
        string1.setText("nieuwjaarsfeest");
        string2.setText("Geef mensen de volgende uitnodigingscode");
        string3.setText("Nodig de volgende mensen uit per e-mail (één adres per regel)");
        sendInvites.setText("stuur uitnodigingen");
        cancelButton.setText("annuleren");
    }
}