package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import com.google.inject.Inject;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class OpenDebtsNewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private boolean en;

    @FXML
    private VBox vBox;

    @FXML
    private Label openDebts;

    @FXML
    private HBox hbox;

    @FXML
    private Label sentence;

    @FXML
    private CheckBox checkMail;

    @FXML
    private CheckBox checkMark;

    @FXML
    private Label email;

    @FXML
    private Button markReceived;

    @FXML
    private TitledPane bankInformation;

    @FXML
    private VBox vBox1;

    @FXML
    private Label info1;

    @FXML
    private Label accountHolder;

    @FXML
    private Label iban;

    @FXML
    private Label bic;

    @FXML
    private Label emailConfig;

    @FXML
    private Button sendReminder;


    @Inject
    public OpenDebtsNewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void initialize(Event event, boolean en){
        this.event = event;
        this.en = en;
    }

    public void keyPressed(KeyEvent e) {
        /*
        switch (e.getCode()) {
            case ENTER:
                add();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
        */
    }

    public void language(boolean en){
        if(en) en();
        else nl();
    }

    public void en() {

    }

    public void nl() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
