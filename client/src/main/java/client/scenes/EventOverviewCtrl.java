package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Expense;
import commons.Participant;
import commons.Quote;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EventOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Quote> data;

    @FXML
    private ListView<Participant> participants;

    @FXML
    private ComboBox<Participant> eventPayers;

    @FXML
    private TableView<Quote> table;
    @FXML
    private TableColumn<Expense, String> expense;
//    @FXML
//    private TableColumn<Quote, String> colLastName;
//    @FXML
//    private TableColumn<Quote, String> colQuote;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        participants.setCellFactory(q -> new SimpleStringProperty( q.getName()));
        eventPayers.setCellFactory(q -> new SimpleStringProperty( q.getValue().getName()));

        expense.setCellValueFactory(q -> new SimpleStringProperty( q.getValue().getPayer()));
//        colLastName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().person.lastName));
//        colQuote.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().quote));
    }

    public void addQuote() {
        mainCtrl.showAdd();
    }

    public void refresh() {
        var quotes = server.getQuotes();
        data = FXCollections.observableList(quotes);
        table.setItems(data);
        participants.setItems(FXCollections.observableList(server.getParticipantsOfEvent()));
    }
}
