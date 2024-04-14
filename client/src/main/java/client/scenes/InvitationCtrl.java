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
    private Button sendTestButton;
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

        if(!hasConfiguredEmail()){
            sendInvites.setDisable(true);
            sendTestButton.setDisable(true);
        }

    }

    private boolean hasConfiguredEmail() {
//        return false;
        return server.hasConfiguredEmail();
    }

    public void cancel() {
        clearFields();
        mainCtrl.showEventOverview(event);
    }

    public void sendTest() {
        try {
            server.sendTestMail(new commons.emails.MockEmail(event.getTitle()));
        } catch (WebApplicationException e) {
            ErrorMessage.showError(e.getMessage(), mainCtrl);
        }
        InfoMessage.showInfo(mainCtrl.getString("testMailSent"), mainCtrl);
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
        InfoMessage.showInfo(mainCtrl.getString("invitationsSent"), mainCtrl);
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
}