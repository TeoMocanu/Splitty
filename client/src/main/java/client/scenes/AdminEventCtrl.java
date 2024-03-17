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
import commons.Event;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
public class AdminEventCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;

    @FXML
    private TextField title;

    @FXML
    private TextField date;

    @FXML
    private TextField lastActivity;

    @FXML
    private TextField delete;
    @Inject
    public AdminEventCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void cancel() {
        clearFields();
        //mainCtrl.showOverview();
    }
    public void add() {
        try {
            //server.addExpense(getExpense());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        //mainCtrl.showOverview();
    }

    private void clearFields() {
        title.clear();
        date.clear();
        delete.clear();
        lastActivity.clear();
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                add();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
}
