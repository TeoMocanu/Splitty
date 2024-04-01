package client.scenes;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;



import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Event;

import commons.Expense;
import commons.Participant;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class EventOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String en;
    private Event event;
    private Participant selectedParticipant;
    private Participant selectedExpensePayer;
    private Expense selectedExpense;
    private int selectedFilteringMode = 0;
    private List<Participant> participants = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private List<Participant> payers = new ArrayList<>();

    private String eventName;

    @FXML
    private ListView participantsListView;
    @FXML
    private ComboBox expensePayersComboBox;
    @FXML
    private ComboBox filteringModeComboBox;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Button sendInvitesButton;
    @FXML
    private Button editTitleButton;
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
    private TableView expensesTableView;
    @FXML
    private TableColumn<Expense, String> titleColumn;
    @FXML
    private TableColumn<Expense, Float> amountColumn;
    @FXML
    private TableColumn<Expense, String> payerColumn;
    @FXML
    private TableColumn<Expense, LocalDate> dateColumn;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    public void initialize(Event event, String en) {
        this.event = event;
        this.en = en;
        language();

        eventTitleLabel.setText(event.getTitle());
        participantsListView.setOnMouseClicked(this::handleParticipantsListViewClick);
        expensesTableView.setOnMouseClicked(this::handleExpensesTableViewClick);
        expensePayersComboBox.setOnAction(e -> handleExpensePayersComboBoxAction());
        filteringModeComboBox.setOnAction(e -> handleFilteringModeComboBoxAction());

        initExpensesTableView(event);
        initParticipantsListView(event);
        initExpensePayersComboBox();
        initFilteringModeComboBox();


    }

    private void initExpensesTableView(Event event) {
        expenses = server.getAllExpensesFromEvent(event);
        ObservableList<Expense> observableExpenseList = FXCollections.observableArrayList(expenses);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        payerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPayer().getName()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("localDate"));

        expensesTableView.setItems(observableExpenseList);

        titleColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(2));
        amountColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(1));
        payerColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(1));
        dateColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(1));

        ScrollBar vScrollBar = (ScrollBar) expensesTableView.lookup(".scroll-bar:vertical");

        vScrollBar.visibleProperty().addListener((observable, wasVisible, isVisible) -> {
            if (isVisible) {
                // If the scrollbar is visible, divide table width by 3 and subtract scrollbar width
                titleColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(6));
                amountColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(4));
                payerColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(4));
                dateColumn.prefWidthProperty().bind(expensesTableView.widthProperty().divide(4).subtract(4));
            }
        });
    }

    private void initExpensePayersComboBox() {
        Participant all = new Participant("All", new Event("All"));
        selectedExpensePayer = all;
        payers = new ArrayList<>();
        payers.add(all);
        participants = server.getAllParticipantsFromEvent(event.getId());
        for(Participant p : participants) {
            payers.add(p);
        }
        ObservableList<Participant> observablePayerList = FXCollections.observableArrayList(payers);
        expensePayersComboBox.setItems(observablePayerList);
        expensePayersComboBox.getSelectionModel().selectFirst();
    }

    private void initFilteringModeComboBox() {
        ObservableList<String> observableSelectionModeList = FXCollections.observableArrayList("Paid by", "Owed by");
        filteringModeComboBox.setItems(observableSelectionModeList);
        filteringModeComboBox.getSelectionModel().selectFirst();
    }

    private void initParticipantsListView(Event event) {
        participants = server.getAllParticipantsFromEvent(event.getId());
        ObservableList<Participant> observableParticipantList = FXCollections.observableArrayList(participants);
        participantsListView.setItems(observableParticipantList);
        participantsListView.refresh();
    }

    private void handleExpensesTableViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedExpense = (Expense) expensesTableView.getSelectionModel().getSelectedItem();
        }
    }

    private void handleParticipantsListViewClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedParticipant = (Participant) participantsListView.getSelectionModel().getSelectedItem();
        }
    }
    private void handleFilteringModeComboBoxAction(){
        selectedFilteringMode = filteringModeComboBox.getSelectionModel().getSelectedIndex();
        filterExpenses();
    }
    private void handleExpensePayersComboBoxAction(){
        selectedExpensePayer = (Participant) expensePayersComboBox.getSelectionModel().getSelectedItem();
        filterExpenses();
    }

    private void filterExpenses() {
        if(selectedExpensePayer.getName().equals("All")) {
            initExpensesTableView(event);
        } else {
            if (selectedFilteringMode == 0) {
                expenses = server.getAllExpensesFromEventPaidBy(event.getId(), selectedExpensePayer.getId());
            }
            if (selectedFilteringMode == 1) {
                expenses = server.getAllExpensesFromEventOwedBy(event.getId(), selectedExpensePayer.getId());
            }
            expensesTableView.setItems(FXCollections.observableArrayList(expenses));
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
        server.editEvent(event);
        mainCtrl.showStarterPage(en);
    }

    public void editTitle() {
        mainCtrl.showEditTitle(event, en);
    }

    public void editExpense() {
        if(selectedExpense != null) {
            mainCtrl.showEditExpense(selectedExpense.getEvent(), selectedExpense, en);
        }
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

    public void language() {
        if(en.equals("en")) en();
        else if(en.equals("nl")) nl();
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