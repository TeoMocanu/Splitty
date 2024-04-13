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
import commons.Expense;
import commons.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private Expense expense = null;
    ObservableList<String> types = FXCollections.observableArrayList("Food", "Entrance fees", "Travel", "Activities", "Other");
    ObservableList<String> currencies = FXCollections.observableArrayList("EUR", "USD");

    ObservableList<String> participants = FXCollections.observableArrayList();
    ObservableList<CheckBox> splitOptions = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> name;

    @FXML
    private TextField content;

    @FXML
    private TextField amount;

    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> currency;
    @FXML
    private DatePicker date;

    @FXML
    private Label title;
    @FXML
    private Label paid;
    @FXML
    private Label what;
    @FXML
    private Label howMany;
    @FXML
    private Label when;
    @FXML
    private Label howSplit;
    @FXML
    private CheckBox everyone;
    @FXML
    private CheckBox somePeople;
    @FXML
    private Label typeL;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView<CheckBox> menu;
    @FXML
    private TextField newType;


    @Inject
    public AddExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    void initialize(Event event){
        this.event = server.getEvent(event.getId());
        if(event.getTypes() != null && event.getTypes().size() > 0) {
            types = FXCollections.observableArrayList("food", "venue", "transport", "activities", "other");
            types.addAll(event.getTypes());
        }
        type.setItems(types);
        type.setValue("other");
        currency.setItems(currencies);
        currency.setValue("EUR");
        participants = FXCollections.observableArrayList();
        splitOptions = FXCollections.observableArrayList();
        for(Participant p : event.getParticipants()){
            participants.add(p.getName());
            splitOptions.add(new CheckBox(p.getName()));
        }
        menu.setItems(splitOptions);
        name.setItems(participants);
        everyone.setSelected(true);
        somePeople.setSelected(false);
        name.setValue(" ");
        date.setValue(null);
    }

    public void initialize(Event event, Expense expense) { // initializing the data from an existing expense into the window
        this.event = server.getEvent(event.getId());
        newType.setPromptText(mainCtrl.getString("addNewType"));
        expense = server.getExpenseById(event.getId(), expense.getId());
        if(event.getTypes() != null && event.getTypes().size() > 0) {
            types = FXCollections.observableArrayList("food", "venue", "transport", "activities", "other");
            types.addAll(event.getTypes());
        }
        type.setItems(types);
        type.setValue(expense.getType());
        currency.setItems(currencies);
        currency.setValue("EUR");
        participants = FXCollections.observableArrayList();
        splitOptions = FXCollections.observableArrayList();
        for(Participant p : event.getParticipants()){
            participants.add(p.getName());
            splitOptions.add(new CheckBox(p.getName()));
        }
        name.setItems(participants);

        content.setText(expense.getTitle());
        amount.setText(Float.toString(expense.getAmount()));
        name.setValue(expense.getPayer().getName());
        date.setValue(expense.getLocalDate());

        if(expense.getSplitters().size() >= event.getParticipants().size()) everyone.setSelected(true);
        else {
            somePeople.setSelected(true);
            for(CheckBox c : splitOptions) {
                for(Participant p : expense.getSplitters()) {
                    if(p.getName().equals(c.getText())) c.setSelected(true);
                }
            }
        }
        menu.setItems(splitOptions);
    }

    public void cancel() {
        clearFields();
        expense = null;
        mainCtrl.showEventOverview(event);
    }

    public void add() {
        try {
            Expense newExpense = createExpense();
            double amountChange = newExpense.getAmount();
            if(expense != null) {
                server.editExpense(newExpense);
                amountChange = newExpense.getAmount() - expense.getAmount();
            }
            else {
                server.addExpense(newExpense);
                event.setLastAction("added expense");
            }
            this.event = server.updateEvent(event);

            for(Participant p : newExpense.getSplitters()){
                List<Debt> debts = server.getAllDebtsFromEvent(event);
                for(Debt debt : debts) {
                    if(debt.getDebtor().getId() == p.getId() && debt.getCreditor().getId() == newExpense.getPayer().getId()) {
                        debt.addAmount(amountChange / newExpense.getSplitters().size()); // dividing the amount equally between all the splitters
                        server.editDebt(debt);
                    }
                    if(debt.getCreditor().getId() == p.getId() && debt.getDebtor().getId() == newExpense.getPayer().getId()) {
                        debt.addAmount((-1.0) * amountChange / newExpense.getSplitters().size());
                        if(debt.getAmount() < 0) {
                            debt.setAmount(debt.getAmount() * (-1.0));
                            debt.setCreditor(debt.getDebtor());
                            debt.setDebtor(p);
                        }
                        server.editDebt(debt);
                    }
                }
            }
            // Possibly just replace the whole loop with a new endpoint
            // server.editDebt(server.getDebt(event, expense.getPayer(), p).addAmount(expense.getAmount()));

        } catch (Exception e) {
/*
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            if(en.equals("en"))
                alert.setContentText(e.getMessage() + "- internal error in adding the expense");
            else if(en.equals("nl"))
                alert.setContentText(e.getMessage() + "- interne fout bij het toevoegen van de kosten");
            alert.showAndWait();
 */
            ErrorMessage.showError(mainCtrl.getString("internalErrorMessage")+" "+e.getMessage(), mainCtrl);
            return;
        }

        clearFields();
        mainCtrl.showEventOverview(event);
    }

    private Expense createExpense() {
        float amount;
        LocalDate date;
        List<Participant> debtors = new ArrayList<>();
        Participant payer = null;
        String typeSelected;
        try {
            amount = Float.parseFloat(this.amount.getText());
            if(currency.getValue().equals("USD")) amount *= 0.93;
            date = this.date.getValue();
            typeSelected = type.getValue();

            for(Participant p : event.getParticipants()){
                if(p.getName().equals(name.getValue())) payer = p;
            }

            if(everyone.isSelected()){
                debtors = event.getParticipants();
            }
            else if(somePeople.isSelected()){
                for(CheckBox c : splitOptions){
                    if(c.isSelected()){
                        for(Participant p : event.getParticipants()){
                            if(p.getName().equals(c.getText())) debtors.add(p);
                        }
                    }
                }
            }
        } catch (Exception e){
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }

        if(expense != null){
            if(expense.getLocalDate() != null) date = expense.getLocalDate();
            if(expense.getSplitters() != null) debtors = expense.getSplitters();
        }
        return new Expense(event, date, payer, debtors, content.getText(), amount, typeSelected);
    }

    @FXML
    private void handleEveryone(){
        if(everyone.isSelected()){
            somePeople.setSelected(false);
        }
    }
    @FXML
    private void handleSomePeople(){
        if(somePeople.isSelected()){
            everyone.setSelected(false);
        }
    }

    private void clearFields() {
        name.setValue(" ");
        content.clear();
        amount.clear();
        currency.setValue("EUR");
        //type.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                if(!newType.getText().equals("")) {
                    event.addType(newType.getText());
                    server.editEvent(event);
                    types.add(newType.getText());
                    type.setItems(types);
                    type.setValue(newType.getText());
                    newType.clear();
                } else add();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
/*
    public void language(){
        if(en.equals("en")) en();
        else nl();
    }

    public void en(){
        title.setText("Add Expense");
        paid.setText("Who paid?");
        what.setText("What for?");
        howMany.setText("How much?");
        typeL.setText("Type:");
        addButton.setText("Add");
        cancelButton.setText("Cancel");
        when.setText("When?");
        howSplit.setText("How to split?");
        everyone.setText("With Everyone");
        somePeople.setText("Only some people");
    }
    public void nl(){
        title.setText("Kosten Toevoegen");
        paid.setText("Wie heeft betaald?");
        what.setText("Waarvoor?");
        howMany.setText("Hoe veel?");
        typeL.setText("Type:");
        addButton.setText("Toevoegen");
        cancelButton.setText("Annuleren");
        when.setText("Wanneer?");
        howSplit.setText("Hoe splitsen?");
        everyone.setText("Met iedereen");
        somePeople.setText("Slechts enkele mensen");
    }
 */
}