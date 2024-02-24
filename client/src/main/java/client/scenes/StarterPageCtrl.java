package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.fxml.FXML;
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

    private String eventName;
    private List<Event> eventList;

    public StarterPageCtrl(ServerUtils server) {
        this.server = server;
        this.eventList = new ArrayList<>();
    }

    public StarterPageCtrl(ServerUtils server, List<Event> list) {
        this.server = server;
        this.eventList = list;
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

    public void createNewEvent(ServerUtils server) {
        eventName = createNewEvent.getText();
        Event newEvent = new Event(eventName);
        eventList.add(newEvent);
    }

    public void joinEvent(ServerUtils server) {
        eventName = createNewEvent.getText();
        Event newEvent = new Event(eventName);
        eventList.add(newEvent);
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getCode()) {
            case ENTER:
                if(createNewEvent.getText() == null || createNewEvent.getText().equals(""))
                    joinEvent(server);
                else
                    createNewEvent(server);
                break;
            default:
                break;
        }
    }
}
