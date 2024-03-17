package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Event;

import commons.Participant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;


public class EventOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private boolean en;
    private Event event;

    @FXML
    private ListView participantsListView;
    @FXML
    private ComboBox eventPayersComboBox;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Button sendInvitesButton;
    @FXML
    private Label participantsLabel;
    @FXML
    private Button participantAddButton;
    @FXML
    private Button participantEditButton;
    @FXML
    private Label expensesLabel;
    @FXML
    private Button addExpenseButton;
    @FXML
    private Button backButton;
    @FXML
    private Button settleDebtsButton;



    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        participants.setCellFactory(q -> new SimpleStringProperty( q.getName()));
//        eventPayers.setCellFactory(q -> new SimpleStringProperty( q.getValue().getName()));
//
//        expense.setCellValueFactory(q -> new SimpleStringProperty( q.getValue().getPayer()));
//        colLastName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().person.lastName));
//        colQuote.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().quote));
        // participantsListView.setItems(ParticipantController.getParticipantsByEvent(event));
    }

    public void addParticipant() {
        mainCtrl.showContactDetailsAdd(event, en);
    }

    public void editParticipant() {
        mainCtrl.showContactDetailsEdit(new Participant(), en);
    }

    public void addExpense() {
        mainCtrl.showAddExpense(event, en);
    }

    public void settleDebts() {
        mainCtrl.showOpenDebts(event, en);
    }

    public void back() {
        mainCtrl.showStarterPage();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ESCAPE:
                back();
                break;
            default:
                break;
        }
    }
    public void nl() {
        //TODO
    }
}