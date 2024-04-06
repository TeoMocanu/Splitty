package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.emails.Invitation;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class InvitationCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    @FXML
    private TextArea emails;
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

    public void initialize(Event event) {
        this.event = event;
        code.setText(Long.toString(event.getId()));
        title.setText(event.getTitle());
    }

    public void cancel() {
        clearFields();
        mainCtrl.showEventOverview(event);
    }

    public void send() {
        List<String> emails;
        try {
            emails = getEmails();
        } catch (IllegalArgumentException e) {
            ErrorMessage.showError(e.getMessage(), mainCtrl);
            return;
        }
        for (String email : emails) {
            try {
                server.sendInvitation(new Invitation(email, event.getTitle(), event.getId()));
            } catch (WebApplicationException e) {
                ErrorMessage.showError(e.getMessage(), mainCtrl);
            }
        }
        if(en.equals("en")){
            InfoMessage.showInfo("Invitations sent", en);
        } else {
            InfoMessage.showInfo("Uitnodigingen verstuurd", en);
        }
        clearFields();
        mainCtrl.showEventOverview(event);
    }

    public List<String> getEmails() throws IllegalArgumentException {
        String unformattedMails = emails.getText();
        String[] lines = unformattedMails.split("\n|\r\n");
        List<String> mails = new ArrayList<>();
        String errors = "";
        for (String line : lines) {
            if (line.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9]{2,}")) {
                mails.add(line);
            } else {
                errors += mainCtrl.getString("invalidEmail") + " " + line + "\n";
//                errors += en.equals("en") ? ("Invalid mail: " + line + "\n") : ("Ongeldige mail: " + line + "\n");
            }
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors);
        }
        return mails;
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
/*
    public void language() {
        if (en.equals("en")) en();
        else if(en.equals("nl")) nl();
    }

    public void en() {
        text1.setText("Give people the following invite code: ");
        text2.setText("Invite the following people by email (one address per line)");
        sendInvites.setText("send invites");
        cancelButton.setText("cancel");
    }

    public void nl() {
        text1.setText("Geef mensen de volgende uitnodigingscode: ");
        text2.setText("Nodig de volgende mensen uit per e-mail (\u00e9\u00e9n adres per regel)");
        sendInvites.setText("stuur uitnodigingen");
        cancelButton.setText("annuleren");
    }

 */
}