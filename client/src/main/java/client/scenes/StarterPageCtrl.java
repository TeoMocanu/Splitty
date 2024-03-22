package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.google.inject.Inject;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


public class StarterPageCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField createNewEvent;

    @FXML
    private TextField joinEvent;

    @FXML
    private ListView<Event> listView;

    @FXML
    private Button languageButtonStart;

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    @FXML
    private Button deleteHistoryButton;

    @FXML
    private Label createNewEventLabel;

    @FXML
    private Label joinEventLabel;

    @FXML
    private Label recentlyViewedEventsLabel;
    @FXML
    private Label serverLabel;
    @FXML
    private Button changeServerButton;
    @FXML
    private List<ContextMenu> contextMenuList;
    private String eventName;
    private List<Event> eventList;

    private boolean en;
    @Inject
    public StarterPageCtrl(ServerUtils server,  MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.eventList = new ArrayList<>();
        this.listView= new ListView<>();
        this.contextMenuList = new ArrayList<>();
    }

//    public StarterPageCtrl(ServerUtils server, List<Event> list) {
//        this.server = server;
//        this.eventList = list;
//    }

    @FXML
    public void initialize() {
        // Set mouse click event listener for the ListView
        listView.setOnMouseClicked(this::handleListViewClick);
        en = true;
        languageButtonStart.setText("NL");
        en();
        this.serverLabel.setText(server.getServer());
    }


    private void handleListViewClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // Right-click
            Event selectedEvent = listView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                // Create ContextMenu
                if(!contextMenuList.isEmpty()) {
                    contextMenuList.get(0).hide();
                    contextMenuList.remove(0);
                }
                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(e -> {
                    eventList.remove(selectedEvent);
                    listView.setItems(FXCollections.observableList(eventList));
                });
                contextMenu.getItems().add(deleteMenuItem);
                contextMenuList.add(contextMenu);

                // Display ContextMenu
                contextMenu.show(listView, event.getScreenX(), event.getScreenY());
            }
        }
        if (event.getButton() == MouseButton.PRIMARY) { // Left-click
            Event selectedEvent = listView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                mainCtrl.showEventOverview(selectedEvent, en);
                listView.getSelectionModel().clearSelection();
            }
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> lastSearch() {
        List<Event> searches = new ArrayList<>();
        for(int i = eventList.size() - 1; i >= 0 && searches.size() < 4; i--)
            searches.add(eventList.get(i));
        return searches;
    }

    public boolean isHistoryEmpty() {
        return eventList.size() == 0;
    }

    public boolean historyContains(String name) {
        return eventList.contains(name);
    }

    public void createNewEvent() {
        eventName = createNewEvent.getText();
        Event newEvent = new Event(eventName);

        Event repEvent = server.addEvent(newEvent);

        eventList.add(repEvent);
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        listView.setItems(FXCollections.observableList(observableEventList));
        listView.refresh();

        mainCtrl.showEventOverview(repEvent, en);
    }

    public void joinEvent() {
        try {
            long eventId = Long.parseLong(joinEvent.getText());
            Event event = server.getEvent(eventId);

            if (event != null) {
                if(eventList.contains(event)) {
                    eventList.remove(event);
                }
                eventList.add(event);
                ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
                listView.setItems(FXCollections.observableList(observableEventList));
                listView.refresh();
            }
        } catch (jakarta.ws.rs.BadRequestException e) {
            // Handle the HTTP 400 exception
            if(en)
                ErrorMessage.showError("No event with this invitation code was found.");
            else
                ErrorMessage.showError("Er is geen evenement met deze uitnodigingscode gevonden.");
        } catch (java.lang.NumberFormatException e) {
            // Handle the number format exception
            if(en)
                ErrorMessage.showError("Invalid code.");
            else
                ErrorMessage.showError("Ongeldige code.");
        }
    }

    public void keyPressed(KeyEvent e) {
        TextField source = (TextField) e.getSource();
        if (e.getCode() == KeyCode.ENTER) {
            if (source == createNewEvent) {
                createNewEvent();
            } else if (source == joinEvent) {
                joinEvent();
            }
        }
    }

    public void language(){
        if(languageButtonStart.getText().equals("NL")){
            en = false;
            nl();
        }
        else{
            en = true;
            en();
        }
    }

    public void en(){
        languageButtonStart.setText("NL");
        createButton.setText("Create");
        joinButton.setText("Join");
        deleteHistoryButton.setText("Delete history");
        createNewEventLabel.setText("Create New Event");
        joinEventLabel.setText("Join Event");
        recentlyViewedEventsLabel.setText("Recently viewed events");
        changeServerButton.setText("Change Server");
    }
    public void nl(){
        languageButtonStart.setText("EN");
        createButton.setText("Creëren");
        joinButton.setText("Meedoen");
        deleteHistoryButton.setText("Verwijder geschiedenis");
        createNewEventLabel.setText("Nieuw evenement maken");
        joinEventLabel.setText("Doe mee aan evenement");
        recentlyViewedEventsLabel.setText("Recent bekeken evenementen");
        changeServerButton.setText("Server Wijzigen");
    }

    public void refresh() {
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        listView.setItems(observableEventList);
    }

    public void clear() {
        createNewEvent.clear();
        joinEvent.clear();
        eventList.clear();
        listView.getItems().clear();
    }

    public void deleteHistory() {
        eventList.clear();
        listView.getItems().clear();
    }

    public void admin(){
        mainCtrl.showAdminLogin();
    }

    public void changeServer(){ mainCtrl.showChangeServer(en); }

    public Label getServerLabel() { return serverLabel; }

}
