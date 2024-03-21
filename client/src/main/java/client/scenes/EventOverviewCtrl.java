package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Event;

import commons.Expense;
import commons.Participant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;


public class EventOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private boolean en;
    private Event event;
    private List<Participant> participants = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

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
    @FXML
    private Button save;
    @FXML
    private ScrollPane expensesScrollPane;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Participant> observableParticipantList = FXCollections.observableArrayList(participants);
        if(this.event != null) {
            expenses = event.getExpenses();
            participants = event.getParticipants();
        }
        participantsListView.setItems(FXCollections.observableList(observableParticipantList));
        participantsListView.refresh();
        ObservableList<Expense> observableExpenseList = FXCollections.observableArrayList(expenses);
        if(!observableExpenseList.isEmpty()) expensesScrollPane.setContent(new ListView<>(observableExpenseList));
        //expensesScrollPane.setFitToHeight(true);
        //expensesScrollPane.setFitToWidth(true);
    }

    public void initializeEvent(Event event, boolean en){
        this.event = server.getEvent(event.getId()); // updates the event every instance of the window
        this.en = en;
        eventTitleLabel.setText(event.getTitle());
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

    public void sendInvites() {
        mainCtrl.showInvitation(event, en);
    }

    public void back() {
        mainCtrl.showStarterPage();
    }

    public void ok(){
        server.editEvent(event);
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
        participantsLabel.setText("Deelnemers");
        participantAddButton.setText("Toevoegen");
        participantEditButton.setText("Bewerken");
        expensesLabel.setText("Uitgaven");
        addExpenseButton.setText("Toevoegen");
        settleDebtsButton.setText("Schulden vereffenen");
        sendInvitesButton.setText("Uitnodigingen versturen");
        backButton.setText("Terug");
        save.setText("Opslaan");
    }
}