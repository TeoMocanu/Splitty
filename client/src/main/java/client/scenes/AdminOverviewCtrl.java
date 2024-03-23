package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.ws.rs.WebApplicationException;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.List;

public class AdminOverviewCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private String currentLanguage;

    @FXML
    private TableView<TableRowData> tableView;

    @FXML
    public Button serverInfoButton;

    @FXML
    public ChoiceBox<String> sortChoiceBox;

    ObservableList<String> sortChoiceBoxProperties
            = FXCollections.observableArrayList("ID", "Title");

    @FXML
    public Button languageButton;

    @FXML
    public Button sortButton;

    @FXML
    private Button backButton;

    public class TableRowData {
        private final SimpleLongProperty id;

        private final SimpleStringProperty title;
        private final SimpleListProperty<Participant> participants;
        private final SimpleListProperty<Expense> expenses;


        public TableRowData(SimpleLongProperty id, SimpleStringProperty title,
                            SimpleListProperty<Participant> participants,
                            SimpleListProperty<Expense> expenses) {
            this.id = id;
            this.title = title;
            this.participants = participants;
            this.expenses = expenses;
        }

        public long getId() {
            return id.get();
        }

        public SimpleLongProperty idProperty() {
            return id;
        }

        public void setId(long id) {
            this.id.set(id);
        }

        public String getTitle() {
            return title.get();
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public ObservableList<Participant> getParticipants() {
            return participants.get();
        }

        public SimpleListProperty<Participant> participantsProperty() {
            return participants;
        }

        public void setParticipants(ObservableList<Participant> participants) {
            this.participants.set(participants);
        }

        public ObservableList<Expense> getExpenses() {
            return expenses.get();
        }

        public SimpleListProperty<Expense> expensesProperty() {

            return expenses;
        }

        public void setExpenses(ObservableList<Expense> expenses) {
            this.expenses.set(expenses);
        }
    }

    public void applySort() {
        TableColumn<TableRowData, Number> idColumn = (TableColumn<TableRowData, Number>) tableView.getColumns().get(0); // Assuming the ID column is the first one
        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(idColumn);
        tableView.sort();
    }
    public void initialize(boolean en) {
        sortChoiceBox.setItems(sortChoiceBoxProperties);
        this.currentLanguage = en ? "EN" : "NL";
        language();
        ObservableList<TableRowData> data = FXCollections.observableArrayList();
        List<Event> allEvents = server.getAllEvents();
        System.out.println(allEvents);
        for (Event event : allEvents) {
            data.add(new TableRowData(new SimpleLongProperty(event.getId()),
                    new SimpleStringProperty(event.getTitle()),
                    new SimpleListProperty<>(FXCollections.observableArrayList(event.getParticipants())),
                    new SimpleListProperty<>(FXCollections.observableArrayList(event.getExpenses()))));
        }
        tableView.setItems(data);
    }

    public void languageSwitch() {
        if (currentLanguage.equals("EN")) {
            currentLanguage = "NL";
            nl();
        } else {
            currentLanguage = "EN";
            en();
        }
    }

    public void language() {
        if (currentLanguage.equals("EN")) {
            en();
        } else {
            nl();
        }
    }

    public void en() {
        languageButton.setText("EN");
        serverInfoButton.setText("Server Info");
        backButton.setText("EXIT");

    }

    public void nl() {
        languageButton.setText("NL");
        serverInfoButton.setText("Server Informatie");
        backButton.setText("AFSLUITEN");
    }

    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    public void cancel() {
        clearFields();
        mainCtrl.showStarterPage(currentLanguage.equals("EN"));
    }

    public void ok() {
        try {
            // TODO: Add admin functionality, like seeing server instances


        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showStarterPage(currentLanguage.equals("EN"));
    }

    private void clearFields() {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                exitAdminOverview();
                break;
            default:
                break;
        }
    }

    public void showServerInfo() {
        try {
            String serverInfo = server.fetchAllServerInfo();
            System.out.println("Server Health: " + serverInfo);
        } catch (Exception e) {
            System.out.println("Failed to fetch Server Health: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exitAdminOverview() {
        mainCtrl.showStarterPage(currentLanguage.equals("EN"));
    }
}
