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

import commons.Quote;
import jakarta.ws.rs.WebApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class OpenDebtsCtrl {

    //private final ServerUtils server;
    //private final MainCtrl mainCtrl;

    ObservableList<String> types = FXCollections.observableArrayList("food", "venue", "transport", "activities", "other");
    ObservableList<String> currencies = FXCollections.observableArrayList("EUR", "USD");

    @FXML
    private TextField desc;


    @FXML
    private TableView<Quote> table;
    @FXML
    private Button cancelButton;
    @FXML
    private Button languageButton;
    @FXML
    private Button sendReminder;
    @FXML
    private Button markReceived;
    @FXML
    private Label gives;
    @FXML
    private Label to;
/*
    @Inject
    public AddExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
*/
    @FXML
    private void initialize(){
        makeEn();
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

    private String getExpense() {
        var p = desc.getText();
        //var q = quote.getText();
        return p;
    }

    private void clearFields() {
        desc.clear();
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

    public void language(boolean en){
        if(en) makeEn();
        else makeNl();
    }

    public void makeEn(){
        cancelButton.setText("Cancel");
        markReceived.setText("mark received");
        sendReminder.setText("send reminder");
        gives.setText("gives");
        to.setText("to");
    }
    public void makeNl(){
        cancelButton.setText("Annuleren");
        markReceived.setText("merk ontvangen");
        sendReminder.setText("herinnering sturen");
        gives.setText("geeft");
        to.setText("naar");
    }
}