package client.scenes;


import java.util.ArrayList;

import java.util.List;


import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Event;

import commons.Expense;
import commons.Participant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

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
    private Participant selectedExpensePayer;
    private Expense selectedExpense;
    private List<Participant> participants = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private List<String> payers = new ArrayList<>();

    @FXML
    private ListView participantsListView;
    @FXML
    private ComboBox expensePayersComboBox;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Button editTitleButton;
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
    private Button editExpenseButton;
    @FXML
    private Button backButton;
    @FXML
    private Button settleDebtsButton;
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
        else en();

        eventTitleLabel.setText(event.getTitle());
        participantsListView.setOnMouseClicked(this::handleParticipantsListViewClick);

        initParticipantsListView(event);
        initExpensePayersComboBox();
        initExpensesScrollPane(event);

    }

    private void initExpensesScrollPane(Event event) {
        expenses = server.getAllExpensesFromEvent(event.getId());
        System.out.println("Expenses: " + expenses);
        ObservableList<Expense> observableExpenseList = FXCollections.observableArrayList(expenses);
        if(!observableExpenseList.isEmpty()) expensesScrollPane.setContent(new ListView(observableExpenseList));
        if(expensesScrollPane != null) expensesScrollPane.setOnMouseClicked(this::handleExpensesListViewClick);
        //expensesScrollPane.setFitToHeight(true);
        //expensesScrollPane.setFitToWidth(true);
    }

    private void initExpensePayersComboBox() {
        payers.add("All");
        for(Participant p : participants) {
            payers.add(p.getName());
        }
        ObservableList<String> observablePayerList = FXCollections.observableArrayList(payers);
        expensePayersComboBox.setItems(observablePayerList);
        expensePayersComboBox.getSelectionModel().selectFirst();
    }

    private void initParticipantsListView(Event event) {
        participants = server.getAllParticipantsFromEvent(event.getId());
        System.out.println("Participants: " + participants);
        ObservableList<Participant> observableParticipantList = FXCollections.observableArrayList(participants);
        participantsListView.setItems(observableParticipantList);
        participantsListView.refresh();
    }

    private void handleExpensesListViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedExpense = (Expense) ((ListView) expensesScrollPane.getContent()).getSelectionModel().getSelectedItem();
        }
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

    public void editTitle() {
        mainCtrl.showEditEventTitle(event, en);
    }

    public void editExpense() {
        if(selectedExpense != null) {
            mainCtrl.showEditExpense(selectedExpense.getEvent(), selectedExpense, en);
        }
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

    public void en() {
        participantsLabel.setText("Participants");
        participantAddButton.setText("Add");
        participantEditButton.setText("Edit Selected");
        expensesLabel.setText("Expenses");
        addExpenseButton.setText("Add");
        settleDebtsButton.setText("Settle Debts");
        sendInvitesButton.setText("Send Invites");
        backButton.setText("Back");
        editExpenseButton.setText("Edit Selected");
        editTitleButton.setText("Edit Title");
    }
    public void nl() {
        participantsLabel.setText("Deelnemers");
        participantAddButton.setText("Toevoegen");
        participantEditButton.setText("Bewerk Geselecteerde");
        expensesLabel.setText("Uitgaven");
        addExpenseButton.setText("Toevoegen");
        settleDebtsButton.setText("Schulden Vereffenen");
        sendInvitesButton.setText("Uitnodigingen Versturen");
        backButton.setText("Terug");
        editExpenseButton.setText("Bewerk Geselecteerde");
        editTitleButton.setText("Titel Bewerken");
    }
}