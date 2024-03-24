package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Debt;
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

    private String eventName;

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

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*participants.setCellFactory(q -> new SimpleStringProperty( q.getName()));
        eventPayers.setCellFactory(q -> new SimpleStringProperty( q.getValue().getName()));

        expense.setCellValueFactory(q -> new SimpleStringProperty( q.getValue().getPayer()));
        colLastName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().person.lastName));
        colQuote.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().quote));
         participantsListView.setItems(ParticipantController.getParticipantsByEvent(event));*/
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
        //TODO
    }

    public void test() {
        List<Debt> debt = server.getDebts(1);
        System.out.println(debt.get(0));
    }
    public void setSettleDebtsButton()
    {
        eventName = settleDebtsButton.getText();
        Event newEvent = new Event(eventName);


        Event repEvent = server.addEvent(newEvent);
        mainCtrl.showOpenDebts(repEvent, en);
    }

}