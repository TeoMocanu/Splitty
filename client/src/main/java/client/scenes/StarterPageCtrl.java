package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;


public class StarterPageCtrl {
    private final ServerUtils server; //to be implemented

    @FXML
    private TextField createNewEvent;

    @FXML
    private TextField joinEvent;

    public StarterPageCtrl(ServerUtils server) {
        this.server = server;
    }

    @Inject
    public void CreateNewEvent(ServerUtils server) {
        // to be implemented
    }

    public void joinEvent(ServerUtils server) {
        // to be implemented
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getCode()) {
            case ENTER:
                break;
            default:
                break;
        }
    }
}
