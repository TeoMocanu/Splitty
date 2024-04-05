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

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Debt;
import commons.Event;
import commons.emails.Invitation;
import commons.emails.Reminder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.List;

public class OpenDebtsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private String en;
    private Event event;
    private List<Debt> currentDebts;
    @FXML
    private Label titleLabel;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private TreeView<HBox> table;
    @FXML
    private Button backButton;
    @FXML
    private Button settleDebtsButton;

    @Inject
    public OpenDebtsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void initialize(Event event, String en) {
        this.event = event;
        this.en = en;
        language();

        eventTitleLabel.setText(event.getTitle());
        currentDebts = server.getAllDebtsFromEvent(event);
        initializeDebtsTable(currentDebts);
    }

    private void initializeDebtsTable(List<Debt> debts) {
        table.setRoot(null);
        TreeItem<HBox> rootNode = new TreeItem<>(new HBox(new Label("Active Debts")));
        rootNode.setExpanded(true);

        if(debts != null && debts.size() > 0)
            for(Debt d : debts) {
                if(d.getAmount() == 0) continue; // do not display debts with no pending amount

                String text = d.getDebtor().getName() + " gives " + d.getAmount() + "\u20ac to "+ d.getCreditor().getName();
                if(en.equals("nl")) text = d.getDebtor().getName() + " geeft " + d.getAmount() + "\u20ac aan "+ d.getCreditor().getName();
                String received = "Mark Received";
                if(en.equals("nl")) received = "Merk Ontvangen";
                Label label = new Label(text);
                label.setStyle("-fx-font-weight: bold;");
                Button buttonR = new Button(received);
                buttonR.resize(15, 10); // adjust the size of the button
                buttonR.setOnAction(e -> { markReceived(d); });
                TreeItem<HBox> node = new TreeItem<>(new HBox(5.0, label, buttonR));
                node.setExpanded(false);

                if(d.getCreditor().getIban() != null && d.getCreditor().getBic() != null) {
                    text = "Bank information available, transfer money to:";
                    if(en.equals("nl")) text = "Bankgegevens beschikbaar, geld overmaken naar:";
                    node.getChildren().add(new TreeItem<>(new HBox(new Label(text))));

                    text = "Account holder: " + d.getCreditor().getName();
                    if(en.equals("nl")) text = "Rekeninghouder: " + d.getCreditor().getName();
                    node.getChildren().add(new TreeItem<>(new HBox(new Label(text))));

                    node.getChildren().add(new TreeItem<>(new HBox(new Label("IBAN: " + d.getCreditor().getIban()))));
                    node.getChildren().add(new TreeItem<>(new HBox(new Label("BIC: " + d.getCreditor().getBic()))));

                    text = "Send Reminder";
                    if(en.equals("nl")) text = "Herinnering verzenden";
                    Button button = new Button(text);
                    button.setOnAction(e -> { sendReminder(d); });
                    node.getChildren().add(new TreeItem<>(new HBox(button)));

                } else {
                    text = "Bank information not available, ask holder for more";
                    if(en.equals("nl")) text = "Bankgegevens niet beschikbaar, vraag de houder om meer";
                    node.getChildren().add(new TreeItem<>(new HBox(new Label(text))));
                    if(d.getCreditor().getEmail() != null) {
                        text = "Send Reminder";
                        if(en.equals("nl")) text = "Herinnering verzenden";
                        Button button = new Button(text);
                        button.setOnAction(e -> { sendReminder(d); });
                        node.getChildren().add(new TreeItem<>(new HBox(button)));
                    }
                }

                rootNode.getChildren().add(node);
            }

        table.setRoot(rootNode);
    }

    public void markReceived(Debt debt) {
        //TODO: add alert asking "are you sure you want to reset this debt amount?"
        debt.setAmount(0);
        server.editDebt(debt);
        currentDebts = server.getAllDebtsFromEvent(event);
        initializeDebtsTable(currentDebts);
    }

    public void sendReminder(Debt debt) {
        String email = debt.getDebtor().getEmail();
        Reminder reminder = new Reminder(email, event.getTitle(), debt.getAmount(), debt.getCreditor().getName());
        try{
            server.sendReminder(reminder);
        } catch (Exception e) {
            ErrorMessage.showError(e.getMessage(), en);
        }
        if(en.equals("en"))
            InfoMessage.showInfo("Reminder sent", en);
        else if(en.equals("nl"))
            InfoMessage.showInfo("Herinnering verzonden", en);
    }

    public void back() {
        clearFields();
        mainCtrl.showEventOverview(event, en);
    }

    public void settleAllDebts() {
        //TODO: also alert about resetting all the debts
        if(currentDebts != null && currentDebts.size() > 0)
            for(Debt d : currentDebts){
                d.setAmount(0);
                server.editDebt(d);
            }
        currentDebts = server.getAllDebtsFromEvent(event);
        initializeDebtsTable(currentDebts);
    }

    private void clearFields() {
        table.setRoot(new TreeItem<HBox>());
        eventTitleLabel.setText("");
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                //
                break;
            case ESCAPE:
                back();
                break;
            default:
                break;
        }
    }

    public void language() {
        if(en.equals("en")) en();
        else if(en.equals("nl")) nl();
    }

    public void en(){
        titleLabel.setText("Open Debts");
        backButton.setText("Back");
        settleDebtsButton.setText("Settle all debts");
    }
    public void nl(){
        titleLabel.setText("Schulden");
        backButton.setText("Rug");
        settleDebtsButton.setText("Regel alle schulden");
    }
}