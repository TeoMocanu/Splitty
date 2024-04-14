package client.scenes;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import client.utils.LanguageUtils;
import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;

import commons.Expense;
import commons.Participant;

import jakarta.ws.rs.WebApplicationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class EventOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


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
    private ImageView flagView;
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

    @FXML
    private Button editTitleButton;
    @FXML
    private Button sendInvitesButton;
    @FXML
    private Label participantsLabel;
    @FXML
    private Button participantEditButton;
    @FXML
    private Button participantAddButton;
    @FXML
    private Label expensesLabel;
    @FXML
    private Label filerLabel;
    @FXML
    private Button editExpenseButton;
    @FXML
    private Button addExpenseButton;
    @FXML
    private Button languageButton;
    @FXML
    private Button backButton;
    @FXML
    private Button settleDebtsButton;
    @FXML
    private Button deleteParticipantButton;
    @FXML
    private Button deleteExpenseButton;
    @FXML
    private List<ContextMenu> contextMenuList;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.contextMenuList = new ArrayList<>();
    }

    public void initialize(Event event) {
        this.event = server.getEvent(event.getId());

        eventTitleLabel.setText(event.getTitle());

        rebindUI();

        participantsListView.setOnMouseClicked(this::handleParticipantsListViewClick);
        expensesTableView.setOnMouseClicked(this::handleExpensesTableViewClick);
        expensePayersComboBox.setOnAction(e -> handleExpensePayersComboBoxAction());
        filteringModeComboBox.setOnAction(e -> handleFilteringModeComboBoxAction());

        initExpensesTableView(event);
        initParticipantsListView(event);
        initExpensePayersComboBox();
        initFilteringModeComboBox();

        server.registerForUpdates(e -> {
            initParticipantsListView(event);
        });
    }

    private void rebindUI() {
        LanguageUtils.update(editTitleButton, "editTitle");
        LanguageUtils.update(sendInvitesButton, "sendInvites");
        LanguageUtils.update(participantsLabel, "participants");
        LanguageUtils.update(participantEditButton, "editSelected");
        LanguageUtils.update(participantAddButton, "add");
        LanguageUtils.update(expensesLabel, "expenses");
        LanguageUtils.update(filerLabel, "filter");
        LanguageUtils.update(editExpenseButton, "editSelected");
        LanguageUtils.update(addExpenseButton, "add");
        LanguageUtils.update(languageButton, "LG");
        LanguageUtils.update(backButton, "back");
        LanguageUtils.update(settleDebtsButton, "settleDebts");
        LanguageUtils.update(deleteParticipantButton, "deleteSelected");
        LanguageUtils.update(deleteExpenseButton, "deleteSelected");


        LanguageUtils.update(titleColumn, "title");
        LanguageUtils.update(payerColumn, "payer");
        LanguageUtils.update(amountColumn, "amountEUR");
        LanguageUtils.update(dateColumn, "date");

        LanguageUtils.update(flagView);
        mainCtrl.setPrimaryStageTitle(mainCtrl.getString("createEditEvent"));
    }

    private void initExpensesTableView(Event event) {
        expensesTableView.setPlaceholder(new Label(mainCtrl.getString("noExpensesYet")));

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
        Participant all = new Participant(mainCtrl.getString("all"), new Event("All"));
        selectedExpensePayer = all;
        payers = new ArrayList<>();
        payers.add(all);
        participants = server.getAllParticipantsFromEvent(event.getId());
        for (Participant p : participants) {
            payers.add(p);
        }
        ObservableList<Participant> observablePayerList = FXCollections.observableArrayList(payers);
        expensePayersComboBox.setItems(observablePayerList);
        expensePayersComboBox.getSelectionModel().selectFirst();
    }

    private void initFilteringModeComboBox() {
        ObservableList<String> observableSelectionModeList =
                FXCollections.observableArrayList(mainCtrl.getString("paidBy"), mainCtrl.getString("owedBy"));
        filteringModeComboBox.setItems(observableSelectionModeList);
        filteringModeComboBox.getSelectionModel().selectFirst();
    }

    private void initParticipantsListView(Event event) {
        participants = server.getAllParticipantsFromEvent(event.getId());
        ObservableList<Participant> observableParticipantList = FXCollections.observableArrayList(participants);
        participantsListView.setItems(observableParticipantList);
        participantsListView.refresh();
    }

    private boolean showConfirmationDialogExpenses(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(mainCtrl.getString("confirmation"));
        alert.initOwner(expensesTableView.getScene().getWindow());
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    private void handleExpensesTableViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedExpense = (Expense) expensesTableView.getSelectionModel().getSelectedItem();
        }
        if (mouseEvent.getButton() == MouseButton.SECONDARY) { // Right-click
            selectedExpense = (Expense) expensesTableView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                // Create ContextMenu
                if (!contextMenuList.isEmpty()) {
                    contextMenuList.get(0).hide();
                    contextMenuList.remove(0);
                }
                ContextMenu contextMenu = new ContextMenu();

                MenuItem deleteMenuItem = new MenuItem(mainCtrl.getString("delete"));
                deleteMenuItem.setOnAction(e -> {
                    if (showConfirmationDialogExpenses(mainCtrl.getString("confirmationMessageDelete"))) {
                        deleteExpense();
                    }
                });
                contextMenu.getItems().add(deleteMenuItem);
                contextMenuList.add(contextMenu);

                // Display ContextMenu
                contextMenu.show(expensesTableView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        }
    }

    private boolean showConfirmationDialogParticipants(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(mainCtrl.getString("confirmation"));
        alert.initOwner(participantsListView.getScene().getWindow());
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    private void handleParticipantsListViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Left-click
            selectedParticipant = (Participant) participantsListView.getSelectionModel().getSelectedItem();
        }
        if (mouseEvent.getButton() == MouseButton.SECONDARY) { // Right-click
            selectedParticipant = (Participant) participantsListView.getSelectionModel().getSelectedItem();
            if (selectedParticipant != null) {
                // Create ContextMenu
                if (!contextMenuList.isEmpty()) {
                    contextMenuList.get(0).hide();
                    contextMenuList.remove(0);
                }
                ContextMenu contextMenu = new ContextMenu();

                MenuItem deleteMenuItem = new MenuItem(mainCtrl.getString("delete"));
                deleteMenuItem.setOnAction(e -> {
                    if (showConfirmationDialogParticipants(mainCtrl.getString("confirmationMessageDelete"))) {
                        deleteParticipant();
                    }
                });
                contextMenu.getItems().add(deleteMenuItem);
                contextMenuList.add(contextMenu);

                // Display ContextMenu
                contextMenu.show(participantsListView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        }
    }

    private void handleFilteringModeComboBoxAction() {
        selectedFilteringMode = filteringModeComboBox.getSelectionModel().getSelectedIndex();
        filterExpenses();
    }

    private void handleExpensePayersComboBoxAction() {
        selectedExpensePayer = (Participant) expensePayersComboBox.getSelectionModel().getSelectedItem();
        filterExpenses();
    }

    private void filterExpenses() {
        if (selectedExpensePayer.getName().equals(mainCtrl.getString("all"))) {
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
        mainCtrl.showContactDetailsAdd(event);
    }

    public void editParticipant() {
        if (selectedParticipant != null) {
            Participant participant = server.getParticipantById(event, selectedParticipant.getId());
            mainCtrl.showContactDetailsEdit(participant);
        }
    }

    public void deleteParticipant() {
        if (selectedParticipant != null) {
            if(!selectedParticipant.getExpensesPaidBy().isEmpty() || !selectedParticipant.getExpensesToPay().isEmpty()) {
                throw new WebApplicationException(mainCtrl.getString("invalidInput"));
            }
            else if(showConfirmationDialogParticipants(mainCtrl.getString("confirmationMessageDelete"))){
                List<Debt> participantDebts = server.getDebtsByParticipant(selectedParticipant);
                if(participantDebts != null && !participantDebts.isEmpty()) {
                    for (Debt d : participantDebts) {
                        server.deleteDebt(d);
                    }
                }
                server.deleteParticipant(selectedParticipant);
                initParticipantsListView(event);
                initExpensePayersComboBox();
            }
        }
    }

    public void addExpense() {
        mainCtrl.showAddExpense(event);
    }

    public void settleDebts() {
        mainCtrl.showOpenDebts(event);
    }

    public void sendInvites() {
        mainCtrl.showInvitation(event);
    }

    public void back() {
        mainCtrl.showStarterPage();
    }

    public void editTitle() {
        mainCtrl.showEditTitle(event);
    }

    public void editExpense() {
        if(selectedExpense != null) {
            mainCtrl.showEditExpense(event, selectedExpense);
        }
    }

    public void deleteExpense() {
        if (selectedExpense != null) {
            server.deleteExpense(selectedExpense);
            initExpensesTableView(event);
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

    public void languageSwitch() {
        mainCtrl.changeLanguage();
        rebindUI();
        initExpensesTableView(event);
        initParticipantsListView(event);
        initExpensePayersComboBox();
        initFilteringModeComboBox();
    }
}