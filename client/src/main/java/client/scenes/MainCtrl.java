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


import commons.Expense;
import commons.Participant;
import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private StarterPageCtrl starterPageCtrl;
    private Scene starterPage;

    private InvitationCtrl invitationCtrl;
    private Scene invitation;
    private AddExpenseCtrl addExpenseCtrl;
    private Scene addExpense;
    private AdminLoginCtrl adminLoginCtrl;
    private Scene adminLogin;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private ContactDetailCtrl contactDetailCtrl;
    private Scene contactDetails;
    private ChangeServerCtrl changeServerCtrl;
    private Scene changeServer;
    private EventOverviewCtrl eventOverviewCtrl;
    private Scene eventOverview;
    private EditTitleCtrl editTitleCtrl;
    private Scene editTitle;
    private OpenDebtsNewCtrl openDebtsNewCtrl;
    private Scene openDebts;
    //private Scene scene;
    //private Parent root;


    public MainCtrl() {
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStarterPage("en");
        primaryStage.show();
    }

    public void starterPage(Pair<StarterPageCtrl, Parent> starterPage){
        this.starterPageCtrl = starterPage.getKey();
        this.starterPage = new Scene(starterPage.getValue());
    }

    public void adminLogin(Pair<AdminLoginCtrl, Parent> adminLogin){
        this.adminLoginCtrl = adminLogin.getKey();
        this.adminLogin = new Scene(adminLogin.getValue());
    }
    public void adminOverview(Pair<AdminOverviewCtrl, Parent> adminOverview){
        this.adminOverviewCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());
    }

    public void contactDetails(Pair<ContactDetailCtrl, Parent> contactDetails){
        this.contactDetailCtrl = contactDetails.getKey();
        this.contactDetails = new Scene(contactDetails.getValue());
    }

    public void addExpense(Pair<AddExpenseCtrl, Parent> addExpense){
        this.addExpenseCtrl = addExpense.getKey();
        this.addExpense = new Scene(addExpense.getValue());
    }

    public void eventOverview(Pair<EventOverviewCtrl, Parent> eventOverview){
        this.eventOverviewCtrl = eventOverview.getKey();
        this.eventOverview = new Scene(eventOverview.getValue());
    }

    public void invitation(Pair<InvitationCtrl, Parent> invitation) {
        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());
    }

    public void changeServer(Pair<ChangeServerCtrl, Parent> changeServer){
        this.changeServerCtrl = changeServer.getKey();
        this.changeServer = new Scene(changeServer.getValue());
    }

    public void editTitle(Pair<EditTitleCtrl, Parent> editTitle){
        this.editTitleCtrl = editTitle.getKey();
        this.editTitle = new Scene(editTitle.getValue());
    }

    public void setOpenDebtsNewCtrl(Pair<OpenDebtsNewCtrl, Parent> openDebtsNewCtrl){
        this.openDebtsNewCtrl = openDebtsNewCtrl.getKey();
        this.openDebts = new Scene(openDebtsNewCtrl.getValue());
    }

    public void showStarterPage(String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle("Starter Page");
        primaryStage.setScene(starterPage);
        starterPageCtrl.initialize(en);
        starterPage.setOnKeyPressed(e -> starterPageCtrl.keyPressed(e));
        //scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("high-contrast-theme.css").toExternalForm());;
    }

    public void showAdminLogin(String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(adminLogin);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        adminLoginCtrl.initialize(en);
        adminLogin.setOnKeyPressed(e -> adminLoginCtrl.keyPressed(e));
    }
    public void showAdminOverview(String en) {
        primaryStage.setTitle("Admin Overview");
        primaryStage.setScene(adminOverview);
        adminOverviewCtrl.initialize(en);
        adminOverview.setOnKeyPressed(e -> adminOverviewCtrl.keyPressed(e));
    }

    public void showAddExpense(Event event, String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle("Add Expense");
        primaryStage.setScene(addExpense);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        addExpenseCtrl.initialize(event, en);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showEditExpense(Event event, Expense expense, String en) {
        primaryStage.setTitle("Edit Expense");
        primaryStage.setScene(addExpense);
        addExpenseCtrl.setExpense(expense);
        addExpenseCtrl.initialize(event, en);
        addExpense.setOnKeyPressed(e ->addExpenseCtrl.keyPressed(e));
    }

    public void showEventOverview(Event event, String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(380);
        primaryStage.setMinHeight(450);
        primaryStage.setTitle("Create/Edit Event");
        primaryStage.setScene(eventOverview);
        primaryStage.sizeToScene();
        eventOverviewCtrl.initialize(event, en);
        eventOverview.setOnKeyPressed(e ->eventOverviewCtrl.keyPressed(e));
    }


    public void showInvitation(Event event, String en){
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(220);
        primaryStage.setTitle("Invitation");
        primaryStage.setScene(invitation);
        primaryStage.sizeToScene();
        invitationCtrl.initialize(event, en);
        invitation.setOnKeyPressed(e -> invitationCtrl.keyPressed(e));
    }

    public void showContactDetailsAdd(Event event, String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle("Add Participant");
        primaryStage.setScene(contactDetails);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        contactDetailCtrl.initialize(event, en);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showContactDetailsEdit(Participant participant, String en) {
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(Double.MIN_VALUE);
        primaryStage.setMinHeight(Double.MIN_VALUE);
        primaryStage.setTitle("Edit Participant");
        primaryStage.setScene(contactDetails);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        contactDetailCtrl.initialize(participant.getEvent(), en, participant);
        contactDetails.setOnKeyPressed(e ->contactDetailCtrl.keyPressed(e));
    }

    public void showOpenDebts(Event event, String en) {
        primaryStage.setTitle("Settle Debts");
        primaryStage.setScene(openDebts);
        openDebtsNewCtrl.initialize(event, en);
        //openDebtsNewCtrl.displayEvent();
        openDebts.setOnKeyPressed(e ->openDebtsNewCtrl.keyPressed(e));
    }

    public void showChangeServer(String en) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Change Server");
        primaryStage.setScene(changeServer);
        changeServerCtrl.initialize(en);
    }

    public void showEditTitle(Event event, String en) {
        //primaryStage.setResizable(false);
        primaryStage.setTitle("Edit Title");
        primaryStage.setScene(editTitle);
        editTitleCtrl.initialize(event, en);
        editTitle.setOnKeyPressed(e ->editTitleCtrl.keyPressed(e));
    }

    public StarterPageCtrl getStarterPageCtrl() {
        return starterPageCtrl;
    }
}