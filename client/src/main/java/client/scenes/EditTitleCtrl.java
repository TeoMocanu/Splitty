package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class EditTitleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;

    private boolean en;

    @FXML
    private TextField titleField;
    @FXML
    private Label title;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Inject
    public EditTitleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void initialize(Event event, boolean en){
        this.en = en;
        this.event = event;
        titleField.setText(event.getTitle());
        language(en);
    }

    public void save(){
        String newTitle = this.titleField.getText();
        event.setTitle(newTitle);
        server.editEvent(event);
        mainCtrl.showEventOverview(event, en);
    }

    public void cancel(){
        this.titleField.setText("");
        mainCtrl.showEventOverview(event, en);
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
    public void language(boolean en){
        if(en) en();
        else nl();
    }
    public void en(){
        saveButton.setText("Save");
        cancelButton.setText("Cancel");
        title.setText("Edit Title");
    }
    public void nl(){
        saveButton.setText("Redden");
        cancelButton.setText("Annuleren");
        title.setText("Titel Bewerken");
    }
}
