package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.Arrays;
import java.util.List;

public class InvitationCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private boolean en;

    @FXML
    private TextField emails;
    @FXML
    private Label title;
    @FXML
    private Label text1;
    @FXML
    private Label text2;
    @FXML
    private Label code;
    @FXML
    private Button sendInvites;
    @FXML
    private Button cancelButton;

    @Inject
    public InvitationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void initialize(Event event, boolean en) {
        this.event = event;
        this.en = en;
        language(en);
        code.setText(Long.toString(event.getId()));
    }

    //nimic
    public void cancel() {
        clearFields();
        mainCtrl.showEventOverview(event, en);
    }

    public void send() {
        try {
            server.sendInvitations(getEmails(), code.getText());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showEventOverview(event, en);
    }

    private List<String> getEmails() {
        String str = emails.getText();
        List<String> emails = Arrays.asList(str.split("\n"));
        return emails;
    }

    private void clearFields() {
        emails.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                send();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
    public void language(boolean en){
        if(en) en();
        else nl();
    }
    public void en(){
        title.setText("New Year Party");
        text1.setText("Give people the following invite code");
        text2.setText("Invite the following people by email (one address per line)");
        sendInvites.setText("send invites");
        cancelButton.setText("cancel");
    }
    public void nl(){
        title.setText("nieuwjaarsfeest");
        text1.setText("Geef mensen de volgende uitnodigingscode");
        text2.setText("Nodig de volgende mensen uit per e-mail (één adres per regel)");
        sendInvites.setText("stuur uitnodigingen");
        cancelButton.setText("annuleren");
    }
}