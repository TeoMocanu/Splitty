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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class EventOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private boolean en;
    private Event event;
    private Participant selectedParticipant;
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
    public void initialize(Event event, boolean en) {
        this.event = event;
        this.en = en;
        if(!en) nl();

        eventTitleLabel.setText(event.getTitle());
        participantsListView.setOnMouseClicked(this::handleParticipantsListViewClick);

        participants = server.getAllParticipantsFromEvent(event.getId());
        System.out.println("Participants: " + participants);
        ObservableList<Participant> observableParticipantList = FXCollections.observableArrayList(participants);
        participantsListView.setItems(observableParticipantList);
        participantsListView.refresh();

        expenses = server.getAllExpensesFromEvent(event.getId());
        System.out.println("Expenses: " + expenses);
        ObservableList<Expense> observableExpenseList = FXCollections.observableArrayList(expenses);
        if(!observableExpenseList.isEmpty()) expensesScrollPane.setContent(new ListView(observableExpenseList));
        //expensesScrollPane.setFitToHeight(true);
        //expensesScrollPane.setFitToWidth(true);
    }

    private void handleParticipantsListViewClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedParticipant = (Participant) participantsListView.getSelectionModel().getSelectedItem();
        }
    }

    public void addParticipant() {
        mainCtrl.showContactDetailsAdd(event, en);
    }

    public void editParticipant() {
        if(selectedParticipant != null) {
            mainCtrl.showContactDetailsEdit(selectedParticipant, en);
        }
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
        mainCtrl.showStarterPage(en);
    }

    public void ok(){
        server.editEvent(event);
        mainCtrl.showStarterPage(en);
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