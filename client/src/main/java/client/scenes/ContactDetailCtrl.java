/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Participant;
import commons.Person;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class ContactDetailCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ibanField;

    @FXML
    private TextField bicField;

    private Participant participant

    @Inject
    public ContactDetailCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
//        this.participant = new Participant(null, null, null, null);
        this.participant = new Participant(null, null, null);
    }

    @Inject
    public ContactDetailCtrl(ServerUtils server, MainCtrl mainCtrl, Participant participant) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.participant = participant;
    }

    public void initFields(){
        this.nameField.setText(participant.getName());
        this.emailField.setText(participant.getEmail());
        this.ibanField.setText(participant.getIban());
//        this.bicField.setText(participant.getBic());
    }

    public void abort() {
        clearFields();
        mainCtrl.showOverview();
    }

    public void ok() {
        try {
            server.editDetails(getParticipant());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }

    private Participant getParticipant() {
        var p = new Person(firstName.getText(), lastName.getText());
        var q = quote.getText();
        return new Quote(p, q);
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        ibanField.clear();
        bicField.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                abort();
                break;
            default:
                break;
        }
    }
}