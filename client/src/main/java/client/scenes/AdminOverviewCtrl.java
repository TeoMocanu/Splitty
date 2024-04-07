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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;

public class AdminOverviewCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TableView<TableRowData> tableView;

    @FXML
    public Button serverInfoButton;

    @FXML
    public ChoiceBox<String> sortChoiceBox;

    ObservableList<String> sortChoiceBoxProperties
            = FXCollections.observableArrayList("ID", "Title");

    @FXML
    public Button importButtonText;
    @FXML
    public Button importButtonFile;
    @FXML
    public Button downloadButton;
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
        if (sortChoiceBox.getValue().equals("ID")) {
            sortById();
        } else {
            sortByTitle();
        }
    }

    private void sortById() {
        tableView.getSortOrder().clear();
        tableView.getSortOrder().add(tableView.getColumns().get(0));
        tableView.sort();
    }

    private void sortByTitle() {
        tableView.getSortOrder().clear();
        tableView.getSortOrder().add(tableView.getColumns().get(1));
        tableView.sort();
    }
    public Event getSelectedEvent() {
        TableRowData selectedRow = tableView.getSelectionModel().getSelectedItem();
        return server.getEvent(selectedRow.getId());
    }
    public void tableInitialize()  {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem getJSON = new MenuItem("Copy JSON to Clipboard");
        MenuItem downloadJSON = new MenuItem("Download JSON");
        contextMenu.getItems().add(deleteItem);
        contextMenu.getItems().add(getJSON);
        contextMenu.getItems().add(downloadJSON);
        // Setting up the action for the 'Delete' menu item (placeholder for now)
        deleteItem.setOnAction((event) -> {
            Event eventToDelete = getSelectedEvent();
            server.deleteEvent(eventToDelete);
            renderTable();
        });
        getJSON.setOnAction((event) -> {
            Event eventToGet = getSelectedEvent();
            String string = null;
            try {
                string = eventToGet.toJSONString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(string);
            clipboard.setContent(content);

            renderTable();
        });
        downloadJSON.setOnAction((event) -> {
            Event eventToDownload = getSelectedEvent();
            String string = null;
            try {
                string = eventToDownload.toJSONString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save JSON File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File file = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());
            if (file != null) {
                try {
                    Files.write(file.toPath(), string.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            renderTable();
        } );
        // Attach the context menu to each row in the table
        tableView.setRowFactory(tv -> {
            TableRow<TableRowData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && (!row.isEmpty())) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });
    }

    public void renderTable() {
        ObservableList<TableRowData> data = FXCollections.observableArrayList();
        List<Event> allEvents = server.getAllEvents();
        for (Event event : allEvents) {
            data.add(new TableRowData(new SimpleLongProperty(event.getId()),
                    new SimpleStringProperty(event.getTitle()),
                    new SimpleListProperty<>(FXCollections.observableArrayList(event.getParticipants())),
                    new SimpleListProperty<>(FXCollections.observableArrayList(event.getExpenses()))));
        }
        tableView.setItems(data);
        tableInitialize();
    }

    public void initialize() {
        sortChoiceBox.setItems(sortChoiceBoxProperties);
        renderTable();
    }

    public void languageSwitch() {
        mainCtrl.changeLanguage();
        mainCtrl.showAdminOverview();
        initialize();
    }
/*
    public void language() {
        if (currentLanguage.equals("en")) en();
        else if(currentLanguage.equals("nl")) nl();
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
*/
    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    public void cancel() {
        clearFields();
        mainCtrl.showStarterPage();
    }

    public void ok() {
        try {

        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showStarterPage();
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
        } catch (Exception e) {
            System.out.println("Failed to fetch Server Health: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void importFromText() {
        try {
            TextArea textArea = new TextArea();
            textArea.setPromptText("Enter JSON here");
            textArea.setWrapText(true);
            textArea.setPrefSize(400, 200);
            Button okButton = new Button("OK");


            VBox popupLayout = new VBox();
            popupLayout.setAlignment(javafx.geometry.Pos.CENTER);
            popupLayout.setSpacing(10);
            popupLayout.setPrefSize(400, 300);
            popupLayout.getChildren().addAll(textArea, okButton);

            Popup popup = new Popup();

            popup.getContent().add(popupLayout);
            popup.show(importButtonText.getScene().getWindow());
            okButton.setOnAction(e -> {
                try {
                    String textContent = textArea.getText();

                    List<String> eventsJSON = List.of(textContent.split("\n"));
                    for(String singleEventJSON :eventsJSON) {
                        Event newEvent = server.createEvent(singleEventJSON);
                        server.addEvent(newEvent);
                    }
                    renderTable();
                    popup.hide();
                } catch (Exception ex) {
                    System.out.println("Failed to import data: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            // Popup closes when clicked outside of it
            importButtonText.getScene().setOnMouseClicked( e -> {
                if (!popupLayout.getBoundsInParent().contains(e.getX(), e.getY())) {
                    popup.hide();
                }
            });
            renderTable();
        } catch (Exception e) {
            System.out.println("Failed to import data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void importFromFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Open JSON File");
            File file = fileChooser.showOpenDialog(importButtonFile.getScene().getWindow());
            if (file != null) {
                // Read the file's content. Assuming the content is a JSON string you want to import.
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

                List<String> eventsJSON = List.of(content.split("\n"));
                for(String singleEventJSON :eventsJSON) {
                    Event newEvent = server.createEvent(singleEventJSON);
                    server.addEvent(newEvent);
                }
                renderTable();
            }
            renderTable();
        } catch (Exception e) {
            System.out.println("Failed to import data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void downloadAllEvents() {
        try {
            String version = com.fasterxml.jackson.databind.cfg.PackageVersion.VERSION.toString();
            System.out.println("Jackson version: " + version);

            List<Event> allEvents = server.getAllEvents();
            StringBuilder allEventsJSON = new StringBuilder();
            for(Event event : allEvents) {
                String json = event.toJSONString();
                allEventsJSON.append(json).append("\n");
            }

            String allJSON = allEventsJSON.toString();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save JSON File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File file = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());
            if (file != null) {
                Files.write(file.toPath(), allJSON.getBytes());
            }
        } catch (Exception e) {
            System.out.println("Failed to download data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exitAdminOverview() {
        mainCtrl.showStarterPage();
    }
}
