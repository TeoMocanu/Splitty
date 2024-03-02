package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;
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
        eventName = joinEvent.getText();
        Event newEvent = new Event(eventName);
        eventList.add(newEvent);
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
