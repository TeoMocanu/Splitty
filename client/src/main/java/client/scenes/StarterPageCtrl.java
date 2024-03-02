package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import com.google.inject.Inject;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


public class StarterPageCtrl {
    private final ServerUtils server; //to be implemented

    @FXML
    private TextField createNewEvent;

    @FXML
    private TextField joinEvent;

    @FXML
    private ListView<Event> listView;

    private String eventName;
    private List<Event> eventList;

    @Inject
    public StarterPageCtrl(ServerUtils server) {
        this.server = server;
        this.eventList = new ArrayList<>();
        this.listView = new ListView<>();
    }

//    public StarterPageCtrl(ServerUtils server, List<Event> list) {
//        this.server = server;
//        this.eventList = list;
//    }

    @FXML
    public void initialize() {
        // Set mouse click event listener for the ListView
        listView.setOnMouseClicked(this::handleListViewClick);
    }

    private void handleListViewClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // Right-click
            Event selectedEvent = listView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                // Create ContextMenu
                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(e -> {
                    eventList.remove(selectedEvent);
                    listView.setItems(FXCollections.observableList(eventList));
                });
                contextMenu.getItems().add(deleteMenuItem);

                // Display ContextMenu
                contextMenu.show(listView, event.getScreenX(), event.getScreenY());
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
        eventList.add(newEvent);
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        listView.setItems(FXCollections.observableList(observableEventList));
        listView.refresh();
    }

    public void joinEvent() {
        try {
            long eventId = Long.parseLong(joinEvent.getText());
            Event event = server.getEvent(eventId);

            if (event != null) {
                eventList.add(event);
                ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
                listView.setItems(FXCollections.observableList(observableEventList));
                listView.refresh();
            }
        } catch (jakarta.ws.rs.BadRequestException e) {
            // Handle the HTTP 400 exception
            ErrorMessage.showError("No event with this invitation code was found.");
        }
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getCode()) {
            case ENTER:
                if(createNewEvent.getText() == null || createNewEvent.getText().equals(""))
                    joinEvent();
                else
                    createNewEvent();
                break;
            default:
                break;
        }
    }

    public void refresh() {
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
        //listView.setItems(observableEventList);
    }
}
